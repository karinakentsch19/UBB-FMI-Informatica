package ro.iss.biblioteca.Domain;

import java.util.Objects;

public class ImprumutDTO {
    private Long idImprumut;
    private Long idCarte;

    private Long idUtilizator;

    private String numeCarte;

    private String autor;

    private String numeUtilizator;

    private String prenumeUtilizator;

    public ImprumutDTO(Long idImprumut, Long idCarte, Long idUtilizator, String numeCarte, String autor, String numeUtilizator, String prenumeUtilizator) {
        this.idImprumut = idImprumut;
        this.idCarte = idCarte;
        this.idUtilizator = idUtilizator;
        this.numeCarte = numeCarte;
        this.autor = autor;
        this.numeUtilizator = numeUtilizator;
        this.prenumeUtilizator = prenumeUtilizator;
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

    public String getNumeCarte() {
        return numeCarte;
    }

    public void setNumeCarte(String numeCarte) {
        this.numeCarte = numeCarte;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getPrenumeUtilizator() {
        return prenumeUtilizator;
    }

    public void setPrenumeUtilizator(String prenumeUtilizator) {
        this.prenumeUtilizator = prenumeUtilizator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImprumutDTO that = (ImprumutDTO) o;
        return Objects.equals(idImprumut, that.idImprumut) && Objects.equals(idCarte, that.idCarte) && Objects.equals(idUtilizator, that.idUtilizator) && Objects.equals(numeCarte, that.numeCarte) && Objects.equals(autor, that.autor) && Objects.equals(numeUtilizator, that.numeUtilizator) && Objects.equals(prenumeUtilizator, that.prenumeUtilizator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idImprumut, idCarte, idUtilizator, numeCarte, autor, numeUtilizator, prenumeUtilizator);
    }

    @Override
    public String toString() {
        return "ImprumutDTO{" +
                "idImprumut=" + idImprumut +
                ", idCarte=" + idCarte +
                ", idUtilizator=" + idUtilizator +
                ", numeCarte='" + numeCarte + '\'' +
                ", autor='" + autor + '\'' +
                ", numeUtilizator='" + numeUtilizator + '\'' +
                ", prenumeUtilizator='" + prenumeUtilizator + '\'' +
                '}';
    }
}
