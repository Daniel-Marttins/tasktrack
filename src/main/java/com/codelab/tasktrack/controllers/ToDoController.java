package com.codelab.tasktrack.controllers;

import com.codelab.tasktrack.dtos.ToDoItemUpdateDTO;
import com.codelab.tasktrack.entities.ToDoItem;
import com.codelab.tasktrack.entities.User;
import com.codelab.tasktrack.exceptions.ToDoException;
import com.codelab.tasktrack.exceptions.UserException;
import com.codelab.tasktrack.services.ToDoItemService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/to-do")
public class ToDoController {

    @Autowired
    private ToDoItemService doItemService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createToDo(@RequestParam(value = "id") Long id, @RequestBody ToDoItem toDoItem) {
        try {
            ToDoItem item = doItemService.saveToDoItem(id, toDoItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating to-do-item: " + e.getMessage());
        }
    }

    @GetMapping(value = "/find")
    public ResponseEntity<?> getToDoById(@RequestParam(value = "id") Long id, @RequestParam(value = "ownerId") Long ownerId) {
        try {
            ToDoItem item = doItemService.getToDoItem(id, ownerId);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (ToDoException.ToDoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when searching to-do-item: " + e.getMessage());
        }
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<?> getAllToDoItems(@RequestParam(value = "id") Long id) {
        try {
            List<ToDoItem> items = doItemService.getAllToDoItems(id);
            return ResponseEntity.status(HttpStatus.OK).body(items);
        } catch (UserException.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when searching to-do-items: " + e.getMessage());
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateToDo(@RequestParam(value = "id") Long id, @RequestBody ToDoItemUpdateDTO toDoItem) {
        try {
            ToDoItem item = doItemService.updateToDo(id, toDoItem);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (ToDoException.ToDoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update to-do-item: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating to-do-item: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteToDo(@RequestParam(value = "id") Long id) {
        try {
            doItemService.deleteToDoItem(id);
            return ResponseEntity.status(HttpStatus.OK).body("To-do-item deleted successfully!");
        } catch (ToDoException.ToDoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete to-do-item: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when deleting to-do-item: " + e.getMessage());
        }
    }

}
