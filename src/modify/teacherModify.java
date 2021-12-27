package modify;

import Model.Teacher;
import Model.fullTimeTeacher;
import Model.partTimeTeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sample.Main;

import java.net.URL;

//Vu Trong Nhan
public class teacherModify {
    private static ObservableList<Teacher> TeacherList = FXCollections.observableArrayList();
    private static ObservableList<fullTimeTeacher> TeacherFullTimeList = FXCollections.observableArrayList();
    private static ObservableList<partTimeTeacher> TeacherPartTimeList = FXCollections.observableArrayList();


    public teacherModify() {
    }

    public boolean addTeacher(Teacher teacher){ // upcasting fullTime or partTime teacher
        boolean flag = true ;
        for (int i = 0; i < TeacherList.size(); i++) {
            if(TeacherList.get(i).getName().equals(teacher.getName()) && TeacherList.get(i).getPhoneNumber().equals(teacher.getPhoneNumber()) && TeacherList.get(i).getSalary() == teacher.getSalary() && TeacherList.get(i).getSpecialty().equals(teacher.getSpecialty()) && TeacherList.get(i).getTeachAtSchoolYear() == teacher.getTeachAtSchoolYear()){
                flag = false;
                break;
            }
        }
        if(flag == true){
            TeacherList.add(teacher);
            if(teacher.GetKind() == "Full Time"){
                fullTimeTeacher teacher1 = (fullTimeTeacher) teacher; // dowcasting to fullTimeTeacher
                TeacherFullTimeList.add(teacher1);
            }
            if(teacher.GetKind() == "Part Time"){
                partTimeTeacher teacher1 = (partTimeTeacher) teacher;// dowcasting to partTimeTeacher
                TeacherPartTimeList.add(teacher1);
            }
        }
       return flag;
    }


    public void deleteFullTime(fullTimeTeacher teacher){

        for (int i = 0; i <TeacherFullTimeList.size() ; i++) {//delete full time table
            if(TeacherFullTimeList.get(i).getIdFullTime() == teacher.getIdFullTime()){
                TeacherFullTimeList.remove(TeacherFullTimeList.get(i));
                break;
            }
        }

        for (int i = 0; i < TeacherList.size(); i++) { //delete main table
            if(TeacherList.get(i).GetKind().equals("Full Time")){
                fullTimeTeacher teacher1 = (fullTimeTeacher) TeacherList.get(i);
                if(teacher1.getIdFullTime() == teacher.getIdFullTime()) {
                    TeacherList.remove(TeacherList.get(i));
                    break;
                }
            }
        }
    }
    public void deletePartTime(partTimeTeacher teacher){//delete part time table
        for (int i = 0; i < TeacherPartTimeList.size(); i++) {
            if(TeacherPartTimeList.get(i).getIdPartTime() == teacher.getIdPartTime() ){
                TeacherPartTimeList.remove(TeacherPartTimeList.get(i));
                break;
            }
        }
        for (int i = 0; i < TeacherList.size(); i++) {//delete main table
            if(TeacherList.get(i).GetKind().equals("Part Time")){
                partTimeTeacher teacher1 = (partTimeTeacher) TeacherList.get(i);
                if(teacher1.getIdPartTime() == teacher.getIdPartTime()){
                    TeacherList.remove(TeacherList.get(i));
                    break;
                }
            }
        }
    }

