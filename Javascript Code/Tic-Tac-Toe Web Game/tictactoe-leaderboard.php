<?php
// Connect to MySQL
$conn = new mysqli("localhost", "creaypgc_creativeautomatedsimulations", "Dustyahu2024!", "creaypgc_Games");
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
// SQL query
$strSql = "SELECT * FROM ticTacToe ORDER BY total_win_time ASC LIMIT 10";

// Execute query
$result = $conn->query($strSql);

// Prepare data for JSON
$data = array();
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $data[] = $row;
    }
} else {
    $data[] = array('message' => 'No results found');
}



// Close connection
$conn->close();

// Output JSON data
header('Content-Type: application/json');
echo json_encode($data);
// Log that the script was accessed
error_log("tictactoe-save-result.php was accessed at " . date('Y-m-d H:i:s'), 3, "access.log");
?>