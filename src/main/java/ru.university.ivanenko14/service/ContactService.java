package ru.university.ivanenko14.service;

import ru.university.ivanenko14.exception.BusinessException;
import ru.university.ivanenko14.exception.ContactNotFoundException;
import ru.university.ivanenko14.model.Contact;
import ru.university.ivanenko14.repository.ContactRepository;

import java.util.List;

public class ContactService {
    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    // Добавление контакта с базовой валидацией
    public void addContact(String lastName, String firstName, String phone1, String phone2, String phone3) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new BusinessException("Фамилия не может быть пустой!");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new BusinessException("Имя не может быть пустым!");
        }
        if (phone1 == null || phone1.trim().isEmpty()) {
            throw new BusinessException("Первый номер телефона обязателен для заполнения!");
        }

        Contact contact = new Contact(
                lastName.trim(),
                firstName.trim(),
                phone1.trim(),
                phone2 != null ? phone2.trim() : null,
                phone3 != null ? phone3.trim() : null
        );
        repository.add(contact);
    }

    // Получение всех контактов (уже отсортированных)
    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    // Поиск по фамилии
    public List<Contact> findByLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new BusinessException("Введите фамилию для поиска!");
        }
        List<Contact> found = repository.findByLastName(lastName.trim());
        if (found.isEmpty()) {
            throw new ContactNotFoundException("Контакты с фамилией '" + lastName + "' не найдены.");
        }
        return found;
    }

    // Поиск по номеру телефона
    public List<Contact> findByPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new BusinessException("Введите номер телефона для поиска!");
        }
        List<Contact> found = repository.findByPhone(phone.trim());
        if (found.isEmpty()) {
            throw new ContactNotFoundException("Контакты с номером '" + phone + "' не найдены.");
        }
        return found;
    }

    // Удаление контакта
    public void deleteContact(String lastName, String firstName) {
        boolean deleted = repository.delete(lastName.trim(), firstName.trim());
        if (!deleted) {
            throw new ContactNotFoundException("Не удалось удалить: абонент " + lastName + " " + firstName + " не найден.");
        }
    }

    // Редактирование контакта
    public void updateContact(String oldLastName, String oldFirstName,
                              String newLastName, String newFirstName,
                              String newPhone1, String newPhone2, String newPhone3) {
        if (newLastName == null || newLastName.trim().isEmpty()) {
            throw new BusinessException("Новая фамилия не может быть пустой!");
        }
        if (newFirstName == null || newFirstName.trim().isEmpty()) {
            throw new BusinessException("Новое имя не может быть пустым!");
        }
        if (newPhone1 == null || newPhone1.trim().isEmpty()) {
            throw new BusinessException("Первый номер телефона обязателен!");
        }

        Contact updatedContact = new Contact(
                newLastName.trim(),
                newFirstName.trim(),
                newPhone1.trim(),
                newPhone2 != null ? newPhone2.trim() : null,
                newPhone3 != null ? newPhone3.trim() : null
        );

        boolean updated = repository.update(oldLastName.trim(), oldFirstName.trim(), updatedContact);
        if (!updated) {
            throw new ContactNotFoundException("Не удалось обновить: абонент " + oldLastName + " " + oldFirstName + " не найден.");
        }
    }
}