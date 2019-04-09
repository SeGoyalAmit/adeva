package com.skt.adeva.controller;

import com.skt.adeva.model.Book;
import com.skt.adeva.model.Response;
import com.skt.adeva.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/v1/books", method = RequestMethod.POST)
    public ResponseEntity<Response> saveBook(@RequestBody Book book) {
        logger.info("Creating Book : {}", book);

        //TODO Add any validation if required. For Now assuming no validation.
        Book dbBook = bookService.save(book);
        Response response = new Response();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setStatus("success");
        response.getData().add(dbBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/v1/books", method = RequestMethod.GET)
    public ResponseEntity<Response> getBooks() {
        List<Book> dbBooks = bookService.getAll();
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus("success");
        response.getData().addAll(dbBooks);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/v1/books/{id}", "/v1/books/{id}/update"}, method = RequestMethod.PATCH)
    public ResponseEntity<Response> updateBook(@PathVariable(name = "id") Long id, @RequestBody Book book) {
        Book dbBook = bookService.update(id, book);
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus("success");
        response.getData().add(dbBook);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/v1/books/{id}", "/v1/books/{id}/delete"}, method = RequestMethod.DELETE)
    public ResponseEntity<Response> deleteBook(@PathVariable(value = "id") long id) {
        bookService.delete(id);
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus("success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
