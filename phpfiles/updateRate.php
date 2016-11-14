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

$sql1 = 
"UPDATE tableOfRecipes
SET recipe_rate='$recipeRate', amount_of_rate='$recipeAmountOfRate'
WHERE recipe_name='$recipeName' and ingredients='$recipeIngredients'";

$result = mysqli_query($conn, $sql1); //run select query
	
if ($conn->query($sql1) === TRUE) {
    echo "Recipe Created"; //table created
} else {
    echo "Error create recipe: " . $conn->error; //error -> table wont created
}

$conn->close();
?>			