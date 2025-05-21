package ubb.ro.socialnetworkgui.UsefulTools;

import ubb.ro.socialnetworkgui.Domain.Entity;

public interface Observer<Event extends TargetedWindow> {
    public void update(Event event);
}