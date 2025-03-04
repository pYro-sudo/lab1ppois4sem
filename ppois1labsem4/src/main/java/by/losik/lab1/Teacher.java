package by.losik.lab1;

import lombok.Data;

@Data
public class Teacher implements TeacherTasks {
    private String name;

    @Override
    public StudyingMaterials giveStudyingMaterials(Classroom classroom, StudyingMaterials materials) {
        if (this == classroom.getTeacher()) {
            for (Child child : classroom.getChildren()) {
                child.setStudyingMaterials(materials);
            }
        }
        return materials;
    }

    @Override
    public ExercisingGames exercise(Classroom classroom, ExercisingGames exercisingGames) {
        if (this == classroom.getTeacher()) {
            for (Child child : classroom.getChildren()) {
                child.setExercised(true);
            }
        }
        return exercisingGames;
    }
}