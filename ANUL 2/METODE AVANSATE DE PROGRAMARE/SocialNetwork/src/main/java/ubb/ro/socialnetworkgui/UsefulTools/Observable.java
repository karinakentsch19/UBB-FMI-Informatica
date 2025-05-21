package ubb.ro.socialnetworkgui.UsefulTools;

public interface Observable<E extends TargetedWindow> {

    public void addObserver(Observer<E> observer);

    public void deleteObserver(Observer<E> observer);

    public void notifyAllObservers(E event);

}
