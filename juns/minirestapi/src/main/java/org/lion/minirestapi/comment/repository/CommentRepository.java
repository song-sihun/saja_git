package org.lion.minirestapi.comment.repository;

import org.lion.minirestapi.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByActivateAndPostId(boolean activate, Long postId, Pageable pageable);
    Page<Comment> findAllByActivateAndParentId(boolean activate, Long parentId, Pageable pageable);
    Page<Comment> findAllByActivate(boolean active, Pageable pageable);

    @Modifying
    @Query("update Comment c set c.activate = false where c.post.id = :postId and c.activate = true ")
    void deactivateByPostId(@Param("postId") Long postId);
}
