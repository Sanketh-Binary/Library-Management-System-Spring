package com.lms.model.persistence.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lms.bean.Book;

import org.springframework.jdbc.core.RowMapper;

public class BookRowMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int id = resultSet.getInt("bookId");
		String bookName = resultSet.getString("bookName");
		int isbnNumber = resultSet.getInt("ISBNNumber");
		String authorName = resultSet.getString("bookAuthor");
		String publisherName = resultSet.getString("bookAuthor");
		String bookType = resultSet.getString("bookType");
		int bookNumber = resultSet.getInt("noOfBooks");

		Book book = new Book(id,bookName, isbnNumber, authorName, publisherName, bookType, bookNumber);
		return book;
	}
}
