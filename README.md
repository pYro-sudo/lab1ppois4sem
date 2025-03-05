# Laboratory task 1, variant 75, Losik Yaroslav

## Problem

Simulate a kindengarten.

### Subject domain

Children's education & upbringing

### Main Entities

- ```Teacher```
- ```Child```
- ```Studying materials```
- ```Parent```
- ```Exercising games```

## Implementation

- ```App.java``` is [located in src/main/java/by/losik/lab1][1]
- ```Child.java``` is [located in src/main/java/by/losik/lab1][1]
- ```Classroom.java``` is [located in src/main/java/by/losik/lab1][1]
- ```Context.java``` is [located in src/main/java/by/losik/lab1][1]
- ```ContextImpl.java``` is [located in src/main/java/by/losik/lab1][1]
- ```ExercisingGames.java``` is [located in src/main/java/by/losik/lab1][1]
- ```Parent.java``` is [located in src/main/java/by/losik/lab1][1]
- ```ParentDuties.java``` is [located in src/main/java/by/losik/lab1][1]
- ```States.java``` is [located in src/main/java/by/losik/lab1][1]
- ```StudyingMaterials.java``` is [located in src/main/java/by/losik/lab1][1]
- ```Teacher.java``` is [located in src/main/java/by/losik/lab1][1]
- ```TeacherTasks.java``` [located in src/main/java/by/losik/lab1][1]

[1]: https://github.com/pYro-sudo/lab1ppois4sem/tree/main/ppois1labsem4/src/main/java/by/losik/lab1

##About

- ```App.java``` is used for launching the application.
- ```Child.java``` represents a child entity.
- ```Classroom.java``` is used as a storage of children and teachers.
- ```Context.java``` is used to store all the information about every single entity of this project, an interface.
- ```ContextImpl.java``` is the class that implements ```Context.java```.
- ```ExercisingGames.java``` represents a unit of activity.
- ```Parent.java``` represents a parent of a child.
- ```ParentDuties.java``` is used to point out main actions performed by parents.
- ```States.java``` is a class used to avoid implementing so-called 'State' pattern.
- ```StudyingMaterials.java``` is used as an educational unit.
- ```Teacher.java``` is the teacher of children, which belongs to some classrom, in which he is capable of providing tasks.
- ```TeacherTasks.java``` an interface to mark the actions performed by a teacher.

