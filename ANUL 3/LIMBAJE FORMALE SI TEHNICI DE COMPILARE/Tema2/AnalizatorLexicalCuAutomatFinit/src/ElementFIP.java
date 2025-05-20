import java.util.Objects;

public class ElementFIP {
    private int cod;

    private int pozitieInTS;

    public ElementFIP(int cod, int pozitieInTS) {
        this.cod = cod;
        this.pozitieInTS = pozitieInTS;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getPozitieInTS() {
        return pozitieInTS;
    }

    public void setPozitieInTS(int pozitieInTS) {
        this.pozitieInTS = pozitieInTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementFIP that = (ElementFIP) o;
        return cod == that.cod && pozitieInTS == that.pozitieInTS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cod, pozitieInTS);
    }

    @Override
    public String toString() {
        return "ElementFIP{" +
                "cod=" + cod +
                ", pozitieInTS=" + pozitieInTS +
                '}';
    }
}
