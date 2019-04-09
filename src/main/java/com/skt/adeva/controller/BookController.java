package com.skt.adeva.controller;

import com.skt.adeva.model.Book;
import com.skt.adeva.model.CreateResponse;
import com.skt.adeva.model.Response;
import com.skt.adeva.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/v1/books", method = RequestMethod.POST)
    public ResponseEntity<Response> saveBook(@RequestBody Book book) {
        logger.info("Creating Book : {}", book);

        //TODO Add any validation if required. For Now assuming no validation.
        Book dbBook = bookService.save(book);
        Response response = new Response();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setStatus("success");
        List<CreateResponse> data = new ArrayList<>(1);
        CreateResponse createResponse = new CreateResponse();
        createResponse.setBook(dbBook);
        data.add(createResponse);
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/v1/books", method = RequestMethod.GET)
    public ResponseEntity<Response> getBooks() {
        logger.info("Get All Book : ");

        List<Book> dbBooks = bookService.getAll();
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus("success");
        List<Book> data = new ArrayList<>(dbBooks);
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/books/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> getBooks(@PathVariable(name = "id") Long id) {
        logger.info("Get Book with Id: {}", id);

        Book dbBook = bookService.findById(id);
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus("success");
        if (dbBook != null) {
            response.setData(dbBook);
        } else {
            response.setMessage("Book not found with Id: " + id);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/v1/books/{id}", "/v1/books/{id}/update"}, method = RequestMethod.PATCH)
    public ResponseEntity<Response> updateBook(@PathVariable(name = "id") Long id, @RequestBody Book book) {
        logger.info("Updating Book: {} for Book Id: {}", book, id);

        //TODO Add any validation if required. For Now assuming no validation.
        Book dbBook = bookService.update(id, book);
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus("success");
        response.setData(dbBook);
        response.setMessage("The book " + dbBook.getOldBookName() + " was updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/v1/books/{id}", "/v1/books/{id}/delete"}, method = RequestMethod.DELETE)
    public ResponseEntity<Response> deleteBook(@PathVariable(value = "id") long id) {
        logger.info("Deleting Book for Book Id: {}", id);

        //TODO Add any validation if required. For Now assuming no validation. Might get NullPointer Exception if we search non existing id.
        Book dbBook = bookService.delete(id);
        Response response = new Response();
        response.setStatusCode(HttpStatus.NO_CONTENT.value());
        response.setStatus("success");
        response.setMessage("The book " + dbBook.getName() + " was deleted successfully");
        response.setData(new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/external-books", method = RequestMethod.GET)
    public ResponseEntity<Response> getExternalBooks(@RequestParam(value = "nameOfABook") String nameOfABook) {
        logger.info("Get External Books for Book Name: {}", nameOfABook);

        final String uri = "https://www.anapioficeandfire.com/api/books?name=" + nameOfABook;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Chrome");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<Book[]> result = restTemplate.exchange(uri, HttpMethod.GET, entity, Book[].class);

        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus("success");
        response.setData(Arrays.asList(result.getBody()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }
}
