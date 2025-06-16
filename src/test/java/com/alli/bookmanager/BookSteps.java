package com.alli.bookmanager;

import com.alli.bookmanager.Book;
import com.alli.bookmanager.BookDao;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class BookSteps {
    private BookDao dao;
    private Book book;
    private Book found;

    @Given("I have a new book with title {string} and author {string}")
    public void givenNewBook(String title, String author) throws SQLException {
        dao  = new BookDao();
        book = new Book(1, title, author);
    }

    @When("I save it")
    public void whenISaveIt() throws SQLException {
        dao.add(book);
    }

    @Then("I can load it by its ID")
    public void thenICanLoadIt() throws SQLException {
        found = dao.findById(book.getId());
        assertNotNull(found, "Book should be found");
        assertEquals(book.getId(),    found.getId());
        assertEquals(book.getTitle(), found.getTitle());
        assertEquals(book.getAuthor(),found.getAuthor());
    }
}
