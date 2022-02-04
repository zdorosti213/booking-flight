package com.dfreight.reservation.rest;

import com.dfreight.flightticket.TicketDto;
import com.dfreight.reservation.aspect.PerformanceLogger;
import com.dfreight.reservation.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private static final Logger logger = Logger.getLogger(ReservationController.class.getName());

    private final ReservationService reservationService;

    @PerformanceLogger
    @GetMapping("/")
    public ResponseEntity<String> getAllFlights(@RequestParam(value = "source", required = false) String source,
                                                @RequestParam(value = "destination", required = false) String destination) {
        try {
            List<TicketDto> allFlights = reservationService.getAllFlights(source, destination);
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(allFlights);
            return ResponseEntity.ok(String.format("{\"tickets\":%s}", json));
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return ResponseEntity.internalServerError().body("No Content");
        }
    }
}
