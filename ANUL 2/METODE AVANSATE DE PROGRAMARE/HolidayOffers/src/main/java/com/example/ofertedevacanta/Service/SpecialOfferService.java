package com.example.ofertedevacanta.Service;

import com.example.ofertedevacanta.Domain.DTO.ExtendedSpecialOffer;
import com.example.ofertedevacanta.Domain.SpecialOffer;
import com.example.ofertedevacanta.Repository.SpecialOfferRepository;

import java.time.LocalDate;

public class SpecialOfferService extends AbstractService {
    private SpecialOfferRepository specialOfferRepository;

    public SpecialOfferService(SpecialOfferRepository specialOfferRepository) {
        this.specialOfferRepository = specialOfferRepository;
    }

    public Iterable<SpecialOffer> getAllSpecialOffersFromADateAndAHotel(LocalDate startDate, LocalDate endDate, Double hotelId) {
        return specialOfferRepository.getAllSpecialOffersFromADateAndAHotel(startDate, endDate, hotelId);
    }

    public Iterable<ExtendedSpecialOffer> getAllSpecialOffersForAClient(Integer fidelityGrade){
        return specialOfferRepository.getAllSpecialOffersForAClient(fidelityGrade);
    }
}
