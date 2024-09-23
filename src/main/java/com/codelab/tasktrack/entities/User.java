package com.codelab.tasktrack.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BIGINT", nullable = false)
    private Long uid;

    @Column(columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
    private String username;

    @Column(columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
    private String email;

    @Column(columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String imgUrl;

    @OneToMany(mappedBy = "ownerId", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ToDoItem> toDoItems;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createAt;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updateAt;

}
