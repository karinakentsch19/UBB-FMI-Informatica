package ubb.ro.socialnetworkgui.Domain;

import java.util.Objects;

public class FriendRequest extends Entity<Pair<Long,Long>>{
    private Long idUserFrom;

    private Long idUserTo;

    private String status;

    public FriendRequest(Long idUserFrom, Long idUserTo, String state) {
        this.idUserFrom = idUserFrom;
        this.idUserTo = idUserTo;
        this.status = state;
    }

    public Long getIdUserFrom() {
        return idUserFrom;
    }

    public void setIdUserFrom(Long idUserFrom) {
        this.idUserFrom = idUserFrom;
    }

    public Long getIdUserTo() {
        return idUserTo;
    }

    public void setIdUserTo(Long idUserTo) {
        this.idUserTo = idUserTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest that = (FriendRequest) o;
        return Objects.equals(idUserFrom, that.idUserFrom) && Objects.equals(idUserTo, that.idUserTo) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserFrom, idUserTo, status);
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "idUserFrom=" + idUserFrom +
                ", idUserTo=" + idUserTo +
                ", state='" + status + '\'' +
                '}';
    }
}
