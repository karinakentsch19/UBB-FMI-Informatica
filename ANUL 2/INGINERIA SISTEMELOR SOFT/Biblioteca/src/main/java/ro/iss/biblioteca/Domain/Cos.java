package ro.iss.biblioteca.Domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Cosuri")
public class Cos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCos")
    private Long idCos;

    @Column(name="idCarte")
    private Long idCarte;

    @Column(name="idUtilizator")
    private Long idUtilizator;

    @Column(name="dataPlasarii")
    private String dataPlasarii;

    public Cos(Long idCarte, Long idUtilizator, String dataPlasarii) {
        this.idCarte = idCarte;
        this.idUtilizator = idUtilizator;
        this.dataPlasarii = dataPlasarii;
    }

    public Cos() {

    }

    public Long getIdCos() {
        return idCos;
    }

    public void setIdCos(Long idCos) {
        this.idCos = idCos;
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

    public String getDataPlasarii() {
        return dataPlasarii;
    }

    public void setDataPlasarii(String dataPlasarii) {
        this.dataPlasarii = dataPlasarii;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cos cos = (Cos) o;
        return Objects.equals(idCos, cos.idCos) && Objects.equals(idCarte, cos.idCarte) && Objects.equals(idUtilizator, cos.idUtilizator) && Objects.equals(dataPlasarii, cos.dataPlasarii);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCos, idCarte, idUtilizator, dataPlasarii);
    }

    @Override
    public String toString() {
        return "Cos{" +
                "idCos=" + idCos +
                ", idCarte=" + idCarte +
                ", idUtilizator=" + idUtilizator +
                ", dataPlasarii='" + dataPlasarii + '\'' +
                '}';
    }
}
