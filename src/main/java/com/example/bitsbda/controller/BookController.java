package com.example.bitsbda.controller;

import com.example.bitsbda.entity.Book;
import com.example.bitsbda.exception.DatabaseException;
import com.example.bitsbda.service.AuthorService;
import com.example.bitsbda.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooksWithAuthors());
        return "list";
    }

    @GetMapping("/books/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "form";
    }

    @PostMapping("/books")
    public String createBook(@ModelAttribute Book book, @RequestParam("authorId") Long authorId, Model model) {
        try {
            book.setAuthor(authorService.getAuthorById(authorId));
            bookService.saveBook(book);
            return "redirect:/books";
        } catch (DatabaseException | EntityNotFoundException ex) {
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("errorMessage", ex.getMessage());
            return "form";
        }
    }

    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Book book = bookService.getBookById(id);
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.getAllAuthors());
            return "form";
        } catch (EntityNotFoundException ex) {
            return "redirect:/books";
        }
    }

    @PostMapping("/books/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book,
            @RequestParam("authorId") Long authorId, Model model) {
        try {
            book.setAuthor(authorService.getAuthorById(authorId));
            bookService.updateBook(id, book);
            return "redirect:/books";
        } catch (DatabaseException | EntityNotFoundException ex) {
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("errorMessage", ex.getMessage());
            return "form";
        }
    }
}
