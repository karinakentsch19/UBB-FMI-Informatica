package ro.iss.biblioteca.Domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Utilizatori")
public class Utilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idUtilizator")
    private Long idUtilizator;

    @Column(name="nume")
    private String nume;

    @Column(name="prenume")
    private String prenume;

    @Column(name="cnp")
    private Long cnp;

    @Column(name="adresa")
    private String adresa;

    @Column(name="email")
    private String email;

    @Column(name="telefon")
    private String telefon;

    @Column(name="parola")
    private String parola;

    @Column(name="esteBibliotecar")
    private Boolean esteBibliotecar;

    public Utilizator(String nume, String prenume, Long cnp, String adresa, String email, String telefon, String parola, Boolean esteBibliotecar) {
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.adresa = adresa;
        this.email = email;
        this.telefon = telefon;
        this.parola = parola;
        this.esteBibliotecar = esteBibliotecar;
    }

    public Utilizator() {

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

    public Long getCnp() {
        return cnp;
    }

    public void setCnp(Long cnp) {
        this.cnp = cnp;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public Boolean getEsteBibliotecar() {
        return esteBibliotecar;
    }

    public void setEsteBibliotecar(Boolean esteBibliotecar) {
        this.esteBibliotecar = esteBibliotecar;
    }

    public Long getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Long idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizator that = (Utilizator) o;
        return Objects.equals(idUtilizator, that.idUtilizator) && Objects.equals(nume, that.nume) && Objects.equals(prenume, that.prenume) && Objects.equals(cnp, that.cnp) && Objects.equals(adresa, that.adresa) && Objects.equals(email, that.email) && Objects.equals(telefon, that.telefon) && Objects.equals(parola, that.parola) && Objects.equals(esteBibliotecar, that.esteBibliotecar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilizator, nume, prenume, cnp, adresa, email, telefon, parola, esteBibliotecar);
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "idUtilizator=" + idUtilizator +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", cnp=" + cnp +
                ", adresa='" + adresa + '\'' +
                ", email='" + email + '\'' +
                ", telefon=" + telefon +
                ", parola='" + parola + '\'' +
                ", esteBibliotecar=" + esteBibliotecar +
                '}';
    }
}
