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

import lombok.Data;

@Data
//@Entity
//@Table(name = "item")
public class ItemForm implements Serializable {
	private static final long serialVersionUID = -6647247658748349084L;

//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	@Size(max = 10)
	private String name;

	@NotBlank
	@Size(max = 100000)
	private String price;

	@NotBlank
	@Size(max = 400)
	private String content;
	
	private boolean favorite;

//    public boolean isFavorite() {
//        return favorite;
//    }
//
//    public void setFavorite(boolean favorite) {
//        this.favorite = favorite;
//    }
//	
//	public void clear() {
//		name = null;
//		price = null;
//		content = null;
//	}
	
	public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public void clear() {
        this.id = 0;
        this.name = null;
        this.price = null;
        this.content = null;
        this.favorite = false;
    }

//	public Object getId() {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
	
//	public long getId() {
//        return id;
//    }
//    public void setId(long id) {
//        this.id = id;
//    }

//	public void delete() {
//		name = null;
//		price = null;
//		content = null;
//	}


	
}
