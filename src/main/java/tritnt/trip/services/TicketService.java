package tritnt.trip.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tritnt.trip.model.Ticket;
import tritnt.trip.repositories.TicketRepository;
import tritnt.trip.repositories.TripRepository;
import tritnt.trip.repositories.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public Ticket bookTicket(Long tripId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));

//        if (trip.getSeatsAvailable() <= 0) {
//            throw new RuntimeException("No seats available");
//        }

        Ticket ticket = new Ticket();
//        ticket.setUser(user);
//        ticket.setTrip(trip);
        ticket.setStatus("BOOKED");
        ticket.setBookingDate(LocalDateTime.now());

//        trip.setSeatsAvailable(trip.getSeatsAvailable() - 1);
//        tripRepository.save(trip);
        return ticket;
    }
}
