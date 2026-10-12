package ru.university.ivanenko14.model;

import java.util.Objects;

public class Contact implements Comparable<Contact> {
    private String lastName;
    private String firstName;
    private String phone1;
    private String phone2;
    private String phone3;

    public Contact(String lastName, String firstName, String phone1, String phone2, String phone3) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
    }

    // Геттеры и сеттеры
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getPhone1() { return phone1; }
    public void setPhone1(String phone1) { this.phone1 = phone1; }

    public String getPhone2() { return phone2; }
    public void setPhone2(String phone2) { this.phone2 = phone2; }

    public String getPhone3() { return phone3; }
    public void setPhone3(String phone3) { this.phone3 = phone3; }


    @Override
    public int compareTo(Contact other) {
        int result = this.lastName.compareToIgnoreCase(other.lastName);
        if (result != 0) return result;

        result = this.firstName.compareToIgnoreCase(other.firstName);
        if (result != 0) return result;

        String p1 = this.phone1 != null ? this.phone1 : "";
        String p2 = other.phone1 != null ? other.phone1 : "";
        return p1.compareTo(p2);
    }

    @Override
    public String toString() {
        return "Абонент: " + lastName + " " + firstName +
                " | Тел 1: " + phone1 +
                (phone2 != null && !phone2.isEmpty() ? " | Тел 2: " + phone2 : "") +
                (phone3 != null && !phone3.isEmpty() ? " | Тел 3: " + phone3 : "");
    }
}