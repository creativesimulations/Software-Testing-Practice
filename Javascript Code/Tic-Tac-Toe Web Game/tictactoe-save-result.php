<?php
// Connect to MySQL
$conn = new mysqli("localhost", "creaypgc_creativeautomatedsimulations", "Dustyahu2024!", "creaypgc_Games");

// Check connection
if ($conn->connect_error) {
    die(json_encode(array("error" => "Connection failed: " . $conn->connect_error)));
}

// Include the insertGameResult function here
function insertGameResult($username, $wins, $losses, $ties, $totalWinTime, $totalGames) {
    global $conn;
    
    $sql = "INSERT INTO ticTacToe (username, wins, losses, ties, total_win_time, total_games)
           VALUES (?, ?, ?, ?, ?, ?)";
    
    if ($stmt = $conn->prepare($sql)) {
        $stmt->bind_param("siiiid", $username, $wins, $losses, $ties, $totalWinTime, $totalGames);
        
        if ($stmt->execute()) {
            return true; // Game result was successfully inserted
        } else {
            error_log("Failed to execute INSERT statement: " . $stmt->error);
            return false; // Failed to insert game result
        }
    }
    error_log("Failed to prepare statement: " . $conn->error);
    return false; // Failed to prepare statement
}

// Ensure all required POST parameters are present
$requiredParams = ['username', 'wins', 'losses', 'ties', 'totalWinTime', 'totalGames'];
foreach ($requiredParams as $param) {
    if (!isset($_POST[$param])) {
        echo json_encode(array("error" => "Missing required parameter: $param"));
        exit;
    }
}

// Save game result
$result = insertGameResult(
    $_POST['username'],
    intval($_POST['wins']),
    intval($_POST['losses']),
    intval($_POST['ties']),
    floatval($_POST['totalWinTime']),
    intval($_POST['totalGames'])
);

if ($result) {
    echo json_encode(array("success" => true));
} else {
    echo json_encode(array("error" => "Failed to save game result"));
}

$conn->close();
?>