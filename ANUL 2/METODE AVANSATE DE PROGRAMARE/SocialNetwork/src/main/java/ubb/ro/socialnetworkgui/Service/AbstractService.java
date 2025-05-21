package ubb.ro.socialnetworkgui.Service;

import ubb.ro.socialnetworkgui.Domain.Entity;
import ubb.ro.socialnetworkgui.UsefulTools.EntitiesThatChange;
import ubb.ro.socialnetworkgui.UsefulTools.Observable;
import ubb.ro.socialnetworkgui.UsefulTools.Observer;
import ubb.ro.socialnetworkgui.UsefulTools.TargetedWindow;

import java.util.List;

public class AbstractService implements Observable<TargetedWindow> {
    List<Observer<TargetedWindow>> observerList;

    public AbstractService(List<Observer<TargetedWindow>>observerList) {
        this.observerList = observerList;
    }

    @Override
    public void addObserver(Observer<TargetedWindow> observer) {
        observerList.add(observer);
    }

    @Override
    public void deleteObserver(Observer<TargetedWindow> observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyAllObservers(TargetedWindow event) {
        observerList.forEach(observer -> observer.update(event));
    }
}
