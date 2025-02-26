package tritnt.trip.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tritnt.trip.model.Ticket;
import tritnt.trip.services.TicketService;

@RestController
@RequestMapping("api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/book/{userId}/{tripId}")
    public ResponseEntity<Ticket> bookTicket(@PathVariable Long userId, @PathVariable Long tripId) {
        return ResponseEntity.ok(ticketService.bookTicket(userId, tripId));
    }
}
