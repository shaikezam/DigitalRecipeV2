<?php
$conn = mysqli_connect('mysql.hostinger.co.il','u628268477_root','123456', 'u628268477_myapp');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error); //could not connect to the DB and sending error message
	echo "Error";
} 
$recipeName = $_REQUEST["recipeName"]; //get player 1 name
$recipeIngredients= $_REQUEST["recipeIngredients"]; //get player 2 name
$recipeInstructions = $_REQUEST["recipeInstructions"]; //get player 1 name
$recipeUserName= $_REQUEST["recipeUserName"]; //get player 2 name
$recipeRate = $_REQUEST["recipeRate"]; //get player 1 name
$recipeAmountOfRate= $_REQUEST["recipeAmountOfRate"]; //get player 2 name

$sql = //create player table where name is the key
'INSERT INTO tableOfRecipes VALUES (DEFAULT,"'.$recipeName.'", "'.$recipeIngredients.'", "'.$recipeInstructions.'", "'.$recipeRate.'", "'.$recipeUserName.'", "'.$recipeAmountOfRate.'")'; //insert player 1 

if ($conn->query($sql) === TRUE) {
    echo "Recipe Created"; //table created
} else {
    echo "Error create recipe: " . $conn->error;// . $conn->error; //error -> table wont created
}

$sql = //create player table where name is the key
'INSERT INTO tableUsersSeeRecipes VALUES ("a")'; //insert player 1 

if ($conn->query($sql) === TRUE) {
    echo "Recipe Created"; //table created
} else {
    echo "Error create recipe: " . $conn->error;// . $conn->error; //error -> table wont created
}

$conn->close();
?>