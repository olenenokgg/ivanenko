package ru.university.ivanenko14.repository;

import ru.university.ivanenko14.model.Contact;
import java.util.List;

public interface ContactRepository {

    void add(Contact contact);

    List<Contact> findAll();

    List<Contact> findByLastName(String lastName);

    List<Contact> findByPhone(String phone);

    boolean delete(String lastName, String firstName);

    boolean update(String oldLastName, String oldFirstName, Contact updatedContact);
}