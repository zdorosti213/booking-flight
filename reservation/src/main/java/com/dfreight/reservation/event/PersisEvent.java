package com.dfreight.reservation.event;

import org.springframework.context.ApplicationEvent;

import java.util.List;

public class PersisEvent<T> extends ApplicationEvent {

    private List<T> eventContent;
    private Class<T> contentType;

    public PersisEvent(Object source, List<T> eventContent, Class<T> contentType) {
        super(source);
        this.eventContent = eventContent;
        this.contentType = contentType;
    }

    public List<T> getEventContent() {
        return eventContent;
    }

    public Class<T> getContentType() {
        return contentType;
    }
}