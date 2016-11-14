<?php
$conn = mysqli_connect('mysql.hostinger.co.il','u628268477_root','123456', 'u628268477_myapp');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error); //could not connect to the DB and sending error message
	echo "Error";
} 
$userName = $_REQUEST["userName"]; //get player 1 name
$userPassword= $_REQUEST["userPassword"]; //get player 2 name

$sql = //create player table where name is the key
'INSERT INTO tableOfUsers VALUES ("'.$userName.'", "'.$userPassword.'")'; //insert player 1 

if ($conn->query($sql) === TRUE) {
    echo "User register"; //table created
} else {
    echo "Error register user: " . $conn->error; //error -> table wont created
}

$conn->close();
?>