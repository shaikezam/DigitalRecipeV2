<?php
$conn = mysqli_connect('mysql.hostinger.co.il','u628268477_root','123456', 'u628268477_myapp');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error); //could not connect to the DB and sending error message
	echo "Error";
} 
$sql = //create player table where name is the key
"CREATE table IF NOT EXISTS tableOfUsers
 (user_name varchar(50) primary key,
 password varchar(50) not null)";

if ($conn->query($sql) === TRUE) {
    echo "Table tableOfUsers created successfully\n"; //table created
} else {
    echo "Error creating table: " . $conn->error; //error -> table wont created
}

$sql = //create player table where name is the key
"CREATE table IF NOT EXISTS tableOfRecipes
 (ID int NOT NULL AUTO_INCREMENT,
 recipe_name varchar(50) not null,
 ingredients varchar(5000) not null,
 instructions varchar(5000) not null,
 recipe_rate int(50) not null,
 user_name varchar(50) not null,
 amount_of_rate int(50) not null,
PRIMARY KEY (ID),
CONSTRAINT FOREIGN KEY (user_name) REFERENCES tableOfUsers(user_name))";

if ($conn->query($sql) === TRUE) {
    echo "Table tableOfRecipes created successfully\n"; //table created
} else {
    echo "Error creating table: " . $conn->error; //error -> table wont created
}

$sql = //create player table where name is the key
"CREATE table IF NOT EXISTS tableUsersSeeRecipes
 ( users_see varchar(500000) not null)";

if ($conn->query($sql) === TRUE) {
    echo "Table tableUsersSeeRecipes created successfully\n"; //table created
} else {
    echo "Error creating table: " . $conn->error; //error -> table wont created
}

$conn->close();
?>	