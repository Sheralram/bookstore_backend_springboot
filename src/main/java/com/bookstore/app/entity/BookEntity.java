package com.bookstore.app.entity;

import com.bookstore.app.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="Book") 
@NoArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class BookEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	private String author;
	private int price;
	private int markedPrice;
	private double rating;
	private String img;
	private int quantity;
	
	@Column(length = 10000)
	private String description;

	public BookEntity(BookDto bookDto){
		super();
		this.id=id;
		this.name=bookDto.getName();
		this.author=bookDto.getAuthor();
		this.price=bookDto.getPrice();
		this.rating=bookDto.getRating();
		this.img=bookDto.getImg();
		this.description=bookDto.getDescription();
		this.quantity=bookDto.getQuatity();
		this.markedPrice=bookDto.getMarkedPrice();
	}
	
	
	public BookEntity(int id, BookDto bookDto){
		super();
		this.id=id;
		this.name=bookDto.getName();
		this.author=bookDto.getAuthor();
		this.price=bookDto.getPrice();
		this.rating=bookDto.getRating();
		this.img=bookDto.getImg();
		this.description=bookDto.getDescription();
		this.quantity=bookDto.getQuatity();
	}
	
}
