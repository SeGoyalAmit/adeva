package com.skt.adeva.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skt.adeva.hibernate.type.array.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

@Entity
@Table(name = "books")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book implements Serializable {
    private static final long serialVersionUID = -5241781253380015253L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "isbn")
    private String isbn;

    @Type(type = "json")
    @Column(name = "authors", columnDefinition = "json")
    private String[] authors;

    @Column(name = "country")
    private String country;

    @Column(name = "number_of_pages")
    @JsonProperty(value = "number_of_pages")
    @JsonAlias({"number_of_pages", "numberOfPages"})
    private int numberOfPages;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "release_date")
    @JsonProperty(value = "release_date")
    @JsonAlias({"release_date", "released"})
    private Date released;

    @JsonIgnore
    private transient String oldBookName;

    public String getOldBookName() {
        return oldBookName;
    }

    public void setOldBookName(String oldBookName) {
        this.oldBookName = oldBookName;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getReleased() {
        return released;
    }

    public void setReleased(Date released) {
        this.released = released;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        result = prime * result + ((released == null) ? 0 : released.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + numberOfPages;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if(released == null){
            if(other.released != null)
                return false;
        } else if(!released.equals(other.released))
            return false;
        if(numberOfPages != other.numberOfPages)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", isbn=" + isbn + ", publisher=" + publisher +
                ", country=" + country + ", authors=" + Arrays.asList(authors) + ", numberOfPages=" +
                numberOfPages + ", releaseDate=" + released + "]";
    }
}
