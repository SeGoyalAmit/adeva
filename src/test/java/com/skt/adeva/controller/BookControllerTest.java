package com.skt.adeva.controller;

import com.skt.adeva.model.Book;
import com.skt.adeva.repository.BookRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    MockMvc mockMvc;

    @Mock
    private BookController bookController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    BookRepository bookRepository;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void test_saveBook_bookSaveSuccessfully() throws Exception {
        HttpEntity<Object> book = getHttpEntity("{\n" +
                "\t\"name\": \"Test Book\",\n" +
                "\t\"isbn\": \"123-3213243567\",\n" +
                "\t\"authors\": [\n" +
                "\t\t\"Test Author\"\n" +
                "\t],\n" +
                "\t\"number_of_pages\": 350,\n" +
                "\t\"publisher\": \"Test Books\",\n" +
                "\t\"country\": \"United States\",\n" +
                "\t\"release_date\": \"2019-08-01\"\n" +
                "}");
        ResponseEntity<Book> response = null;
        try {
            response = template.postForEntity("/api/v1/books", book, Book.class);

            Assert.assertEquals(201, response.getStatusCode().value());
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals("Test Book", response.getBody().getName());
        } finally {
            //cleanup the user
            if (response != null && response.getBody() != null) {
                bookRepository.deleteById(response.getBody().getId());
            }
        }
    }

    @Test
    public void test_getBooks_booksFound() throws Exception {
        HttpEntity<Object> book = getHttpEntity("{\n" +
                "\t\"name\": \"Test Book\",\n" +
                "\t\"isbn\": \"123-3213243567\",\n" +
                "\t\"authors\": [\n" +
                "\t\t\"Test Author\"\n" +
                "\t],\n" +
                "\t\"number_of_pages\": 350,\n" +
                "\t\"publisher\": \"Test Books\",\n" +
                "\t\"country\": \"United States\",\n" +
                "\t\"release_date\": \"2019-08-01\"\n" +
                "}");
        ResponseEntity<Book> response = null;
        ResponseEntity<Book> response1 = null;
        try {
            response = template.postForEntity("/api/v1/books", book, Book.class);
            Assert.assertEquals(201, response.getStatusCode().value());

            book = getHttpEntity("{\n" +
                    "\t\"name\": \"Test Book 1\",\n" +
                    "\t\"isbn\": \"123-3213243567\",\n" +
                    "\t\"authors\": [\n" +
                    "\t\t\"Test Author\"\n" +
                    "\t],\n" +
                    "\t\"number_of_pages\": 350,\n" +
                    "\t\"publisher\": \"Test Books\",\n" +
                    "\t\"country\": \"United States\",\n" +
                    "\t\"release_date\": \"2019-08-01\"\n" +
                    "}");
            response1 = template.postForEntity("/api/v1/books", book, Book.class);
            Assert.assertEquals(201, response1.getStatusCode().value());

            ResponseEntity<Book[]> getResponse = template.getForEntity("/api/v1/books", Book[].class);

            Assert.assertNotNull(getResponse.getBody());
            Assert.assertEquals(200, getResponse.getStatusCode().value());
            Assert.assertEquals(2, getResponse.getBody().length);
        } finally {
            //cleanup the user
            if (response != null && response.getBody() != null) {
                bookRepository.deleteById(response.getBody().getId());
            }
            if (response1 != null && response1.getBody() != null) {
                bookRepository.deleteById(response1.getBody().getId());
            }
        }
    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }
}
