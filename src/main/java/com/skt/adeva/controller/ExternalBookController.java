package com.skt.adeva.controller;

import com.skt.adeva.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class ExternalBookController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/api/external-books", method = RequestMethod.GET)
    public ResponseEntity<Book[]> getExternalBooks(@RequestParam(value = "nameOfABook") String nameOfABook) {
        final String uri = "https://www.anapioficeandfire.com/api/books?name=" + nameOfABook;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Chrome");
        headers.add("Accept", "application/vnd.anapioficeandfire+json; version=1");
        //headers.add("Content-Type", "application/json");
        //headers.add("Content-Encoding", "gzip");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Book[]> result = restTemplate.exchange(uri, HttpMethod.GET, entity, Book[].class);

        System.out.println(result);



//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
//        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//        //ResponseEntity<Book[]> response = restTemplate.exchange("https://www.anapioficeandfire.com/api/books?name=" + nameOfABook, HttpMethod.GET, entity, Book[].class);
//        ResponseEntity<Book[]> response = restTemplate.getForEntity("https://www.anapioficeandfire.com/api/books?name=" + nameOfABook, Book[].class);
//        System.out.println(response);
        return result;
    }

    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }
}
