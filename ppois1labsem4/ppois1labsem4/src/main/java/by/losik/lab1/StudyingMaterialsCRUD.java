package by.losik.lab1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StudyingMaterialsCRUD extends EntityCRUD{
    public StudyingMaterialsCRUD(ContextImpl context, Scanner scanner) {
        super(context, scanner);
    }

    @Override
    public void create() {
        StudyingMaterials studyingMaterials = new StudyingMaterials();
        System.out.println("Enter the topic");
        studyingMaterials.setTopic(scanner.nextLine());
        Map<String, String> materials = new HashMap<>();
        while (!scanner.nextLine().equals("stop")) {
            System.out.println("Enter the subtopic, then the content");
            materials.put(scanner.nextLine(), scanner.nextLine());
            System.out.println("Enter 'stop' to stop filling the materials");
        }
        studyingMaterials.setMaterials(materials);
        contextImpl.getMaterialsList().add(studyingMaterials);
        System.out.println("Successfully added new material!");
    }

    @Override
    public void read() {
        System.out.println("The materials:");
        contextImpl.getMaterialsList().forEach(x -> {
            System.out.println("Topic:" + x.getTopic());
            x.getMaterials().forEach((key, value) -> System.out.println("Subtopic: " + key
                    + "\nContent: " + value));
        });
    }

    @Override
    public void delete() {
        System.out.println("Enter the topic");
        contextImpl.removeStudyingMaterials(scanner.nextLine());
        System.out.println("Successfully removed!");
    }
}