    public  void updateFullTime(String name,int teachAtSchoolYear,String specialty,String phoneNumber,double coefficient,double salary,int idFullTime){
        for (int i = 0; i < TeacherFullTimeList.size(); i++) {
            if(TeacherFullTimeList.get(i).getIdFullTime() == idFullTime){
                TeacherFullTimeList.get(i).setName(name);
                TeacherFullTimeList.get(i).setTeachAtSchoolYear(teachAtSchoolYear);
                TeacherFullTimeList.get(i).setSpecialty(specialty);
                TeacherFullTimeList.get(i).setPhoneNumber(phoneNumber);
                TeacherFullTimeList.get(i).setCoefficient(coefficient);
                TeacherFullTimeList.get(i).setSalary(salary);
                break;
            }

        }
        for (int j = 0; j < TeacherList.size(); j++) {
            if(TeacherList.get(j).GetKind().equals("Full Time")) {
                fullTimeTeacher teacher = (fullTimeTeacher) TeacherList.get(j);
                if (teacher.getIdFullTime() == idFullTime) {
                    TeacherList.get(j).setName(name);
                    TeacherList.get(j).setTeachAtSchoolYear(teachAtSchoolYear);
                    TeacherList.get(j).setSpecialty(specialty);
                    TeacherList.get(j).setPhoneNumber(phoneNumber);
                    TeacherList.get(j).setSalary(salary);
                    break;
                }
            }
        }
    }
    public  void updatePartTime(String name,int teachAtSchoolYear,String specialty,String phoneNumber,double teachingHour,double salary,int idPartTime){
        for (int i = 0; i < TeacherPartTimeList.size(); i++) {
            if(TeacherPartTimeList.get(i).getIdPartTime() == idPartTime){
                TeacherPartTimeList.get(i).setName(name);
                TeacherPartTimeList.get(i).setTeachAtSchoolYear(teachAtSchoolYear);
                TeacherPartTimeList.get(i).setSpecialty(specialty);
                TeacherPartTimeList.get(i).setPhoneNumber(phoneNumber);
                TeacherPartTimeList.get(i).setTeachingHour(teachingHour);
                TeacherPartTimeList.get(i).setSalary(salary);
                break;
            }
            for (int j = 0; j < TeacherList.size(); j++) {
                partTimeTeacher teacher = (partTimeTeacher) TeacherList.get(i);
                if(teacher.getIdPartTime()== idPartTime){
                    TeacherList.get(i).setName(name);
                    TeacherList.get(i).setTeachAtSchoolYear(teachAtSchoolYear);
                    TeacherList.get(i).setSpecialty(specialty);
                    TeacherList.get(i).setPhoneNumber(phoneNumber);
                    TeacherList.get(i).setSalary(salary);
                    break;
                }

            }
        }
    }


    public static  ObservableList<fullTimeTeacher> returnFullTimeList(){
        return TeacherFullTimeList;
    }
    public  ObservableList<partTimeTeacher> returnPartTimeList(){
        return TeacherPartTimeList;
    }
    public static ObservableList<Teacher> returnTeacher() {
        return TeacherList;
    }

   // find name ,speacialty
   public static   ObservableList<Teacher> returnNameList(String nameText){
       ObservableList<Teacher> nameList = FXCollections.observableArrayList();
       String regex = ".*\\b"+nameText.toLowerCase()+".*";
       for (int i = 0; i < TeacherList.size(); i++) {
           if(TeacherList.get(i).getName().toLowerCase().matches(regex)){
               nameList.add(TeacherList.get(i));
           }
       }
       return nameList;
    }
    public static  ObservableList<Teacher> returnSpecialtyList(String specialtyText){
        ObservableList<Teacher> specialtyList = FXCollections.observableArrayList();
        String regex = ".*\\b"+specialtyText.toLowerCase()+".*";
        for (int i = 0; i < TeacherList.size(); i++) {
            if(TeacherList.get(i).getSpecialty().toLowerCase().matches(regex)){
                specialtyList.add(TeacherList.get(i));
            }
        }
        return specialtyList;
    }
    public static ObservableList<Teacher> returnSalaryList(double salary){
        ObservableList<Teacher> salaryList = FXCollections.observableArrayList();
        for (int i = 0; i < TeacherList.size(); i++) {
            if(TeacherList.get(i).getSalary()>= salary){
                salaryList.add(TeacherList.get(i));
            }
        }
        return salaryList;
    }
}

