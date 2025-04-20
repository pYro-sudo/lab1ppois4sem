package by.losik.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ClassroomTest {
    @Test
    public void testClassroom(){
        Classroom classroom = new Classroom();
        classroom.setId(4);
        classroom.setTeacher(new Teacher());
        classroom.setChildren(List.of(new Child(), new Child(), new Child()));
        Assertions.assertTrue(classroom.getId() == 4);
        Assertions.assertTrue(classroom.getTeacher() != null);
        Assertions.assertTrue(classroom.getChildren() != null && classroom.getChildren().size() == 3);
    }
}
