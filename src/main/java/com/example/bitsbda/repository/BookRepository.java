package com.example.bitsbda.repository;

import com.example.bitsbda.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b join fetch b.author")
    List<Book> findAllBooksWithAuthors();
}
