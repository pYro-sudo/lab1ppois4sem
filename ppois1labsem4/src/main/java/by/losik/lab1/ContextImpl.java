package by.losik.lab1;

import lombok.Data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@Data
public class ContextImpl implements Context{
    private List<Child> childrenList = new CopyOnWriteArrayList<>();
    private List<Teacher> teacherList = new CopyOnWriteArrayList<>();
    private List<StudyingMaterials> materialsList = new CopyOnWriteArrayList<>();
    private List<Classroom> classroomList = new CopyOnWriteArrayList<>();
    private List<Parent> parentList = new CopyOnWriteArrayList<>();
    private List<ExercisingGames> exercisingGamesList = new CopyOnWriteArrayList<>();

    @Override
    public Stream<Child> getChild(String name){
        return childrenList.stream().filter(x -> name.equals(x.getName()));
    }
    @Override
    public Stream<Teacher> getTeacher(String name){
        return teacherList.parallelStream().filter(x -> name.equals(x.getName()));
    }
    @Override
    public Stream<StudyingMaterials> getMaterial(String topic){
        return materialsList.parallelStream().filter(x -> topic.equals(x.getTopic()));
    }
    @Override
    public Stream<Classroom> getClassroom(int id){
        return classroomList.stream().filter(x -> id == x.getId());
    }
    @Override
    public Stream<Parent> getParents(String name){
        return parentList.stream().filter(x -> name.equals(x.getName()));
    }
    @Override
    public Stream<ExercisingGames> getGames(String task){
        return exercisingGamesList.stream().filter(x -> x.getTask().equals(task));
    }

    @Override
    public Stream<Child> removeChildren(String name){
        var removedChildren = getChild(name);

        childrenList.forEach(x -> {
            if(x.getName().equals(name)){
                childrenList.remove(x);
            }
        });

        parentList.forEach(x -> x.getKids().forEach(y -> {
            if(y.getName().equals(name)){
                x.getKids().remove(y);
            }
        }));

        classroomList.forEach(x ->
            x.getChildren().forEach(y -> {
                if(y.getName().equals(name)){
                    x.getChildren().remove(y);
                }
            })
        );

        return removedChildren;
    }

    @Override
    public Stream<Teacher> removeTeachers(String name){
        var removedTeachers = getTeacher(name);

        childrenList.forEach(x -> {
            if(x.getName().equals(name)){
                childrenList.remove(x);
            }
        });

        classroomList.forEach(x -> {
            if(x.getTeacher().getName().equals(name)){
                x.setTeacher(null);
            }
        });

        return removedTeachers;
    }

    @Override
    public Stream<StudyingMaterials> removeStudyingMaterials(String topic){
        var removedStudyingMaterials = getMaterial(topic);

        materialsList.forEach(x -> {
            if(x.getTopic().equals(topic)){
                materialsList.remove(x);
            }
        });

        childrenList.forEach(x -> {
            if(x.getStudyingMaterials() != null){
                if(x.getStudyingMaterials().getTopic().equals(topic)){
                    x.setStudyingMaterials(null);
                }
            }
        });

        return removedStudyingMaterials;
    }

    @Override
    public Stream<Classroom> removeClassrooms(int id){
        var removedClassrooms = getClassroom(id);

        classroomList.forEach(x -> {
            if(x.getId() == id){
                classroomList.remove(x);
            }
        });

        return removedClassrooms;
    }

    @Override
    public Stream<Parent> removeParents(String name){
        var removedParents = getParents(name);

        parentList.forEach(x -> {
            if(x.getName().equals(name)){
                parentList.remove(x);
            }
        });

        childrenList.forEach(x -> {
            if(x.getMom() != null) {
                if (x.getMom().getName().equals(name)) {
                    x.setMom(null);
                }
            }
            if(x.getDad() != null){
                if(x.getDad().getName().equals(name)){
                    x.setDad(null);
                }
            }
        });

        return removedParents;
    }

    @Override
    public Stream<ExercisingGames> removeGames(String task){
        var removedGames = getGames(task);

        exercisingGamesList.forEach(x-> {
            if (x.getTask().equals(task)) {
                exercisingGamesList.remove(x);
            }
        });

        return removedGames;
    }
}
