<?php
include "connect.php";
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';

$tendangnhap = $_POST['tendangnhap'];
$query = 'SELECT * FROM `user` WHERE `tendangnhap` = "'.$tendangnhap.'" ';
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
  $result[] = ($row);
}
if (empty($result)) {
  $arr = ['success'=> false,
      'message'=> "Ten dang nhap khong dung",
      'result'=> $result];
}
else {
  $tendangnhap=($result[0]["tendangnhap"]);
  $password=($result[0]["password"]);
   $link="<a href='http://192.168.80.1/appbanhang/reset_pass.php?key=".$tendangnhap."&reset=".$password."'>Click To Reset password</a>";
    $mail = new PHPMailer();
    $mail->CharSet =  "utf-8";
    $mail->IsSMTP();
    // enable SMTP authentication
    $mail->SMTPAuth = true;                  
    // GMAIL username
    $mail->Username = "kimquangtv123@gmail.com";
    // GMAIL password
    $mail->Password = "kimquang2401"; // pass của email
    $mail->SMTPSecure = "ssl";  
    // sets GMAIL as the SMTP server
    $mail->Host = "smtp.gmail.com";
    // set the SMTP port for the GMAIL server
    $mail->Port = "465";
    $mail->From= "kimquangtv123@gmail.com"; // email nhận resetpass
    $mail->FromName='SenNy Memory';
    $mail->AddAddress($tendangnhap, 'reciever_name');
    $mail->Subject  =  'Reset Password';
    $mail->IsHTML(true);
    $mail->Body    = $link;
    if($mail->Send())
    {
      $arr = ['success'=> true,
      'message'=> "Vui lòng kiểm tra email",
      'result'=> $result];
    }
    else
    {
      $arr = ['success'=> false,
      'message'=> "Gửi email thất bại",
      'result'=> $result];
    }
    }
    print_r(json_encode($arr));
?>