package ubb.ro.socialnetworkgui.Domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Friendship extends Entity<Pair<Long,Long>>{
    private Long idUser1, idUser2;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private LocalDateTime meetingDate;

    public Friendship(Long idUser1, Long idUser2) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.meetingDate = LocalDateTime.now();
    }

    public Friendship(Long idUser1, Long idUser2, LocalDateTime meetingDate) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.meetingDate = meetingDate;
    }

    /**
     *
     * @return first user part of the friendhsip
     */
    public Long getUser1() {
        return idUser1;
    }

    /**
     * Sets the first user who's part of the friendhsip
     * @param idUser11 Long
     */
    public void setUser1(Long idUser11) {
        this.idUser1 = idUser11;
    }

    /**
     *
     * @return second user part of the friendhsip
     */
    public Long getUser2() {
        return idUser2;
    }
    /**
     * Sets the second user who's part of the friendhsip
     * @param idUser12 Long
     */
    public void setUser2(Long idUser12) {
        this.idUser1 = idUser12;
    }

    /**
     *
     * @return the meeting date of the friendhsip
     */
    public LocalDateTime getMeetingDate() {
        return meetingDate;
    }

    /**
     * Sets a new meeting date for a friendhsip
     * @param meetingDate LocalDate
     */
    public void setMeetingDate(LocalDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(idUser1, that.idUser1) && Objects.equals(idUser2, that.idUser2) && Objects.equals(meetingDate, that.meetingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser1, idUser2, meetingDate);
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "idUser1=" + idUser1 +
                ", idUser2=" + idUser2 +
                ", meetingDate=" + meetingDate.format(dateTimeFormatter) +
                '}';
    }
}
