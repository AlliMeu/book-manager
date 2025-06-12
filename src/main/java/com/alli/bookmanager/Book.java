package com.alli.bookmanager;

public class Book {
    private final int id;
    private final String title;
    private final String author;

    public Book(int id, String title, String author) {
        this.id     = id;
        this.title  = title;
        this.author = author;
    }

    public int getId()           { return id; }
    public String getTitle()     { return title; }
    public String getAuthor()    { return author; }

    @Override
    public String toString() {
        return "Book{id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
