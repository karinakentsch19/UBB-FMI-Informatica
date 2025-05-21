package com.example.ofertedevacanta.Service;

import com.example.ofertedevacanta.Domain.Client;
import com.example.ofertedevacanta.Domain.Hotel;
import com.example.ofertedevacanta.Domain.Reservation;
import com.example.ofertedevacanta.Repository.ClientRepository;
import com.example.ofertedevacanta.Repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ReservationService extends AbstractService{
    private ReservationRepository reservationRepository;

    private ClientRepository clientRepository;


    public ReservationService(ReservationRepository reservationRepository, ClientRepository clientRepository) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
    }

    public void addReservation(Long clientId, String hotelName, LocalDateTime startDate, Integer noNights){
        Hotel hotel = reservationRepository.findHotelByName(hotelName).get();
        Reservation reservation = new Reservation(clientId, hotel.getId(), startDate, noNights);
        reservationRepository.add(reservation);
        Client client = clientRepository.find(clientId).get();
        List<Client> clients = StreamSupport.stream(clientRepository.getAllClientsExceptOne(clientId).spliterator(), false).collect(Collectors.toList());
        notifyAllObservers(client.getHobby(), hotelName, clients);
    }
}

