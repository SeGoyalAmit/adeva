package com.skt.adeva.repository;

import com.skt.adeva.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface BookRepository extends CrudRepository<Book, Long> {

}
