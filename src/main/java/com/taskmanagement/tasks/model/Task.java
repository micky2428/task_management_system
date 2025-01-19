package com.taskmanagement.tasks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taskmanagement.auth.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //jakarta.validation 的註解
    @Column(nullable = false)
    @NotEmpty(message = "Invalid: task cannot be empty")
    private String task;

    //表示任務的詳細描述、完成狀態和創建時間
    private String details;
    private Boolean completed;

    @Column(name = "task_created_at", nullable = false, updatable = false)
    private LocalDateTime taskCreatedAt;

    //預定完成日期
    @Column(name = "due_date")
    private LocalDate dueDate;  

    //防止無限遞歸的情況發生，因為 User 類可能也有一個指向 Task 的引用
    @ManyToOne
    @JsonIgnore
    private User user;

    @PrePersist
    public void prePersist() {
        if (taskCreatedAt == null) {
            taskCreatedAt = LocalDateTime.now();
        }
    }

}

//Task 通常更依賴於資料庫的自動生成行為（如 id 欄位由資料庫生成），而且某些屬性（如 taskCreatedAt 和 completed）可能會在系統運行過程中被設置。因此，無需強制要求在創建 Task 時必須立即提供所有屬性。