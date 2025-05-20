package ro.iss.biblioteca.Domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Imprumuturi")
public class Imprumut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idImprumut")
    private Long idImprumut;
    @Column(name="idCarte")
    private Long idCarte;
    @Column(name="idUtilizator")
    private Long idUtilizator;
    @Column(name="dataInchirierii")
    private String dataInchirierii;

    public Imprumut(Long idCarte, Long idUtilizator, String dataInchirierii) {
        this.idCarte = idCarte;
        this.idUtilizator = idUtilizator;
        this.dataInchirierii = dataInchirierii;
    }

    public Imprumut() {

    }

    public Long getIdImprumut() {
        return idImprumut;
    }

    public void setIdImprumut(Long idImprumut) {
        this.idImprumut = idImprumut;
    }

    public Long getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(Long idCarte) {
        this.idCarte = idCarte;
    }

    public Long getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Long idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public String getDataInchirierii() {
        return dataInchirierii;
    }

    public void setDataInchirierii(String dataInchirierii) {
        this.dataInchirierii = dataInchirierii;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Imprumut imprumut = (Imprumut) o;
        return Objects.equals(idImprumut, imprumut.idImprumut) && Objects.equals(idCarte, imprumut.idCarte) && Objects.equals(idUtilizator, imprumut.idUtilizator) && Objects.equals(dataInchirierii, imprumut.dataInchirierii);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idImprumut, idCarte, idUtilizator, dataInchirierii);
    }

    @Override
    public String toString() {
        return "Imprumut{" +
                "idImprumut=" + idImprumut +
                ", idCarte=" + idCarte +
                ", idUtilizator=" + idUtilizator +
                ", dataInchirierii='" + dataInchirierii + '\'' +
                '}';
    }
}
