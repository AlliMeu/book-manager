package com.alli.bookmanager;

import com.alli.bookmanager.Book;
import com.alli.bookmanager.BookDao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class BookService {
    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void add(Book book) {
        try {
            bookDao.add(book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book findById(int id) {
        try {
            return bookDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
