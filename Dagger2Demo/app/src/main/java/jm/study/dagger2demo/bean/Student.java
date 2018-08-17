package jm.study.dagger2demo.bean;

/**
 * @author PRDEV
 *         Created on 2018-8-16.
 */

public class Student {

    private String name;
    private String id;
    private String gender;

    public Student(String name, String id, String gender) {
        this.name = name;
        this.id = id;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
