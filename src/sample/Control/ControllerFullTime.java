package sample.Control;
//Vu Trong Nhan , Nguyen Anh Tuan

import Model.Teacher;
import com.sun.javafx.fxml.builder.ProxyBuilder;
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

import Model.fullTimeTeacher;
import javafx.stage.Stage;
import javafx.util.Duration;
import modify.teacherModify;
import org.controlsfx.control.Notifications;
import sample.Main;
import sample.style.Style;


public class ControllerFullTime  implements Initializable{


    private Stage stage;
    private Scene scene;
    private Parent root;
    private Double x, y;
    Integer index = -1;
    teacherModify teacherModify = new teacherModify();
    ObservableList<fullTimeTeacher> fullTimeList = teacherModify.returnFullTimeList();


    URL image = Main.class.getClassLoader().getResource("asset/verified.png");
    Image imgSuccess = new Image(String.valueOf(image));
    URL imageError = Main.class.getClassLoader().getResource("asset/delete.png");
    Image imgError = new Image(String.valueOf(imageError));

    //Button
    @FXML
    private Button btnPartTimeTable, btnHome, btnFullTimetable, btnSignout, btnInfo;

    @FXML
    private TextField nameText, teachAtSchoolYearText, specialtyText, phoneText, coefficientText, salaryText;

    @FXML
    private Label  inform, nameLabel, teachAtSchoolYearLabel, specialtyLabel, phoneLabel, coefficientLabel ,IDLabel;
    @FXML
    public TableView<fullTimeTeacher> table1;

    @FXML
    private TableColumn<fullTimeTeacher, String> col_name;
    @FXML
    private TableColumn<fullTimeTeacher, Integer> col_teachAtSchoolYear;
    @FXML
    private TableColumn<fullTimeTeacher, Integer> col_id;
    @FXML
    private TableColumn<fullTimeTeacher, String> col_specialty;
    @FXML
    private TableColumn<fullTimeTeacher, String> col_phone;
    @FXML
    private TableColumn<fullTimeTeacher, Double> col_salary;
    @FXML
    private TableColumn<fullTimeTeacher, Double> col_coefficient;
    @FXML
    private ChoiceBox IDChoiceBox;

