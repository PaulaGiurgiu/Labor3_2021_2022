package test;

import com.uni.model.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private final Person p1 = new Person("Alice", "Hart");

    @Test
    void getVorname() {
        assertEquals("Alice", p1.getVorname());
    }

    @Test
    void getNachname() {
        assertEquals("Hart", p1.getNachname());
    }

    @Test
    void setVorname() {
        p1.setVorname("Zoe");
        assertEquals("Zoe", p1.getVorname());
    }

    @Test
    void setNachname() {
        p1.setNachname("Miller");
        assertEquals("Miller", p1.getNachname());
    }
}