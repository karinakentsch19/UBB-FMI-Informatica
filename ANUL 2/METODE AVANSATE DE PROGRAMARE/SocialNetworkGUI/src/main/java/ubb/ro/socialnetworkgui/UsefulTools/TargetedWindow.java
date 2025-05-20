package ubb.ro.socialnetworkgui.UsefulTools;

import java.util.Objects;

public class TargetedWindow {
    private Long id;

    EntitiesThatChange event;

    public TargetedWindow(Long id, EntitiesThatChange event) {
        this.id = id;
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public EntitiesThatChange getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetedWindow that = (TargetedWindow) o;
        return Objects.equals(id, that.id) && event == that.event;
    }

    @Override
    public String toString() {
        return "TargetedWindow{" +
                "id=" + id +
                ", event=" + event +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, event);
    }
}