    static ObservableList<Integer> listID= FXCollections.observableArrayList(0);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDChoiceBox.setValue(0);
    }

    public void updateChoiceBox(){
        IDChoiceBox.setItems(listID);
    }
    @FXML
    void getSelected(MouseEvent event) {
        index = table1.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        nameText.setText(col_name.getCellData(index));
        teachAtSchoolYearText.setText(col_teachAtSchoolYear.getCellData(index).toString());
        specialtyText.setText(col_specialty.getCellData(index));
        phoneText.setText(col_phone.getCellData(index));
        coefficientText.setText(col_coefficient.getCellData(index).toString());
        salaryText.setText(col_salary.getCellData(index).toString());

        IDChoiceBox.setValue(Integer.parseInt(col_id.getCellData(index).toString()));

    }

    public void UpdateTable() {

        col_name.setCellValueFactory(new PropertyValueFactory<fullTimeTeacher, String>("name"));
        col_teachAtSchoolYear.setCellValueFactory(new PropertyValueFactory<fullTimeTeacher, Integer>("teachAtSchoolYear"));
        col_id.setCellValueFactory(new PropertyValueFactory<fullTimeTeacher, Integer>("idFullTime"));
        col_specialty.setCellValueFactory(new PropertyValueFactory<fullTimeTeacher, String>("specialty"));
        col_phone.setCellValueFactory(new PropertyValueFactory<fullTimeTeacher, String>("phoneNumber"));
        col_coefficient.setCellValueFactory(new PropertyValueFactory<fullTimeTeacher, Double>("coefficient"));
        col_salary.setCellValueFactory(new PropertyValueFactory<fullTimeTeacher, Double>("salary"));
        table1.setItems(fullTimeList);
    }


    public void addUser(javafx.event.ActionEvent actionEvent) throws IOException {
        String name = nameText.getText();
        String schoolYearTxt = teachAtSchoolYearText.getText();
        String specialty = specialtyText.getText();
        String phone = phoneText.getText();
        String coefficentTxt = coefficientText.getText();


        if (name == "" && schoolYearTxt == "" && specialty == "" && phone == "" && coefficentTxt == "") {
            Style.setDanger(nameText, nameLabel, inform);
            Style.setDanger(teachAtSchoolYearText, teachAtSchoolYearLabel, inform);
            Style.setDanger(specialtyText, specialtyLabel, inform);
            Style.setDanger(phoneText, phoneLabel, inform);
            Style.setDanger(coefficientText, coefficientLabel, inform);

            inform.setText("Không được để trống dòng");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        } else if (name == "") {
            Style.setDanger(nameText, nameLabel, inform);
            inform.setText("Không được để trống dòng tên");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        } else if (schoolYearTxt == "") {
            Style.setDanger(teachAtSchoolYearText, teachAtSchoolYearLabel, inform);
            inform.setText("Không được để trống dòng năm nhập trường");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        } else if (specialty == "") {
            Style.setDanger(specialtyText, specialtyLabel, inform);
            inform.setText("Không được để trống dòng chuyên môn");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        } else if (phone == "") {
            Style.setDanger(phoneText, phoneLabel, inform);
            inform.setText("Không được để trống dòng số điện thoại");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        } else if (coefficentTxt == "") {
            Style.setDanger(coefficientText, coefficientLabel, inform);
            inform.setText("Không được để trống dòng tỉ lệ nghạch");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        }


        else {
            Double coefficient = Double.parseDouble(coefficientText.getText());
            int teachAtSchoolYear = Integer.parseInt(teachAtSchoolYearText.getText());
            fullTimeTeacher teacher = new fullTimeTeacher(name,teachAtSchoolYear,specialty,phone,coefficient);

            if(teacherModify.addTeacher(teacher) == true){
                double luong = teacher.getLuong();

                Style.success(inform, "Insert database thành công!");
                salaryText.setText(Double.toString(luong));

                Notifications notifications = Notifications.create()
                        .title("Thành công")
                        .text("Bạn đã thêm data thành công")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_CENTER)
                        .graphic(new ImageView(imgSuccess));
                notifications.darkStyle();
                notifications.show();

                listID.add(teacher.getIdFullTime());
                updateChoiceBox();

                UpdateTable();
                clearText();
            }
            else{
                teacher.setCount(teacher.getIdFullTime()-1);
                teacher.setCountTeacher(teacher.getId()-1);
                Notifications notifications = Notifications.create()
                        .title("Lỗi")
                        .text("Thông tin của giáo viên đã được nhập trong bảng")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_CENTER)
                        .graphic(new ImageView(imgError));
                notifications.darkStyle();
                notifications.show();
                clearText();
            }
        }
    }

    public void editUser(ActionEvent actionEvent) throws IOException {
        String name = nameText.getText();
        String schoolYearTxt = teachAtSchoolYearText.getText();
        String specialty = specialtyText.getText();
        String phone = phoneText.getText();
        String coefficentTxt = coefficientText.getText();
        int idFull = (int) IDChoiceBox.getValue();

        if (name == "" && schoolYearTxt == "" && specialty == "" && phone == "" && coefficentTxt == "" && IDChoiceBox.getValue() == "") {
            Style.setDanger(nameText, nameLabel, inform);
            Style.setDanger(teachAtSchoolYearText, teachAtSchoolYearLabel, inform);
            Style.setDanger(specialtyText, specialtyLabel, inform);
            Style.setDanger(phoneText, phoneLabel, inform);
            Style.setDanger(coefficientText, coefficientLabel, inform);

            inform.setText("Không được để trống dòng");
            inform.setVisible(true);
            inform.setStyle("-fx-text-fill:red");
        }
        else if(IDChoiceBox.getValue().equals(0)){

            Style.setInform(inform,"Bảng chưa có dữ liệu để update");
            Notifications notifications = Notifications.create()
                    .title("Không thành công")
                    .text("Bảng chưa có dữ liệu để update")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_CENTER)
                    .graphic(new ImageView(imgError));
            notifications.darkStyle();
            notifications.show();
        }

        else{
            try {
                Double coefficient = Double.parseDouble(coefficientText.getText());
                int teachAtSchoolYear = Integer.parseInt(teachAtSchoolYearText.getText());
                fullTimeTeacher teacher = new fullTimeTeacher(coefficient);
                double luong = teacher.getLuong();
                teacherModify.updateFullTime(name,teachAtSchoolYear,specialty,phone,coefficient,luong,idFull);

                table1.refresh();


                Style.success(inform,"Update database thành công");

                Notifications notifications = Notifications.create()
                        .title("Thành công")
                        .text("Bạn đã sửa thông tin thành công")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_CENTER)
                        .graphic(new ImageView(imgSuccess));
                notifications.darkStyle();
                notifications.show();


                clearText();

            } catch(Exception e){
                System.out.println(e);
            }
        }

    }

    public void deleteUser(ActionEvent actionEvent) throws IOException {

        int idFull = (int) IDChoiceBox.getValue();

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

            fullTimeTeacher teacher = new fullTimeTeacher(idFull);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm data");
            alert.setContentText("Bạn có muốn xóa dữ liệu của người này ? (dữ liệu sẽ bị xóa mãi mãi)");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("Quay lại", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    for (int i = 0; i <listID.size() ; i++) {
                        if(listID.get(i)==idFull){
                            listID.remove(listID.get(i));
                            break;
                        }
                    }
                    teacherModify.deleteFullTime(teacher);
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

    public void clearText() {
        nameText.setText("");
        teachAtSchoolYearText.setText("");
        specialtyText.setText("");
        phoneText.setText("");
        coefficientText.setText("");
        salaryText.setText("");
        IDChoiceBox.setValue(0);
    }


    @FXML
    private void Close(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
    }


}

