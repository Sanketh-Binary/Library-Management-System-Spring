package com.lms.model.service;

import com.lms.bean.Book;
import com.lms.model.persistence.BookLMSDAO;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
    private BookLMSDAO bookLMSDAO;

    @Override
    public Boolean addBook(Book book) {
        return bookLMSDAO.addBook(book);
    }

    @Override
    public Boolean removeBook(String bookName) {
        return bookLMSDAO.removeBook(bookName) > 0;
    }

    @Override
    public Book searchBook(String bookName) {
        return bookLMSDAO.searchBook(bookName);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookLMSDAO.getAllBooks();
    }

    @Override
    public boolean issueBook(int transactionId, int bookId, String ScheduledDate) {
        LocalDate date = LocalDate.now();
        System.out.println("Current date: " + date);
        return bookLMSDAO.issueBook(transactionId, bookId, date.toString(), ScheduledDate);
    }

    @Override
    public String checkDueReturnDate(int empID, int bookID) {
        return bookLMSDAO.checkReturnDate(empID, bookID);
    }

    @Override
    public String returnBook(int tranID) {
        return bookLMSDAO.returnBook(tranID);
    }
}
