package com.example.faptebune.Domain;

import java.util.Objects;

public class Persoana extends Entity<Long>{
    private String nume, prenume, username, parola, oras, strada, numarStrada, telefon;

    public Persoana(String nume, String prenume, String username, String parola, String oras, String strada, String numarStrada, String telefon) {
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.parola = parola;
        this.oras = oras;
        this.strada = strada;
        this.numarStrada = numarStrada;
        this.telefon = telefon;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNumarStrada() {
        return numarStrada;
    }

    public void setNumarStrada(String numarStrada) {
        this.numarStrada = numarStrada;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persoana persoana = (Persoana) o;
        return Objects.equals(nume, persoana.nume) && Objects.equals(prenume, persoana.prenume) && Objects.equals(username, persoana.username) && Objects.equals(parola, persoana.parola) && Objects.equals(oras, persoana.oras) && Objects.equals(strada, persoana.strada) && Objects.equals(numarStrada, persoana.numarStrada) && Objects.equals(telefon, persoana.telefon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, username, parola, oras, strada, numarStrada, telefon);
    }
}
