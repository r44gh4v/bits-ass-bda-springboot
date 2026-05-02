package com.example.bitsbda.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bitsbda.entity.Author;
import com.example.bitsbda.entity.Book;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void findAllBooksWithAuthors_returnsJoinedResults() {
        Author author = new Author("Test Author", "Test Nation");
        author = authorRepository.save(author);

        Book book = new Book("Test Book", "Test Genre", author);
        bookRepository.save(book);

        List<Book> results = bookRepository.findAllBooksWithAuthors();

        assertThat(results).isNotEmpty();
        Book result = results.stream()
                .filter(found -> "Test Book".equals(found.getTitle()))
                .findFirst()
                .orElseThrow();
        assertThat(result.getAuthor()).isNotNull();
        assertThat(result.getAuthor().getName()).isEqualTo("Test Author");
    }
}
