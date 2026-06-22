package org.lion.minirestapi.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.lion.minirestapi.base.domain.BaseTimeEntity;
import org.lion.minirestapi.post.domain.Post;
import org.lion.minirestapi.comment.dto.CommentUpdateDTO;
import org.lion.minirestapi.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"post", "user", "parent", "children"}) // ***무한 루프 방지
@Builder
@Table(name = "comments")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;

    @Column(nullable = false, length = 300)
    private String comment;

    @Column(nullable = false)
    @Builder.Default
    private boolean activate=true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> children = new ArrayList<>();

    public void updateComment(CommentUpdateDTO commentUpdateDTO) {
        this.comment = commentUpdateDTO.comment();
    }

    public void deactivate() {
        this.activate=false;
    }

}
