package by.losik.lab1;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {
    @SneakyThrows
    public static void main(String[] args) {
        ContextImpl context = new ContextImpl();
        try {
            while (true) {
                System.out.println("""
                        The list of commands:
                          CONTEXT_INIT : init the context,
                          CONTEXT_GET_CHILDREN : get children list,
                          CONTEXT_GET_CLASSROOMS : get classroom list,
                          CONTEXT_GET_GAMES : get exercising games list,
                          CONTEXT_GET_PARENTS : get parents list,
                          CONTEXT_GET_STUDYING_MATERIALS : get studying material list,
                          CONTEXT_GET_TEACHERS : get teacher list,
                          CONTEXT_CHILD_DESTROY : remove all named children,
                          CONTEXT_CLASSROOM_DESTROY : remove all named classrooms,
                          CONTEXT_GAME_DESTROY : remove all exercising games,
                          CONTEXT_PARENT_DESTROY : remove all named parents,
                          CONTEXT_STUDYING_MATERIALS_DESTROY : remove all named studying materials,
                          CONTEXT_TEACHER_DESTROY : remove all named teachers,
                          CONTEXT_DESTROY : destroy the context,
                          CHILD_INIT : add new child,
                          CHILD_STUDY : get some child study,
                          CLASSROOM_INIT : add new classroom,
                          GAME_INIT : add new exercising game,
                          PARENT_INIT : add new parent,
                          PARENT_FEED_KIDS : feed their children,
                          PARENT_PUT_TO_SLEEP : make some parent get his child to sleep,
                          PARENT_GET_TO_SCHOOL : make some parent get his child to school,
                          PARENT_BRING_BACK_FROM_SCHOOL : make some parent bring his child from school,
                          STUDYING_MATERIALS_INIT : add new studying materials,
                          TEACHER_INIT : add new teacher,
                          TEACHER_TEACH : make some teacher teach kids in some classroom,
                          TEACHER_EXERCISE : make some teacher exercise kids in some classroom,
                          EXIT : to exit the program""");
                switch (new Scanner(System.in).nextLine()) {
                    case "CONTEXT_INIT" -> comply(States.CONTEXT_INIT, context);
                    case "CONTEXT_GET_CHILDREN" -> comply(States.CONTEXT_GET_CHILDREN, context);
                    case "CONTEXT_GET_CLASSROOMS" -> comply(States.CONTEXT_GET_CLASSROOMS, context);
                    case "CONTEXT_GET_GAMES" -> comply(States.CONTEXT_GET_GAMES, context);
                    case "CONTEXT_GET_PARENTS" -> comply(States.CONTEXT_GET_PARENTS, context);
                    case "CONTEXT_GET_STUDYING_MATERIALS" -> comply(States.CONTEXT_GET_STUDYING_MATERIALS, context);
                    case "CONTEXT_GET_TEACHERS" -> comply(States.CONTEXT_GET_TEACHERS, context);
                    case "CONTEXT_CHILD_DESTROY" -> comply(States.CONTEXT_CHILD_DESTROY, context);
                    case "CONTEXT_CLASSROOM_DESTROY" -> comply(States.CONTEXT_CLASSROOM_DESTROY, context);
                    case "CONTEXT_GAME_DESTROY" -> comply(States.CONTEXT_GAME_DESTROY, context);
                    case "CONTEXT_PARENT_DESTROY" -> comply(States.CONTEXT_PARENT_DESTROY, context);
                    case "CONTEXT_STUDYING_MATERIALS_DESTROY" ->
                            comply(States.CONTEXT_STUDYING_MATERIALS_DESTROY, context);
                    case "CONTEXT_TEACHER_DESTROY" -> comply(States.CONTEXT_TEACHER_DESTROY, context);
                    case "CONTEXT_DESTROY" -> comply(States.CONTEXT_DESTROY, context);
                    case "CHILD_INIT" -> comply(States.CHILD_INIT, context);
                    case "CHILD_STUDY" -> comply(States.CHILD_STUDY, context);
                    case "CLASSROOM_INIT" -> comply(States.CLASSROOM_INIT, context);
                    case "GAME_INIT" -> comply(States.GAME_INIT, context);
                    case "PARENT_INIT" -> comply(States.PARENT_INIT, context);
                    case "PARENT_FEED_KIDS" -> comply(States.PARENT_FEED_KIDS, context);
                    case "PARENT_PUT_TO_SLEEP" -> comply(States.PARENT_PUT_TO_SLEEP, context);
                    case "PARENT_GET_TO_SCHOOL" -> comply(States.PARENT_GET_TO_SCHOOL, context);
                    case "PARENT_BRING_BACK_FROM_SCHOOL" -> comply(States.PARENT_BRING_BACK_FROM_SCHOOL, context);
                    case "STUDYING_MATERIALS_INIT" -> comply(States.STUDYING_MATERIALS_INIT, context);
                    case "TEACHER_INIT" -> comply(States.TEACHER_INIT, context);
                    case "TEACHER_TEACH" -> comply(States.TEACHER_TEACH, context);
                    case "TEACHER_EXERCISE" -> comply(States.TEACHER_EXERCISE, context);
                    case "EXIT" -> comply(States.EXIT, context);
                    default -> throw new RuntimeException();
                }
            }
        } catch (Exception e) {
            System.out.println("Saving...");
            new Thread(() -> {
                saveState(context);
                System.gc();
            });
            Thread.sleep(1000);
            main(new String[0]);
        }
    }

    public static void loadState(ContextImpl contextImpl) throws InterruptedException {
        if (new File("src\\main\\resources\\savedState.json").exists()) {
            try (InputStream fileReader = new FileInputStream("src\\main\\java\\resources\\savedState.json")) {
                System.out.println("Current directory: " + System.getProperty("user.dir"));
                JSONObject jsonObject = new JSONObject(fileReader.readAllBytes());

                JSONArray childrenArray = jsonObject.getJSONArray("children");
                childrenArray.toList().forEach(child -> {
                    JSONObject JSONChild = new JSONObject(child.toString());
                    Child someKid = new Child();
                    someKid.setName(JSONChild.getString("name"));
                    someKid.setSlept(JSONChild.getBoolean("slept"));
                    someKid.setAte(JSONChild.getBoolean("ate"));
                    someKid.setTalkedToParents(JSONChild.getBoolean("talkedToParents"));
                    someKid.setStudied(JSONChild.getBoolean("studied"));
                    someKid.setExercised(JSONChild.getBoolean("exercised"));

                    AtomicBoolean momIsFound = new AtomicBoolean(false);
                    contextImpl.getParentList().forEach(parent -> {
                        if (parent.getName().equals(JSONChild.getString("mom"))) {
                            someKid.setMom(parent);
                            momIsFound.set(true);
                        }
                    });
                    if (!momIsFound.get()) {
                        Parent parent = new Parent();
                        parent.setName(JSONChild.getString("mom"));
                        parent.getKids().add(someKid);
                        contextImpl.getParentList().add(parent);
                    }

                    AtomicBoolean dadIsFound = new AtomicBoolean(false);
                    contextImpl.getParentList().forEach(parent -> {
                        if (parent.getName().equals(JSONChild.getString("dad"))) {
                            someKid.setDad(parent);
                            dadIsFound.set(true);
                        }
                    });
                    if (!dadIsFound.get()) {
                        Parent parent = new Parent();
                        parent.setName(JSONChild.getString("dad"));
                        parent.getKids().add(someKid);
                        contextImpl.getParentList().add(parent);
                    }
                });

                JSONArray teacherArray = jsonObject.getJSONArray("teachers");
                teacherArray.toList().forEach(teacher -> {
                    JSONObject JSONTeacher = new JSONObject(teacher.toString());
                    Teacher teacherToPut = new Teacher();
                    teacherToPut.setName(String.valueOf(JSONTeacher.get("name")));
                    contextImpl.getTeacherList().add(teacherToPut);
                });

                JSONArray materialArray = jsonObject.getJSONArray("studyingMaterials");
                materialArray.toList().forEach(studyingMaterial -> {
                    JSONObject JSONStudyingMaterial = new JSONObject(studyingMaterial.toString());
                    StudyingMaterials someStudyingMaterial = new StudyingMaterials();
                    someStudyingMaterial.setTopic(JSONStudyingMaterial.getString("topic"));
                    JSONArray jsonArrayOfSubtopics = JSONStudyingMaterial.getJSONArray("subtopics");
                    Map<String, String> subtopics = new HashMap<>();
                    jsonArrayOfSubtopics.toList().forEach(subtopic -> {
                        JSONObject subtopicObject = new JSONObject(subtopic);
                        subtopics.put(subtopicObject.getString("subtopic"), subtopicObject.getString("content"));
                    });
                    someStudyingMaterial.setMaterials(subtopics);
                    contextImpl.getMaterialsList().add(someStudyingMaterial);
                });

                JSONArray classroomArray = jsonObject.getJSONArray("classrooms");
                classroomArray.toList().forEach(classroom -> {
                    JSONObject jsonClassroom = new JSONObject(classroom.toString());
                    Classroom someClassroom = new Classroom();
                    someClassroom.setId(jsonClassroom.getInt("id"));

                    AtomicBoolean teacherIsFound = new AtomicBoolean(false);
                    contextImpl.getTeacherList().forEach(teacher -> {
                        if (teacher.getName().equals(jsonClassroom.getString("teacher"))) {
                            someClassroom.setTeacher(teacher);
                            teacherIsFound.set(true);
                        }
                    });
                    if (!teacherIsFound.get()) {
                        Teacher teacher = new Teacher();
                        teacher.setName(jsonClassroom.getString("teacher"));
                        contextImpl.getTeacherList().add(teacher);
                    }

                    JSONArray kidsArray = jsonClassroom.getJSONArray("children");
                    kidsArray.toList().forEach(kid -> {
                        AtomicBoolean childIsFound = new AtomicBoolean(false);
                        contextImpl.getChildrenList().forEach(child -> {
                            if (child.getName().equals(new JSONObject(kid).getString("childName"))) {
                                someClassroom.getChildren().add(child);
                                childIsFound.set(true);
                            }
                        });
                        if (!childIsFound.get()) {
                            Child unknownChild = new Child();
                            unknownChild.setName(new JSONObject(kid).getString("childName"));
                            unknownChild.setMom(null);
                            unknownChild.setDad(null);
                            unknownChild.setSlept(false);
                            unknownChild.setStudied(false);
                            unknownChild.setExercised(false);
                            unknownChild.setAte(false);
                            unknownChild.setTalkedToParents(false);
                            someClassroom.getChildren().add(unknownChild);
                            contextImpl.getChildrenList().add(unknownChild);
                        }
                    });
                    contextImpl.getClassroomList().add(someClassroom);
                });

                JSONArray parentsArray = jsonObject.getJSONArray("parents");
                parentsArray.toList().forEach(parent -> {
                    AtomicBoolean parentIsFound = new AtomicBoolean(false);
                    JSONObject parentObject = new JSONObject(parent.toString());
                    Parent someParent = new Parent();
                    contextImpl.getParentList().forEach(contextParent -> {
                        if (contextParent.getName().equals(parentObject.getString("name"))) {
                            parentIsFound.set(true);
                        }
                    });

                    if (!parentIsFound.get()) {
                        someParent.setName(parentObject.getString("name"));
                        contextImpl.getParentList().add(someParent);
                    } else {
                        for (Parent exactParent : contextImpl.getParentList()) {
                            if (parentObject.getString("name").equals(exactParent.getName())) {
                                someParent = exactParent;
                                break;
                            }
                        }
                    }

                    JSONArray childrenOfParent = parentObject.getJSONArray("children");
                    Parent finalSomeParent = someParent;
                    childrenOfParent.toList().forEach(kid -> {
                        AtomicBoolean childIsFound = new AtomicBoolean(false);
                        contextImpl.getChildrenList().forEach(child -> {
                            if (child.getName().equals(new JSONObject(kid).getString("kidName"))) {
                                childIsFound.set(true);
                            }
                        });
                        if (!childIsFound.get()) {
                            Child newChild = new Child();
                            newChild.setName(new JSONObject(kid).getString("kidName"));
                            newChild.setMom(null);
                            newChild.setDad(null);
                            newChild.setSlept(false);
                            newChild.setStudied(false);
                            newChild.setExercised(false);
                            newChild.setAte(false);
                            newChild.setTalkedToParents(false);
                            contextImpl.getChildrenList().add(newChild);
                        } else {
                            for (Child child : contextImpl.getChildrenList()) {
                                if (child.getName().equals(new JSONObject(kid).getString("kidName"))) {
                                    if (!finalSomeParent.getKids().contains(child)) {
                                        finalSomeParent.getKids().add(child);
                                        break;
                                    }
                                }
                            }
                        }
                    });


                });

                JSONArray exercisesArray = jsonObject.getJSONArray("exercises");
                exercisesArray.toList().forEach(exercise -> {
                    ExercisingGames exercisingGame = new ExercisingGames();
                    exercisingGame.setTask(new JSONObject(exercise).getString("task"));
                    exercisingGame.setTime(new Date(new JSONObject(exercise).getString("time")));
                    contextImpl.getExercisingGamesList().add(exercisingGame);
                });
            } catch (Exception e) {
                System.out.println("Something went wrong. It is either about the content of file or parsing error");
                Thread.sleep(5000);
            }
        }
    }

    public static void saveState(ContextImpl contextImpl) {
        List<Child> childrenList = contextImpl.getChildrenList();
        List<Teacher> teacherList = contextImpl.getTeacherList();
        List<StudyingMaterials> materialsList = contextImpl.getMaterialsList();
        List<Classroom> classroomList = contextImpl.getClassroomList();
        List<Parent> parentList = contextImpl.getParentList();
        List<ExercisingGames> exercisingGamesList = contextImpl.getExercisingGamesList();
        JSONObject jsonObject = new JSONObject();

        JSONArray childrenArray = new JSONArray();
        childrenList.forEach(child -> {
            JSONObject childObject = new JSONObject();
            childObject.put("name", child.getName());
            childObject.put("slept", child.isSlept());
            childObject.put("ate", child.isAte());
            childObject.put("talkedToParents", child.isTalkedToParents());
            childObject.put("studied", child.isStudied());
            childObject.put("exercised", child.isExercised());
            childObject.put("mom", child.getMom().getName());
            childObject.put("dad", child.getDad().getName());
            childrenArray.put(childObject);
        });
        jsonObject.put("children", childrenArray);

        JSONArray teacherArray = new JSONArray();
        teacherList.forEach(teacher -> {
            JSONObject teacherObject = new JSONObject();
            teacherObject.put("name", teacher.getName());
            teacherArray.put(teacherObject);
        });
        jsonObject.put("teachers", teacherArray);

        JSONArray materialArray = new JSONArray();
        materialsList.forEach(materials -> {
            JSONObject materialObject = new JSONObject();
            materialObject.put("topic", materials.getTopic());
            JSONArray arrayOfSubtopics = new JSONArray();
            materials.getMaterials().forEach((key, value) -> {
                JSONObject subtopicObject = new JSONObject();
                subtopicObject.put("subtopic", key);
                subtopicObject.put("content", value);
                arrayOfSubtopics.put(subtopicObject);
            });
            materialObject.put("subtopics", arrayOfSubtopics);
            materialArray.put(materialObject);
        });
        jsonObject.put("studyingMaterials", materialArray);

        JSONArray classroomArray = new JSONArray();
        classroomList.forEach(classroom -> {
            JSONObject classroomObject = new JSONObject();
            classroomObject.put("id", classroom.getId());
            classroomObject.put("teacher", classroom.getTeacher().getName());
            JSONArray classroomChildrenArray = new JSONArray();
            classroom.getChildren().forEach(y ->
                    classroomChildrenArray.put(new JSONObject().put("childName", y.getName()))
            );
            classroomObject.put("children", classroomChildrenArray);
        });
        jsonObject.put("classrooms", classroomArray);

        JSONArray parentArray = new JSONArray();
        parentList.forEach(parent -> {
            JSONObject parentObject = new JSONObject();
            parentObject.put("name", parent.getName());
            JSONArray kidsArray = new JSONArray();
            parent.getKids().forEach(kid -> kidsArray.put(new JSONObject().put("kidName", kid.getName())));
            parentObject.put("children", kidsArray);
        });
        jsonObject.put("parents", parentArray);

        JSONArray exerciseArray = new JSONArray();
        exercisingGamesList.forEach(exercisingGames ->
                exerciseArray.put(new JSONObject().put("task", exercisingGames.getTask())
                        .put("time", exercisingGames.getTime())));
        jsonObject.put("exercises", exerciseArray);
        try {
            System.out.println("Current directory: " + System.getProperty("user.dir"));
            if (new File("src\\main\\resources\\savedState.json").exists()) {
                FileWriter writer = new FileWriter("src\\main\\resources\\savedState.json", false);
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void comply(States state, ContextImpl context) throws InterruptedException {
        try {
            switch (state) {
                case CONTEXT_INIT -> loadState(context);
                case CONTEXT_GET_CHILDREN -> {
                    System.out.println("Enter the name of children to get");
                    System.out.println(context.getChild(new Scanner(System.in).nextLine()));
                }
                case CHILD_INIT -> {
                    System.out.println("Enter the name of the child");
                    Child child = new Child();
                    Scanner scanner = new Scanner(System.in);
                    child.setName(scanner.nextLine());
                    child.setStudyingMaterials(null);
                    child.setExercised(false);
                    child.setTalkedToParents(false);
                    child.setSlept(false);
                    child.setAte(false);
                    if (context.getParentList().isEmpty()) {
                        System.out.println("For now you are unable to define the parents of the child in the list");
                        child.setMom(null);
                        child.setDad(null);
                        break;
                    }
                    System.out.println("Enter the mom's name");
                    String mom = scanner.nextLine();
                    List<Parent> parentList = context.getParents(mom).toList();
                    while (parentList.isEmpty()) {
                        System.out.println("There is no such parent. Try again");
                        if (mom.equals("no")) {
                            break;
                        }
                        mom = scanner.nextLine();
                        parentList = context.getParents(mom).toList();
                    }
                    child.setMom(parentList.get(0));
                    System.out.println("Enter the dad's name");
                    String dad = scanner.nextLine();
                    parentList = context.getParents(dad).toList();
                    while (parentList.isEmpty()) {
                        System.out.println("There is no such parent. Try again");
                        dad = scanner.nextLine();
                        if (dad.equals("no")) {
                            break;
                        }
                        parentList = context.getParents(dad).toList();
                    }
                    child.setDad(parentList.get(0));
                }
                case CHILD_STUDY -> {
                    System.out.println("Enter the name of the child to study");
                    context.getChild(new Scanner(System.in).nextLine()).forEach(Child::study);
                    System.out.println("Now they are studying");
                }
                case CONTEXT_CHILD_DESTROY -> {
                    System.out.println("Enter the name of the child to remove");
                    context.removeChildren(new Scanner(System.in).nextLine());
                }
                case CONTEXT_GET_CLASSROOMS -> {
                    System.out.println("Enter the id of the classroom");
                    context.getClassroom(
                                    new Scanner(System.in).nextInt()).
                            forEach(x -> {
                                System.out.println("Id: " + x.getId() + "\nTeacher: " +
                                        x.getTeacher().getName() + "\nChildren: ");
                                x.getChildren().forEach(y -> System.out.print(y.getName() + " "));
                                System.out.print("\n");
                            });
                }
                case CLASSROOM_INIT -> {
                    System.out.println("Enter the id of the classroom");
                    Classroom classroom = new Classroom();
                    Scanner scanner = new Scanner(System.in);
                    int id = scanner.nextInt();
                    if (!context.getClassroom(id).toList().isEmpty()) {
                        System.out.println("The classroom already exists. Enter the new id");
                        id = scanner.nextInt();
                    }
                    classroom.setId(id);

                    if (context.getTeacherList().isEmpty()) {
                        System.out.println("For now you can't form classes due to the lack of staff");
                        break;
                    }
                    String teacher = scanner.nextLine();
                    List<Teacher> teacherList = context.getTeacher(teacher).toList();
                    if (context.getTeacher(teacher).toList().isEmpty()) {
                        System.out.println("There is no such teacher. Try again");
                        teacher = scanner.nextLine();
                        teacherList = context.getTeacher(teacher).toList();
                    }
                    classroom.setTeacher(teacherList.get(0));

                    if (context.getChildrenList().isEmpty()) {
                        System.out.println("For now you can't form classes due to the lack of demanding children");
                        break;
                    }
                    String children = scanner.nextLine();
                    List<Child> childrenList = context.getChild(children).toList();
                    if (children.isEmpty()) {
                        System.out.println("There is no such kid. Try again");
                        children = scanner.nextLine();
                        childrenList = context.getChild(children).toList();
                    }
                    childrenList.forEach(x -> classroom.getChildren().add(x));
                    context.getClassroomList().add(classroom);
                }
                case CONTEXT_CLASSROOM_DESTROY -> {
                    System.out.println("Enter the id of classroom to be destroyed");
                    context.removeClassrooms(new Scanner(System.in).nextInt());
                }
                case CONTEXT_GET_GAMES -> {
                    System.out.println("Enter the task");
                    context.getGames(new Scanner(System.in).nextLine()).forEach(x ->
                            System.out.println("Task: " + x.getTask() + "\nTime:" + x.getTime()));
                }
                case GAME_INIT -> {
                    ExercisingGames exercisingGames = new ExercisingGames();
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter the task");
                    exercisingGames.setTask(scanner.nextLine());
                    System.out.println("Enter the year");
                    int year = scanner.nextInt();
                    System.out.println("Enter the month");
                    int month = scanner.nextInt();
                    System.out.println("Enter the date");
                    int date = scanner.nextInt();

                    exercisingGames.setTime(new Date(year, month, date));
                    context.getExercisingGamesList().add(exercisingGames);
                }
                case CONTEXT_GAME_DESTROY -> {
                    System.out.println("Enter the exercising game to remove by the task");
                    context.removeGames(new Scanner(System.in).nextLine());
                }
                case CONTEXT_GET_PARENTS -> {
                    System.out.println("Enter the name of the parent");
                    context.getParents(new Scanner(System.in).nextLine()).forEach(x -> {
                        System.out.println("Parent: " + x.getName() + " ");
                        System.out.println("Children: ");
                        x.getKids().forEach(y -> System.out.print(y.getName() + " "));
                    });
                }
                case PARENT_INIT -> {
                    System.out.println("Enter the parents name");
                    Parent parent = new Parent();
                    parent.setName(new Scanner(System.in).nextLine());
                    context.getParentList().add(parent);
                }
                case PARENT_FEED_KIDS -> {
                    System.out.println("Enter the parents name");
                    context.getParents(new Scanner(System.in).nextLine()).forEach(x ->
                            x.getKids().forEach(x::feedChildren));
                }
                case PARENT_PUT_TO_SLEEP -> {
                    System.out.println("Enter the name of the parent");
                    context.getParents(new Scanner(System.in).nextLine()).forEach(x -> x.getKids()
                            .forEach(x::getChildrenToSleep));
                }
                case PARENT_GET_TO_SCHOOL -> {
                    System.out.println("Enter the name of the parent");
                    Scanner scanner = new Scanner(System.in);
                    List<Parent> parentList = context.getParents(scanner.nextLine()).toList();
                    if (!parentList.isEmpty()) {
                        System.out.println("Enter the id of the classroom");
                        List<Classroom> classroomList = context.getClassroom(scanner.nextInt()).toList();
                        parentList.forEach(x -> x.getKidsToSchool(classroomList.get(0)));
                    }
                }
                case PARENT_BRING_BACK_FROM_SCHOOL -> {
                    System.out.println("Enter the name of the parent");
                    Scanner scanner = new Scanner(System.in);
                    List<Parent> parentList = context.getParents(scanner.nextLine()).toList();
                    if (!parentList.isEmpty()) {
                        System.out.println("Enter the id of the classroom");
                        List<Classroom> classroomList = context.getClassroom(scanner.nextInt()).toList();
                        parentList.forEach(x -> classroomList.forEach(x::bringKidsBack));
                    }
                }
                case CONTEXT_PARENT_DESTROY -> {
                    System.out.println("Enter the name of the parent to remove");
                    context.removeParents(new Scanner(System.in).nextLine());
                }
                case CONTEXT_GET_STUDYING_MATERIALS -> {
                    System.out.println("Enter the topic of the studying material to destroy");
                    context.getMaterial(new Scanner(System.in).nextLine()).forEach(x -> {
                        System.out.println("Topic:" + x.getTopic());
                        x.getMaterials().forEach((key, value) -> System.out.println("Subtopic: " + key
                                + "\nContent: " + value));
                    });
                }
                case STUDYING_MATERIALS_INIT -> {
                    Scanner scanner = new Scanner(System.in);
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
                    context.getMaterialsList().add(studyingMaterials);
                }
                case CONTEXT_STUDYING_MATERIALS_DESTROY -> {
                    System.out.println("Enter the topic");
                    context.removeStudyingMaterials(new Scanner(System.in).nextLine());
                }
                case CONTEXT_GET_TEACHERS -> {
                    System.out.println("Enter the name of the teacher");
                    context.getTeacher(new Scanner(System.in).nextLine())
                            .forEach(x -> System.out.println("Teacher: " + x.getName()));
                }
                case TEACHER_INIT -> {
                    System.out.println("Enter the teacher's name");
                    Teacher teacher = new Teacher();
                    teacher.setName(new Scanner(System.in).nextLine());
                    context.getTeacherList().add(teacher);
                }
                case TEACHER_TEACH -> {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter the teacher's name");
                    List<Teacher> teacherList = context.getTeacher(scanner.nextLine()).toList();
                    System.out.println("Enter the id of the classroom");
                    List<Classroom> classroomList = context.getClassroom(scanner.nextInt()).toList();
                    System.out.println("Enter the topic of the studying materials");
                    List<StudyingMaterials> studyingMaterialsList = context.getMaterial(scanner.nextLine()).toList();
                    if (!(teacherList.isEmpty() && classroomList.isEmpty() && studyingMaterialsList.isEmpty())) {
                        teacherList.forEach(x -> classroomList
                                .forEach(y -> x.giveStudyingMaterials(y, studyingMaterialsList.get(0))));
                    }
                }
                case TEACHER_EXERCISE -> {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter the teacher's name");
                    List<Teacher> teacherList = context.getTeacher(scanner.nextLine()).toList();
                    System.out.println("Enter the id of the classroom");
                    List<Classroom> classroomList = context.getClassroom(scanner.nextInt()).toList();
                    System.out.println("Enter the task of the exercise");
                    List<ExercisingGames> exercisingGamesList = context.getGames(scanner.nextLine()).toList();
                    if (!(teacherList.isEmpty() && classroomList.isEmpty() && exercisingGamesList.isEmpty())) {
                        teacherList.forEach(x -> classroomList
                                .forEach(y -> x.exercise(y, exercisingGamesList.get(0))));
                    }
                }
                case CONTEXT_TEACHER_DESTROY -> {
                    System.out.println("Enter the name of the teacher");
                    context.removeTeachers(new Scanner(System.in).nextLine());
                }
                case CONTEXT_DESTROY -> {
                    context.getChildrenList().clear();
                    context.getParentList().clear();
                    context.getClassroomList().clear();
                    context.getMaterialsList().clear();
                    context.getExercisingGamesList().clear();
                }
                case EXIT -> {
                    saveState(context);
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Thread.sleep(5000);
            comply(state, context);
        }
    }
}
