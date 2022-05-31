<?php
include "connect.php";
$tendangnhap = $_POST['tendangnhap'];
$password = $_POST['password'];
$username = $_POST['username'];
$sdt = $_POST['sdt'];

//Xet ten dang nhap
$query = 'SELECT * FROM `user` WHERE `tendangnhap` = "'.$tendangnhap.'" ';
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);
if ($numrow > 0)
{
	$arr = ['success'=> false,
			'message'=> "Ten dang nhap da duoc su dung"];
}
else
{
	$query = 'INSERT INTO `user`(`tendangnhap`, `password`, `username`, `sdt`) VALUES ("'.$tendangnhap.'","'.$password.'","'.$username.'","'.$sdt.'")';
	$data = mysqli_query($conn, $query);

	if ($data == true)
	{
		$arr = ['success'=> true,
			'message'=> "Thanhcong"];
	}
	else
	{
		$arr = ['success'=> false,
			'message'=> "That bai"];
	}
}

print(json_encode($arr));
?>