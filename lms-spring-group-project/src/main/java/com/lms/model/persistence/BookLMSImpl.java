package com.lms.model.persistence;

import com.lms.bean.Book;
import com.lms.model.persistence.helper.BookRowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookLMSImpl implements BookLMSDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	String pass = "root";

	@Override
	public boolean addBook(Book book) {
		int rows = 0;

		int rand = 0;
		int max = 10000;
		int min = 20000;
		int range = max - min + 1;
		rand = (int) (Math.random() * range) + min;

		String query = "INSERT INTO BOOK values(?,?,?,?,?,?,?)";

		rows = jdbcTemplate.update(query, rand, book.getBookName(), book.getIsbnNumber(), book.getAuthorName(),
				book.getPublisherName(), book.getBookType(), book.getBookNumber());

		return rows > 0;
	}

	@Override
	public int removeBook(String bookName) {
		int rows = 0;
	String query = "DELETE FROM book WHERE bookName=?";
    	 rows = jdbcTemplate.update(query, bookName);
		return rows;
	}

	@Override
	public Book searchBook(String name) {
		Book book = null;
		try {
    		String query="SELECT * FROM book  WHERE bookName=?";
    		book=jdbcTemplate.queryForObject(query, new BookRowMapper(), name);
    		}
    		catch(EmptyResultDataAccessException ex) {
    			return book;
    		}
		return book;
	}

	@Override
	public boolean issueBook(int employeeId, int bookId, String issueDate, String ScheduledDate) {
		int rows = 0, rows1 = 0, row2 = 0;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lms", "root", pass);
				PreparedStatement preparedStatement1 = connection
						.prepareStatement("{call insertIntoTransactions(?,?)}");
				PreparedStatement preparedStatement2 = connection
						.prepareStatement("update employee set booksIssued = booksIssued+1 where employeeId =?;");
				PreparedStatement preparedStatement = connection.prepareStatement("{call insertIntoTBR(?,?,?,?)}");) {

			int f = 0;
			int rand = 0;
			while (f == 0) {
				int max = 10000;
				int min = 20000;
				int range = max - min + 1;
				rand = (int) (Math.random() * range) + min;
				f = 1;
			}
			preparedStatement1.setInt(1, rand);
			preparedStatement1.setInt(2, employeeId);
			rows1 = preparedStatement1.executeUpdate();

			preparedStatement2.setInt(1, employeeId);
			row2 = preparedStatement2.executeUpdate();

			preparedStatement.setString(3, issueDate);
			preparedStatement.setInt(1, bookId);
			preparedStatement.setString(4, ScheduledDate);
			preparedStatement.setInt(2, rand);

			rows = preparedStatement.executeUpdate();

			System.out.println(rows);
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return rows > 0 && rows1 > 0 && row2 > 0;
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> bookList = new ArrayList<>();
		String query="SELECT * FROM BOOK";
		 bookList = jdbcTemplate.query(query, new BookRowMapper());
		
		return bookList;
	}

	@Override
	public String checkReturnDate(int empID, int bookId) {
		StringBuilder sb = new StringBuilder();

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lms", "root", pass);
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT scheduledReturn FROM employee INNER JOIN transations using(employeeID) "
								+ "INNER JOIN tbr using(transationId) " + "WHERE bookId = ? AND employeeID = ?")) {
			preparedStatement.setInt(1, bookId);
			preparedStatement.setInt(2, empID);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				
				Date date = resultSet.getDate("scheduledReturn");
				System.out.println(date);
				sb.append("EmpID: ").append(empID).append("| ");
				sb.append("Book ID: ").append(bookId).append("| ");
				sb.append("Return Date: ").append(date.toString()).append("\n");

			}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return sb.toString();
	}

	@Override
	public String returnBook(int transactionID) {
		int r1 = 0;
		int rs1 = 0;
		String r2 = "";
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lms", "root", pass)) {
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE tbr SET returnDate = NOW() WHERE transationID = ?");
			preparedStatement.setInt(1, transactionID);
			PreparedStatement ps1 = connection
					.prepareStatement("UPDATE employee SET booksIssued = booksIssued - 1 WHERE employeeId = "
							+ "(SELECT employeeID FROM transations WHERE transationID = ?)");
			ps1.setInt(1, transactionID);
			rs1 = ps1.executeUpdate();
			r1 = preparedStatement.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * from tbr where transationId = ?");
            preparedStatement2.setInt(1, transactionID);
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            int bookId = 0;
            if (resultSet2.next()) {
                bookId = resultSet2.getInt("bookId");
            }
//
            if (bookId > 0) {
                r2 = r2 + "" + calculateFine(transactionID, bookId);
            }

            PreparedStatement preparedStatement3 = connection.prepareStatement("UPDATE book SET noOfBooks = noOfBooks + 1 WHERE "
                    + "bookID = ?");
            preparedStatement3.setInt(1, bookId);
            preparedStatement3.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return r2;
	}

	private double calculateFine(int transactionId, int bookID) {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lms", "root", pass)) {
			
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT issueDate, returnDate FROM tbr where transationId = ?");
			
			preparedStatement.setInt(1, transactionId);
			
			ResultSet resultSet = preparedStatement.executeQuery();
		
			java.sql.Date issueDate = null;
			java.sql.Date returnDate = null;
			
			if(resultSet.next()) {
				issueDate = resultSet.getDate("issueDate");
				returnDate = resultSet.getDate("returnDate");
			}

			PreparedStatement diff = connection.prepareStatement("SELECT DATEDIFF(?, ?) AS diff");
			
			diff.setString(1, String.valueOf(returnDate));
			diff.setString(2, String.valueOf(issueDate));
			
			ResultSet days_rs = diff.executeQuery();

			int days = -1;
			if (days_rs.next()) {
				days = days_rs.getInt("diff");
			}

			PreparedStatement preparedStatement1 = connection
					.prepareStatement("SELECT bookType from book where " + "bookId = ?");
			
			preparedStatement1.setInt(1, bookID);
			ResultSet rs = preparedStatement1.executeQuery();
			
			String bookType = null;
			System.out.println("-------------------------------------------->>>>>>>>>>>>>>>>>" + days);
			if (rs.next()) {
				bookType = rs.getString("bookType");
			}

			if (days > 7) {
				
				if(bookType.compareToIgnoreCase("Data Analytics") == 0) {
					return 5 * (days - 7);
				}
				else if (bookType.compareToIgnoreCase("Technology") == 0) {
					return 6 * (days - 7);
				}
				else if (bookType.compareToIgnoreCase("Management") == 0) {
					return 7 * (days - 7);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
