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
    private BookRepository bookRepository;

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
        Book updatedBook;
        Book dbBook = findById(bookId);
        if (dbBook != null) {
            if (book != null) {
                if (book.getAuthors() != null) {
                    dbBook.setAuthors(book.getAuthors());
                }
                if (book.getCountry() != null) {
                    dbBook.setCountry(book.getCountry());
                }
                if (book.getIsbn() != null) {
                    dbBook.setIsbn(book.getIsbn());
                }
                if (book.getName() != null) {
                    dbBook.setOldBookName(dbBook.getName());
                    dbBook.setName(book.getName());
                }
                if (book.getNumberOfPages() > 0) {
                    dbBook.setNumberOfPages(book.getNumberOfPages());
                }
                if (book.getPublisher() != null) {
                    dbBook.setPublisher(book.getPublisher());
                }
                if (book.getReleased() != null) {
                    dbBook.setReleased(book.getReleased());
                }
            }
            updatedBook = bookRepository.save(dbBook);
        } else {
            updatedBook = bookRepository.save(book);
        }
        return updatedBook;
    }

    @Override
    public Book delete(Long bookId) {
        Book dbBook = findById(bookId);
        if (dbBook != null) {
            bookRepository.delete(dbBook);
        }
        return dbBook;
    }
}
