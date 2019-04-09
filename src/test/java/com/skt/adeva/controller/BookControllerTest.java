package com.skt.adeva.controller;

import com.skt.adeva.model.Book;
import com.skt.adeva.model.CreateResponse;
import com.skt.adeva.model.Response;
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

import java.sql.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    private MockMvc mockMvc;

    @Mock
    private BookController bookController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private BookRepository bookRepository;

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
        Book dbbook = null;
        try {
            ResponseEntity<Response> response = template.postForEntity("/api/v1/books", book, Response.class);

            Assert.assertEquals(201, response.getStatusCodeValue());
            Assert.assertNotNull(response.getBody());
            Response responseBody = response.getBody();
            List<CreateResponse> data = (List<CreateResponse>) responseBody.getData();
            dbbook = data.get(0).getBook();
            Assert.assertEquals("Test Book", dbbook.getName());
            Assert.assertEquals("123-3213243567", dbbook.getIsbn());
            Assert.assertEquals(350, dbbook.getNumberOfPages());
            Assert.assertEquals("Test Books", dbbook.getPublisher());
            Assert.assertEquals("United States", dbbook.getCountry());
            Assert.assertEquals(Date.valueOf("2019-08-01"), dbbook.getReleased());
            Assert.assertArrayEquals(new String[]{"Test Author"}, dbbook.getAuthors());

        } finally {
            //cleanup the user
            if (dbbook != null) {
                bookRepository.deleteById(dbbook.getId());
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
        Book dbbook = null;
        Book dbbook1 = null;
        try {
            ResponseEntity<Response> response = template.postForEntity("/api/v1/books", book, Response.class);
            Assert.assertEquals(201, response.getStatusCodeValue());
            Response responseBody = response.getBody();
            List<CreateResponse> data = (List<CreateResponse>) responseBody.getData();
            dbbook = data.get(0).getBook();

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
            ResponseEntity<Response> response1 = template.postForEntity("/api/v1/books", book, Response.class);
            Assert.assertEquals(201, response1.getStatusCodeValue());
            responseBody = response1.getBody();
            data = (List<CreateResponse>) responseBody.getData();
            dbbook1 = data.get(0).getBook();

            ResponseEntity<Response> getResponse = template.getForEntity("/api/v1/books", Response.class);
            Assert.assertEquals(200, getResponse.getStatusCodeValue());
            Assert.assertNotNull(getResponse.getBody());

            List<Book> dbBooks = (List<Book>) getResponse.getBody().getData();
            Assert.assertEquals(2, dbBooks.size());
        } finally {
            //cleanup the user
            if (dbbook != null) {
                bookRepository.deleteById(dbbook.getId());
            }
            if (dbbook1 != null) {
                bookRepository.deleteById(dbbook1.getId());
            }
        }
    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }
}
