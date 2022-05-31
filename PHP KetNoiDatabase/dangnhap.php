<?php
include "connect.php";
$tendangnhap = $_POST['tendangnhap'];
$password = $_POST['password'];
$query = 'SELECT * FROM `user` WHERE `tendangnhap` = "'.$tendangnhap.'" AND `password` = "'.$password.'"';
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
}
if (!empty($result))
{
	$arr = ['success'=> true,
			'message'=> "Thanh cong",
			'result'=> $result];
}
else
{
	$arr = ['success'=> false,
			'message'=> "That bai",
			'result'=> $result];
}
print(json_encode($arr));
?>