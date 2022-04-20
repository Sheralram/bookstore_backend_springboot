package com.bookstore.app.service;

import com.bookstore.app.dto.ResponseDto;
import com.bookstore.app.entity.BookEntity;
import com.bookstore.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class BookService {

	private static final String BOOK_DETAILS_ADDED = "Book added";

	@Autowired
	private BookRepository bookRepository;
	String line="";

	public BookEntity getById(int id){
		if(bookRepository.getById(id)!=null) {
			return bookRepository.getById(id);
		}else {
			return null;

		}
	}

	public List<BookEntity> getAllList(){
		return bookRepository.findAll();
	}
	
	public BookEntity addBook(BookEntity bookEntity) {
		bookRepository.save(bookEntity);
		return bookEntity;
	}
	
	public List<BookEntity> bookSearch(String name){
		if(bookRepository.findAllByNameStartsWith(name)!=null) {
			return bookRepository.findAllByNameStartsWith(name);
		}else {
			return null;
			
		}
	}
	public ResponseDto saveBookData() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new
					FileReader("src/main/resources/books_data.csv"));
			while ((line = bufferedReader.readLine()) != null) {
				String[] data = line.split(",");
				BookEntity bookEntity = new BookEntity();
				bookEntity.setAuthor(data[1].replaceAll("'", ""));
				bookEntity.setName(data[2].replaceAll("'", ""));
				bookEntity.setQuantity(Integer.parseInt(data[3]));
				bookEntity.setImg(data[4]);
				bookEntity.setPrice(Integer.parseInt(data[5]));
				IntStream.range(7, data.length - 1).forEach(column -> data[6] += "," + data[column]);
				bookEntity.setDescription(data[6]);
				bookRepository.save(bookEntity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseDto(BOOK_DETAILS_ADDED, null);
	}

}
