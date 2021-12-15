-Các thư viện cần thiết như javafx , mysql-connector, controlsfx , jfoenix  đã có sẵn trong chương trình
-Nếu chương trình chưa set up sdk xin hãy download java sdk 17
-Chương trình được viết trên intelij nên để có trải nghiệm tốt nhất bạn nên sử dụng intelj để mở trương trình

-Chỉnh lại Edit Configurations để intelij biết chạy hàm main ở đâu :
   +Bước 1 : mở lib --> javafx-sdk-17.0.1 --> lib , copy địa chỉ của lib
   +Bước 2 : mở Run --> Edit Configurations
   +Bước 3 : Modify Option --> Add VM Options
   +Bước 4 : copy đoạn lênh này vào : --module-path "paste địa chỉ của lib javafx-sdk-17.0.1 mà bạn vừa copy vào ngoặc này" --add-modules javafx.controls,javafx.fxml

-Kết nối database mysql :
   +Bước 1 : Cài đặt xamp myphpadmin
   +Bước 2 : tạo một csdl mới với tên oop_project;
   +Bước 3 : Ấn nhập và chọn file oop_project.sql có trong tệp Instruction