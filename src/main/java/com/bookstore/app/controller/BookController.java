package com.bookstore.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.app.dto.BookDto;
import com.bookstore.app.dto.ResponseDto;
import com.bookstore.app.entity.BookEntity;
import com.bookstore.app.service.BookService;


@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/book")
@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	
	@RequestMapping("/")
	public ResponseEntity<ResponseDto> getAllBookList(){
		ResponseDto responseDto = new ResponseDto("List Of Books", bookService.getAllList());
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}
	
	
	@RequestMapping("/{id}")
	public ResponseEntity<ResponseDto> getBookById(@PathVariable int id){
		ResponseDto responseDto = new ResponseDto("Book:", bookService.getById(id));
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<ResponseDto> addBook(@RequestBody BookDto bookDto){
		BookEntity bookEntity= new BookEntity(bookDto);
		ResponseDto responseDto = new ResponseDto("Book Added", bookService.addBook(bookEntity));
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}
	
	@RequestMapping("/search/{name}")
	public ResponseEntity<ResponseDto> searchBookByName(@PathVariable String name){
		ResponseDto responseDto = new ResponseDto("Book:", bookService.bookSearch(name));
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}

	@GetMapping(value = "/fetch-details")
	public ResponseEntity<ResponseDto> saveBookDataFromCSV() {
		return new ResponseEntity<ResponseDto>(bookService.saveBookData(), HttpStatus.OK);
	}
}
