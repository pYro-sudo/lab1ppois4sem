package by.losik.lab1;

import java.util.stream.Stream;

public interface Context {
    Stream<Child> getChild(String name);
    Stream<Teacher> getTeacher(String name);
    Stream<StudyingMaterials> getMaterial(String topic);
    Stream<Classroom> getClassroom(int id);
    Stream<Parent> getParents(String name);
    Stream<ExercisingGames> getGames(String task);
    Stream<Child> removeChildren(String name);
    Stream<Teacher> removeTeachers(String name);
    Stream<StudyingMaterials> removeStudyingMaterials(String topic);
    Stream<Classroom> removeClassrooms(int id);
    Stream<Parent> removeParents(String name);
    Stream<ExercisingGames> removeGames(String task);
}
