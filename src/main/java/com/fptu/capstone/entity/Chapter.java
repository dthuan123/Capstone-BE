package com.fptu.capstone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Chapter {

    @Id
    @Column(name = "chapter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chapter_name")
    private String name;

    @Column(name = "chapter_content")
    private String content;

    @Column(name = "started_date")
    private Date startedDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "to_be_published_date")
    private Date publishDate;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ChapterStatus chapterStatus;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<ChapterComment> comments;

    @ManyToMany(mappedBy = "historyList")
    @JsonIgnore
    private List<User> users;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<ChapterComment> getComments() {
        return comments;
    }

    public void setComments(List<ChapterComment> comments) {
        this.comments = comments;
    }

    public ChapterStatus getChapterStatus() {
        return chapterStatus;
    }

    public void setChapterStatus(ChapterStatus chapterStatus) {
        this.chapterStatus = chapterStatus;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
