package com.example.bitsbda.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.example.bitsbda.entity.Author;
import com.example.bitsbda.entity.Book;
import com.example.bitsbda.exception.DatabaseException;
import com.example.bitsbda.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void saveBook_returnsSavedBook() {
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.saveBook(book);

        assertThat(result).isSameAs(book);
    }

    @Test
    void saveBook_wrapsDataIntegrityViolation() {
        Book book = new Book();
        when(bookRepository.save(book)).thenThrow(new DataIntegrityViolationException("constraint"));

        assertThatThrownBy(() -> bookService.saveBook(book))
                .isInstanceOf(DatabaseException.class);
    }

    @Test
    void getAllBooksWithAuthors_returnsRepositoryResults() {
        List<Book> books = List.of(new Book());
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(books);

        List<Book> result = bookService.getAllBooksWithAuthors();

        assertThat(result).isEqualTo(books);
    }

    @Test
    void getBookById_throwsWhenMissing() {
        when(bookRepository.findById(44L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookById(44L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void updateBook_updatesFieldsOnExistingEntity() {
        Author oldAuthor = new Author("Old Author", "Old Nation");
        Author newAuthor = new Author("New Author", "New Nation");
        Book existing = new Book("Old Title", "Old Genre", oldAuthor);
        existing.setId(1L);
        Book updated = new Book("New Title", "New Genre", newAuthor);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(existing)).thenReturn(existing);

        Book result = bookService.updateBook(1L, updated);

        assertThat(result.getTitle()).isEqualTo("New Title");
        assertThat(result.getGenre()).isEqualTo("New Genre");
        assertThat(result.getAuthor()).isSameAs(newAuthor);
    }
}
