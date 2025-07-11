package com.alli.bookmanager;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class BookDao {
    private final Connection conn;

    public BookDao() throws SQLException {
        // 1) open an in-memory H2 database
        // This line means "Use the JDBC driver to create a connection to an H2 in-memory DB named books"
        conn = DriverManager.getConnection("jdbc:h2:mem:books;DB_CLOSE_DELAY=-1");

        // 2) create the table if it doesn't exist
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "author VARCHAR(255))");
        }
    }

    public void add(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.executeUpdate();
        }

    }


    public Book findById(int id) throws SQLException {
        String sql = "SELECT id, title, author FROM books WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    );
                }
                return null;  // not found
            }
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

