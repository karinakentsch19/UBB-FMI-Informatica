import java.util.HashSet;
import java.util.Set;

class Element<K, V> {
    K key;
    V value;

    public Element(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class TabelaDeDispersie<K, V> {
    private Element<K, V>[] elements;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public TabelaDeDispersie(int capacitate) {
        this.capacity = capacitate;
        elements = new Element[capacitate];
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void add(K key, V value) {
        int index = hash(key);

        // Verificăm dacă cheia există deja și actualizăm valoarea
        //in programul meu nu se va folosi nicioadata
        if (elements[index] != null && elements[index].key.equals(key)) {
            elements[index].value = value;
            return;
        }

        //cautam o poz libera pentru a adauga
        //nu se intampla pt ca numarul de atomi reprezinta capacitatea deci nu vor exista coliziuni
        while (elements[index] != null) {
            index = (index + 1) % capacity;
        }
        elements[index] = new Element<>(key, value);
        size++;
    }

    public V get(K key) {
        int index = hash(key);

        //prima valoare de care da elemnts[index] va fi cea cautata => Theta(1)
        while (elements[index] != null) {
            if (elements[index].key.equals(key)) {
                return elements[index].value;
            }
            index = (index + 1) % capacity;
        }
        return null;
    }

    public boolean containsValue(V value) {
        for(Element<K,V> element: elements){
            if (element != null && element.value.equals(value))
                return true;
        }
        return false;
    }

    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (int i = 0; i < capacity; i++)
            if (elements[i] != null)
                keySet.add(elements[i].key);
        return keySet;
    }
}
