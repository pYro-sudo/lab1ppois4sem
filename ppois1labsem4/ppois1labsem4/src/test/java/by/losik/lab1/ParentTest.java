package by.losik.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ParentTest {
    @Test
    public void testParents(){
        Parent parent = new Parent();
        parent.setName("Olga");
        Classroom classroom = new Classroom();
        parent.setKids(List.of(new Child(), new Child()));
        parent.getKids().get(0).setMom(parent);
        parent.getKids().get(1).setMom(parent);
        Assertions.assertTrue(parent.getName().equals("Olga"));
        Assertions.assertTrue(parent.getKids().size() == 2);
        Assertions.assertTrue(parent.feedChildren(parent.getKids().get(0)));
        Assertions.assertTrue(parent.getChildrenToSleep(parent.getKids().get(0)));
        Assertions.assertTrue(parent.talkToKids(parent.getKids().get(0)));
        Assertions.assertTrue(parent.playWithKids(parent.getKids().get(0)));
        Assertions.assertTrue(parent.getKidsToSchool(classroom).size() == 2);
        Assertions.assertTrue(parent.bringKidsBack(classroom).size() == 2);
    }
}
