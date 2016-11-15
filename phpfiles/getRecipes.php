<?php
$conn = mysqli_connect('mysql.hostinger.co.il','u628268477_root','123456', 'u628268477_myapp');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error); //could not connect to the DB and sending error message
	echo "Error";
} 

$userName = $_REQUEST["userName"]; //get player 1 name

$sql1 = //create player table where name is the key
"SELECT * FROM tableOfRecipes where user_name = '$userName'";

$result = mysqli_query($conn, $sql1); //run select query
	
	if(!$result) // error in query
		echo 'Invalid query: ' . mysqli_error(); //sending error message
	
$numResults = mysqli_num_rows($result);
if($numResults == 0) {
	echo 'User isnt found';
} else {
	while($row = mysqli_fetch_array($result)) { //send back result
		echo $row['recipe_name'] . '*' . $row['ingredients'] . '*' . $row['instructions'] . '*' . $row['recipe_rate'] . '*' . $row['user_name'] . '*' . $row['amount_of_rate'] . '^'; 
	}
}

$conn->close();
?>			