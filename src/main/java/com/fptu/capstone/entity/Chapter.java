package com.fptu.capstone.entity;

import javax.persistence.*;

@Entity
public class Chapter {

    @Id
    @Column(name = "chapter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chapter_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
