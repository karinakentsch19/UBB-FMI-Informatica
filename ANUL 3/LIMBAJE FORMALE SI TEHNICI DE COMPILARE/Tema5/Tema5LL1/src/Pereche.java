import java.util.Objects;

public class Pereche<E1, E2> {
    private E1 first;

    private E2 second;

    public Pereche(E1 first, E2 second) {
        this.first = first;
        this.second = second;
    }

    public E1 getFirst() {
        return first;
    }

    public void setFirst(E1 first) {
        this.first = first;
    }

    public E2 getSecond() {
        return second;
    }

    public void setSecond(E2 second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pereche<?, ?> pereche = (Pereche<?, ?>) o;
        return Objects.equals(first, pereche.first) && Objects.equals(second, pereche.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pereche{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
