package com.kangwon.a356.kangwonunivapp.dataprocess.database.layoutdataset;

public class ListItem {


        String classname;
        String instructor;

        public ListItem(String classname, String instructor)
        {
            this.classname = classname;
            this.instructor = instructor;
        }

    public String getClassname() {
        return classname;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
