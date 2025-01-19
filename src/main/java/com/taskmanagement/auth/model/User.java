package com.taskmanagement.auth.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.taskmanagement.tasks.model.Task;


//將 User 類別標記為一個 JPA 實體類，它將對應到資料庫中的表
@Entity
//資料表名稱是 users
@Table(name = "users")
//Lombok 庫，會自動生成所有屬性的 getter 和 setter 方法。這樣可以避免手動撰寫冗長的 getter 和 setter 方法
@Getter
@Setter
//自動生成一個無參構造函數，這是 JPA 所要求的
@NoArgsConstructor
//生成一個包含所有屬性的參數化構造函數
@AllArgsConstructor
public class User {

    //每個 User 實體都會有一個唯一的 id(主鍵)，生成主鍵的策略是自動生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //欄位為不可為 null 且必須唯一
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String role;

    //一個 User 實體可以擁有多個 Task 實體
    //關聯是由 Task 實體中的 user 屬性來維護的
    //LAZY只有在需要時才會查詢相關的 taskList
    //對 User 實體進行操作時，對應的 Task 也會進行相應的操作（如保存、刪除等）
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //防止在返回給前端的 JSON 內容中，taskList 屬性會被不小心序列化，從而避免無窮的遞歸（例如 User 有 taskList，每個 Task 又指向 User
    @JsonIgnore
    private List<Task> taskList;
    


    //Constructor
    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    //返回用戶的 taskList，當 taskList 為 null 時，初始化為空列表，避免 NullPointerException
    public List<Task> getTaskList() {
        if (taskList == null) {
            taskList = new ArrayList<>();
        }
        return taskList;
    }
}


