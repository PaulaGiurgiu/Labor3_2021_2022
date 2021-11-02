package test;

import com.uni.model.Vorlesung;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VorlesungTest {
    private final Vorlesung v1 = new Vorlesung("BD", 1, 11, 30, 5);

    @Test
    void getName() {
        assertEquals("BD", v1.getName());
    }

    @Test
    void getLehrer() {
        assertEquals(1, v1.getLehrer());
    }

    @Test
    void getVorlesungID() {
        assertEquals(11, v1.getVorlesungID());
    }

    @Test
    void getMaxEnrollment() {
        assertEquals(30, v1.getMaxEnrollment());
    }

    @Test
    void getStudentsEnrolled() {
        assertEquals(0, v1.getStudentsEnrolled().size());
    }

    @Test
    void getCredits() {
        assertEquals(5, v1.getCredits());
    }

    @Test
    void setName() {
        v1.setName("BD2");
        assertEquals("BD2", v1.getName());
    }

    @Test
    void setLehrer() {
        v1.setLehrer(2);
        assertEquals(2, v1.getLehrer());
    }

    @Test
    void setVorlesungID() {
        v1.setVorlesungID(12);
        assertEquals(12, v1.getVorlesungID());
    }

    @Test
    void setMaxEnrollment() {
        v1.setMaxEnrollment(60);
        assertEquals(60, v1.getMaxEnrollment());
    }

    @Test
    void setStudentsEnrolled() {
        List<Long> list = new ArrayList<>();
        list.add(12L);
        v1.setStudentsEnrolled(list);
        assertEquals(1, v1.getStudentsEnrolled().size());
    }

    @Test
    void setCredits() {
        v1.setCredits(6);
        assertEquals(6, v1.getCredits());
    }
}