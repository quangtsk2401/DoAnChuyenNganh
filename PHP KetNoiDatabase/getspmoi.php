<?php
include "connect.php";
$query = "SELECT * FROM `sanphamoi` ORDER BY id DESC LIMIT 0,10";
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