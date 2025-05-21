package com.example.ofertedevacanta.Domain;

import com.example.ofertedevacanta.Utils.Hobby;

import java.util.Objects;

public class Client extends Entity<Long>{
    private String name;
    private Integer fidelityGrade, age;

    private Hobby hobby;

    public Client(String name, Integer fidelityGrade, Integer age, Hobby hobby) {
        this.name = name;
        this.fidelityGrade = fidelityGrade;
        this.age = age;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFidelityGrade() {
        return fidelityGrade;
    }

    public void setFidelityGrade(Integer fidelityGrade) {
        this.fidelityGrade = fidelityGrade;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) && Objects.equals(fidelityGrade, client.fidelityGrade) && Objects.equals(age, client.age) && hobby == client.hobby;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fidelityGrade, age, hobby);
    }
}
