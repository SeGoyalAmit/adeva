package com.skt.adeva.service;

import com.skt.adeva.model.Book;
import com.skt.adeva.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().forEach(bookList::add);
        return bookList;
    }

    public Book save(Book p) {
        return bookRepository.save(p);
    }

    @Override
    public Book findById(Long bookId) {
        Optional<Book> dbBook = bookRepository.findById(bookId);
        return dbBook.orElse(null);
    }

    @Override
    public Book update(Long bookId, Book book) {
        //TODO need to do some more tweaks
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long bookId) {
        bookRepository.delete(bookRepository.findById(bookId).get());
    }
}
