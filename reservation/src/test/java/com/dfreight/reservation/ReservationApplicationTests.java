package com.dfreight.reservation;

import com.dfreight.flightticket.TicketDto;
import com.dfreight.reservation.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.dfreight.flightticket.TicketDto.TicketInfo;

@SpringBootTest
class ReservationApplicationTests {

    @Autowired
    private ReservationService reservationService;

    @Test
    void contextLoads() {
        TicketInfo Tehran_Dubai = new TicketInfo();
        Tehran_Dubai.setFrom("Tehran");
        Tehran_Dubai.setTo("Dubai");
        TicketInfo Tehran_London = new TicketInfo();
        Tehran_London.setFrom("Tehran");
        Tehran_London.setTo("London");
        TicketInfo Tehran_Istanbul = new TicketInfo();
        Tehran_Istanbul.setFrom("Tehran");
        Tehran_Istanbul.setTo("Istanbul");
        TicketInfo Dubai_London = new TicketInfo();
        Dubai_London.setFrom("Dubai");
        Dubai_London.setTo("London");

        TicketDto mahan = new TicketDto();
        mahan.setAirline("Mahan Airline");
        mahan.setTicketInfos(Arrays.asList(Tehran_Dubai,Tehran_London));

        TicketDto qatar = new TicketDto();
        qatar.setAirline("Qatar Airline");
        qatar.setTicketInfos(Arrays.asList(Tehran_Istanbul, Dubai_London));

        List<TicketDto> ticketList = Arrays.asList(mahan, qatar);

        Assertions.assertEquals(4, reservationService.filterBySourceAndDestination(ticketList, null,null).stream()
                .map(TicketDto::getTicketInfos)
                .flatMap(List::stream)
                .count());
        Assertions.assertEquals(3, reservationService.filterBySourceAndDestination(ticketList, "Tehran",null).stream()
                .map(TicketDto::getTicketInfos)
                .flatMap(List::stream)
                .count());
        Assertions.assertEquals(2, reservationService.filterBySourceAndDestination(ticketList, null,"London").stream()
                .map(TicketDto::getTicketInfos)
                .flatMap(List::stream)
                .count());
        Assertions.assertEquals(0, reservationService.filterBySourceAndDestination(ticketList, "Istanbul","Shanghai").stream()
                .map(TicketDto::getTicketInfos)
                .flatMap(List::stream)
                .count());
    }

}
