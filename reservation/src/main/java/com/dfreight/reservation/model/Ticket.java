package com.dfreight.reservation.model;

import com.dfreight.flightticket.TicketDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "creation_time", updatable = false)
    LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    LocalDateTime updateTime;

    private String airline;
    private String source;
    private String destination;
    private String dateStr;
    private Double price;
    private String status;

    public static List<Ticket> mapDto2Entity(List<TicketDto> list) {
        List<Ticket> result = new ArrayList<>();
        list.stream()
                .forEach(dto -> {
                    String airline = dto.getAirline();
                    dto.getTicketInfos().stream()
                            .forEach(info -> {
                                Ticket ticket = new Ticket();
                                ticket.setAirline(airline);
                                ticket.setSource(info.getFrom());
                                ticket.setDestination(info.getTo());
                                ticket.setDateStr(info.getDate());
                                ticket.setStatus(info.getStatus());
                                ticket.setPrice(info.getPrice());
                                result.add(ticket);
                            });
                });
        return result;
    }
}
