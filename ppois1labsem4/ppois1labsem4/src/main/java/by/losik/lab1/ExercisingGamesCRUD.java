package by.losik.lab1;

import java.util.Scanner;

public class ExercisingGamesCRUD extends EntityCRUD{
    public ExercisingGamesCRUD(ContextImpl context, Scanner scanner) {
        super(context, scanner);
    }

    @Override
    public void create() {
        ExercisingGames exercisingGames = new ExercisingGames();
        System.out.println("Enter the task");
        exercisingGames.setTask(scanner.nextLine());

        contextImpl.getExercisingGamesList().add(exercisingGames);
        System.out.println("Successfully added new game!");
    }

    @Override
    public void read() {
        System.out.println("The tasks list:\n");
        contextImpl.getExercisingGamesList().forEach(x ->
                System.out.println("Task: " + x.getTask()));
    }

    @Override
    public void delete() {
        System.out.println("Enter the exercising game to remove by the task");
        contextImpl.removeGames(new Scanner(System.in).nextLine());
        System.out.println("Successfully removed!");
    }
}
