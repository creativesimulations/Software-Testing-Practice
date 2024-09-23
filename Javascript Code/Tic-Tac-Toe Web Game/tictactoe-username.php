<?php
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $username = $_POST["username"];
    
    try {
        require_once "dbh.inc.php";
        
        // Check if the username already exists
        $checkQuery = "SELECT * FROM ticTacToe WHERE username = ?";
        $checkStmt = $pdo->prepare($checkQuery);
        $checkStmt->execute([$username]);
        
        if ($checkStmt->rowCount() > 0) {
            // Username exists
            $response = [
                "status" => "existing",
                "message" => htmlspecialchars($username),
                "username" => htmlspecialchars($username)
            ];
        } else {
            // Username doesn't exist, insert it
          //  $insertQuery = "INSERT INTO ticTacToe (username) VALUES (?)";
          //  $insertStmt = $pdo->prepare($insertQuery);
          //  $insertStmt->execute([$username]);
            
            $response = [
                "status" => "new",
                "message" => htmlspecialchars($username),
                "username" => htmlspecialchars($username)
            ];
        }
        
        echo json_encode($response);
        
        $pdo = null;
        $checkStmt = null;
        $insertStmt = null;
        
    } catch (PDOException $e) {
        $response = [
            "status" => "error",
            "message" => "An error occurred: " . $e->getMessage()
        ];
        echo json_encode($response);
    }
} else {
    $response = [
        "status" => "error",
        "message" => "Invalid request method"
    ];
    echo json_encode($response);
}
?>