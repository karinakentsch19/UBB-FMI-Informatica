package com.example.faptebune.Service;

import com.example.faptebune.Domain.Nevoie;
import com.example.faptebune.Repository.NevoieRepository;

import java.time.LocalDateTime;

public class NevoieService extends AbstractService{
    NevoieRepository nevoieRepository;

    public NevoieService(NevoieRepository nevoieRepository) {
        this.nevoieRepository = nevoieRepository;
    }

    public Iterable<Nevoie> getNevoiCuOmSalvatorNullDinOrasDat(String oras, Long omInNevoie){
        return nevoieRepository.getNevoiCuOmSalvatorNullDinOrasDat(oras, omInNevoie);
    }

    public Iterable<Nevoie> getNevoiPentruOmSalvatorDat(Long omSalvator){
        return nevoieRepository.getNevoiPentruOmSalvatorDat(omSalvator);
    }

    public void addNevoie(String titlu, String descriere, LocalDateTime deadline, Long omInNevoie){
        Nevoie nevoie = new Nevoie(titlu, descriere, deadline, omInNevoie, null, "Caut erou!");
        nevoieRepository.add(nevoie);
        notifyAllObservers();


        //find oras dupa omInNevoie
    }

    public void updateNevoie(Long id_nevoie, String titlu, String descriere, LocalDateTime deadline, Long omInNevoie, Long omSalvator, String status){
        Nevoie nevoie = new Nevoie(titlu, descriere, deadline, omInNevoie, omSalvator, status);
        nevoie.setId(id_nevoie);
        nevoieRepository.update(nevoie);
        notifyAllObservers();
    }
}
