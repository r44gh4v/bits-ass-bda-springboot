package com.example.bitsbda.config;

import com.example.bitsbda.entity.Author;
import com.example.bitsbda.entity.Book;
import com.example.bitsbda.repository.AuthorRepository;
import com.example.bitsbda.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public DataInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (authorRepository.count() > 0 || bookRepository.count() > 0) {
            return;
        }

        List<Author> authors = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            authors.add(new Author("Author " + i, "Nationality " + i));
        }

        authors = authorRepository.saveAll(authors);

        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Author author = authors.get(i - 1);
            Book book = new Book("Book Title " + i, "Genre " + i, author);
            author.getBooks().add(book);
            books.add(book);
        }

        bookRepository.saveAll(books);
    }
}
