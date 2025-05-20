import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ThreadLinkedList {
    private boolean inCursDeFolosire;

    private final Map<Integer, Integer> punctaj;

    private final Set<Integer> persoaneDescalificate;

    public ThreadLinkedList(){
        this.inCursDeFolosire = false;
        this.punctaj = new HashMap<>();
        this.persoaneDescalificate = new TreeSet<>();
    }

    public synchronized void add(Pair pair) throws InterruptedException{
        while (inCursDeFolosire){
            wait();
        }
        inCursDeFolosire = true;

        if (!persoaneDescalificate.contains(pair.first)){
            if (pair.second == -1){
                punctaj.remove(pair.first);
                persoaneDescalificate.add(pair.first);
            }
            else{
                if (punctaj.containsKey(pair.first)){
                    punctaj.put(pair.first, pair.second + punctaj.get(pair.first));
                }
                else{
                    punctaj.put(pair.first, pair.second);
                }
            }
        }
        inCursDeFolosire = false;
        notify();
    }

    public synchronized Set<Pair> clasament(){
        Set<Pair> clasament = new TreeSet<>();
        for (Integer id: punctaj.keySet())
            clasament.add(new Pair(id, punctaj.get(id)));
        return clasament;
    }

}
