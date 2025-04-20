package by.losik.lab1;

import java.util.List;
import java.util.Scanner;

public class ParentCRUD extends EntityCRUD{
    public ParentCRUD(ContextImpl context, Scanner scanner) {
        super(context, scanner);
    }

    @Override
    public void create() {
        System.out.println("Enter the parents name");
        Parent parent = new Parent();
        parent.setName(scanner.nextLine());
        contextImpl.getParentList().add(parent);
        System.out.println("Successfully added new parent!");
    }

    @Override
    public void read() {
        System.out.println("Parent list:\n");
        contextImpl.getParentList().forEach(x -> {
            System.out.println("Parent: " + x.getName() + " ");
            System.out.println("Children: ");
            x.getKids().forEach(y -> System.out.print(y.getName() + " "));
            System.out.println();
        });
    }

    @Override
    public void delete() {
        System.out.println("Enter the name of the parent to remove");
        contextImpl.removeParents(scanner.nextLine());
        System.out.println("Successfully removed!");
    }

    public void feedKids() {
        System.out.println("Enter the parents name");
        contextImpl.getParents(scanner.nextLine()).forEach(x ->
                x.getKids().forEach(x::feedChildren));
        System.out.println("Successfully fed!");
    }

    public void putToSleep() {
        System.out.println("Enter the name of the parent");
        contextImpl.getParents(scanner.nextLine()).forEach(x -> x.getKids()
                .forEach(x::getChildrenToSleep));
        System.out.println("Successfully put to sleep!");
    }

    public void bringChildrenToSchool() {
        System.out.println("Enter the name of the parent");
        List<Parent> parentList = contextImpl.getParents(scanner.nextLine()).toList();
        if (!parentList.isEmpty()) {
            System.out.println("Enter the id of the classroom");
            List<Classroom> classroomList = contextImpl.getClassroom(scanner.nextInt()).toList();
            parentList.forEach(x -> x.getKidsToSchool(classroomList.get(0)));
        }
        System.out.println("Successfully brought to school!");
    }

    public void bringChildrenFromSchool() {
        System.out.println("Enter the name of the parent");
        List<Parent> parentList = contextImpl.getParents(scanner.nextLine()).toList();
        if (!parentList.isEmpty()) {
            System.out.println("Enter the id of the classroom");
            List<Classroom> classroomList = contextImpl.getClassroom(scanner.nextInt()).toList();
            parentList.forEach(x -> classroomList.forEach(x::bringKidsBack));
        }
        System.out.println("Successfully brought from school!");
    }
}
