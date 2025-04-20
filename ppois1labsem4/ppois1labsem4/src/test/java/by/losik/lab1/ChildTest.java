package by.losik.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChildTest {
    @Test
    public void testChild(){
        Child child = new Child();
        child.setSlept(false);
        child.setDad(new Parent());
        child.setMom(new Parent());
        child.setAte(false);
        child.setTalkedToParents(false);
        child.setExercised(false);
        child.setName("Ivan");
        child.setStudyingMaterials(new StudyingMaterials());
        Assertions.assertTrue(child.getName().equals("Ivan"));
        Assertions.assertTrue(!child.isExercised());
        Assertions.assertTrue(!child.isAte());
        Assertions.assertTrue(!child.isTalkedToParents());
        Assertions.assertTrue(child.getMom() != null);
        Assertions.assertTrue(child.getDad() != null);
        Assertions.assertTrue(child.study());
        Assertions.assertTrue(child.getStudyingMaterials() instanceof StudyingMaterials);
        Assertions.assertTrue(child.isStudied());
    }
}
