package com.codelab.tasktrack.entities;

import com.codelab.tasktrack.types.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "toDoItems")
public class ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BIGINT", nullable = false)
    private Long uid;

    @ManyToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "id")
    @JsonBackReference
    private User ownerId;

    @Column(columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status taskStatus;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String description;

    @Column()
    private boolean favorite;

    @Column(columnDefinition = "VARCHAR(100)", length = 100)
    private String color;

    @Column()
    private LocalDateTime createAt;

    @Column()
    private LocalDateTime updateAt;

}
