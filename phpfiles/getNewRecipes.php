<?php
	$conn = mysqli_connect('mysql.hostinger.co.il','u628268477_root','123456', 'u628268477_myapp');
	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error); //could not connect to the DB and sending error message
		echo "Error";
	} 
	$sql = //create player table where name is the key
	"SELECT * FROM tableUsersSeeRecipes where users_see like 'a'";
	$result = mysqli_query($conn, $sql); //run select query
	$numResults = mysqli_num_rows($result);
	if ($numResults > 0) {
		echo "1";
		$sql1 = 
		"UPDATE tableUsersSeeRecipes SET users_see='b' WHERE users_see='a'";
		$result = mysqli_query($conn, $sql1); //run select query
	
		if(!$result) // error in query
			echo 'Invalid query: ' . mysqli_error(); //sending error message

		} else {
			echo "2";
		}

	$conn->close();
?>