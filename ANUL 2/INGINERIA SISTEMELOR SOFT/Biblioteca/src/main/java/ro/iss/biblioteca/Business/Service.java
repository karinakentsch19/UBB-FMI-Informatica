package ro.iss.biblioteca.Business;

import ro.iss.biblioteca.Domain.*;
import ro.iss.biblioteca.Repository.CarteRepository;
import ro.iss.biblioteca.Repository.CosRepository;
import ro.iss.biblioteca.Repository.ImprumutRepository;
import ro.iss.biblioteca.Repository.UtilizatorRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Service implements IService {

    private List<Observer> observers;
    private UtilizatorRepository utilizatorRepository;

    private CarteRepository carteRepository;

    private CosRepository cosRepository;

    private ImprumutRepository imprumutRepository;

    public Service(UtilizatorRepository utilizatorRepository, CarteRepository carteRepository, CosRepository cosRepository, ImprumutRepository imprumutRepository) {
        this.utilizatorRepository = utilizatorRepository;
        this.carteRepository = carteRepository;
        this.cosRepository = cosRepository;
        this.imprumutRepository = imprumutRepository;
        this.observers = new ArrayList<>();
    }

    @Override
    public void signUp(String nume, String prenume, Long cnp, String adresa, String email, String telefon, String parola) {
        Utilizator utilizator = new Utilizator(nume, prenume, cnp, adresa, email, telefon, parola, false);
        utilizatorRepository.add(utilizator);
    }

    @Override
    public Utilizator login(String email, String parola) {
        Optional<Utilizator> opUtilizator = utilizatorRepository.findUserByEmailAndPassword(email, parola);
        if (opUtilizator.isPresent())
            return opUtilizator.get();
        else
            throw new RuntimeException("Utilizatorul nu exista\n");
    }

    @Override
    public Iterable<Carte> getAllCartiDisponibile() {
        return carteRepository.getAllCartiDisponibile();
    }

    @Override
    public void adaugaCartiInCosulUnuiUtilizator(List<Carte> carti, Utilizator utilizator) {
        for (Carte carte : carti) {
            Cos cos = new Cos(carte.getIdCarte(), utilizator.getIdUtilizator(), LocalDateTime.now().toString());
            cosRepository.add(cos);
        }
        notifyAllObservers();
    }

    @Override
    public Iterable<Carte> getAllCartiDinCosulUnuiUtilizator(Utilizator utilizator) {
        return cosRepository.getAllCartiDinCosulUnuiUtilizator(utilizator.getIdUtilizator());
    }

    @Override
    public void stergeCartiDinCosulUnuiUtilizator(List<Carte> carti, Utilizator utilizator) {
        for (Carte carte : carti) {
            cosRepository.deleteCarteDinCosulUnuiUtilizator(carte.getIdCarte(), utilizator.getIdUtilizator());
        }
        notifyAllObservers();
    }

    @Override
    public void stergeToateCartileDinCosulUnuiUtilizator(Utilizator utilizator) {
        Iterable<Carte> carti = getAllCartiDinCosulUnuiUtilizator(utilizator);
        List<Carte> cartiDeSters = new ArrayList<>();
        for (Carte carte : carti) {
            cartiDeSters.add(carte);
        }
        stergeCartiDinCosulUnuiUtilizator(cartiDeSters, utilizator);
        notifyAllObservers();
    }

    @Override
    public void imprumutaCarti(List<Carte> carti, Utilizator utilizator) {
        for (Carte carte : carti) {
            Imprumut imprumut = new Imprumut(carte.getIdCarte(), utilizator.getIdUtilizator(), LocalDateTime.now().toString());
            imprumutRepository.add(imprumut);
        }
        notifyAllObservers();
    }

    @Override
    public Iterable<ImprumutDTO> getAllCartiImprumutate() {
        return imprumutRepository.getAllCartiImprumutate();
    }

    @Override
    public void returneazaCarti(List<ImprumutDTO> cartiImprumutate) {
        for (ImprumutDTO imprumut : cartiImprumutate) {
            imprumutRepository.delete(imprumut.getIdImprumut());
        }
        notifyAllObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
