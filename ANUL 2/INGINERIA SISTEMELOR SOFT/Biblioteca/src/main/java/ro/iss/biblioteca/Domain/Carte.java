package ro.iss.biblioteca.Domain;

import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "Carti")
public class Carte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCarte")
    private Long idCarte;

    @Column(name="nume")
    private String nume;

    @Column(name="autor")
    private String autor;


    public Carte(String nume, String autor) {
        this.nume = nume;
        this.autor = autor;
    }

    public Carte() {

    }

    public Long getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(Long idCarte) {
        this.idCarte = idCarte;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return Objects.equals(idCarte, carte.idCarte) && Objects.equals(nume, carte.nume) && Objects.equals(autor, carte.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarte, nume, autor);
    }

    @Override
    public String toString() {
        return "Carte{" +
                "idCarte=" + idCarte +
                ", nume='" + nume + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }
}
