package cn.harryai.harrywebsocket.model;

/**
 * @author Harry
 * @since 2019/6/30 10:37
 */
public class User {
    private String treeId;


    private String name;

    public User(String name, String treeId) {
        this.treeId = treeId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "treeId='" + treeId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
