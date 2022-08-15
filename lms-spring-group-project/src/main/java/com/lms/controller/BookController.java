package com.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lms.bean.Book;
import com.lms.bean.Employee;
import com.lms.model.service.BookService;

@Controller
public class BookController {
	
	@Autowired
	private BookService bs;
	
	

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
