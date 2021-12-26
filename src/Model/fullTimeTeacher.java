package Model;
//Nguyen Anh Tuan
public class fullTimeTeacher extends Teacher{
    private double coefficient;

    private  int idFullTime;
    static int count=0;


    public fullTimeTeacher(String name, int teachAtSchoolYear, String specialty, String phoneNumber, double coefficient) {
        super(name, teachAtSchoolYear, specialty, phoneNumber);
        this.coefficient = coefficient;
        this.idFullTime = ++count;
        this.setSalary(coefficient *3000000);

    }

    public fullTimeTeacher(double coefficient){
        this.coefficient = coefficient;
    }



    public fullTimeTeacher(int idFullTime) {
        this.idFullTime = idFullTime;
    }


    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }


    public double getLuong(){
        return this.getCoefficient() *3000000;
    } //overidding

    public String GetKind(){
        return "Full Time";
    }//overidding

    public  int getIdFullTime() {
        return this.idFullTime;
    }



}
