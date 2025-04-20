package by.losik.lab1;

import lombok.Data;

import java.util.Map;

@Data
public class StudyingMaterials {
    private String topic;
    private Map<String, String> materials;
}
