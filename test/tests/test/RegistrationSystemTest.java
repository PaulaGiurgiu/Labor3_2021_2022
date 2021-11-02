package test;

import com.uni.model.Lehrer;
import com.uni.model.Student;
import com.uni.model.Vorlesung;
import com.uni.repository.RegistrationSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class RegistrationSystemTest {
    private RegistrationSystem registrationSystem = new RegistrationSystem();

    private final Student s1 = new Student("Zoe", "Miller", 1111);
    private final Student s2 = new Student("Alice", "Hart", 1112);
    private final Student s3 = new Student("Alice", "Miller", 1113);

    private final Lehrer l1 = new Lehrer("Tom", "John", 1);
    private final Lehrer l2 = new Lehrer("Jack", "Storm", 2);

    private final Vorlesung v1 = new Vorlesung("BD", l1.getLehrerID(), 100, 30, 5);
    private final Vorlesung v2 = new Vorlesung("BD2", l2.getLehrerID(), 101, 31, 6);
    private final Vorlesung v3 = new Vorlesung("BD3", l1.getLehrerID(), 102, 32, 7);

    public RegistrationSystemTest(){

        registrationSystem.getStudentRepository().save(s1);
        registrationSystem.getStudentRepository().save(s2);

        registrationSystem.getLehrerRepository().save(l1);
        registrationSystem.getLehrerRepository().save(l2);

        registrationSystem.getVorlesungRepository().save(v1);
        registrationSystem.getVorlesungRepository().save(v2);
        registrationSystem.getVorlesungRepository().save(v3);
    }

    @Test
    void register() throws IOException {
        Assertions.assertTrue(registrationSystem.register(v1, s1));
        Assertions.assertTrue(registrationSystem.register(v1, s2));
        Assertions.assertTrue(registrationSystem.register(v2, s1));
        Assertions.assertTrue(registrationSystem.register(v2, s2));

        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(100, registrationSystem.getVorlesungRepository().findOne(0).getVorlesungID());
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(0).getStudentsEnrolled());


        list.clear();
        Assertions.assertEquals(101, registrationSystem.getVorlesungRepository().findOne(1).getVorlesungID());
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(1).getStudentsEnrolled());

        list.clear();
        Assertions.assertEquals(102, registrationSystem.getVorlesungRepository().findOne(2).getVorlesungID());
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(2).getStudentsEnrolled());

        Assertions.assertEquals(1111, registrationSystem.getStudentRepository().findOne(0).getStudentID());
        list.add(100L);
        list.add(101L);
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(0).getEnrolledCourses());
        Assertions.assertEquals(11, registrationSystem.getStudentRepository().findOne(0).getTotalCredits());

        Assertions.assertEquals(1112, registrationSystem.getStudentRepository().findOne(1).getStudentID());
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(1).getEnrolledCourses());
        Assertions.assertEquals(11, registrationSystem.getStudentRepository().findOne(1).getTotalCredits());

        list.clear();
        Assertions.assertEquals(1, registrationSystem.getLehrerRepository().findOne(0).getLehrerID());
        list.add(v1.getVorlesungID());
        list.add(v3.getVorlesungID());
        Assertions.assertEquals(list, registrationSystem.getLehrerRepository().findOne(0).getVorlesungen());

        list.clear();
        Assertions.assertEquals(2, registrationSystem.getLehrerRepository().findOne(1).getLehrerID());
        list.add(v2.getVorlesungID());
        Assertions.assertEquals(list, registrationSystem.getLehrerRepository().findOne(1).getVorlesungen());

    }

    @Test
    void testRegister() throws IOException {
        Assertions.assertTrue(registrationSystem.register(v1.getVorlesungID(), s1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v1.getVorlesungID(), s2.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v2.getVorlesungID(), s1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v2.getVorlesungID(), s2.getStudentID()));
        try {
            Assertions.assertTrue(registrationSystem.register(v3.getVorlesungID(), s3.getStudentID()));
        }
        catch (IOException ioException){
            System.out.println("Die Daten konnten nicht aktualisieren. Bitte versuch mit anderen eingefugten Daten.\n");
        }

        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(100, registrationSystem.getVorlesungRepository().findOne(0).getVorlesungID());
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(0).getStudentsEnrolled());


        list.clear();
        Assertions.assertEquals(101, registrationSystem.getVorlesungRepository().findOne(1).getVorlesungID());
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(1).getStudentsEnrolled());

        list.clear();
        Assertions.assertEquals(102, registrationSystem.getVorlesungRepository().findOne(2).getVorlesungID());
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(2).getStudentsEnrolled());

        Assertions.assertEquals(1111, registrationSystem.getStudentRepository().findOne(0).getStudentID());
        list.add(100L);
        list.add(101L);
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(0).getEnrolledCourses());
        Assertions.assertEquals(11, registrationSystem.getStudentRepository().findOne(0).getTotalCredits());

        Assertions.assertEquals(1112, registrationSystem.getStudentRepository().findOne(1).getStudentID());
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(1).getEnrolledCourses());
        Assertions.assertEquals(11, registrationSystem.getStudentRepository().findOne(1).getTotalCredits());

        list.clear();
        Assertions.assertEquals(1, registrationSystem.getLehrerRepository().findOne(0).getLehrerID());
        list.add(v1.getVorlesungID());
        list.add(v3.getVorlesungID());
        Assertions.assertEquals(list, registrationSystem.getLehrerRepository().findOne(0).getVorlesungen());

        list.clear();
        Assertions.assertEquals(2, registrationSystem.getLehrerRepository().findOne(1).getLehrerID());
        list.add(v2.getVorlesungID());
        Assertions.assertEquals(list, registrationSystem.getLehrerRepository().findOne(1).getVorlesungen());
    }

    @Test
    void unregister() throws IOException {
        register();

        registrationSystem.unregister(v1.getVorlesungID(), s1.getStudentID());

        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(100, registrationSystem.getVorlesungRepository().findOne(0).getVorlesungID());
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(0).getStudentsEnrolled());

        list.clear();
        Assertions.assertEquals(101, registrationSystem.getVorlesungRepository().findOne(1).getVorlesungID());
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(1).getStudentsEnrolled());

        list.clear();
        Assertions.assertEquals(1111, registrationSystem.getStudentRepository().findOne(0).getStudentID());
        list.add(101L);
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(0).getEnrolledCourses());
        Assertions.assertEquals(6, registrationSystem.getStudentRepository().findOne(0).getTotalCredits());

        list.clear();
        Assertions.assertEquals(1112, registrationSystem.getStudentRepository().findOne(1).getStudentID());
        list.add(100L);
        list.add(101L);
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(1).getEnrolledCourses());
        Assertions.assertEquals(11, registrationSystem.getStudentRepository().findOne(1).getTotalCredits());


        registrationSystem.unregister(v2.getVorlesungID(), s2.getStudentID());

        list.clear();
        Assertions.assertEquals(100, registrationSystem.getVorlesungRepository().findOne(0).getVorlesungID());
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(0).getStudentsEnrolled());

        list.clear();
        Assertions.assertEquals(101, registrationSystem.getVorlesungRepository().findOne(1).getVorlesungID());
        list.add(1111L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(1).getStudentsEnrolled());

        list.clear();
        Assertions.assertEquals(1111, registrationSystem.getStudentRepository().findOne(0).getStudentID());
        list.add(101L);
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(0).getEnrolledCourses());
        Assertions.assertEquals(6, registrationSystem.getStudentRepository().findOne(0).getTotalCredits());

        list.clear();
        Assertions.assertEquals(1112, registrationSystem.getStudentRepository().findOne(1).getStudentID());
        list.add(100L);
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(1).getEnrolledCourses());
        Assertions.assertEquals(5, registrationSystem.getStudentRepository().findOne(1).getTotalCredits());

    }

    @Test
    void retrieveCoursesWithFreePlaces() throws IOException {
        Assertions.assertTrue(registrationSystem.register(v1.getVorlesungID(), s1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v1.getVorlesungID(), s2.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v2.getVorlesungID(), s1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v2.getVorlesungID(), s2.getStudentID()));

        HashMap<Integer, Vorlesung> map = new HashMap<Integer, Vorlesung>();
        map.put(32, v3);
        map.put(28, v1);
        map.put(29, v2);
        Assertions.assertEquals(map, registrationSystem.retrieveCoursesWithFreePlaces());

    }

    @Test
    void retrieveStudentsEnrolledForACourse() throws IOException {
        Assertions.assertTrue(registrationSystem.register(v1.getVorlesungID(), s1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v1.getVorlesungID(), s2.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v2.getVorlesungID(), s1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v2.getVorlesungID(), s2.getStudentID()));

        List<Long> list = new ArrayList<>();
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.retrieveStudentsEnrolledForACourse(v1.getVorlesungID()));
        Assertions.assertEquals(list, registrationSystem.retrieveStudentsEnrolledForACourse(v2.getVorlesungID()));
        list.clear();
        Assertions.assertEquals(list, registrationSystem.retrieveStudentsEnrolledForACourse(v3.getVorlesungID()));

    }

    @Test
    void getAllCourses() throws IOException {
        register();
        Assertions.assertEquals(3, registrationSystem.getAllCourses().size());
    }

    @Test
    void getAllStudents() throws IOException {
        register();
        Assertions.assertEquals(2, registrationSystem.getAllStudents().size());
    }

    @Test
    void getAllLehrer() throws IOException {
        register();
        Assertions.assertEquals(2, registrationSystem.getAllLehrer().size());
    }

    @Test
    void changeCreditFromACourse() throws IOException {
        Assertions.assertTrue(registrationSystem.register(v1.getVorlesungID(), s1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v2.getVorlesungID(), s1.getStudentID()));

        Assertions.assertTrue(registrationSystem.changeCreditFromACourse(100, 10));
        Assertions.assertEquals(1111, registrationSystem.getStudentRepository().findOne(0).getStudentID());
        Assertions.assertEquals(16, registrationSystem.getStudentRepository().findOne(0).getTotalCredits());
    }

    @Test
    void deleteVorlesungFromLehrer() throws IOException {
        Assertions.assertTrue(registrationSystem.register(v1.getVorlesungID(), s1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v1.getVorlesungID(), s2.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v2.getVorlesungID(), s1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(v2.getVorlesungID(), s2.getStudentID()));

        Assertions.assertTrue(registrationSystem.deleteVorlesungFromLehrer(l1.getLehrerID(), v1.getVorlesungID()));
        Assertions.assertTrue(!registrationSystem.deleteVorlesungFromLehrer(l1.getLehrerID(), v2.getVorlesungID()));

        List<Vorlesung> list = new ArrayList<>();
        list.add(v2);
        list.add(v3);
        Assertions.assertEquals(list, registrationSystem.getAllCourses());
    }
}