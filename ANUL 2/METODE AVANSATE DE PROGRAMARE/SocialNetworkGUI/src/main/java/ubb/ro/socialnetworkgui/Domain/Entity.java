package ubb.ro.socialnetworkgui.Domain;

public class Entity<ID> {
    ID id;

    /**
     *
     * @return id
     */
    public ID getId() {
        return id;
    }

    /**
     *
     * @param id new id
     */
    public void setId(ID id) {
        this.id = id;
    }
}
