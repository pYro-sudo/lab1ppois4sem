package by.losik.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TeacherTest {
    @Test
    public void testTeachers(){
        Teacher teacher = new Teacher();
        teacher.setName("Nambi");
        Assertions.assertTrue(teacher.exercise(new Classroom(), new ExercisingGames()) instanceof ExercisingGames);
        Assertions.assertTrue(teacher.
                giveStudyingMaterials(new Classroom(), new StudyingMaterials()) instanceof StudyingMaterials);
        Assertions.assertTrue(teacher.getName().equals("Nambi"));
    }
}
