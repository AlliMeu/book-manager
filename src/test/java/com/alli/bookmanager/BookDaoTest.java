package com.alli.bookmanager;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class BookDaoTest {
    private BookDao dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = new BookDao();
    }

    @Test
    void testAddAndFind() throws SQLException {
        Book b = new Book(1, "1984", "Orwell");
        dao.add(b);

        Book found = dao.findById(1);
        assertNotNull(found, "Book should be found");
        assertEquals(1, found.getId());
        assertEquals("1984", found.getTitle());
        assertEquals("Orwell", found.getAuthor());
    }

    @Test
    void testDelete() throws SQLException {
        Book b = new Book(2, "Brave New World", "Huxley");
        dao.add(b);

        // ensure it’s there
        assertNotNull(dao.findById(2));

        // delete and verify
        dao.delete(2);
        assertNull(dao.findById(2), "Book should no longer exist");
    }
}
