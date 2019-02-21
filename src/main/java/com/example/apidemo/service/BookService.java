package com.example.apidemo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.apidemo.entity.Book;
import com.example.apidemo.repository.jpa.BookJpaRepository;

@Service
public class BookService {

    private final BookJpaRepository bookJpaRepository;

    public BookService(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    private volatile boolean cacheMiss = false;

    public boolean isCacheMiss() {
        boolean isCacheMiss = this.cacheMiss;
        this.cacheMiss = false;
        return isCacheMiss;
    }

    protected void setCacheMiss() {
        this.cacheMiss = true;
    }

    @Cacheable(value = "book")
    public Book getBookById(String id) {

        setCacheMiss();

        Book book = this.bookJpaRepository.findBookById(id);

        return book;
    }

}
