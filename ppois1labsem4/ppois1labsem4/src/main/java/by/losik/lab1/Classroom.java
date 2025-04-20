package by.losik.lab1;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

@Data
public class Classroom {
    private int id;
    private Teacher teacher;
    private List<Child> children = new ArrayList<>();
}
