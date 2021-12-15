import javax.management.MBeanRegistrationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String []name= {"Vu Trong Nhan","Nguyen Huy Hoang","Nguyen anh Tuan"};
        String expression = ".*\\b"+input+".*";
        for (int i = 0; i < name.length; i++) {
            if(name[i].toLowerCase().matches(expression)){
                System.out.println("Match " + name[i]);
            }
        }

    }

//    public static void main(String[] args) {
//
//        String expression = ".*\\bN.*";
//        System.out.println("Vu trong Nhan".matches(expression));
//    }
}
