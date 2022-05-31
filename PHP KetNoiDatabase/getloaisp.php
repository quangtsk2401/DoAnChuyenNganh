<?php
include "connect.php";
$query = "SELECT * FROM sanpham";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code ...
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