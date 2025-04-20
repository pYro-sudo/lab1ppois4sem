package by.losik.lab1;

import java.util.List;

public interface ParentDuties {
    boolean feedChildren(Child child);
    boolean getChildrenToSleep(Child child);
    boolean talkToKids(Child child);
    boolean playWithKids(Child child);
    List<Child> getKidsToSchool(Classroom classroom);
    List<Child> bringKidsBack(Classroom classroom);
}
