package com.bookstore.app.service;

import com.bookstore.app.entity.BookEntity;
import com.bookstore.app.entity.Cart;
import com.bookstore.app.entity.UserEntity;
import com.bookstore.app.helper.Bookcart;
import com.bookstore.app.repository.BookRepository;
import com.bookstore.app.repository.CartRepository;
import com.bookstore.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CartService {
    @Autowired
    CartRepository cartRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    BookRepository bookRepo;


    public String add(int userId, int bookId, int quantity) {
        BookEntity book = bookRepo.getById(bookId);
        UserEntity user = userRepo.getById(userId);
        Cart cart = cartRepo.findByUserAndBook(user, book);
        if (cart == null && book != null && user != null) {
            cart = new Cart(user, book, quantity);
            cartRepo.save(cart);
        } else if (cart != null && book != null && user != null) {
            cart.setQuantity(quantity);
            cartRepo.save(cart);

        } else {
            return "Invalid... Not Added";
        }

        return "Added";
    }

    public List<Bookcart> getList(int userId) {
        UserEntity user = userRepo.getById(userId);
        List<Cart> cartList = cartRepo.findByUser(user);
        List<Bookcart> bookList = cartList.stream().map(n -> new Bookcart(n.getBook(), n.getQuantity()))
                .collect(Collectors.toList());
        return bookList;
    }

    public List<BookEntity> getListForOrder(int userId) {
        UserEntity user = userRepo.getById(userId);
        List<Cart> cartList = cartRepo.findByUser(user);
        List<BookEntity> bookList = cartList.stream().map(n -> n.getBook()).collect(Collectors.toList());
        return bookList;
    }

    public String delete(int userId, int bookId, int quantity) {
        BookEntity book = bookRepo.getById(bookId);
        UserEntity user = userRepo.getById(userId);
        Cart cart = cartRepo.findByUserAndBook(user, book);
        if (cart != null && book != null && user != null) {
            if (quantity <= 0) {
                cartRepo.delete(cart);
            } else {
                cart.setQuantity(quantity);
                cartRepo.save(cart);
            }
            return "Updated";
        }
        return "Invalid";
    }

    public void deleteAll(UserEntity user) {
        cartRepo.deleteAllByUser(user);
    }
}
