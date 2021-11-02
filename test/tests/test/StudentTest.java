package test;

import com.uni.model.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private final Student s1 = new Student("Alice", "Hart", 1);

    @Test
    void getVorname() {
        assertEquals("Alice", s1.getVorname());
    }

    @Test
    void getNachname() {
        assertEquals("Hart", s1.getNachname());
    }

    @Test
    void getStudentID() {
        assertEquals(1, s1.getStudentID());
    }

    @Test
    void getTotalCredits() {
        assertEquals(0, s1.getTotalCredits());
    }

    @Test
    void getEnrolledCourses() {
        assertEquals(0, s1.getEnrolledCourses().size());
    }

    @Test
    void setVorname() {
        s1.setVorname("Zoe");
        assertEquals("Zoe", s1.getVorname());
    }

    @Test
    void setNachname() {
        s1.setNachname("Miller");
        assertEquals("Miller", s1.getNachname());
    }

    @Test
    void setStudentID() {
        s1.setStudentID(11);
        assertEquals(11, s1.getStudentID());
    }

    @Test
    void setTotalCredits() {
        s1.setTotalCredits(2);
        assertEquals(2, s1.getTotalCredits());
    }

    @Test
    void setEnrolledCourses() {
        List<Long> list = new ArrayList<Long>();
        list.add(list.size(), 12L);
        s1.setEnrolledCourses(list);
        assertEquals(1, s1.getEnrolledCourses().size());
    }
}