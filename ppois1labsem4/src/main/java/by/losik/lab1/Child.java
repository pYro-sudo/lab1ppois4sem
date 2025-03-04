package by.losik.lab1;

import lombok.Data;

@Data
public class Child {
    private String name;
    private boolean slept;
    private boolean ate;
    private boolean talkedToParents;
    private boolean studied;
    private boolean exercised;
    private Parent mom;
    private Parent dad;
    private StudyingMaterials studyingMaterials;

    public boolean study(){
        studied = !(this.studyingMaterials == null);
        return studied;
    }
}
