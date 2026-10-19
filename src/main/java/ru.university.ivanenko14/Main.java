package ru.university.ivanenko14;

import ru.university.ivanenko14.controller.CmdController;
import ru.university.ivanenko14.repository.ContactRepository;
import ru.university.ivanenko14.repository.ContactRepositoryInMemoryImpl;
import ru.university.ivanenko14.service.ContactService;

public class Main {
    public static void main(String[] args) {
        // Создаем слои приложения:
        // 1. Репозиторий
        ContactRepository repository = new ContactRepositoryInMemoryImpl();

        // 2. Сервис (передаем ему репозиторий)
        ContactService service = new ContactService(repository);

        // 3. Контроллер консоли (передаем ему сервис)
        CmdController controller = new CmdController(service);

        // Запуск интерфейса
        controller.run();
    }
}