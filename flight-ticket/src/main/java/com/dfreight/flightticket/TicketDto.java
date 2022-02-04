package com.dfreight.flightticket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TicketDto {
    private String airline;
    private List<TicketInfo> ticketInfos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TicketInfo {
        private String from;
        private String to;
        private String date;
        private Double price;
        private String status;
    }
}
