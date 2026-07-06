package org.lion.minirestapi.post.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.base.handler.ErrorString;
import org.lion.minirestapi.base.handler.PostNotFoundException;
import org.lion.minirestapi.comment.repository.CommentRepository;
import org.lion.minirestapi.post.domain.Post;
import org.lion.minirestapi.post.dto.PostCreateDTO;
import org.lion.minirestapi.post.dto.PostDetailResponseDTO;
import org.lion.minirestapi.post.dto.PostListResponseDTO;
import org.lion.minirestapi.post.dto.PostUpdateDTO;
import org.lion.minirestapi.post.repository.PostRepository;
import org.lion.minirestapi.user.domain.Role;
import org.lion.minirestapi.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ImageStorageService imageStorageService;

    @Transactional
    public PostDetailResponseDTO createPost(PostCreateDTO postCreateDTO, User loginUser) {
        Post post = Post.builder()
                .title(postCreateDTO.title())
                .content(postCreateDTO.content())
                .category(postCreateDTO.category())
                .user(loginUser)
                .build();
        return PostDetailResponseDTO.fromEntity(postRepository.save(post));
    }

    public Page<PostListResponseDTO> findAll(@NonNull Pageable pageable) {
        return postRepository.findAllByActivateTrue(pageable).map(PostListResponseDTO::fromEntity);
    }

    public Page<PostDetailResponseDTO> findAllAdmin(User loginUser, @NonNull Pageable pageable) {
        if (!isAdmin(loginUser)) {
            throw new AccessDeniedException(ErrorString.ACCESS_DENIED.getMessage());
        }
        return postRepository.findAll(pageable).map(PostDetailResponseDTO::fromEntity);
    }

    public PostDetailResponseDTO findPostById(@NonNull Long postId) {
        Post post = postRepository.findByIdAndActivateTrue(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorString.POST_NOT_FOUND.getMessage()));
        return PostDetailResponseDTO.fromEntity(post);
    }

    @Transactional
    public PostDetailResponseDTO uploadImage(@NonNull Long postId, @NonNull MultipartFile image, @NonNull User loginUser) {
        Post post = postRepository.findByIdAndActivateTrue(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorString.POST_NOT_FOUND.getMessage()));
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new AccessDeniedException(ErrorString.ACCESS_DENIED.getMessage());
        }
        post.updateImageUrl(imageStorageService.store(image));
        return PostDetailResponseDTO.fromEntity(post);
    }

    @Transactional
    public void deactivatePostById(@NonNull Long postId, @NonNull User loginUser) {
        Post post = postRepository.findByIdAndActivateTrue(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorString.POST_NOT_FOUND.getMessage()));
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new AccessDeniedException(ErrorString.ACCESS_DENIED.getMessage());
        }
        post.deactivate();
        commentRepository.deactivateByPostId(postId);
    }

    @Transactional
    public void deletePostById(@NonNull Long postId, @NonNull User loginUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorString.POST_NOT_FOUND.getMessage()));
        if (!post.getUser().getId().equals(loginUser.getId()) && !isAdmin(loginUser)) {
            throw new AccessDeniedException(ErrorString.ACCESS_DENIED.getMessage());
        }
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostDetailResponseDTO updatePostById(@NonNull Long postId, @NonNull PostUpdateDTO postUpdateDTO, @NonNull User loginUser) {
        Post post = postRepository.findByIdAndActivateTrue(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorString.POST_NOT_FOUND.getMessage()));
        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new AccessDeniedException(ErrorString.ACCESS_DENIED.getMessage());
        }
        post.updateTitle(postUpdateDTO.getTitle());
        post.updateDescription(postUpdateDTO.getContent());
        post.updateCategory(postUpdateDTO.getCategory());
        return PostDetailResponseDTO.fromEntity(post);
    }

    private boolean isAdmin(User user) {
        return user.getRoles().stream()
                .map(Role::getName)
                .anyMatch("ADMIN"::equals);
    }
}
