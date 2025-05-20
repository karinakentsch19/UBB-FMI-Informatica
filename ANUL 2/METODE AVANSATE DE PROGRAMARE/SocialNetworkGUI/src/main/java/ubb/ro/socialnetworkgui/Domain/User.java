package ubb.ro.socialnetworkgui.Domain;

import java.util.Objects;

public class User extends Entity<Long> {
    private String firstname, lastname, password;

    public User(String firstname, String lastname, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    /**
     * Return the user's name
     *
     * @return String
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Change the user's name
     *
     * @param firstname String
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Return the user's surname
     *
     * @return String
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Change the user's surname
     *
     * @param lastname String
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Return the user's password
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a new password for the user
     *
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
