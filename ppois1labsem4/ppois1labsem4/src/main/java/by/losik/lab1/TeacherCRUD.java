package by.losik.lab1;

import java.util.List;
import java.util.Scanner;

public class TeacherCRUD extends EntityCRUD{
    public TeacherCRUD(ContextImpl context, Scanner scanner) {
        super(context, scanner);
    }

    @Override
    public void create() {
        System.out.println("Enter the teacher's name");
        Teacher teacher = new Teacher();
        teacher.setName(scanner.nextLine());
        contextImpl.getTeacherList().add(teacher);
        System.out.println("Successfully added new teacher!");
    }

    @Override
    public void read() {
        System.out.println("Teacher list:");
        contextImpl.getTeacherList()
                .forEach(x -> System.out.println("Teacher: " + x.getName()));
    }

    @Override
    public void delete() {
        System.out.println("Enter the name of the teacher");
        contextImpl.removeTeachers(scanner.nextLine());
        System.out.println("Successfully removed!");
    }

    public void teach() {
        System.out.println("Enter the teacher's name");
        List<Teacher> teacherList = contextImpl.getTeacher(scanner.nextLine()).toList();
        System.out.println("Enter the id of the classroom");
        List<Classroom> classroomList = contextImpl.getClassroom(scanner.nextInt()).toList();
        System.out.println("Enter the topic of the studying materials");
        List<StudyingMaterials> studyingMaterialsList = contextImpl.getMaterial(scanner.nextLine()).toList();
        if (!(teacherList.isEmpty() && classroomList.isEmpty() && studyingMaterialsList.isEmpty())) {
            teacherList.forEach(x -> classroomList
                    .forEach(y -> x.giveStudyingMaterials(y, studyingMaterialsList.get(0))));
        }
        System.out.println("Successfully taught!");
    }

    public void exercise() {
        System.out.println("Enter the teacher's name");
        List<Teacher> teacherList = contextImpl.getTeacher(scanner.nextLine()).toList();
        System.out.println("Enter the id of the classroom");
        List<Classroom> classroomList = contextImpl.getClassroom(scanner.nextInt()).toList();
        System.out.println("Enter the task of the exercise");
        List<ExercisingGames> exercisingGamesList = contextImpl.getGames(scanner.nextLine()).toList();
        if (!(teacherList.isEmpty() && classroomList.isEmpty() && exercisingGamesList.isEmpty())) {
            teacherList.forEach(x -> classroomList
                    .forEach(y -> x.exercise(y, exercisingGamesList.get(0))));
        }
        System.out.println("Successfully exercised!");
    }
}
