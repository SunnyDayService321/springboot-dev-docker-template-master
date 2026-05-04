package com.example.demo.models;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item")
@Data
public class ItemEntity implements Serializable {
	private static final long serialVersionUID = -6647247658748349084L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
// フィールド
	private long id;
    private String name;
    private String price;
    private String content;
    private boolean favorite;
    
 // ゲッター（値を取得）
    public long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPrice() {
        return price;
    }
    
    public String getContent() {
        return content;
    }
    
    public boolean isFavorite() {
        return favorite;
    }
    
    
 // セッター（値を設定）
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

}
