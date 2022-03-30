const url = 'http://localhost:8080';
let stompClient;
let pl_id;
let pl_name;
let pl_symbol;
var gameId = 552;


function connectToSocket(gameId) {
    console.log("connecting to the game");
    let socket = new SockJS(url + "/gameplay/game/");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to the frame: " + frame);
        stompClient.subscribe("/topic/game-progress/" + gameId, function (response) {
            let data = JSON.parse(response.body);
            console.log(data);
            displayResponse(data);
        })
    })
}

function create_game() {
    let name = document.getElementById("name").value;
    if (name == null || name === '') {
        // alert("Please enter login");
        $("#alert-message").html("Пожалуйста, введите имя!");
    } else {
        $.ajax({
            url: url + "/gameplay/start",
            type: 'POST',
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "id" : "1",
                "name": name,
                "symbol" : "X"
            }),
            success: function (data) {
                pl_id = data.Player[0].id;
                pl_name = data.Player[0].name;
                pl_symbol = data.Player[0].symbol;
                reset();
                connectToSocket(gameId);
                // alert(pl_name + " вы создали игру!");
                $("#message").html(pl_name + " вы создали игру!");
                num = 0;
                gameOn = true;
            },
            error: function (error) {
                console.log(error);
                $("#alert-message").html("Ошибка! Error = " + error);
            }
        })
    }
}


function connectToGame() {
    let name = document.getElementById("name").value;
    if (name == null || name === '') {
        // alert("Пожалуйста, введите имя!");
        $("#alert-message").html("Пожалуйста, введите имя!");
    } else {
        $.ajax({
            url: url + "/gameplay/connect",
            type: 'POST',
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "id" : "2",
                "name": name,
                "symbol" : "O"
            }),
            success: function (data) {
                pl_id = data.Player[0].id;
                pl_name = data.Player[0].name;
                pl_symbol = data.Player[0].symbol;
                reset();
                connectToSocket(gameId);
                if (name === data.Player[1].name) {
                    // alert(name + ", congrats you're playing with: " + data.Player[0].name);
                    $("#message").html(name + ", вы играете с: " + data.Player[0].name);
                } else {
                    // alert(name + ", congrats you're playing with: " + data.Player[1].name);
                    $("#message").html(name + ", вы играете с: " + data.Player[1].name);
                }
                // alert("Игрок " + data.Player[0].name + " ходит первым!");
                $("#message2").html("Игрок " + data.Player[0].name + " (" + data.Player[0].symbol + ") ходит первым!");
            },
            error: function(request, status, error) {
                let statusCode = request.status; // код ответа
                console.log(error);
                // alert("Ошибка! Попытка присоединения третьего или игрока с существующим именем! Error=  " + statusCode);
                $("#alert-message").html("Ошибка! Попытка присоединения третьего или игрока с существующим именем! Error=  " + statusCode);
            }
        })
    }
}
