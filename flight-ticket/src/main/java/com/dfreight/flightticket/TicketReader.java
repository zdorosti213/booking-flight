package com.dfreight.flightticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketReader {

    private final ResourceLoader resourceLoader;
    private static final String RESOURCE_LOCATION = "classpath:/ticket/";
    private static final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public TicketDto readTickets(String jsonFile) {
        return mapper.readValue(resourceLoader.getResource(RESOURCE_LOCATION + jsonFile).getInputStream(), TicketDto.class);
    }

}
