package com.sokolmeteo.dao.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Device {
    private String id;
    private String name;
    private String imei;
    private String password;
    private boolean permittedToDelete;
    private boolean permittedToRead;
    private boolean permittedToWrite;
}
