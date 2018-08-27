package com.hunter.fastandroid.vo;

import java.util.List;

public class DoubanResponse extends Page{
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
