<?php
include "connect.php";
if($_GET['key'] && $_GET['reset'])
{
  $tendangnhap=$_GET['key'];
  $password=$_GET['reset'];
$query = "select tendangnhap,password from user where tendangnhap='$tendangnhap' and password='$password'";
  $data = mysqli_query($conn, $query);
  if($data == true)
  {
    ?>
    <form method="post" action="submit_new.php">
    <input type="hidden" name="tendangnhap" value="<?php echo $tendangnhap;?>">
    <p>Nhập vào mật khẩu mới</p>
    <input type="password" name='password'>
    <input type="submit" name="submit_password">
    </form>
    <?php
  }
}
?>