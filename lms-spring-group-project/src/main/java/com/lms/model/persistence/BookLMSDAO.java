package com.lms.model.persistence;

import java.util.List;

import com.lms.bean.Book;

public interface BookLMSDAO {
	
    boolean addBook(Book book);
    
    int removeBook(String bookName);
    
    Book searchBook(String bookName);
    
     List<Book> getAllBooks();
     
    boolean issueBook(int transactionId,int bookId,String issueDate,String ScheduledDate);
    
	String checkReturnDate(int empID, int bookID);
	
	boolean returnBook(int transctionID);
}
