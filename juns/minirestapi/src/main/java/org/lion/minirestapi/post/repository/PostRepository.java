package org.lion.minirestapi.post.repository;

import org.jspecify.annotations.NonNull;
import org.lion.minirestapi.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByActivateTrue(@NonNull Pageable pageable);
    Page<Post> findAllByTitleContaining(String title, @NonNull Pageable pageable);
    Optional<Post> findByIdAndActivateTrue(Long id);

}
