package com.fptu.capstone.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Report {

    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int report_id;

    @Column(name = "report_content")
    private String report_content;

    @Column(name = "reported_date")
    private Date reported_date;

    @Column(name = "response_date")
    private Date response_date;

    @Column(name = "response_content")
    private String response_content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User user_sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User userReceiver;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ReportStatus status_id;

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public String getReport_content() {
        return report_content;
    }

    public void setReport_content(String report_content) {
        this.report_content = report_content;
    }

    public Date getReported_date() {
        return reported_date;
    }

    public void setReported_date(Date reported_date) {
        this.reported_date = reported_date;
    }

    public Date getResponse_date() {
        return response_date;
    }

    public void setResponse_date(Date response_date) {
        this.response_date = response_date;
    }

    public String getResponse_content() {
        return response_content;
    }

    public void setResponse_content(String response_content) {
        this.response_content = response_content;
    }

    public User getUser_sender() {
        return user_sender;
    }

    public void setUser_sender(User user_sender) {
        this.user_sender = user_sender;
    }

    public User getUser_receiver() {
        return userReceiver;
    }

    public void setUser_receiver(User user_receiver) {
        this.userReceiver = user_receiver;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public ReportStatus getStatus_id() {
        return status_id;
    }

    public void setStatus_id(ReportStatus status_id) {
        this.status_id = status_id;
    }
}
