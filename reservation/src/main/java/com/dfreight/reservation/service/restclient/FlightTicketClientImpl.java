package com.dfreight.reservation.service.restclient;

import com.dfreight.flightticket.TicketDto;
import com.dfreight.reservation.aspect.PerformanceLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Logger;

@Service
public class FlightTicketClientImpl implements FlightTicketClient {

    private static final Logger logger = Logger.getLogger(FlightTicketClientImpl.class.getName());

    @Override
    @PerformanceLogger
    public List<TicketDto> getTicketListJson() {
        String mahanURL = "http://localhost:8181/tickets/mahan";
        String qatarURL = "http://localhost:8181/tickets/qatar";

        var httpClient = HttpClient.newHttpClient();
        HttpRequest firstRequest = HttpRequest.newBuilder(URI.create(mahanURL)).GET().build();
        HttpRequest secondRequest = HttpRequest.newBuilder(URI.create(qatarURL)).GET().build();

        var firstFuture = httpClient.sendAsync(firstRequest, HttpResponse.BodyHandlers.ofString());
        var secondFuture = httpClient.sendAsync(secondRequest, HttpResponse.BodyHandlers.ofString());

        /*BiFunction<HttpResponse<String>, HttpResponse<String>, String> combinerFunction =
                (firstResponse, secondResponse) ->
                        String.format("{\"tickets\":[%s,\n%s]}", firstResponse.body(), secondResponse.body());*/

        ObjectMapper objectMapper = new ObjectMapper();
        BiFunction<HttpResponse<String>, HttpResponse<String>, List<TicketDto>> combinerFunction =
                (firstResponse, secondResponse) -> {
                    try {
                        List<TicketDto> list = new ArrayList<>();
                        list.add(objectMapper.readValue(firstResponse.body(), TicketDto.class));
                        list.add(objectMapper.readValue(secondResponse.body(), TicketDto.class));
                        return list;
                    } catch (Exception e) {
                        logger.warning("get flightList get mapping exception: " + e.getMessage());
                        return Collections.EMPTY_LIST;
                    }
                };

        return firstFuture
                .thenCombine(secondFuture, combinerFunction)
                .join();
    }
}