package com.codelab.tasktrack.repositories;

import com.codelab.tasktrack.entities.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {
    List<ToDoItem> findByOwnerId_Id(Long id);
}
