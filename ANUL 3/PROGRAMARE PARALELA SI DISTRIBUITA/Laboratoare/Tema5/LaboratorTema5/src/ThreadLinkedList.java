import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ThreadLinkedList {
    private Map<Integer, Boolean> inCursDeFolosire;

    private final Map<Integer, Integer> punctaj;

    private final Map<Integer, Integer> tara;

    private final Set<Integer> persoaneDescalificate;

    public ThreadLinkedList() {
        this.inCursDeFolosire = new HashMap<>();
        this.punctaj = new HashMap<>();
        this.tara = new HashMap<>();
        this.persoaneDescalificate = new TreeSet<>();
    }

    public synchronized void add(Pair pair) throws InterruptedException {
        if (!inCursDeFolosire.containsKey(pair.id)) {
            inCursDeFolosire.put(pair.id, false);
        }
        while (inCursDeFolosire.get(pair.id)) {
            wait();
        }
        inCursDeFolosire.put(pair.id, true);

        tara.put(pair.id, pair.tara);

        if (!persoaneDescalificate.contains(pair.id)) {
            if (pair.punctaj == -1) {
                punctaj.remove(pair.id);
                persoaneDescalificate.add(pair.id);
            } else {
                if (punctaj.containsKey(pair.id)) {
                    punctaj.put(pair.id, pair.punctaj + punctaj.get(pair.id));
                } else {
                    punctaj.put(pair.id, pair.punctaj);
                }
            }
        }
        inCursDeFolosire.put(pair.id, false);
        notify();
    }

    public synchronized Set<Pair> clasament() {
        Set<Pair> clasament = new TreeSet<>();
        for (Integer id : punctaj.keySet())
            clasament.add(new Pair(id, tara.get(id), punctaj.get(id)));
        return clasament;
    }

}
