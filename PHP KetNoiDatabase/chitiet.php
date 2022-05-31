<?php
include "connect.php";
$page = $_POST['page'];
$total = 5;  //Lấy 5sp trên 1 trang
$pos = ($page-1)*$total;
$loai = $_POST['loai'];
$query = 'SELECT * FROM `sanphamoi` WHERE `loai` = '.$loai.' LIMIT '.$pos.','.$total.'';
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
}
if (!empty($result))
{
	$arr = ['success'=> true,
			'message'=> "Thanhcong",
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