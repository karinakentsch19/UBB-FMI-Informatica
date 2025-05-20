import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.util.LinkedList;
import java.util.Queue;

public class ThreadQueue {
    private Queue<Pair> listaPunctaje;

    private int readers;

    private boolean finished;

    public ThreadQueue(int readers) {
        this.readers = readers;
        this.listaPunctaje = new LinkedList<>();
        this.finished = readers != 0;
    }

    public synchronized void produce(Pair pair) throws InterruptedException{
        listaPunctaje.add(pair);
        notify();
    }

    public synchronized Pair consume() throws InterruptedException{
        while(listaPunctaje.isEmpty() && !finished){
            wait();
        }
        if (!listaPunctaje.isEmpty()){
            Pair pair = listaPunctaje.poll();
            notify();
            return pair;
        }
        return null;
    }

    public synchronized boolean hasValue(){
        return !listaPunctaje.isEmpty() || readers > 0;
    }

    public synchronized void finish(){
        readers--;
        if (readers == 0){
            finished = true;
            notifyAll();
        }
    }
}
