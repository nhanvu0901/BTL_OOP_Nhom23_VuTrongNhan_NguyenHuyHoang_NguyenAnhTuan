package Model;
//Nguyen Huy Hoang
public class partTimeTeacher extends Teacher{
    private double teachingHour;

    private  int idPartTime;

    static int count=0;



    public partTimeTeacher(String name, int teachAtSchoolYear, String specialty, String phoneNumber, double teachingHour) {
        super(name, teachAtSchoolYear, specialty, phoneNumber);
        this.teachingHour = teachingHour;
        this.idPartTime =  ++count;
        this.setSalary(teachingHour*500000 + 1000000);
    }

    public partTimeTeacher(int idPartTime) {
        this.idPartTime = idPartTime;
    }

    public double getTeachingHour() {
        return teachingHour;
    }

    public void setTeachingHour(double teachingHour) {
        this.teachingHour = teachingHour;
    }

    public int getIdPartTime() {
        return idPartTime;
    }

    public double getLuong(){ return teachingHour*500000 + 1000000;} //overidding

    public String GetKind(){
        return "Part Time";
    } //overidding



}
