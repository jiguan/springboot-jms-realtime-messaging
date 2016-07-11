package com.jms.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class MessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Date date;
    public String content;
    public String to;
    public String from;

    public MessageDto() {
        this.date = Calendar.getInstance().getTime();
    }

    @Override
    public String toString() {
        return "[Date: " + date.toString() + ", From: " + from + ", To: " + to + ", Content: " + content + "]";
    }
}
