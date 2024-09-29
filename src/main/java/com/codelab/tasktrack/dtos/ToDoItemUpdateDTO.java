package com.codelab.tasktrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoItemUpdateDTO {
    private String title;
    private String taskStatus;
    private String description;
    private boolean favorite;
    private String color;
    private LocalDateTime updateAt;
}
