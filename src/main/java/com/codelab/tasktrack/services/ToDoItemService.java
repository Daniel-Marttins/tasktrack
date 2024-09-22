package com.codelab.tasktrack.services;

import com.codelab.tasktrack.entities.ToDoItem;
import com.codelab.tasktrack.exceptions.ToDoException;
import com.codelab.tasktrack.repositories.ToDoItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoItemService {

    @Autowired
    private ToDoItemRepository doItemRepository;

    @Transactional
    public ToDoItem saveToDoItem(ToDoItem item) {
        return doItemRepository.save(item);
    }

    @Transactional
    public ToDoItem getToDoItem(Long id) {
        if(doItemRepository.findById(id).isEmpty()) throw new ToDoException
                .ToDoNotFoundException("To-do-item ID " + id + " not found!");
        return doItemRepository.findById(id).get();
    }

    @Transactional
    public List<ToDoItem> getAllToDoItems(Long id) {
        return doItemRepository.findByOwnerId_Id(id);
    }

    @Transactional
    public void deleteToDoItem(Long id) {
        ToDoItem item = doItemRepository.findById(id).orElseThrow(() -> new ToDoException
                .ToDoNotFoundException("To-do-item ID " + id + " not found!")
        );
        doItemRepository.delete(item);
    }

}
