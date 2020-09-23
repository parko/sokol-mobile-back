package com.sokolmeteo.dao.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String author;
    private Date created;
    private Date sent;
    private String state;
    @Column(length = 4000)
    private String details;

    public Record() {
    }

    public Record(String author) {
        this.author = author;
        this.created = new Date();
        this.state = RecordState.IN_PROGRESS.toString();
    }

    public void setFault(String details) {
        this.state = RecordState.FAULT.toString();
        this.details = details;
    }

    public void setSuccess() {
        this.state = RecordState.SENT.toString();
        this.sent = new Date();
    }
}
