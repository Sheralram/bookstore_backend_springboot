package com.bookstore.app.service;

import com.bookstore.app.entity.BookEntity;
import com.bookstore.app.entity.UserEntity;
import com.bookstore.app.entity.Wishlist;
import com.bookstore.app.repository.BookRepository;
import com.bookstore.app.repository.UserRepository;
import com.bookstore.app.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {
	
	@Autowired
	WishlistRepository wishlistRepository;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	public String add(int userId, int bookId) {
		BookEntity book= bookRepo.getById(bookId);
		UserEntity user = userRepo.getById(userId);
		Wishlist wishlist = wishlistRepository.findByUserAndBook(user, book);
		if(wishlist==null && book !=null && user!=null) {
			wishlist= new Wishlist(user, book);
			wishlistRepository.save(wishlist);
		}
		else {
			return "Invalid... Not Added";
		}
		return "Added";
	}
	
	public List<BookEntity> getList(int userId){
		UserEntity user = userRepo.getById(userId);
		List<Wishlist> wishlist= wishlistRepository.findByUser(user);
		List<BookEntity> booklist= wishlist.stream().map(n-> n.getBook()).collect(Collectors.toList());
		return booklist;
	}
	
	public String delete(int userId, int bookId) {
		BookEntity book= bookRepo.getById(bookId);
		UserEntity user = userRepo.getById(userId);
		Wishlist wish= wishlistRepository.findByUserAndBook(user, book);
		if(wish!=null && book !=null && user!=null) {
			wishlistRepository.delete(wish);
			return "Updated";
		}
		return "Invalid";
	}
}
