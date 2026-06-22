package org.lion.minirestapi.comment.service;

import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.base.handler.CommentNotFoundException;
import org.lion.minirestapi.base.handler.ErrorString;
import org.lion.minirestapi.base.handler.PostNotFoundException;
import org.lion.minirestapi.post.domain.Post;
import org.lion.minirestapi.post.repository.PostRepository;
import org.lion.minirestapi.comment.domain.Comment;
import org.lion.minirestapi.comment.dto.CommentCreateDTO;
import org.lion.minirestapi.comment.dto.CommentResponseDTO;
import org.lion.minirestapi.comment.dto.CommentUpdateDTO;
import org.lion.minirestapi.comment.repository.CommentRepository;
import org.lion.minirestapi.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDTO createComment(CommentCreateDTO commentCreateDTO, User loginUser) {

        Post post = postRepository.findByIdAndActivateTrue(commentCreateDTO.postId()).orElseThrow(() -> new PostNotFoundException(ErrorString.POST_NOT_FOUND.getMessage()));

        Comment parentComment = null;

        if(commentCreateDTO.parentID() != null) {
            parentComment = commentRepository.findById(commentCreateDTO.parentID()).orElseThrow(() -> new CommentNotFoundException(ErrorString.COMMENT_NOT_FOUND.getMessage()));

            if(!parentComment.getPost().getId().equals(post.getId())) {
                throw new IllegalArgumentException("같은 게시글에 속하지 않습니다.");
            }

            if (!parentComment.isActivate()) {
                throw new IllegalArgumentException("삭제된 댓글에는 대댓글을 작성할 수 없습니다.");
            }
        }
        Comment comment = Comment.builder()
                .comment(commentCreateDTO.comment())
                .post(post)
                .user(loginUser)
                .parent(parentComment)
                .build();
//        post.addComment(comment); 불필요?

        commentRepository.save(comment);
        return CommentResponseDTO.fromEntity(comment);

    }

    public Page<CommentResponseDTO> findAllByParent(Pageable pageable, Long parentId) {
        return commentRepository.findAllByActivateAndParentId(true, parentId, pageable).map(CommentResponseDTO::fromEntity);
    }

    public Page<CommentResponseDTO> findAllByPost(Pageable pageable, Long postId) {
        return commentRepository.findAllByActivateAndPostId(true, postId, pageable).map(CommentResponseDTO::fromEntity);
    }

    @Transactional
    public CommentResponseDTO updateComment(CommentUpdateDTO commentUpdateDTO, Long commentId, User loginUser) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(ErrorString.COMMENT_NOT_FOUND.getMessage()));
        if(!comment.getUser().getId().equals(loginUser.getId())) {
            throw new AccessDeniedException(ErrorString.ACCESS_DENIED.getMessage());
        }
        comment.updateComment(commentUpdateDTO);
        return CommentResponseDTO.fromEntity(commentRepository.save(comment));
    }


    @Transactional
    public void deleteComment(Long commentId, User loginUser) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(ErrorString.COMMENT_NOT_FOUND.getMessage()));
        if(!comment.getUser().getId().equals(loginUser.getId())) {
            throw new AccessDeniedException(ErrorString.ACCESS_DENIED.getMessage());
        }
        comment.deactivate();
    }

}
