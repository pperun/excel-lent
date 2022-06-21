var ws;
var stompClient;



ws = new SockJS("/questions");

stompClient = Stomp.over(ws);

stompClient.connect({}, function (frame) {
    stompClient.subscribe("/topic/questions", function (message) {
        var fullMessage = JSON.parse(message.body);
        $('#questions').prepend(
            '<div class="row">' +
                '<div class="col s12">' +
                    '<div class="card">' +
                        '<div class="card-content right grey-text" style="padding: 0 24px 0 6px">' +
                            '<p>' + fullMessage.user + '</p>' +
                        '</div>' +
                        '<div class="card-content">' +
                            '<span class="card-title">' + fullMessage.question + '</span>' +
                            '<div class="answers">' +

                            '</div>' +
                            '<div class="card-action" style="padding: 24px 0 24px 0">' +
                                '<button class="btn blue modal-trigger" id="' + fullMessage.id + '" data-target="newAnswerModal" onclick="sendID(this)">' +
                                    '<span>New answer</span>' +
                                '</button>' +
                            '</div>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>'
        );
    });

    stompClient.subscribe("/topic/answers", function (message) {
        var fullMessage = JSON.parse(message.body);
        console.log(fullMessage)
        $("#" + fullMessage.id).parent().prev().prepend(
            '<div class="card-action" style="padding: 0">' +
                '<div class="card-content right grey-text" style="padding: 0 6px 0 6px">' + fullMessage.user + '</div>' +
                '<div class="card-content">' +
                    '<p>' + fullMessage.answer + '</p>' +
                '</div>' +
            '</div>'
        )
    });

}, function (error) {
        console.log("STOMP error" + error)
});




function sendQuestion() {
    stompClient.send('/app/questions', {}, $('#question').val());
}

function sendAnswer() {
    stompClient.send('/app/answers', {}, JSON.stringify([$('#answer').val(), $('#hidden-value').val()]));
}

function sendID(button) {
    $('#hidden-value').val(button.id);
}

$(function() {
    $('.modal').modal();
    $('#sendQuestionButton').click(function () {
        var question = $('#question');
        if(question.val() === '') {
            question.addClass('invalid');
            $('#sendQuestionButton').removeClass('modal-close');
        }
        else {
            question.removeClass('invalid');
            question.focus()
            $('#sendQuestionButton').addClass('modal-close');
            sendQuestion();
            question.val('');
        }
    });
});

$(function() {
    $('.modal').modal();
    var sendAnswerButton = $('#sendAnswerButton');
    sendAnswerButton.click(function () {
        var answer = $('#answer');
        if(answer.val() === '') {
            answer.addClass('invalid');
            $('#sendAnswerButton').removeClass('modal-close');
        }
        else {
            answer.removeClass('invalid');
            answer.focus()
            $('#sendAnswerButton').addClass('modal-close');
            sendAnswer();
            answer.val('');
        }
    });
});

$(function () {
    var inputQuestion = $('#question');
    var inputAnswer = $('#answer');
    inputQuestion.on("keydown", function (event) {
        if(event.key === "Enter") {
            event.preventDefault();
            document.getElementById("sendQuestionButton").click();
        }

    });
    inputAnswer.on("keypress", function (event) {
        if(event.key === "Enter") {
            event.preventDefault();
            document.getElementById("sendAnswerButton").click();
        }
    });
})

