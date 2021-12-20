package sample.Control;


import Model.Teacher;
import Model.fullTimeTeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import Model.partTimeTeacher;

import javafx.stage.Stage;
import javafx.util.Duration;
import modify.teacherModify;
import org.controlsfx.control.Notifications;
import sample.Main;
import sample.style.Style;


import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sample.style.Style;


public class ControllerPartTime implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Double x,y;
    Integer index = -1;

    modify.teacherModify teacherModify = new teacherModify();
    ObservableList<partTimeTeacher> partTimeList = teacherModify.returnPartTimeList();



    URL image = Main.class.getClassLoader().getResource("asset/verified.png");
    Image imgSuccess = new Image(String.valueOf(image));
    URL imageError = Main.class.getClassLoader().getResource("asset/delete.png");
    Image imgError = new Image(String.valueOf(imageError));

    //Button
    @FXML
    private Button buttonUpdate,buttonAdd,buttonDelete;


    @FXML
    private TextField  nameText,teachAtSchoolYearText,specialtyText,phoneText,hourText,salaryText;

    @FXML
    private Label Name, inform,nameLabel,teachAtSchoolYearLabel,specialtyLabel,phoneLabel,hourLabel;
    @FXML
    private TableView<partTimeTeacher> table1;

    @FXML
    private TableColumn<partTimeTeacher, String> col_name;
    @FXML
    private TableColumn<partTimeTeacher, Integer> col_teachAtSchoolYear;
    @FXML
    private TableColumn<partTimeTeacher, Integer> col_id;
    @FXML
    private TableColumn<partTimeTeacher, String> col_specialty;
    @FXML
    private TableColumn<partTimeTeacher, String> col_phone;
    @FXML
    private TableColumn<partTimeTeacher, Double> col_salary;
    @FXML
    private TableColumn<partTimeTeacher, Double> col_hour;
    @FXML
    private ChoiceBox IDChoiceBox;

    static ObservableList<Integer> listID= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDChoiceBox.setValue(0);
    }


    public void updateChoiceBox(){
        IDChoiceBox.setItems(listID);
    }


    @FXML
    void getSelected(MouseEvent event){
        index = table1.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }

        nameText.setText(col_name.getCellData(index));
        teachAtSchoolYearText.setText(col_teachAtSchoolYear.getCellData(index).toString());
        specialtyText.setText(col_specialty.getCellData(index));
        phoneText.setText(col_phone.getCellData(index));
        hourText.setText(col_hour.getCellData(index).toString());
        salaryText.setText(col_salary.getCellData(index).toString());
        IDChoiceBox.setValue(Integer.parseInt(col_id.getCellData(index).toString()));



    }

    public void UpdateTable(){

        col_name.setCellValueFactory(new PropertyValueFactory<partTimeTeacher,String>("name"));
        col_teachAtSchoolYear.setCellValueFactory(new PropertyValueFactory<partTimeTeacher,Integer>("teachAtSchoolYear"));
        col_id.setCellValueFactory(new PropertyValueFactory<partTimeTeacher,Integer>("idPartTime"));
        col_specialty.setCellValueFactory(new PropertyValueFactory<partTimeTeacher,String>("specialty"));
        col_phone.setCellValueFactory(new PropertyValueFactory<partTimeTeacher,String>("phoneNumber"));
        col_salary.setCellValueFactory(new PropertyValueFactory<partTimeTeacher,Double>("salary"));
        col_hour.setCellValueFactory(new PropertyValueFactory<partTimeTeacher,Double>("teachingHour"));

        table1.setItems(partTimeList);
    }


    public void addUser(javafx.event.ActionEvent actionEvent) throws IOException{
        String name = nameText.getText();
        String schoolYearTxt = teachAtSchoolYearText.getText();
        String specialty = specialtyText.getText();
        String phone = phoneText.getText();
        String hourTxt = hourText.getText();

        if(name == "" && schoolYearTxt =="" && specialty=="" && phone =="" && hourTxt==""){
            Style.setDanger(nameText,nameLabel,inform);
            Style.setDanger(teachAtSchoolYearText,teachAtSchoolYearLabel,inform);
            Style.setDanger(specialtyText,specialtyLabel,inform);
            Style.setDanger(phoneText,phoneLabel,inform);
            Style.setDanger(hourText,hourLabel,inform);

            inform.setText("Không được để trống dòng");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        }

        else if(name == ""){
            Style.setDanger(nameText,nameLabel,inform);
            inform.setText("Không được để trống dòng tên");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        }
        else if(schoolYearTxt == ""){
            Style.setDanger(teachAtSchoolYearText,teachAtSchoolYearLabel,inform);
            inform.setText("Không được để trống dòng năm nhập trường");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        }
        else if(specialty == ""){
            Style.setDanger(specialtyText,specialtyLabel,inform);
            inform.setText("Không được để trống dòng chuyên môn");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        }
        else if(phone == ""){
            Style.setDanger(phoneText,phoneLabel,inform);
            inform.setText("Không được để trống dòng số điện thoại");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        }
        else if(hourTxt == ""){
            Style.setDanger(hourText,hourLabel,inform);
            inform.setText("Không được để trống dòng tỉ lệ nghạch");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        }

        else {

                Double hour = Double.parseDouble(hourText.getText());
                int teachAtSchoolYear = Integer.parseInt(teachAtSchoolYearText.getText());
                partTimeTeacher teacher = new partTimeTeacher(name, teachAtSchoolYear, specialty, phone, hour);

                    Style.success(inform, "Insert database thanh cong!");
                    double luong = teacher.getLuong();
                    teacherModify.addTeacher(teacher);
                    salaryText.setText(Double.toString(luong));

                    Notifications notifications = Notifications.create()
                            .title("Thành công")
                            .text("Bạn đã thêm data thành công")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.TOP_CENTER)
                            .graphic(new ImageView(imgSuccess));
                    notifications.darkStyle();
                    notifications.show();
                    listID.add(teacher.getIdPartTime());
                    updateChoiceBox();


                    UpdateTable();
                    clearText();
            }
    }
    public void editUser(ActionEvent actionEvent) throws IOException {
        String name = nameText.getText();
        String schoolYearTxt = teachAtSchoolYearText.getText();
        String specialty = specialtyText.getText();
        String phone = phoneText.getText();
        String hourTxt = hourText.getText();
        int IDPartTime = (int) IDChoiceBox.getValue();
        if(name == "" && schoolYearTxt =="" && specialty=="" && phone==""&& hourTxt==""){
            Style.setDanger(nameText,nameLabel,inform);
            Style.setDanger(teachAtSchoolYearText,teachAtSchoolYearLabel,inform);
            Style.setDanger(specialtyText,specialtyLabel,inform);
            Style.setDanger(phoneText,phoneLabel,inform);
            Style.setDanger(hourText,hourLabel,inform);

            inform.setText("Không được để trống dòng");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        }
        else if(IDChoiceBox.getValue().equals(0)){

            Style.setInform(inform,"Bảng chưa có dữ liệu để update");
            Notifications notifications = Notifications.create()
                    .title("Thành công")
                    .text("Bảng chưa có dữ liệu để update")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_CENTER)
                    .graphic(new ImageView(imgError));
            notifications.darkStyle();
            notifications.show();
        }
        else{

                Double hour = Double.parseDouble(hourText.getText());
                int teachAtSchoolYear = Integer.parseInt(teachAtSchoolYearText.getText());
                partTimeTeacher teacher= new partTimeTeacher(teachAtSchoolYear);
                double luong = teacher.getLuong();
                teacherModify.updatePartTime(name,teachAtSchoolYear,specialty,phone,hour,luong,IDPartTime);
                table1.refresh();
                Style.success(inform,"Update database thanh cong");

                Notifications notifications = Notifications.create()
                        .title("Thành công")
                        .text("Bạn đã sửa thông tin thành công")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_CENTER)
                        .graphic(new ImageView(imgSuccess));
                notifications.darkStyle();
                notifications.show();
                UpdateTable();
                clearText();


        }

    }


    public void deleteUser(ActionEvent actionEvent) throws IOException {

        int idPart = (int) IDChoiceBox.getValue();


        if(IDChoiceBox.getValue().equals(0)){
            Style.setInform(inform,"Không được để trống ID");
            Notifications notifications = Notifications.create()
                    .title("Không thành công")
                    .text("Không để trống dòng ID")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_CENTER)
                    .graphic(new ImageView(imgError));
            notifications.darkStyle();
            notifications.show();
        }
        else {
            partTimeTeacher teacher = new partTimeTeacher(idPart);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm data");
            alert.setContentText("Bạn có muốn xóa dữ liệu của người này ? (dữ liệu sẽ bị xóa mãi mãi)");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);

            ButtonType cancelButton = new ButtonType("Quay lại", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    for (int i = 0; i <listID.size() ; i++) {
                        if(listID.get(i)==idPart){
                            listID.remove(listID.get(i));
                            break;
                        }
                    }
                    teacherModify.deletePartTime(teacher);
                    UpdateTable();
                    clearText();
                    updateChoiceBox();
                    Style.success(inform, "Xóa dữ liệu giáo viên thành công");
                } else {
                    alert.close();
                }
            });
        }
    }
    public void clearText(){
        nameText.setText("");
        teachAtSchoolYearText.setText("");
        specialtyText.setText("");
        phoneText.setText("");
        hourText.setText("");
        salaryText.setText("");
        IDChoiceBox.setValue(0);
    }



    @FXML
    private void Close(MouseEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.hide();
    }


}
