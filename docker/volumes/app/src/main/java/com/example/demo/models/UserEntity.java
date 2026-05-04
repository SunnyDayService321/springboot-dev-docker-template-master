package com.example.demo.models;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users") // MySQLのテーブル名と一致させる
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK: ユーザーID

    @Column(unique = true, nullable = false)
    private String email; // UK: ログインID

    @Column(nullable = false)
    private String password; // ハッシュ化済みパスワード

    private LocalDateTime createdAt; // 登録日時
    
    private LocalDateTime updatedAt; // 更新日時
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 削除日時 (論理削除用)
    
 // Getter (Lombok @Data で自動生成されますが、取り敢えず視覚化)
    public String getPassword() {
        return this.password;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public Long getId() {
    	return this.id;
	}
    
    public LocalDateTime getDeletedAt() {
    	return this.deletedAt;
	}
    
 // 保存前に日時を自動設定する
    @PrePersist
    protected void onCreate() {
    	this.createdAt = LocalDateTime.now();
    	this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
    	this.updatedAt = LocalDateTime.now();
    }

	public void setPassword(String password) {
		this.password = password;
		
	}

	public void setEmail(String email) {
		this.email = email;
		
	}

}
