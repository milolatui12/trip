package tritnt.trip.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tritnt.trip.exception.InvalidTokenException;
import tritnt.trip.model.Ticket;
import tritnt.trip.services.TicketService;

@RestController
@RequestMapping("api/tickets")
@RequiredArgsConstructor
public class    TicketController {
    private final TicketService ticketService;

    @PostMapping("/book/{tripId}")
    public ResponseEntity<Ticket> bookTicket(@PathVariable Long tripId) {
        System.out.println(tripId);
        if (tripId == 1234) {
            throw new InvalidTokenException("Invalid or expired token!");
        }
        return ResponseEntity.ok(ticketService.bookTicket(tripId));
    }
}
