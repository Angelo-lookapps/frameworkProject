package model;
/**
 *
 * @author Angelo-KabyLake
 */
public abstract class BaseModel {
    String id;
 

    public BaseModel(String id) {
        this.id = id;
    }
    public BaseModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
