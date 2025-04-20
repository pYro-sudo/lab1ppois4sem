package by.losik.lab1;

import java.util.List;
import java.util.Scanner;

public class ChildCRUD extends EntityCRUD{
    public ChildCRUD(ContextImpl context, Scanner scanner) {
        super(context, scanner);
    }

    @Override
    public void create() {
        System.out.println("Enter the name of the child");
        Child child = new Child();
        child.setName(scanner.nextLine());
        child.setStudyingMaterials(null);
        child.setExercised(false);
        child.setTalkedToParents(false);
        child.setSlept(false);
        child.setAte(false);
        if (contextImpl.getParentList().isEmpty()) {
            System.out.println("For now you are unable to define the parents of the child in the list");
            child.setMom(null);
            child.setDad(null);
            return;
        }
        System.out.println("Enter the mom's name");
        String mom = scanner.nextLine();
        List<Parent> parentList = contextImpl.getParents(mom).toList();
        while (parentList.isEmpty()) {
            System.out.println("There is no such parent.");
            if (mom.equals("no")) {
                break;
            }
            mom = scanner.nextLine();
            parentList = contextImpl.getParents(mom).toList();
        }
        if(!parentList.isEmpty()){
            child.setMom(parentList.get(0));
            parentList.get(0).getKids().add(child);
        }
        System.out.println("Enter the dad's name");
        String dad = scanner.nextLine();
        parentList = contextImpl.getParents(dad).toList();
        while (parentList.isEmpty()) {
            System.out.println("There is no such parent. Try again");
            dad = scanner.nextLine();
            if (dad.equals("no")) {
                break;
            }
            parentList = contextImpl.getParents(dad).toList();
        }
        if(!parentList.isEmpty()){
            child.setDad(parentList.get(0));
            parentList.get(0).getKids().add(child);
        }
        contextImpl.getChildrenList().add(child);
        System.out.println("the child was added!");
    }

    @Override
    public void read() {
        System.out.println("Child list:\n");
        contextImpl.getChildrenList().forEach(x ->
                System.out.println("Name: " + x.getName() + "\nSlept: " + x.isSlept() +
                        "\nTalked to parents: " + x.isTalkedToParents() + "\nStudied: " + x.isStudied() +
                        "\nExercised: " + x.isExercised() + "\n"));
    }

    @Override
    public void delete() {
        System.out.println("Enter the name of the child to remove");
        contextImpl.removeChildren(scanner.nextLine());
        System.out.println("Successfully removed!");
    }

    public void study() {
        System.out.println("Enter the name of the child to study");
        contextImpl.getChild(scanner.nextLine()).forEach(Child::study);
        System.out.println("Now they are studying");
    }
}
