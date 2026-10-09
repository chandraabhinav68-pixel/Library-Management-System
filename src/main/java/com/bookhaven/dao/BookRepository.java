package com.bookhaven.dao;

import com.bookhaven.model.Book;
import java.util.List;

public interface BookRepository extends LibraryRepository<Book> {
    List<Book> search(String query);
    Book create(String title, String author, String isbn, String category, int totalCopies);
    long count();
    long countAvailable();
}
