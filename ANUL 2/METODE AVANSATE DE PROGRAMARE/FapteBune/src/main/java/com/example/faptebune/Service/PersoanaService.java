package com.example.faptebune.Service;

import com.example.faptebune.Domain.Persoana;
import com.example.faptebune.Repository.PersoanaRepository;

import java.util.Iterator;
import java.util.Optional;

public class PersoanaService extends AbstractService{
    private PersoanaRepository persoanaRepository;

    public PersoanaService(PersoanaRepository persoanaRepository) {
        this.persoanaRepository = persoanaRepository;
    }

    public void addPersoana(String nume, String prenume, String username, String parola, String oras, String strada, String numarStradal, String telefon){
        Persoana persoana = new Persoana(nume, prenume, username, parola, oras, strada, numarStradal, telefon);
        persoanaRepository.add(persoana);
        notifyAllObservers();
    }

    public Persoana persoanaCuUsernameDat(String username){
        Optional<Persoana> persoana = persoanaRepository.findPersoanaByUsername(username);
        if (persoana.isEmpty())
            throw new RuntimeException("Persoana nu exista\n");
        else
            return persoana.get();
    }

    public Iterable<String> getAllUsernames(){
        return persoanaRepository.getAllUsernames();
    }
}
