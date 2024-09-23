 let username;

function handleSubmitClick(event) {
    event.preventDefault();
    var name = document.getElementById('username').value;
    if (name === '') {
        alert("Please enter your name (up to 10 characters)");
        return;
    }
    username = name;
    document.getElementById('username').style.display = 'none';
    document.getElementById('submittedName').style.display = 'none';
    document.getElementById('displayName').innerHTML = username;
    document.getElementById('submittedName').submit();
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('submitName').addEventListener('click', handleSubmitClick);
});