package ru.university.ivanenko14.repository;

import ru.university.ivanenko14.model.Contact;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactRepositoryInMemoryImpl implements ContactRepository {
    private final List<Contact> contacts = new ArrayList<>();

    @Override
    public void add(Contact contact) {
        contacts.add(contact);
    }

    @Override
    public List<Contact> findAll() {
        // Копируем список и сортируем согласно правилам из класса Contact (compareTo)
        List<Contact> sortedList = new ArrayList<>(contacts);
        Collections.sort(sortedList);
        return sortedList;
    }

    @Override
    public List<Contact> findByLastName(String lastName) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getLastName().equalsIgnoreCase(lastName)) {
                result.add(contact);
            }
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public List<Contact> findByPhone(String phone) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            boolean matches = (contact.getPhone1() != null && contact.getPhone1().contains(phone))
                    || (contact.getPhone2() != null && contact.getPhone2().contains(phone))
                    || (contact.getPhone3() != null && contact.getPhone3().contains(phone));
            if (matches) {
                result.add(contact);
            }
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public boolean delete(String lastName, String firstName) {
        return contacts.removeIf(contact ->
                contact.getLastName().equalsIgnoreCase(lastName) &&
                        contact.getFirstName().equalsIgnoreCase(firstName));
    }

    @Override
    public boolean update(String oldLastName, String oldFirstName, Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact current = contacts.get(i);
            if (current.getLastName().equalsIgnoreCase(oldLastName) &&
                    current.getFirstName().equalsIgnoreCase(oldFirstName)) {
                contacts.set(i, updatedContact);
                return true;
            }
        }
        return false;
    }
}