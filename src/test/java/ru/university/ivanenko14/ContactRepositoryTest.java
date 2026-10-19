package ru.university.ivanenko14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.university.ivanenko14.model.Contact;
import ru.university.ivanenko14.repository.ContactRepository;
import ru.university.ivanenko14.repository.ContactRepositoryInMemoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactRepositoryTest {

    private ContactRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ContactRepositoryInMemoryImpl();
    }

    @Test
    void testAddAndFindAllSorted() {
        // Проверяем сортировку по фамилии в алфавитном порядке
        Contact c1 = new Contact("Сидоров", "Иван", "+79991112233", null, null);
        Contact c2 = new Contact("Алексеев", "Петр", "+79992223344", null, null);
        Contact c3 = new Contact("Борисов", "Антон", "+79993334455", null, null);

        repository.add(c1);
        repository.add(c2);
        repository.add(c3);

        List<Contact> all = repository.findAll();

        assertEquals(3, all.size());
        assertEquals("Алексеев", all.get(0).getLastName());
        assertEquals("Борисов", all.get(1).getLastName());
        assertEquals("Сидоров", all.get(2).getLastName());
    }

    @Test
    void testSameNameSortByPhone() {
        // При совпадении имени и фамилии — сортировка по номеру телефона
        Contact c1 = new Contact("Иванов", "Иван", "+79999999999", null, null);
        Contact c2 = new Contact("Иванов", "Иван", "+71111111111", null, null);

        repository.add(c1);
        repository.add(c2);

        List<Contact> all = repository.findAll();

        assertEquals("+71111111111", all.get(0).getPhone1());
        assertEquals("+79999999999", all.get(1).getPhone1());
    }

    @Test
    void testFindByLastName() {
        Contact c1 = new Contact("Петров", "Алексей", "+79001234567", null, null);
        repository.add(c1);

        List<Contact> found = repository.findByLastName("Петров");
        assertEquals(1, found.size());
        assertEquals("Петров", found.get(0).getLastName());
    }

    @Test
    void testFindByPhone() {
        Contact c1 = new Contact("Смирнов", "Олег", "+79110001122", "+79220003344", null);
        repository.add(c1);

        // Ищем по второму номеру
        List<Contact> found = repository.findByPhone("0003344");
        assertEquals(1, found.size());
        assertEquals("Смирнов", found.get(0).getLastName());
    }

    @Test
    void testDelete() {
        Contact c1 = new Contact("Кузнецов", "Денис", "+79009998877", null, null);
        repository.add(c1);

        boolean deleted = repository.delete("Кузнецов", "Денис");
        assertTrue(deleted);
        assertEquals(0, repository.findAll().size());
    }
}