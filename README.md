# Laboratory task 1, variant 75, Losik Yaroslav

## Problem

Simulate a kindengarten.

### Subject domain

Children's education & upbringing

---

## **1. Core Entity Classes**
These represent the main data models in the system.

### **`Child.java`**
- Represents a **student** in the educational system.
- Contains `name`, `slept`, `ate`, `talkedToParents`, `studied`, `exercised`, `mom`, `dad`.
- Interacts with `Parent.java` and `Classroom.java`.

### **`Parent.java`**
- Represents a **parent** of a child.
- Has methods related to `ParentDuties.java`.

### **`Teacher.java`**
- Represents an **educator** with responsibilities.
- Can store `name`.
- Is linked to `TeacherTasks.java` for role-specific duties.

### **`Classroom.java`**
- Represents a **class or group** where teaching occurs.
- Likely contains fields like `id`, `teacher`, and a list of `Child` students.

### **`StudyingMaterials.java`**
- Represents **educational resources** (books, PDFs, assignments, but they are presented via `HasMap`).

### **`ExercisingGames.java`**
- Represents **physical education activities**.
- Contains a `task` field, describing the exercise.

---

## **2. CRUD Operations**
Each entity has a corresponding `*CRUD.java` class for database-like operations.

### **Common CRUD Methods:**
- **`create()`** – Adds a new record.
- **`read()`** – Retrieves an entity.
- **`update()`** – Modifies an existing record.
- **`delete()`** – Removes an entity.

### **Example: `TeacherCRUD.java`**
- Manages persistence for `Teacher` objects.

### **`EntityCRUD.java` (Generic Base Class)**
- Likely an **abstract class or interface** providing default CRUD methods.
- Other `*CRUD` classes may extend/implement this.

---

## **3. Business Logic & Context Management**
### **`Context.java` & `ContextImpl.java`**
- **`Context.java`** is an **interface** defining application-wide operations.
- **`ContextImpl.java`** is the **concrete implementation**.
- May handle:
  - Loading/saving data (via `Serializer.java`).
  - Managing relationships (e.g., assigning a `Child` to a `Classroom`).
  - Validating business rules.

### **`ContextImplCRUD.java`**
- Manages persistence for `all` objects.

---

## **4. Serialization & Data Persistence**
### **`Serializer.java`**
- Likely handles **reading/writing objects** to/from files (JSON, XML, or binary).
- Used by `*CRUD` classes to persist data.

---

## **5. Additional Responsibilities**
### **`ParentDuties.java`**
- Defines **responsibilities of parents** (e.g., attending meetings, paying fees).
- Could be an **enum** or a class with predefined duty types.

### **`TeacherTasks.java`**
- Defines **tasks for teachers** (e.g., grading, lesson planning).
- May include methods like `assignHomework()` or `conductExam()`.

---

## **6. `App.java` (Main Class)**
- The **entry point** of the application.
- Initializes:
  - The `Context` (e.g., `ContextImpl`).
  - Sample data (like `Scanner` instance).
  - A **menu-driven CLI**.

### **Example Structure:**
```java
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
```
