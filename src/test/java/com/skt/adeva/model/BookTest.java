package com.skt.adeva.model;

import com.google.common.testing.EqualsTester;
import org.junit.Test;

public class BookTest {
    @Test
    public void testEqualAndHashcodeForSameTitle() {
        Book book1 = createBook();
        Book book2 = createBook();
        new EqualsTester().addEqualityGroup(book1, book2).testEquals();
    }

    private Book createBook() {
        Book book = new Book();
        book.setId(1L);
        book.setName("My First Book");
        book.setIsbn("123-3213243567");
        book.setCountry("United States");
        book.setNumberOfPages(350);
        book.setPublisher("Acme Books");
        book.setReleaseDate("2019-08-01");
        book.setAuthors(new String[]{"John Doe"});
        return book;
    }
}
