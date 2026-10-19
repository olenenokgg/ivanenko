package ru.university.ivanenko14;

import ru.university.ivanenko14.controller.CmdController;
import ru.university.ivanenko14.repository.ContactRepository;
import ru.university.ivanenko14.repository.ContactRepositoryFileImpl;
import ru.university.ivanenko14.service.ContactService;

public class Main {
    public static void main(String[] args) {
        // Указываем имя файла для сохранения данных
        ContactRepository repository = new ContactRepositoryFileImpl("contacts.txt");
        ContactService service = new ContactService(repository);
        CmdController controller = new CmdController(service);

        controller.run();
    }
}