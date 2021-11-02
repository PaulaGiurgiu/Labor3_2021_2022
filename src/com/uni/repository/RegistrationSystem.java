package com.uni.repository;

import com.uni.model.Lehrer;
import com.uni.model.Student;
import com.uni.model.Vorlesung;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RegistrationSystem {
    private final VorlesungRepository vorlesungRepository;
    private final StudentRepository studentRepository;
    private final LehrerRepository lehrerRepository;

    /**
     * wir erstellen ein neues Objekt von Typ RegistrationSystem
     */
    public RegistrationSystem(){
        this.vorlesungRepository = new VorlesungRepository();
        this.studentRepository = new StudentRepository();
        this.lehrerRepository = new LehrerRepository();
    }

    /**
     * @return alle Elementen aus der "studentRepository"
     */
    public StudentRepository getStudentRepository(){
        return this.studentRepository;
    }

    /**
     * @return alle Elementen aus der "lehrerRepository"
     */
    public LehrerRepository getLehrerRepository(){
        return this.lehrerRepository;
    }

    /**
     * @return alle Elementen aus der "vorlesungRepository"
     */
    public VorlesungRepository getVorlesungRepository(){
        return this.vorlesungRepository;
    }

    /**
     *
     * @param vorlesung ein Objekt von Typ "Vorlesung"
     * @param student ein Objekt von Typ "Student"
     * @return der Student meldet sich fur die gegebene Vorlesung an
     * @throws IOException falls der Student oder die Vorlesung nicht in der Repository Liste sind
     *                     falls es gar kein verfugbar Platz dur der Vorlesung gibt
     *                     falls der Anzahl der Credits des Studenten grosser als 30
     *
     * wir aktualisieren die Listen aus der Student-, Vorlesung- und LehrerRepository
     */
    public boolean register(Vorlesung vorlesung, Student student) throws IOException {
        boolean okV= false, okS= false;
        for (int i = 0; i < this.vorlesungRepository.repoList.size(); i++)
        {
            if (vorlesung.getVorlesungID() == this.vorlesungRepository.repoList.get(i).getVorlesungID()){
                okV= true;
                break;
            }
        }

        for (int i = 0; i < this.studentRepository.repoList.size(); i++)
        {
            if (student.getStudentID() == this.studentRepository.repoList.get(i).getStudentID()) {
                okS = true;
                break;
            }
        }

        if (!okV || !okS) {
            throw new IOException();
        }

        if (vorlesung.getMaxEnrollment() <= vorlesung.getStudentsEnrolled().size() )
            throw new IOException();
        else if(student.getTotalCredits() + vorlesung.getCredits() > 30)
            throw new IOException();
        else {
            boolean okLV = false, okLS = false;
            List<Long> listVorlesungen = new ArrayList<>();
            for (int i = 0; i < studentRepository.repoList.size(); i++) {
                if (studentRepository.repoList.get(i).getStudentID() == student.getStudentID()) {
                    for (int j = 0; j < studentRepository.repoList.get(i).getEnrolledCourses().size(); j++) {
                        listVorlesungen.add(studentRepository.repoList.get(i).getEnrolledCourses().get(j));
                        if (studentRepository.repoList.get(i).getEnrolledCourses().get(j) == vorlesung.getVorlesungID()) {
                            okLV = true;
                        }
                    }
                }
            }

            if (!okLV) {
                listVorlesungen.add(vorlesung.getVorlesungID());
            }


            List<Long> listStudenten = new ArrayList<>();
            for (int i = 0; i < vorlesungRepository.repoList.size(); i++) {
                if (vorlesungRepository.repoList.get(i).getVorlesungID() == vorlesung.getVorlesungID()) {
                    for (int j = 0; j < vorlesungRepository.repoList.get(i).getStudentsEnrolled().size(); j++) {
                        listStudenten.add(vorlesungRepository.repoList.get(i).getStudentsEnrolled().get(j));
                        if (vorlesungRepository.repoList.get(i).getStudentsEnrolled().get(j) == student.getStudentID()) {
                            okLS = true;
                        }
                    }
                }
            }
            if (!okLS) {
                listStudenten.add(student.getStudentID());
            }

            Vorlesung newVorlesung = new Vorlesung(vorlesung.getName(),
                    vorlesung.getLehrer(),
                    vorlesung.getVorlesungID(),
                    vorlesung.getMaxEnrollment(),
                    listStudenten,
                    vorlesung.getCredits());

            Student newStudent = new Student(student.getVorname(),
                    student.getNachname(),
                    student.getStudentID(),
                    student.getTotalCredits() + vorlesung.getCredits(),
                    listVorlesungen);

            vorlesungRepository.update(newVorlesung);
            studentRepository.update(newStudent);
        }

        for (int i = 0; i < lehrerRepository.repoList.size(); i++) {
            updateLehrerCourseList(lehrerRepository.repoList.get(i).getLehrerID());
        }

        return true;

    }

    /**
     *
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param StudentID eine "Long" Zahl, die ein "Student" Id entspricht
     * @return der Student meldet sich fur die gegebene Vorlesung an
     * @throws IOException falls der Student oder die Vorlesung nicht in der Repository Liste sind
     *                     falls es gar kein verfugbar Platz dur der Vorlesung gibt
     *                     falls der Anzahl der Credits des Studenten grosser als 30
     *
     * wir aktualisieren die Listen aus der Student-, Vorlesung- und LehrerRepository
     */
    public boolean register(long VorlesungID, long StudentID) throws IOException{
        boolean okV= false, okS= false;
        int indexVorlesung = 0, indexStudent = 0;
        for (int i = 0; i < this.vorlesungRepository.repoList.size(); i++) {
            if (VorlesungID == this.vorlesungRepository.repoList.get(i).getVorlesungID()){
                indexVorlesung = i;
                okV= true;
                break;
            }
        }

        for (int i = 0; i < this.studentRepository.repoList.size(); i++)
        {
            if (StudentID == this.studentRepository.repoList.get(i).getStudentID()) {
                indexStudent = i;
                okS = true;
                break;
            }
        }

        if (!okV || !okS) {
            throw new IOException();
        }

        Vorlesung vorlesung = this.vorlesungRepository.repoList.get(indexVorlesung);
        Student student = this.studentRepository.repoList.get(indexStudent);

        register(vorlesung, student);
        return true;
    }

    /**
     *
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param StudentID eine "Long" Zahl, die ein "Student" Id entspricht
     *
     * wir loschen die Vorlesung aus der "Vorlesung" Liste des Studenten
     * wir loschen der Student aus der "Studenten" Liste der Vorlesung
     * wir aktualisieren die Listen aus der Student- und VorlesungRepository
     */
    public void unregister(long VorlesungID, long StudentID){
        int indexVorlesung = 0;
        for (int i = 0; i < this.vorlesungRepository.repoList.size(); i++) {
            if (this.vorlesungRepository.repoList.get(i).getVorlesungID() == VorlesungID){
                indexVorlesung = i;
                break;
            }
        }

        Vorlesung vorlesung = this.vorlesungRepository.repoList.get(indexVorlesung);
        List<Long> listVorlesungen = new ArrayList<>();
        List<Long> listStudenten = new ArrayList<>();

        for (int i = 0; i < vorlesung.getStudentsEnrolled().size(); i++) {
            if (vorlesung.getStudentsEnrolled().get(i) != StudentID)
                listStudenten.add(vorlesung.getStudentsEnrolled().get(i));
        }

        for (int i = 0; i < this.studentRepository.repoList.size(); i++) {
            if (this.studentRepository.repoList.get(i).getStudentID() == StudentID){
                Student student = this.studentRepository.repoList.get(i);
                for (int j = 0; j < student.getEnrolledCourses().size(); j++) {
                    if (student.getEnrolledCourses().get(j) == VorlesungID){

                        for (int k = 0; k < student.getEnrolledCourses().size(); k++) {
                            if (student.getEnrolledCourses().get(k) != VorlesungID){
                                listVorlesungen.add(student.getEnrolledCourses().get(k));
                            }
                        }

                        Student newStudent = new Student(student.getVorname(),
                                student.getNachname(),
                                student.getStudentID(),
                                student.getTotalCredits() - vorlesung.getCredits(),
                                listVorlesungen);

                        this.studentRepository.update(newStudent);
                        break;
                    }
                }
            }
        }

        Vorlesung newVorlesung = new Vorlesung(vorlesung.getName(),
                vorlesung.getLehrer(),
                vorlesung.getVorlesungID(),
                vorlesung.getMaxEnrollment(),
                listStudenten,
                vorlesung.getCredits());

        this.vorlesungRepository.update(newVorlesung);

    }

    /**
     *
     * @param LehrerID eine "Long" Zahl, die ein "Lehrer" Id entspricht
     * wir aktualisieren die "Vorlesung "Liste des Lehrers
     */
    private void updateLehrerCourseList(long LehrerID){
        List<Long> listVorlesungen = new ArrayList<>();
        for (int i = 0; i < this.vorlesungRepository.repoList.size(); i++) {
            if (this.vorlesungRepository.repoList.get(i).getLehrer() == LehrerID){
                listVorlesungen.add(this.vorlesungRepository.repoList.get(i).getVorlesungID());
            }
        }
        int indexLehrer = 0;
        for (int i = 0; i < this.lehrerRepository.repoList.size(); i++) {
            if (this.lehrerRepository.repoList.get(i).getLehrerID() == LehrerID){
                indexLehrer = i;
            }
        }

        this.lehrerRepository.update(new Lehrer(this.lehrerRepository.repoList.get(indexLehrer).getVorname(),
                this.lehrerRepository.repoList.get(indexLehrer).getNachname(),
                LehrerID,
                listVorlesungen));

    }

    /**
     *
     * @return ein HashMap mit der Vorlesungen, die freie Platze haben und deren Anzahl
     */
    public HashMap<Integer, Vorlesung> retrieveCoursesWithFreePlaces() {
        HashMap<Integer, Vorlesung> map = new HashMap<Integer, Vorlesung>();
        int freePlatz = 0;
        for (Vorlesung v: vorlesungRepository.findAll()) {
            if (v.getMaxEnrollment() > v.getStudentsEnrolled().size()) {
                freePlatz = v.getMaxEnrollment() - v.getStudentsEnrolled().size();
                map.put(freePlatz, v);
            }

        }

        return map;
    }

    /**
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @return eine Liste von Studenten, die an der gegebenen Vorlesung teilnehmen
     */
    public List<Long> retrieveStudentsEnrolledForACourse(long VorlesungID){
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < this.vorlesungRepository.repoList.size(); i++) {
            if (this.vorlesungRepository.repoList.get(i).getVorlesungID() == VorlesungID){
                list.addAll(this.vorlesungRepository.repoList.get(i).getStudentsEnrolled());
                break;
            }
        }
        return list;
    }

    /**
     * @return alle Elemente aus der "vorlesungRepository"
     */
    public List<Vorlesung> getAllCourses() {
        return vorlesungRepository.findAll();
    }

    /**
     * @return alle Elemente aus der "studentRepository"
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * @return alle Elemente aus der "lehrerRepository"
     */
    public List<Lehrer> getAllLehrer() {
        for (int i = 0; i < lehrerRepository.repoList.size(); i++) {
            updateLehrerCourseList(lehrerRepository.repoList.get(i).getLehrerID());
        }
        return lehrerRepository.findAll();
    }

    /**
     *
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param newCredit die neue Anzahl von Credits
     * @return wir andern die Anzahl der Credits der Vorlesung
     *         wir "unregister" alle Studenten aus der Vorlesung
     *         wir aktualisieren die Vorlesung
     *         wir "register" die alte Studenten zu der Vorlesung
     * @throws IOException falls die alte Studenten nicht mehr an der Vorlesung teilnehmen konnten
     *
     */
    public boolean changeCreditFromACourse(long VorlesungID, int newCredit) throws IOException {
        for (int i = 0; i < vorlesungRepository.repoList.size(); i++) {
            if (vorlesungRepository.repoList.get(i).getVorlesungID() == VorlesungID){
                if (vorlesungRepository.repoList.get(i).getCredits() == newCredit){
                    return true;
                }
                else {
                    Vorlesung v = this.vorlesungRepository.repoList.get(i);
                    Vorlesung newVorlesung = new Vorlesung(v.getName(),
                            v.getLehrer(),
                            v.getVorlesungID(),
                            v.getMaxEnrollment(),
                            v.getStudentsEnrolled(),
                            newCredit);

                    for (int j = 0; j < v.getStudentsEnrolled().size(); j++) {
                        unregister(v.getVorlesungID(), v.getStudentsEnrolled().get(j));
                    }

                    this.vorlesungRepository.update(newVorlesung);

                    List<Long> listStudenten = new ArrayList<>();
                    for (int j = 0; j < v.getStudentsEnrolled().size(); j++) {
                        if(register(v.getVorlesungID(), v.getStudentsEnrolled().get(j))){
                            listStudenten.add(v.getStudentsEnrolled().get(j));
                        }
                    }

                    Vorlesung newVorlesung2 = new Vorlesung(v.getName(),
                            v.getLehrer(),
                            v.getVorlesungID(),
                            v.getMaxEnrollment(),
                            listStudenten,
                            newCredit);

                    this.vorlesungRepository.update(newVorlesung2);

                    return true;
                }

            }
        }

        return false;

    }

    /**
     *
     * @param LehrerID eine "Long" Zahl, die ein "Lehrer" Id entspricht
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @return die Loschung der Vorlesung von dem Lehrer
     *         wir "unregister" alle Studenten aus der Vorlesung
     *         wir aktualisieren die "Vorlesung" Liste des Lehrers
     */
    public boolean deleteVorlesungFromLehrer(long LehrerID, long VorlesungID){
        int indexVorlesung = 0;
        for (int i = 0; i < this.vorlesungRepository.repoList.size(); i++) {
            if (this.vorlesungRepository.repoList.get(i).getVorlesungID() == VorlesungID){
                indexVorlesung = i;
                if (this.vorlesungRepository.repoList.get(i).getLehrer() != LehrerID){
                    return false;
                }
            }
        }

        for (int i = 0; i < this.vorlesungRepository.repoList.get(indexVorlesung).getStudentsEnrolled().size(); i++) {
            unregister(VorlesungID, this.vorlesungRepository.repoList.get(indexVorlesung).getStudentsEnrolled().get(i));
        }

        for (int i = 0; i < this.lehrerRepository.repoList.size(); i++) {
            if (this.lehrerRepository.repoList.get(i).getLehrerID() == LehrerID){
                List<Long> listVorlesungen = new ArrayList<>();
                for (int j = 0; j < this.lehrerRepository.repoList.get(i).getVorlesungen().size(); j++) {
                    if (this.lehrerRepository.repoList.get(i).getVorlesungen().get(j) != VorlesungID){
                        listVorlesungen.add(this.lehrerRepository.repoList.get(i).getVorlesungen().get(j));
                    }
                }


                Lehrer lehrer = this.lehrerRepository.repoList.get(i);
                        Lehrer newLehrer = new Lehrer(lehrer.getVorname(),
                            lehrer.getNachname(),
                            lehrer.getLehrerID(),
                            listVorlesungen);

                this.lehrerRepository.update(newLehrer);
            }

        }

        this.vorlesungRepository.delete(this.vorlesungRepository.repoList.get(indexVorlesung));
        return true;
    }

}
