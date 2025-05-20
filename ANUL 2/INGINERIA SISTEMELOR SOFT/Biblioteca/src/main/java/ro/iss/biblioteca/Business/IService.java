package ro.iss.biblioteca.Business;

import ro.iss.biblioteca.Domain.Carte;
import ro.iss.biblioteca.Domain.ImprumutDTO;
import ro.iss.biblioteca.Domain.Utilizator;

import java.util.List;

public interface IService extends Observable{
    public void signUp(String nume, String prenume, Long cnp, String adresa, String email, String telefon, String parola);

    public Utilizator login(String email, String parola);

    public Iterable<Carte> getAllCartiDisponibile();

    public void adaugaCartiInCosulUnuiUtilizator(List<Carte> carti, Utilizator utilizator);

    public Iterable<Carte> getAllCartiDinCosulUnuiUtilizator(Utilizator utilizator);

    public void stergeCartiDinCosulUnuiUtilizator(List<Carte> carti, Utilizator utilizator);

    public void stergeToateCartileDinCosulUnuiUtilizator(Utilizator utilizator);

    public void imprumutaCarti(List<Carte> carti, Utilizator utilizator);

    public Iterable<ImprumutDTO> getAllCartiImprumutate();

    public void returneazaCarti(List<ImprumutDTO> cartiImprumutate);
}
