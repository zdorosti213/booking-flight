package com.dfreight.flightticket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class FlightTicketController {
    private final TicketReader ticketReader;

    @GetMapping("/mahan")
    public ResponseEntity<TicketDto> gatMahanTickets() {
        var ticketDto = ticketReader.readTickets("mahan.json");
        return ResponseEntity.ok(ticketDto);
    }

    @GetMapping("/qatar")
    public ResponseEntity<TicketDto> gatQatarTickets() {
        var ticketDto = ticketReader.readTickets("qatar.json");
        return ResponseEntity.ok(ticketDto);
    }
}
