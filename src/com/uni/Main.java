package com.uni;

import com.uni.model.Lehrer;
import com.uni.model.Student;
import com.uni.model.Vorlesung;
import com.uni.repository.RegistrationSystem;

import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Student student1 = new Student("Zoe", "Miller", 1111);
        Student student2 = new Student("Alice", "Hart", 1112);

        Lehrer lehrer1 = new Lehrer("Tom", "John", 1);
        Lehrer lehrer2 = new Lehrer("Jack", "Storm", 2);

        Vorlesung vorlesung1 = new Vorlesung("BD", lehrer1.getLehrerID(), 100, 30, 5);
        Vorlesung vorlesung2 = new Vorlesung("BD2", lehrer2.getLehrerID(), 101, 31, 6);
        Vorlesung vorlesung3 = new Vorlesung("BD3", lehrer1.getLehrerID(), 102, 32, 7);

        RegistrationSystem registrationSystem = new RegistrationSystem();
        registrationSystem.getStudentRepository().save(student1);
        registrationSystem.getStudentRepository().save(student2);

        registrationSystem.getLehrerRepository().save(lehrer1);
        registrationSystem.getLehrerRepository().save(lehrer2);

        registrationSystem.getVorlesungRepository().save(vorlesung1);
        registrationSystem.getVorlesungRepository().save(vorlesung2);
        registrationSystem.getVorlesungRepository().save(vorlesung3);

        System.out.println("Studenten List");
        for (Student student:registrationSystem.getAllStudents()) {
            System.out.println(student);
        }

        System.out.println("Lehrer List");
        for (Lehrer lehrer:registrationSystem.getAllLehrer()) {
            System.out.println(lehrer);
        }
        System.out.println("Vorlesungen List");
        for (Vorlesung vorlesung:registrationSystem.getAllCourses()) {
            System.out.println(vorlesung);
        }

        registrationSystem.register(vorlesung1, student1);
        registrationSystem.register(vorlesung1.getVorlesungID(), student2.getStudentID());
        registrationSystem.register(vorlesung2, student1);
        registrationSystem.register(vorlesung2.getVorlesungID(), student2.getStudentID());

        System.out.println("\n");
        System.out.println("Studenten List");
        for (Student student:registrationSystem.getAllStudents()) {
            System.out.println(student);
        }

        System.out.println("Lehrer List");
        for (Lehrer lehrer:registrationSystem.getAllLehrer()) {
            System.out.println(lehrer);
        }
        System.out.println("Vorlesungen List");
        for (Vorlesung vorlesung:registrationSystem.getAllCourses()) {
            System.out.println(vorlesung);
        }

        registrationSystem.unregister(vorlesung2.getVorlesungID(), student2.getStudentID());

        System.out.println("\n");
        System.out.println("Angemeldeten Studenten fur Vorlesung");
        System.out.println(registrationSystem.retrieveStudentsEnrolledForACourse(vorlesung1.getVorlesungID()));
        System.out.println(registrationSystem.retrieveStudentsEnrolledForACourse(vorlesung2.getVorlesungID()));
        System.out.println(registrationSystem.retrieveStudentsEnrolledForACourse(vorlesung3.getVorlesungID()));

        System.out.println("Freie Platze");
        for (Map.Entry map: registrationSystem.retrieveCoursesWithFreePlaces().entrySet()) {
            System.out.println(map.getKey() + " " + map.getValue());
        }

        registrationSystem.changeCreditFromACourse(100, 10);
        System.out.println("Vorlesungen List");
        for (Vorlesung vorlesung:registrationSystem.getAllCourses()) {
            System.out.println(vorlesung);
        }

        System.out.println("Delete Vorlesung");
        registrationSystem.deleteVorlesungFromLehrer(lehrer1.getLehrerID(), vorlesung3.getVorlesungID());
        System.out.println("Vorlesungen List");
        for (Vorlesung vorlesung:registrationSystem.getAllCourses()) {
            System.out.println(vorlesung);
        }
    }
}
