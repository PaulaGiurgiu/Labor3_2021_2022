package test;

import com.uni.model.Lehrer;
import com.uni.model.Person;
import com.uni.model.Student;
import com.uni.model.Vorlesung;
import com.uni.repository.LehrerRepository;
import com.uni.repository.PersonRepository;
import com.uni.repository.StudentRepository;
import com.uni.repository.VorlesungRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryRepositoryTest {
    private final PersonRepository personRepository = new PersonRepository();
    private final Person p1 = new Person("Alice", "Hart");

    private final StudentRepository studentRepository = new StudentRepository();
    private final Student s1 = new Student("Zoe", "Miller", 11);

    private final LehrerRepository lehrerRepository = new LehrerRepository();
    private final Lehrer l1 = new Lehrer("Tom", "John", 1);

    private final VorlesungRepository vorlesungRepository = new VorlesungRepository();
    private final Vorlesung v1 = new Vorlesung("BD", 1, 11, 30, 5);

    @Test
    void findOne() {
        personRepository.save(p1);
        assertEquals(p1, personRepository.findOne(0));

        studentRepository.save(s1);
        assertEquals(s1, studentRepository.findOne(0));

        lehrerRepository.save(l1);
        assertEquals(l1, lehrerRepository.findOne(0));

        vorlesungRepository.save(v1);
        assertEquals(v1, vorlesungRepository.findOne(0));
    }

    @Test
    void findAll() {
        personRepository.save(p1);
        assertEquals(1, personRepository.findAll().size());

        studentRepository.save(s1);
        assertEquals(1, studentRepository.findAll().size());

        lehrerRepository.save(l1);
        assertEquals(1, lehrerRepository.findAll().size());

        vorlesungRepository.save(v1);
        assertEquals(1, vorlesungRepository.findAll().size());
    }

    @Test
    void save() {
        assertEquals(p1, personRepository.save(p1));

        assertEquals(s1, studentRepository.save(s1));

        assertEquals(l1, lehrerRepository.save(l1));

        assertEquals(v1, vorlesungRepository.save(v1));
    }

    @Test
    void delete() {
        personRepository.save(p1);
        assertEquals(1, personRepository.findAll().size());
        personRepository.delete(p1);
        assertEquals(0, personRepository.findAll().size());

        studentRepository.save(s1);
        assertEquals(1, studentRepository.findAll().size());
        studentRepository.delete(s1);
        assertEquals(0, studentRepository.findAll().size());

        lehrerRepository.save(l1);
        assertEquals(1, lehrerRepository.findAll().size());
        lehrerRepository.delete(l1);
        assertEquals(0, lehrerRepository.findAll().size());

        vorlesungRepository.save(v1);
        assertEquals(1, vorlesungRepository.findAll().size());
        vorlesungRepository.delete(v1);
        assertEquals(0, vorlesungRepository.findAll().size());

    }
}