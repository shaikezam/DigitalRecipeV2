<?php
$conn = mysqli_connect('mysql.hostinger.co.il','u628268477_root','123456', 'u628268477_myapp');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error); //could not connect to the DB and sending error message
	echo "Error";
} 

$sql1 = //create player table where name is the key
'DROP TABLE tableOfUsers'; 
$sql2 = //create player table where name is the key
'DROP TABLE tableOfRecipes';
$sql3 = //create player table where name is the key
'DROP TABLE tableUsersSeeRecipes';

if ($conn->query($sql1) === TRUE) {
    echo "table tableOfUsers deleted"; //table created
} else {
    echo "Error delete table: " . $conn->error; //error -> table wont created
}
if ($conn->query($sql2) === TRUE) {
    echo "table tableOfRecipes deleted"; //table created
} else {
    echo "Error delete table: " . $conn->error; //error -> table wont created
}
if ($conn->query($sql3) === TRUE) {
    echo "table tableUsersSeeRecipesdeleted"; //table created
} else {
    echo "Error delete table: " . $conn->error; //error -> table wont created
}

$conn->close();
?>