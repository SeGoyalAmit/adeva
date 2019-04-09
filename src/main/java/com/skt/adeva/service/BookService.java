package com.skt.adeva.service;

import com.skt.adeva.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book save(Book p);

    Book findById(Long bookId);

    Book update(Long bookId, Book book);

    void delete(Long bookId);
}
