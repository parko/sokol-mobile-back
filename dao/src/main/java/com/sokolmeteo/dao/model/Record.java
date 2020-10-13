package com.sokolmeteo.dao.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
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
    @Column(length = 100, name = "station")
    private String station;
    @Column(name = "created")
    private Long created;
    @Column(name = "sent")
    private Long sent;
    @Column(name = "start_date")
    private Long start;
    @Column(name = "end_date")
    private Long end;
    @Column(name = "state")
    private String state;
    @Column(length = 4000, name = "details")
    @JsonRawValue
    private String details;

    public Record() {
    }

    public Record(String author, String station, Long start, Long end) {
        this.author = author;
        this.station = station;
        this.created = new Date().getTime();
        this.start = start;
        this.end = end;
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
