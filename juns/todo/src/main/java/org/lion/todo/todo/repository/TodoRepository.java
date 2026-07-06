package org.lion.todo.todo.repository;


import org.lion.todo.todo.domain.Todo;
import org.lion.todo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUser(User user);
    List<Todo> findByUserAndIsDone(User user, boolean isDone);
}
