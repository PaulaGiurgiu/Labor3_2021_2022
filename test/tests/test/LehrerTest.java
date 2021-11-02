package test;

import com.uni.model.Lehrer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LehrerTest {
    private final Lehrer l1 = new Lehrer("Alice", "Hart", 1);

    @Test
    void getVorname() {
        assertEquals("Alice", l1.getVorname());
    }

    @Test
    void getNachname() {
        assertEquals("Hart", l1.getNachname());
    }

    @Test
    void getLehrerID() {
        assertEquals(1, l1.getLehrerID());
    }

    @Test
    void getVorlesungen() {
        assertEquals(0, l1.getVorlesungen().size());
    }

    @Test
    void setVorname() {
        l1.setVorname("Zoe");
        assertEquals("Zoe", l1.getVorname());
    }

    @Test
    void setNachname() {
        l1.setNachname("Miller");
        assertEquals("Miller", l1.getNachname());
    }

    @Test
    void setLehrerID() {
        l1.setLehrerID(12);
        assertEquals(12, l1.getLehrerID());
    }

    @Test
    void setVorlesungen() {
        List<Long> list = new ArrayList<Long>();
        list.add(list.size(), 12L);
        list.add(list.size(), 14L);
        l1.setVorlesungen(list);
        assertEquals(2, l1.getVorlesungen().size());
    }
}