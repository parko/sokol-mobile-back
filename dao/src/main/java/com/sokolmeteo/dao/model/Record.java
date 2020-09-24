package com.sokolmeteo.dao.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "cmn_record")
@Getter
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100, name = "author")
    private String author;
    @Column(name = "created")
    private Long created;
    @Column(name = "sent")
    private Long sent;
    @Column(name = "state")
    private String state;
    @Column(length = 4000, name = "details")
    private String details;

    public Record() {
    }

    public Record(String author) {
        this.author = author;
        this.created = new Date().getTime();
        this.state = RecordState.IN_PROGRESS.toString();
    }

    public void setFault(String details) {
        this.state = RecordState.FAULT.toString();
        this.details = details;
    }

    public void setSuccess() {
        this.state = RecordState.SENT.toString();
        this.sent = new Date().getTime();
    }
}
