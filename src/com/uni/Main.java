package com.uni;

import com.uni.model.Lehrer;
import com.uni.model.Student;
import com.uni.model.Vorlesung;
import com.uni.repository.RegistrationSystem;

import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Student s1 = new Student("Zoe", "Miller", 1111);
        Student s2 = new Student("Alice", "Hart", 1112);

        Lehrer l1 = new Lehrer("Tom", "John", 1);
        Lehrer l2 = new Lehrer("Jack", "Storm", 2);

        Vorlesung v1 = new Vorlesung("BD", l1.getLehrerID(), 100, 30, 5);
        Vorlesung v2 = new Vorlesung("BD2", l2.getLehrerID(), 101, 31, 6);
        Vorlesung v3 = new Vorlesung("BD3", l1.getLehrerID(), 102, 32, 7);

        RegistrationSystem registrationSystem = new RegistrationSystem();
        registrationSystem.getStudentRepository().save(s1);
        registrationSystem.getStudentRepository().save(s2);

        registrationSystem.getLehrerRepository().save(l1);
        registrationSystem.getLehrerRepository().save(l2);

        registrationSystem.getVorlesungRepository().save(v1);
        registrationSystem.getVorlesungRepository().save(v2);
        registrationSystem.getVorlesungRepository().save(v3);

        System.out.println("Studenten List");
        for (Student s:registrationSystem.getAllStudents()) {
            System.out.println(s);
        }

        System.out.println("Lehrer List");
        for (Lehrer l:registrationSystem.getAllLehrer()) {
            System.out.println(l);
        }
        System.out.println("Vorlesungen List");
        for (Vorlesung v:registrationSystem.getAllCourses()) {
            System.out.println(v);
        }

        registrationSystem.register(v1, s1);
        registrationSystem.register(v1.getVorlesungID(), s2.getStudentID());
        registrationSystem.register(v2, s1);
        registrationSystem.register(v2.getVorlesungID(), s2.getStudentID());

        System.out.println("\n");
        System.out.println("Studenten List");
        for (Student s:registrationSystem.getAllStudents()) {
            System.out.println(s);
        }

        System.out.println("Lehrer List");
        for (Lehrer l:registrationSystem.getAllLehrer()) {
            System.out.println(l);
        }
        System.out.println("Vorlesungen List");
        for (Vorlesung v:registrationSystem.getAllCourses()) {
            System.out.println(v);
        }

        registrationSystem.unregister(v2.getVorlesungID(), s2.getStudentID());

        System.out.println("\n");
        System.out.println("Angemeldeten Studenten fur Vorlesung");
        System.out.println(registrationSystem.retrieveStudentsEnrolledForACourse(v1.getVorlesungID()));
        System.out.println(registrationSystem.retrieveStudentsEnrolledForACourse(v2.getVorlesungID()));
        System.out.println(registrationSystem.retrieveStudentsEnrolledForACourse(v3.getVorlesungID()));

        System.out.println("Freie Platze");
        for (Map.Entry m: registrationSystem.retrieveCoursesWithFreePlaces().entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue());
        }

        registrationSystem.changeCreditFromACourse(100, 10);
        System.out.println("Vorlesungen List");
        for (Vorlesung v:registrationSystem.getAllCourses()) {
            System.out.println(v);
        }

        System.out.println("Delete Vorlesung");
        registrationSystem.deleteVorlesungFromLehrer(l1.getLehrerID(), v3.getVorlesungID());
        System.out.println("Vorlesungen List");
        for (Vorlesung v:registrationSystem.getAllCourses()) {
            System.out.println(v);
        }
    }
}
