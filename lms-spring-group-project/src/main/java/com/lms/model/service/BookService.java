package com.lms.model.service;

import com.lms.bean.Book;

import java.util.List;

public interface BookService {
    Boolean addBook(Book book);

    Boolean removeBook(String bookName);

    Book searchBook(String bookName);
    
    boolean issueBook(int transactionId,int bookId,String ScheduledDate);

    List<Book> getAllBooks();
    
	String checkDueReturnDate(int empID, int bookID);
	
	String returnBook(int tranID);
	
	
}
