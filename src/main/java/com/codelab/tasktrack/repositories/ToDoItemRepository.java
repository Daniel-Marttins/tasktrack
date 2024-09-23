package com.codelab.tasktrack.repositories;

import com.codelab.tasktrack.entities.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {
    List<ToDoItem> findByOwnerId_Id(Long id);
    Optional<ToDoItem> findByIdAndOwnerId_Id(Long id, Long ownerId);
}
