package org.lion.todo.todo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.lion.todo.config.domain.BaseTimeDomain;
import org.lion.todo.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "title", "description", "isDone"})
@Table(name = "todos")
public class Todo extends BaseTimeDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;

    private boolean isDone;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
