package ru.university.ivanenko14.repository;

import ru.university.ivanenko14.model.Contact;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactRepositoryFileImpl implements ContactRepository {
    private final String filePath;
    private final List<Contact> contacts = new ArrayList<>();

    public ContactRepositoryFileImpl(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";", -1);
                if (parts.length >= 5) {
                    String lastName = parts[0];
                    String firstName = parts[1];
                    String phone1 = parts[2];
                    String phone2 = parts[3].isEmpty() ? null : parts[3];
                    String phone3 = parts[4].isEmpty() ? null : parts[4];
                    contacts.add(new Contact(lastName, firstName, phone1, phone2, phone3));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла контактов: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (Contact contact : contacts) {
                String line = String.format("%s;%s;%s;%s;%s",
                        contact.getLastName(),
                        contact.getFirstName(),
                        contact.getPhone1() != null ? contact.getPhone1() : "",
                        contact.getPhone2() != null ? contact.getPhone2() : "",
                        contact.getPhone3() != null ? contact.getPhone3() : "");
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }

    @Override
    public void add(Contact contact) {
        contacts.add(contact);
        saveToFile();
    }

    @Override
    public List<Contact> findAll() {
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
        boolean removed = contacts.removeIf(contact ->
                contact.getLastName().equalsIgnoreCase(lastName) &&
                        contact.getFirstName().equalsIgnoreCase(firstName));
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    @Override
    public boolean update(String oldLastName, String oldFirstName, Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact current = contacts.get(i);
            if (current.getLastName().equalsIgnoreCase(oldLastName) &&
                    current.getFirstName().equalsIgnoreCase(oldFirstName)) {
                contacts.set(i, updatedContact);
                saveToFile();
                return true;
            }
        }
        return false;
    }
}