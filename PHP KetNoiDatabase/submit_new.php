<?php
include "connect.php";

if(isset($_POST['submit_password']) && $_POST['tendangnhap'])
{
  $tendangnhap=$_POST['tendangnhap'];
  $pass=$_POST['password'];
  $query = "update user set password='$pass' where tendangnhap='$tendangnhap'";
  $data = mysqli_query($conn, $query);
  if($data == true){
    echo "Đã đổi mật khẩu thành công. Bạn có thể trở lại app và tiếp tục sử dụng";
  }
}
?>