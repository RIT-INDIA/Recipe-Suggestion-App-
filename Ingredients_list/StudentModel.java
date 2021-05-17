package com.example.akash.cook;

public class StudentModel {

    //@SerializedName("UserName")
    //@Expose
    private String name;

    public String getName() {
        return name;
    }

    //This private field to maintain to every row's state...!
    private boolean isSelected;

    public boolean isSelected() {
        if(StudentAdapter.finallist.contains(name))
        {
            return true;
        }
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setName(String name) {
        this.name=name;
    }
}
