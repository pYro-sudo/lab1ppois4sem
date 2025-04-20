package by.losik.lab1;

import lombok.SneakyThrows;

import java.util.*;

public class App {
    private static final ContextImpl context = new ContextImpl();
    private static final Scanner scanner = new Scanner(System.in);
    private static final ContextImplCRUD contextImplCRUD = new ContextImplCRUD(context, scanner);
    private static final ChildCRUD childCRUD = new ChildCRUD(context, scanner);
    private static final ClassroomCRUD classroomCRUD = new ClassroomCRUD(context, scanner);
    private static final ExercisingGamesCRUD exercisingGamesCRUD = new ExercisingGamesCRUD(context, scanner);
    private static final ParentCRUD parentCRUD = new ParentCRUD(context, scanner);
    private static final StudyingMaterialsCRUD studyingMaterialsCRUD = new StudyingMaterialsCRUD(context, scanner);
    private static final TeacherCRUD teacherCRUD = new TeacherCRUD(context, scanner);
    @SneakyThrows
    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println("""
                        Kindergarten context system.
                        The list of commands:
                          1 : init the context,
                          2 : get children list,
                          3 : get classroom list,
                          4 : get exercising games list,
                          5 : get parents list,
                          6 : get studying material list,
                          7 : get teacher list,
                          8 : remove all named children,
                          9 : remove all named classrooms,
                          10 : remove all exercising games,
                          11 : remove all named parents,
                          12 : remove all named studying materials,
                          13 : remove all named teachers,
                          14 : destroy the context,
                          15 : add new child,
                          16 : get some child study,
                          17 : add new classroom,
                          18 : add new exercising game,
                          19 : add new parent,
                          20 : feed parents children,
                          21 : make some parent get his child to sleep,
                          22 : make some parent get his child to school,
                          23 : make some parent bring his child from school,
                          24 : add new studying materials,
                          25 : add new teacher,
                          26 : make some teacher teach kids in some classroom,
                          27 : make some teacher exercise kids in some classroom,
                          28 : to exit the program""");
                switch (new Scanner(System.in).nextInt()) {
                    case 1 -> contextImplCRUD.read();
                    case 2 -> childCRUD.read();
                    case 3 -> classroomCRUD.read();
                    case 4 -> exercisingGamesCRUD.read();
                    case 5 -> parentCRUD.read();
                    case 6 -> studyingMaterialsCRUD.read();
                    case 7 -> teacherCRUD.read();
                    case 8 -> childCRUD.delete();
                    case 9 -> classroomCRUD.delete();
                    case 10 -> exercisingGamesCRUD.delete();
                    case 11 -> parentCRUD.delete();
                    case 12 -> studyingMaterialsCRUD.delete();
                    case 13 -> teacherCRUD.delete();
                    case 14 -> contextImplCRUD.delete();
                    case 15 -> childCRUD.create();
                    case 16 -> childCRUD.study();
                    case 17 -> classroomCRUD.create();
                    case 18 -> exercisingGamesCRUD.create();
                    case 19 -> parentCRUD.create();
                    case 20 -> parentCRUD.feedKids();
                    case 21 -> parentCRUD.putToSleep();
                    case 22 -> parentCRUD.bringChildrenToSchool();
                    case 23 -> parentCRUD.bringChildrenFromSchool();
                    case 24 -> studyingMaterialsCRUD.create();
                    case 25 -> teacherCRUD.create();
                    case 26 -> teacherCRUD.teach();
                    case 27 -> teacherCRUD.exercise();
                    case 28 -> {
                        contextImplCRUD.update();
                        System.exit(0);
                    }
                    default -> throw new RuntimeException();
                }
            }
        } catch (Exception e) {
            System.out.println("Saving...");
            contextImplCRUD.update();
            Thread.sleep(1000);
            main(new String[0]);
        }
    }
}
