let currentPlayer = 'X';
let board = ['', '', '', '', '', '', '', '', ''];
let startTime;
let timerInterval;
let gameStarted = false;
let playerName = "";
let winAudio = new Audio('assets/audios/tictactoe/win.mp3');
let loseAudio = new Audio('assets/audios/tictactoe/lose.mp3');
let tieAudio = new Audio('assets/audios/tictactoe/tie.mp3');

function validateUsername() {
    const usernameInput = $('input[name="username"]');
    if (usernameInput.val().trim() === '') {
        alert("Please enter your username.");
        return false;
    }
    playerName = usernameInput.val();
    return true;
}

function updateTurnInfo() {
    document.getElementById('turn-info').textContent = `Current Turn: ${currentPlayer}`;
}

function handleCellClick(event) {
    const clickedCell = event.target;
    const cellIndex = parseInt(clickedCell.id.split('-')[1]) - 1;

    if (typeof playerName === 'undefined' || !gameStarted) {
        alert("Please enter your name and start the game!");
        return;
    }
    if (board[cellIndex] !== '') {
        alert(`Cell ${cellIndex + 1} already occupied. Please choose another cell.`);
        return;
    }
    board[cellIndex] = currentPlayer;
    clickedCell.textContent = currentPlayer;
    checkWinner();
}

function checkWinner() {
    const winningCombinations = [[0, 1, 2], [3, 4, 5], [6, 7, 8],
                               [0, 3, 6], [1, 4, 7], [2, 5, 8],
                               [0, 4, 8], [2, 4, 6]];
    for (const combination of winningCombinations) {
        if (board[combination[0]] !== '' && 
            board[combination[0]] === board[combination[1]] && 
            board[combination[1]] === board[combination[2]]) {
                if (currentPlayer === 'X')
                {
                    win(combination);
                }
                else{
                    lose(combination);
                }
            return;
        }
    }
    if (!board.includes('')) {
        tie();
            return;
    }
    switchPlayer();
}

function saveGameResult(username, outcome, totalGames, gameTime) {
    
 //   let totalGameTime = parseInt(localStorage.getItem(username + '_total_game_time')) || 0;
 //   totalGameTime += gameTime;
    localStorage.setItem(playerName + '_total_game_time', gameTime);
    
    $.ajax({
        url: "assets/includes/tictactoe-save-result.php",
        type: "POST",
        dataType: "json",
        data: {
            username: playerName,
            wins: outcome === 1 ? 1 : 0,
            losses: outcome === 2 ? 1 : 0,
            ties: outcome === 3 ? 1 : 0,
            totalWinTime: gameTime,
            totalGames: totalGames
        },
        success: function(response) {
            
            if (response.success) {
                updateLocalStorage(username, outcome, gameTime);
            } else {
                console.error("Error saving game result:", response.error);
            }
        },
        error: function(xhr, status, error) {
            console.error("Error in AJAX call:", status, error);
            console.error("XHR responseText:", xhr.responseText);
            try {
                var errorResponse = JSON.parse(xhr.responseText);
                console.error("Parsed error response:", errorResponse);
            } catch (e) {
                console.error("Error parsing error response:", e);
            }
        }
    });
}

function updateLocalStorage(username, outcome, gameTime) {
    let totalGames = parseInt(localStorage.getItem(username + '_total_games')) || 0;
    let totalWins = parseInt(localStorage.getItem(username + '_total_wins')) || 0;
    let totalLosses = parseInt(localStorage.getItem(username + '_total_losses')) || 0;
    let totalTies = parseInt(localStorage.getItem(username + '_total_ties')) || 0;

    totalGames++;
    if (outcome === 1) totalWins++;
    else if (outcome === 2) totalLosses++;
    else if (outcome === 3) totalTies++;

    localStorage.setItem(username + '_total_games', totalGames);
    localStorage.setItem(username + '_total_wins', totalWins);
    localStorage.setItem(username + '_total_losses', totalLosses);
    localStorage.setItem(username + '_total_ties', totalTies);
}

function win(combination) {
    updateBoard(combination);
    
        const totalGames = parseInt(localStorage.getItem(playerName + '_total_games')) || 0;
        const gameTime = calculateGameTime();

        stopTimer();
        saveGameResult(playerName, 1, totalGames + 1, gameTime);

    winAudio.play();
    alert(`${playerName} wins!`);
}

function lose(combination) {
    updateBoard(combination);
    
    const totalGames = parseInt(localStorage.getItem(playerName + '_total_games')) || 0;
    const gameTime = calculateGameTime();

    stopTimer();
    saveGameResult(playerName, 2, totalGames + 1, gameTime);

    loseAudio.play();
    alert(`${playerName}, how did you lose against yourself?`);
}

function tie() {
    const totalGames = parseInt(localStorage.getItem(playerName + '_total_games')) || 0;
    const gameTime = calculateGameTime();

    stopTimer();
    saveGameResult(playerName, 3, totalGames + 1, gameTime);

    tieAudio.play();
    alert('It\'s a tie!');
}

function calculateGameTime() {
    return Math.floor((new Date().getTime() - startTime) / 1000);
}

function switchPlayer() {
    currentPlayer = currentPlayer === 'X' ? 'O' : 'X';
    updateTurnInfo();
}

function updateBoard(winningCombination) {
    winningCombination.forEach(index => {
        const cell = document.getElementById(`cell-${index + 1}`);
        cell.style.backgroundColor = 'yellow';
        cell.textContent = currentPlayer;
    });
}

function startTimer() {
    gameStarted = true;
    startTime = new Date().getTime();
    timerInterval = setInterval(updateTimer, 100);
}

function updateTimer() {
    const currentTime = new Date().getTime();
    const elapsedTime = (currentTime - startTime) / 1000;
    const minutes = Math.floor(elapsedTime / 60);
    const seconds = Math.floor(elapsedTime % 60);
    document.getElementById('timer').textContent = `Time: ${minutes}:${seconds.toString().padStart(2, '0')}`;
}

function stopTimer() {
    gameStarted = false;
    clearInterval(timerInterval);
}

function startGame() {
    if (playerName != "") {
        board = ['', '', '', '', '', '', '', '', ''];
        document.querySelectorAll('.cell').forEach(cell => {
            cell.textContent = '';
            cell.style.backgroundColor = '';
        });
        startTimer();
        updateTurnInfo();
        gameStarted = true;
    } else {
        alert("Please enter your name before starting the game.");
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.cell').forEach(cell => cell.addEventListener('click', handleCellClick));
    updateTurnInfo();
});
