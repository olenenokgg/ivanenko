package ru.university.ivanenko14.controller;

import ru.university.ivanenko14.exception.BusinessException;
import ru.university.ivanenko14.model.Contact;
import ru.university.ivanenko14.service.ContactService;

import java.util.List;
import java.util.Scanner;

public class CmdController {
    private final ContactService contactService;
    private final Scanner scanner;

    public CmdController(ContactService contactService) {
        this.contactService = contactService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim();

            try {
                switch (command) {
                    case "1":
                        showAllContacts();
                        break;
                    case "2":
                        addContact();
                        break;
                    case "3":
                        findByLastName();
                        break;
                    case "4":
                        findByPhone();
                        break;
                    case "5":
                        editContact();
                        break;
                    case "6":
                        deleteContact();
                        break;
                    case "0":
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Неверная команда! Попробуйте снова.");
                }
            } catch (BusinessException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("=== ТЕЛЕФОННЫЙ СПРАВОЧНИК ===");
        System.out.println("1. Показать всех абонентов (по алфавиту)");
        System.out.println("2. Добавить абонента");
        System.out.println("3. Найти абонента по фамилии");
        System.out.println("4. Найти абонента по номеру телефона");
        System.out.println("5. Редактировать абонента");
        System.out.println("6. Удалить абонента");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void showAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("Справочник пуст.");
            return;
        }
        System.out.println("\n--- Список абонентов ---");
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    private void addContact() {
        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите телефон 1 (обязательно): ");
        String phone1 = scanner.nextLine();
        System.out.print("Введите телефон 2 (необязательно, Enter чтобы пропустить): ");
        String phone2 = scanner.nextLine();
        System.out.print("Введите телефон 3 (необязательно, Enter чтобы пропустить): ");
        String phone3 = scanner.nextLine();

        contactService.addContact(lastName, firstName, phone1, phone2, phone3);
        System.out.println("Абонент успешно добавлен!");
    }

    private void findByLastName() {
        System.out.print("Введите фамилию для поиска: ");
        String lastName = scanner.nextLine();
        List<Contact> found = contactService.findByLastName(lastName);
        System.out.println("\nНайденные абоненты:");
        for (Contact c : found) {
            System.out.println(c);
        }
    }

    private void findByPhone() {
        System.out.print("Введите номер телефона для поиска: ");
        String phone = scanner.nextLine();
        List<Contact> found = contactService.findByPhone(phone);
        System.out.println("\nНайденные абоненты:");
        for (Contact c : found) {
            System.out.println(c);
        }
    }

    private void editContact() {
        System.out.print("Введите текущую фамилию абонента: ");
        String oldLastName = scanner.nextLine();
        System.out.print("Введите текущее имя абонента: ");
        String oldFirstName = scanner.nextLine();

        System.out.println("Введите новые данные:");
        System.out.print("Новая фамилия: ");
        String newLastName = scanner.nextLine();
        System.out.print("Новое имя: ");
        String newFirstName = scanner.nextLine();
        System.out.print("Новый телефон 1: ");
        String newPhone1 = scanner.nextLine();
        System.out.print("Новый телефон 2 (Enter чтобы пропустить): ");
        String newPhone2 = scanner.nextLine();
        System.out.print("Новый телефон 3 (Enter чтобы пропустить): ");
        String newPhone3 = scanner.nextLine();

        contactService.updateContact(oldLastName, oldFirstName, newLastName, newFirstName, newPhone1, newPhone2, newPhone3);
        System.out.println("Данные абонента обновлены!");
    }

    private void deleteContact() {
        System.out.print("Введите фамилию абонента для удаления: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите имя абонента для удаления: ");
        String firstName = scanner.nextLine();

        contactService.deleteContact(lastName, firstName);
        System.out.println("Абонент удален.");
    }
}