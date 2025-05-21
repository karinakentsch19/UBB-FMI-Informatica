package com.example.zboruri.Service;

import com.example.zboruri.Domain.Flight;
import com.example.zboruri.Domain.Pair;
import com.example.zboruri.Domain.Ticket;
import com.example.zboruri.Repository.FlightRepository;
import com.example.zboruri.Repository.TicketRepository;

import java.time.LocalDateTime;

public class TicketService extends AbstractService{

    private TicketRepository ticketRepository;

    private FlightRepository flightRepository;

    public TicketService(TicketRepository ticketRepository, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
    }

    public Integer numberOfTicketsSoldForAFlight(Long flightId){
        return ticketRepository.numberOfTicketsSoldForAFlight(flightId);
    }

    public Integer numberOfFreeTicketsForAFlight(Long flightId){
        Flight flight = flightRepository.find(flightId).get();
        return flight.getSeats() - numberOfTicketsSoldForAFlight(flightId);
    }

    public void buyTicket(String username, Long flightId){
        if (numberOfFreeTicketsForAFlight(flightId) == 0)
            throw new RuntimeException("No more tickets for this flight\n");
        Ticket ticket = new Ticket(username, flightId, LocalDateTime.now());
        ticketRepository.add(ticket);
        notifyAllObservers(flightId);
    }
}
