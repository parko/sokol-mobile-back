package com.sokolmeteo.dao.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String author;
    private Date created;
    private Date sent;
    private int state;
    @Column(length = 4000)
    private String details;
    private String info;

    public Log() {
    }

    public Log(String author) {
        this.author = author;
        this.created = new Date();
        this.state = LogState.IN_PROGRESS.getCode();
    }

    public void fault(String info, String details) {
        this.state = LogState.FAULT.getCode();
        this.info = info;
        this.details = details;
    }

    public void success() {
        this.state = LogState.SENT.getCode();
        this.sent = new Date();
    }
}
