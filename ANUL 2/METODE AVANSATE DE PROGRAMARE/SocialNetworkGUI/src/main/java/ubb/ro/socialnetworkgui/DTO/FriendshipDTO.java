package ubb.ro.socialnetworkgui.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class FriendshipDTO {
    private Long id2;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private LocalDateTime meetingDate;

    private String name;

    public FriendshipDTO(Long id2, LocalDateTime meetingDate, String name) {
        this.id2 = id2;
        this.meetingDate = meetingDate;
        this.name = name;
    }


    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    public LocalDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendshipDTO that = (FriendshipDTO) o;
        return Objects.equals(id2, that.id2) && Objects.equals(meetingDate, that.meetingDate) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id2, meetingDate, name);
    }

    @Override
    public String toString() {
        return "FriendshipDTO{" +
                "id2=" + id2 +
                ", meetingDate=" + meetingDate +
                ", name='" + name + '\'' +
                '}';
    }
}
