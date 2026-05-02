package com.example.bitsbda.service;

import com.example.bitsbda.entity.Book;
import com.example.bitsbda.exception.DatabaseException;
import com.example.bitsbda.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Unable to save book. Please check required fields.", ex);
        }
    }

    public List<Book> getAllBooksWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public Book updateBook(Long id, Book updated) {
        Book existing = getBookById(id);
        existing.setTitle(updated.getTitle());
        existing.setGenre(updated.getGenre());
        existing.setAuthor(updated.getAuthor());

        try {
            return bookRepository.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Unable to update book. Please check required fields.", ex);
        }
    }
}
