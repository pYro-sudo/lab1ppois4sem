package by.losik.lab1;

import java.util.List;
import java.util.Scanner;

public class ClassroomCRUD extends EntityCRUD{
    public ClassroomCRUD(ContextImpl context, Scanner scanner) {
        super(context, scanner);
    }

    @Override
    public void create() {
        System.out.println("Enter the id of the classroom");
        Classroom classroom = new Classroom();
        int id = scanner.nextInt();

        List<Classroom> classroomList = contextImpl.getClassroom(id).toList();
        if(classroomList.isEmpty()){
            classroom.setId(id);
        }
        else {
            classroom = classroomList.get(0);
        }

        if (contextImpl.getTeacherList().isEmpty()) {
            System.out.println("For now you can't form classes due to the lack of staff");
            return;
        }

        System.out.println("Enter the teacher");
        String teacher = scanner.nextLine();
        List<Teacher> teacherList = contextImpl.getTeacher(teacher).toList();
        if (contextImpl.getTeacher(teacher).toList().isEmpty()) {
            System.out.println("There is no such teacher. Try again");
            teacher = scanner.nextLine();
            teacherList = contextImpl.getTeacher(teacher).toList();
        }
        classroom.setTeacher(teacherList.get(0));

        if (contextImpl.getChildrenList().isEmpty()) {
            System.out.println("For now you can't form classes due to the lack of demanding children");
            return;
        }
        System.out.println("Enter the child");
        String children = scanner.nextLine();
        List<Child> childrenList = contextImpl.getChild(children).toList();
        if (children.isEmpty()) {
            System.out.println("There is no such kid. Try again");
            children = scanner.nextLine();
            childrenList = contextImpl.getChild(children).toList();
        }
        Classroom finalClassroom = classroom;
        childrenList.forEach(x -> finalClassroom.getChildren().add(x));
        contextImpl.getClassroomList().add(classroom);
        System.out.println("Successfully added new classroom!");
    }

    @Override
    public void read() {
        System.out.println("The classroom list:\n");
        contextImpl.getClassroomList().
                forEach(x -> {
                    System.out.println("Id: " + x.getId() + "\nTeacher: " +
                            x.getTeacher().getName() + "\nChildren: ");
                    x.getChildren().forEach(y -> System.out.print(y.getName() + " "));
                    System.out.print("\n");
                });
    }

    @Override
    public void delete() {
        System.out.println("Enter the id of classroom to be destroyed");
        contextImpl.removeClassrooms(scanner.nextInt());
        System.out.println("Successfully removed!");
    }
}
