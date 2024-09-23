package com.codelab.tasktrack.services;

import com.codelab.tasktrack.entities.ToDoItem;
import com.codelab.tasktrack.entities.User;
import com.codelab.tasktrack.exceptions.ToDoException;
import com.codelab.tasktrack.exceptions.UserException;
import com.codelab.tasktrack.repositories.ToDoItemRepository;
import com.codelab.tasktrack.repositories.UserRepository;
import com.codelab.tasktrack.types.Status;
import com.codelab.tasktrack.usecases.SystemUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ToDoItemService {

    @Autowired
    private ToDoItemRepository doItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ToDoItem saveToDoItem(Long ownerId, ToDoItem item) {
        int quantityToDo = toDoQuantity(ownerId).size();
        item.setTaskStatus(Status.TODO);
        item.setUid(quantityToDo > 0 ? quantityToDo + 1L : 1L);
        item.setCreateAt(LocalDateTime.now());
        item.setUpdateAt(LocalDateTime.now());
        return doItemRepository.save(item);
    }

    @Transactional
    public ToDoItem getToDoItem(Long id, Long ownerId) {
        Optional<ToDoItem> toDoItem = doItemRepository.findByIdAndOwnerId_Id(id, ownerId);

        if(toDoItem.isEmpty()) throw new ToDoException
                .ToDoNotFoundException("To-do-item ID " + id + " not found!");

        return toDoItem.get();
    }

    @Transactional
    public List<ToDoItem> getAllToDoItems(Long id) {
        if(userRepository.findById(id).isEmpty()) throw new UserException.UserNotFoundException("User not found!");
        return doItemRepository.findByOwnerId_Id(id);
    }

    @Transactional
    public ToDoItem updateToDo(Long id, ToDoItem updateToDo) {
        return doItemRepository.findById(id)
                .map(existingToDo -> {
                    updateToDo.setUpdateAt(LocalDateTime.now());
                    updateToDo.setOwnerId(existingToDo.getOwnerId());
                    SystemUtils.modelMapper.map(updateToDo, existingToDo);
                    return doItemRepository.save(existingToDo);
                }).orElseThrow(() -> new ToDoException
                        .ToDoNotFoundException("To-do-item ID " + id + " not found!")
                );
    }

    @Transactional
    public void deleteToDoItem(Long id) {
        ToDoItem item = doItemRepository.findById(id).orElseThrow(() -> new ToDoException
                .ToDoNotFoundException("To-do-item ID " + id + " not found!")
        );
        doItemRepository.delete(item);
    }

    @Transactional
    private List<ToDoItem> toDoQuantity(Long id) {
        return doItemRepository.findByOwnerId_Id(id);
    }

}
