package sample.Control;

import Model.Teacher;
import Model.fullTimeTeacher;
import Model.partTimeTeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;


import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modify.teacherModify;
import sample.style.Style;


public class ControllerDashBoard implements Initializable{

    ObservableList<Teacher> List = teacherModify.returnTeacher();

    private Stage stage;
    private Scene scene;
    private Parent root;
    private double x, y;
    Integer index = -1;
    private static Teacher teacher;


    private String[] ascii = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "V", "X","Y","Z","q","a","z","w","s","x","e","d","c","r","f","v","t","g","b","y","h","n","u","j","m","i","k","o","l","p"};
    private String[] viet = { "a","à","ả","ã","á","ạ","ă","ằ","ẳ","ẵ","ắ","ặ","â","ầ","ẩ","ẫ","ấ","ậ","b","c","d","đ","e","è","ẻ","ẽ","é","ẹ","ê","ề","ể","ễ","ế","ệ","f","g","h","i","ì","ỉ","ĩ","í","ị","j","k","l","m","n","o","ò","ỏ","õ","ó","ọ","ô","ồ","ổ","ỗ","ố","ộ","ơ","ờ","ở","ỡ","ớ","ợ","p","q","r","s","t","u","ù","ủ","ũ","ú","ụ","ư","ừ","ử","ữ","ứ","ự" };
    public  String toASCII(String tv) {
        StringBuffer buf = new StringBuffer();
        for(int i = 0, l = tv.length(); i < l; ++i) {
            int j;
            for (j = 0; j < viet.length; ++j) if (viet[j].equals(tv.charAt(i))) break;
            buf.append((j < viet.length)? ascii[j]:tv.charAt(i));
        }
        return buf.toString();
    }


    //Button
    @FXML
    private Button btnPartTimeTable,btnHome,btnFullTimetable,btnSignout,btnInfo;

    //Pane

    @FXML
    private Pane pnlFullTime,pnlTable,pnlPartTime,pnlInfo;



    @FXML
    private TextField  searchText;

    @FXML
    private Label Name, inform ,totalNum,totalNumFullTime,totalNumPartTime;

    @FXML
    private TableView<Teacher> table;
    @FXML
    private TableColumn<Teacher, String> col_name;
    @FXML
    private TableColumn<Teacher, Integer> col_teachAtSchoolYear;
    @FXML
    private TableColumn<Teacher, Integer> col_id;
    @FXML
    private TableColumn<Teacher, String> col_specialty;
    @FXML
    private TableColumn<Teacher, String> col_phone;
    @FXML
    private TableColumn<Teacher, Double> col_salary;
    @FXML
    private ChoiceBox<String> choiceBox;

    ObservableList<String> options = FXCollections.observableArrayList("Tên giáo viên","Đơn vị chuyên môn","Số điện thoại","Tìm kiếm lương lớn hơn số lương nhập vào");

    ObservableList<Teacher> data;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.refresh();
        UpdateTable();
        choiceBox.setItems(options);
        choiceBox.setValue("Tên giáo viên");
        btnHome.setStyle(" -fx-background-color : #10165F;");

    }

    public void textChange(){// choice box event onKeyChange moi lan nhap ki tu se tim theo case ma chung ta chon
        String text = searchText.getText();
        if(text.equals("")){
            table.setItems(List);
        }
        ObservableList<Teacher> teacherList = teacherModify.returnTeacher();

        switch (choiceBox.getValue())
        {
            case "Tên giáo viên":
                data = teacherModify.returnNameList(toASCII(text).trim());
                table.setItems(data);

                break;
            case "Đơn vị chuyên môn":
                 data = teacherModify.returnSpecialtyList(toASCII(text).trim());
                 table.setItems(data);
                 break;
            case "Số điện thoại":
                ObservableList<Teacher> userList = FXCollections.observableArrayList();
                Teacher teacher = new Teacher();
                for (int i = 0; i <teacherList.size() ; i++) {
                    if(text.trim().equalsIgnoreCase(teacherList.get(i).getPhoneNumber())){
                        teacher = teacherList.get(i);
                        userList.add(teacher);
                    }
                }
                table.setItems(userList); // Nếu không sử dụng database
                break;
            case "Tìm kiếm lương lớn hơn số lương nhập vào":
                data = teacherModify.returnSalaryList(Double.parseDouble(text.trim()));
                table.setItems(data);
                break;
            default:
                table.setItems(List);
        }


    }


    public  void UpdateTable() {

        col_name.setCellValueFactory(new PropertyValueFactory<Teacher,String>("name"));
        col_teachAtSchoolYear.setCellValueFactory(new PropertyValueFactory<Teacher,Integer>("teachAtSchoolYear"));
        col_id.setCellValueFactory(new PropertyValueFactory<Teacher,Integer>("id"));
        col_specialty.setCellValueFactory(new PropertyValueFactory<Teacher,String>("specialty"));
        col_phone.setCellValueFactory(new PropertyValueFactory<Teacher,String>("phoneNumber"));
        col_salary.setCellValueFactory(new PropertyValueFactory<Teacher,Double>("salary"));
        table.setItems(List);
    }



    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnPartTimeTable) {

            pnlPartTime.setVisible(true);
            pnlTable.setVisible(false);

            pnlFullTime.setVisible(false);
            btnPartTimeTable.setStyle(" -fx-background-color : #10165F;");
            btnHome.setStyle(null);
            btnFullTimetable.setStyle(null);
        }
        if (actionEvent.getSource() == btnHome) {
            searchText.setText("");
            btnHome.setStyle("-fx-background-color : #10165F;");
            btnFullTimetable.setStyle(null);
            btnPartTimeTable.setStyle(null);
            table.refresh();
            int sumOfFull =0;
            int sumOfPart =0;
            for (int i = 0; i < List.size(); i++) {
                if(List.get(i).GetKind().equals("Full Time")){
                    ++sumOfFull;
                }
            }
            for (int i = 0; i < List.size(); i++) {
                if(List.get(i).GetKind().equals("Part Time")){
                    ++sumOfPart;
                }
            }
            totalNumFullTime.setText(String.valueOf(sumOfFull));
            totalNumPartTime.setText(String.valueOf(sumOfPart));

            pnlFullTime.setVisible(false);
            pnlTable.setVisible(true);
            pnlPartTime.setVisible(false);

            UpdateTable();
            int total = table.getItems().size();
            totalNum.setText(String.valueOf(total));

        }
        if (actionEvent.getSource() == btnFullTimetable) {

            pnlFullTime.setVisible(true);
            pnlTable.setVisible(false);
            pnlPartTime.setVisible(false);
            btnFullTimetable.setStyle("-fx-background-color : #10165F;");
            btnHome.setStyle(null);
            btnPartTimeTable.setStyle(null);
        }




    }
    @FXML
    private void Close(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("Bạn có muốn thoát chương trình ?");
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);

        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.hide();
            } else {
                alert.close();
            }
        });

    }

}
