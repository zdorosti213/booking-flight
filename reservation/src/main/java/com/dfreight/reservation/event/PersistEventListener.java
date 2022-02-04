package com.dfreight.reservation.event;

import com.dfreight.reservation.dao.TicketDao;
import com.dfreight.reservation.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class PersistEventListener implements ApplicationListener<PersisEvent> {
    private static final Logger logger = Logger.getLogger(PersistEventListener.class.getName());

    private Map<Class<?>, JpaRepository> daoMap = new HashMap<>();

    public PersistEventListener(@Autowired TicketDao ticketDao) {
        daoMap.put(Ticket.class, ticketDao);
    }

    @Override
    public void onApplicationEvent(PersisEvent event) {
        if (event.getContentType() == null)
            throw new NullPointerException("Entity type is null!!");
        daoMap.get(event.getContentType()).deleteAll();
        daoMap.get(event.getContentType()).saveAll(event.getEventContent());
        logger.info(String.format("[%d] items persisted.", daoMap.get(event.getContentType()).findAll().size()));
    }
}