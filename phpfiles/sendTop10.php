<?php	
	$conn = mysqli_connect('mysql.hostinger.co.il','u628268477_root','123456', 'u628268477_myapp');
	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error); //could not connect to the DB and sending error message
		echo "Error";
	} 

	$userName = $_REQUEST["userName"]; //get player 1 name

	$sql1 =
	"SELECT * FROM tableOfRecipes ORDER BY recipe_rate DESC LIMIT 10"; //query to select the 10 players by winnings

	$result = mysqli_query($conn, $sql1); //run select query
		
		if(!$result) // error in query
			echo 'Invalid query: ' . mysqli_error(); //sending error message
		
	$numResults = mysqli_num_rows($result);
	if($numResults == 0) {
		echo 'User isnt found';
	} else {
		while($row = mysqli_fetch_array($result)) { //send back result
			echo $row['recipe_name'] . '*' . $row['recipe_rate'] . '*' . $row['user_name'] . '^'; 
		}
	}

	$conn->close();
?>