package by.losik.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class StudyingMaterialsTest {
    @Test
    public void testMaterials(){
        StudyingMaterials studyingMaterials = new StudyingMaterials();
        studyingMaterials.setTopic("Art");
        studyingMaterials.setMaterials(Map.of("Introduction","something","Conclusion", "That's all"));
        Assertions.assertTrue(studyingMaterials.getTopic().equals("Art"));
        Assertions.assertTrue(studyingMaterials.getMaterials().entrySet().size() == 2);
        Assertions.assertTrue(studyingMaterials.getMaterials().containsKey("Introduction"));
        Assertions.assertTrue(studyingMaterials.getMaterials().containsKey("Conclusion"));
        Assertions.assertTrue(studyingMaterials.getMaterials().containsValue("something"));
        Assertions.assertTrue(studyingMaterials.getMaterials().containsValue("That's all"));
    }
}
