package by.losik.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ContextTest {
    @Test
    public void testContext(){
        ContextImpl context = new ContextImpl();

        StudyingMaterials studyingMaterials = new StudyingMaterials();
        studyingMaterials.setTopic("Art");
        studyingMaterials.setMaterials(Map.of("Introduction","something","Conclusion", "That's all"));
        context.getMaterialsList().add(studyingMaterials);

        Teacher teacher = new Teacher();
        teacher.setName("Nambi");
        context.getTeacherList().add(teacher);

        Parent parent = new Parent();
        parent.setName("Olga");
        Classroom classroom = new Classroom();
        classroom.setId(4);
        classroom.setTeacher(teacher);
        parent.setKids(List.of(new Child(), new Child()));
        parent.getKids().get(0).setMom(parent);
        parent.getKids().get(1).setMom(parent);

        ExercisingGames exercisingGames = new ExercisingGames();
        exercisingGames.setTime(new Date(2000,12,12));
        exercisingGames.setTask("Mop the floor");

        context.getParentList().add(parent);
        context.getExercisingGamesList().add(exercisingGames);
        context.getClassroomList().add(classroom);
        context.getChildrenList().add(parent.getKids().get(0));
        parent.getKids().get(0).setName("ff");
        context.getChildrenList().add(parent.getKids().get(1));
        parent.getKids().get(1).setName("fd");

        Assertions.assertNotNull(context);
        Assertions.assertEquals(0, context.getChild("asdfgh").count());
        Assertions.assertEquals(1, context.getParents("Olga").count());
        Assertions.assertEquals(1, context.getGames("Mop the floor").count());
        Assertions.assertEquals(1, context.getClassroom(4).count());
        Assertions.assertEquals(1, context.getMaterial("Art").count());

        Assertions.assertEquals(0, context.removeChildren("asdf").count());
        Assertions.assertEquals(1, context.removeParents("Olga").count());
        Assertions.assertEquals(1, context.removeTeachers("Nambi").count());
        Assertions.assertEquals(1, context.removeStudyingMaterials("Art").count());
        Assertions.assertEquals(1, context.removeClassrooms(4).count());
        Assertions.assertEquals(1, context.removeGames("Mop the floor").count());

        context = new ContextImpl();
        context.setChildrenList(List.of(new Child(),new Child()));
        context.setClassroomList(List.of(new Classroom(), new Classroom()));
        context.setParentList(List.of(new Parent(), new Parent()));
        context.setMaterialsList(List.of(new StudyingMaterials(), new StudyingMaterials()));
        context.setTeacherList(List.of(new Teacher(), new Teacher()));
        context.setExercisingGamesList(List.of(new ExercisingGames(), new ExercisingGames()));
        Assertions.assertEquals(2, context.getParentList().size());
        Assertions.assertEquals(2, context.getChildrenList().size());
        Assertions.assertEquals(2, context.getTeacherList().size());
        Assertions.assertEquals(2, context.getClassroomList().size());
        Assertions.assertEquals(2, context.getExercisingGamesList().size());
        Assertions.assertEquals(2, context.getMaterialsList().size());
    }
}
