package by.losik.lab1;

import lombok.Data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Parent implements ParentDuties{
    private List<Child> kids = new CopyOnWriteArrayList<>();
    private String name;

    @Override
    public boolean feedChildren(Child child) {
        child.setAte(true);
        return child.isAte();
    }

    @Override
    public boolean getChildrenToSleep(Child child) {
        child.setSlept(true);
        return child.isSlept();
    }

    @Override
    public boolean talkToKids(Child child) {
        child.setTalkedToParents(this == child.getDad() || this == child.getMom());
        return child.isTalkedToParents();
    }

    @Override
    public boolean playWithKids(Child child) {
        child.setExercised(this == child.getDad() || this == child.getMom());
        return child.isExercised();
    }

    @Override
    public List<Child> getKidsToSchool(Classroom classroom) {
        for (Child child : kids) {
            if (!classroom.getChildren().contains(child)) {
                classroom.getChildren().add(child);
            }
        }
        return this.kids;
    }

    @Override
    public List<Child> bringKidsBack(Classroom classroom) {
        classroom.getChildren().removeIf(child -> this == child.getMom() || this == child.getDad());
        return this.kids;
    }
}