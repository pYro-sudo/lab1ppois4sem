package by.losik.lab1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Serializer implements Serializable {
    private ContextImpl contextImpl;

    public Serializer(ContextImpl context){
        this.contextImpl = context;
    }

    public void loadState() throws InterruptedException {
        if (new File("src\\main\\resources\\savedState.json").exists()) {
            try {
                System.out.println("Current directory: " + System.getProperty("user.dir"));
                Scanner scanner = new Scanner(new File("src\\main\\resources\\savedState.json"));
                StringBuilder stringBuilder = new StringBuilder();
                while(scanner.hasNextLine()){
                    stringBuilder.append(scanner.nextLine());
                }
                JSONObject jsonObject = new JSONObject(String.valueOf(stringBuilder));

                JSONArray childrenArray = jsonObject.getJSONArray("children");
                childrenArray.forEach(child -> {
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
                teacherArray.forEach(teacher -> {
                    JSONObject JSONTeacher = new JSONObject(teacher.toString());
                    Teacher teacherToPut = new Teacher();
                    teacherToPut.setName(String.valueOf(JSONTeacher.get("name")));
                    contextImpl.getTeacherList().add(teacherToPut);
                });

                JSONArray materialArray = jsonObject.getJSONArray("studyingMaterials");
                materialArray.forEach(studyingMaterial -> {
                    JSONObject JSONStudyingMaterial = new JSONObject(studyingMaterial.toString());
                    StudyingMaterials someStudyingMaterial = new StudyingMaterials();
                    someStudyingMaterial.setTopic(JSONStudyingMaterial.getString("topic"));

                    JSONArray jsonArrayOfSubtopics = JSONStudyingMaterial.getJSONArray("subtopics");
                    Map<String, String> subtopics = new HashMap<>();
                    jsonArrayOfSubtopics.forEach(subtopic -> {
                        JSONObject subtopicObject = new JSONObject(subtopic.toString());
                        if (subtopicObject.has("subtopic") && subtopicObject.has("content")) {
                            subtopics.put(subtopicObject.getString("subtopic"), subtopicObject.getString("content"));
                        } else {
                            System.out.println("Warning: Missing 'subtopic' or 'content' in JSON object: " + subtopicObject);
                        }
                    });
                    someStudyingMaterial.setMaterials(subtopics);
                    contextImpl.getMaterialsList().add(someStudyingMaterial);
                });

                JSONArray classroomArray = jsonObject.getJSONArray("classrooms");
                classroomArray.forEach(classroom -> {
                    JSONObject jsonClassroom = new JSONObject(classroom.toString());
                    Classroom someClassroom = new Classroom();
                    someClassroom.setId(jsonClassroom.getInt("id"));

                    String teacherName = jsonClassroom.getString("teacher");
                    contextImpl.getTeacherList().stream()
                            .filter(teacher -> teacher.getName().equals(teacherName))
                            .findFirst()
                            .ifPresentOrElse(someClassroom::setTeacher, () -> {
                                Teacher newTeacher = new Teacher();
                                newTeacher.setName(teacherName);
                                contextImpl.getTeacherList().add(newTeacher);
                                someClassroom.setTeacher(newTeacher);
                            });

                    JSONArray kidsArray = jsonClassroom.getJSONArray("children");
                    kidsArray.forEach(kid -> {
                        JSONObject kidObject = new JSONObject(kid.toString());
                        if (kidObject.has("childName")) {
                            String childName = kidObject.getString("childName");

                            Child newChild = contextImpl.getChildrenList().stream()
                                    .filter(child -> child.getName().equals(childName))
                                    .findFirst()
                                    .orElseGet(() -> {
                                        Child child = new Child();
                                        child.setName(childName);
                                        child.setMom(null);
                                        child.setDad(null);
                                        child.setSlept(false);
                                        child.setStudied(false);
                                        child.setExercised(false);
                                        child.setAte(false);
                                        child.setTalkedToParents(false);
                                        contextImpl.getChildrenList().add(child);
                                        return child;
                                    });

                            someClassroom.getChildren().add(newChild);
                        } else {
                            System.out.println("Warning: Missing 'childName' in JSON object: " + kidObject);
                        }
                    });

                    contextImpl.getClassroomList().add(someClassroom);
                });

                JSONArray parentsArray = jsonObject.getJSONArray("parents");
                parentsArray.forEach(parent -> {
                    JSONObject parentObject = new JSONObject(parent.toString());
                    String parentName = parentObject.getString("name");

                    Parent someParent = contextImpl.getParentList().stream()
                            .filter(p -> p.getName().equals(parentName))
                            .findFirst()
                            .orElseGet(() -> {
                                Parent newParent = new Parent();
                                newParent.setName(parentName);
                                contextImpl.getParentList().add(newParent);
                                return newParent;
                            });

                    JSONArray childrenOfParent = parentObject.getJSONArray("children");
                    childrenOfParent.forEach(kid -> {
                        JSONObject kidObject = new JSONObject(kid.toString());
                        String kidName = kidObject.getString("kidName");

                        Child newChild = contextImpl.getChildrenList().stream()
                                .filter(c -> c.getName().equals(kidName))
                                .findFirst()
                                .orElseGet(() -> {
                                    Child child = new Child();
                                    child.setName(kidName);
                                    child.setMom(null);
                                    child.setDad(null);
                                    child.setSlept(false);
                                    child.setStudied(false);
                                    child.setExercised(false);
                                    child.setAte(false);
                                    child.setTalkedToParents(false);
                                    contextImpl.getChildrenList().add(child);
                                    return child;
                                });

                        if (!someParent.getKids().contains(newChild)) {
                            someParent.getKids().add(newChild);
                        }
                    });
                });

                JSONArray exercisesArray = jsonObject.getJSONArray("exercises");
                exercisesArray.forEach(exercise -> {
                    JSONObject exerciseObject = new JSONObject(exercise.toString());
                    if (exerciseObject.has("task")) {
                        ExercisingGames exercisingGame = new ExercisingGames();
                        exercisingGame.setTask(exerciseObject.getString("task"));
                        contextImpl.getExercisingGamesList().add(exercisingGame);
                    } else {
                        System.out.println("Warning: Missing 'task' in JSON object: " + exerciseObject);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Something went wrong. It is either about the content of file or parsing error." +
                        "\nCheck that all fields are not empty in the file.");
                Thread.sleep(5000);
            }
        }
    }

    public void saveState() {
        List<Child> childrenList = contextImpl.getChildrenList();
        List<Teacher> teacherList = contextImpl.getTeacherList();
        List<Parent> parentList = contextImpl.getParentList();
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
            childObject.put("mom", child.getMom() == null ? "" : child.getMom().getName());
            childObject.put("dad", child.getDad() == null ? "" : child.getDad().getName());
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
        contextImpl.getMaterialsList().forEach(materials -> {
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
        contextImpl.getClassroomList().forEach(classroom -> {
            JSONObject classroomObject = new JSONObject();
            classroomObject.put("id", classroom.getId());
            classroomObject.put("teacher", classroom.getTeacher().getName());
            JSONArray classroomChildrenArray = new JSONArray();
            classroom.getChildren().forEach(child -> {
                JSONObject childObject = new JSONObject();
                childObject.put("childName", child.getName());
                classroomChildrenArray.put(childObject);
            });
            classroomObject.put("children", classroomChildrenArray);
            classroomArray.put(classroomObject);
        });
        jsonObject.put("classrooms", classroomArray);

        JSONArray parentArray = new JSONArray();
        parentList.forEach(parent -> {
            JSONObject parentObject = new JSONObject();
            parentObject.put("name", parent.getName());
            JSONArray kidsArray = new JSONArray();
            parent.getKids().forEach(kid -> {
                JSONObject kidObject = new JSONObject();
                kidObject.put("kidName", kid.getName());
                kidsArray.put(kidObject);
            });
            parentObject.put("children",kidsArray);
            parentArray.put(parentObject);
        });
        jsonObject.put("parents", parentArray);

        JSONArray exerciseArray = new JSONArray();
        contextImpl.getExercisingGamesList().forEach(exercisingGames -> {
            JSONObject exerciseObject = new JSONObject();
            exerciseObject.put("task", exercisingGames.getTask());
            exerciseArray.put(exerciseObject);
        });
        jsonObject.put("exercises", exerciseArray);
        try {
            System.out.println("Current directory: " + System.getProperty("user.dir"));
            if (new File("src\\main\\resources\\savedState.json").exists()) {
                System.out.println("Saving...");
                System.out.println(jsonObject);
                FileWriter writer = new FileWriter("src\\main\\resources\\savedState.json", false);
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ContextImpl getContext() {
        return contextImpl;
    }

    public void setContext(ContextImpl context) {
        this.contextImpl = context;
    }
}
