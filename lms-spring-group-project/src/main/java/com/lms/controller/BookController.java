package com.lms.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.lms.bean.Book;
import com.lms.model.service.BookService;

@Controller
public class BookController {
	
	@Autowired
	private BookService bs;
	
	@RequestMapping("/returnBookInput")
    public ModelAndView inputReturnBookController() {
        return new ModelAndView("ReturnBookInput");
    }
	
	@RequestMapping("returnBook")
	public ModelAndView returnBookController(@RequestParam("tranID") int tranID) {
		boolean result =  bs.returnBook(tranID);
		String rs = result == true ? "Success Return" : "Failed During Return";
		return new ModelAndView("ReturnBookDateOutput", "message", rs);
	}
	
	////
	@RequestMapping("/checkReturnDateInput")
    public ModelAndView inputCheckReturnBook() {
        return new ModelAndView("BookReturnDateInput");
    }
	
	@RequestMapping("checkReturnDate")
	public ModelAndView  getReturnDateController(@RequestParam("employeeId") int empID, 
			@RequestParam("bookId") int bookId ) {
		String result =  bs.checkDueReturnDate(empID, bookId);
		
		return new ModelAndView("ReturnBookDateOutput", "message", result);
	}	
	////
	
	
	@RequestMapping("issueBookForEmployee")
	public ModelAndView  issueBookController() {
		
		boolean result = bs.issueBook(0, 0, null); //Please fill
		String rs = result == true ? "Book Issued" : "Failure";
		return new ModelAndView("IssueBook", "message", rs);
	}
	
	@RequestMapping("showBooks")
	public ModelAndView  showBook() {
		
		List<Book> bookList= bs.getAllBooks();
		return new ModelAndView("ShowAllBooks", "bookList", bookList);
	}
	
	@RequestMapping("addBook")
	public ModelAndView addBook() {
		
		return new ModelAndView("inputBookDetails","book",new Book());
	}
	
	@RequestMapping("/saveBook")
	public ModelAndView saveBook(@ModelAttribute("book") Book book) {
        ModelAndView modelAndView = new ModelAndView();

        String message = null;
        if (bs.addBook(book))
            message = "Book Addded Successfully";
        else
            message = "Book Addition Failed";

        modelAndView.addObject("message", message);
        modelAndView.setViewName("Output2");

        return modelAndView;
    }

	
	 @RequestMapping("/inputBookNamePageForDelete")
	    public ModelAndView inputEmpIdPageForDeleteController() {
	        return new ModelAndView("InputBookName","command",new Book());
	    }
	    

	    @RequestMapping("/removeBook")
	    public ModelAndView deleteEmployeeController(@ModelAttribute("command") Book book) {
	        ModelAndView modelAndView = new ModelAndView();

	        String message = "";
	        if (bs.removeBook(book.getBookName()))
	            message = "Book with Name " + book.getBookName() + " Deleted !";
	        else
	            message = "Book with Name " + book.getBookName() + " Does not exist !";

	        modelAndView.addObject("message", message);
	        modelAndView.setViewName("Output2");

	        return modelAndView;
	    }
	    @RequestMapping("/inputBookNameForSearch")
	    public ModelAndView inputEmpIdPageForSearchController() {
	        return new ModelAndView("InputBookName2","command",new Book());
	    }
	    
	    @RequestMapping("/searchBook")
	    public ModelAndView searchBookController(@RequestParam("bookName") String name) {
	        ModelAndView modelAndView = new ModelAndView();

	        Book book = bs.searchBook(name);
	        if (book != null) {
	            modelAndView.addObject("book", book);
	            modelAndView.setViewName("ShowBook");
	        } else {
	            String message = "Book with Name " + name + " does not exist!";
	            modelAndView.addObject("message", message);
	            modelAndView.setViewName("Output2");
	        }
	        return modelAndView;
	    }    
}
