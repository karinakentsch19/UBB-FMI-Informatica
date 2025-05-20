import java.util.Comparator;
import java.util.Objects;

public class Pair implements Comparable<Pair> {
    int id;
    int tara;

    int punctaj;

    public Pair(int id, int tara, int punctaj) {
        this.id = id;
        this.tara = tara;
        this.punctaj = punctaj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTara() {
        return tara;
    }

    public void setTara(int tara) {
        this.tara = tara;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return id == pair.id && tara == pair.tara && punctaj == pair.punctaj;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tara, punctaj);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "id=" + id +
                ", tara=" + tara +
                ", punctaj=" + punctaj +
                '}';
    }

    @Override
    public int compareTo(Pair o) {
        if (this.punctaj == o.punctaj)
            return this.id - o.id;
        return -1 * (this.punctaj - o.punctaj);
    }
}

