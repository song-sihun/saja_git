package org.lion.minirestapi.post.domain;

import jakarta.persistence.*;
import lombok.*;
import org.lion.minirestapi.base.domain.BaseTimeEntity;
import org.lion.minirestapi.comment.domain.Comment;
import org.lion.minirestapi.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "comments")
@Table(name = "posts")
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private boolean activate=true;

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.content = description;
    }

    public void deactivate() {
        this.activate=false;
    }

}
