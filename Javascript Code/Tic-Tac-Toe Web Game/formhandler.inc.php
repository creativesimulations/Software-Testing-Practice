<?php

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST["username"];
    
     echo htmlspecialchars($username);
    
    try {
        require_once "dbh.inc.php";
        
        // Check if username already exists
        $checkQuery = "SELECT COUNT(*) FROM ticTacToe WHERE username = :username";
        $stmt = $pdo->prepare($checkQuery);
        $stmt->bindParam(':username', $username);
        $stmt->execute();
        $usernameExists = $stmt->fetchColumn();
     
        if ($usernameExists > 0) {
            echo "Username already exists. Please choose a different name.";
        } else {
            // Username does not exist, proceed to insert
            $postQuery = "INSERT INTO ticTacToe (username) VALUES (:username)";
            $stmt = $pdo->prepare($postQuery);
            $stmt->bindParam(':username', $username);
            $stmt->execute();
            
            if ($stmt->rowCount() > 0) {
                echo "Username successfully added.";
            } else {
                throw new Exception("Failed to add username");
            }
        }
        
    } catch(PDOException $e) {
        echo "Database error: " . $e->getMessage();
    } catch(Exception $e) {
        echo "An error occurred: " . $e->getMessage();
    }
} else {
    echo "Please submit the form using POST method.";
}