
var jQueryScript = document.createElement('script');
jQueryScript.setAttribute('src','https://code.jquery.com/jquery-1.10.2.min.js');
document.head.appendChild(jQueryScript);

var ajaxScript = document.createElement('script');
ajaxScript.setAttribute('src','https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js');
document.head.appendChild(ajaxScript);

var sockJs = new SockJS("/stomp/chat");
//1. SockJS를 내부에 들고있는 stomp를 내어줌
var stomp = Stomp.over(sockJs);

// $(document).ready(function (){
//
// });
// initStomp();

connectSSE($('#currRoomId').val());
function initStomp() {

    // var roomId = $("#currRoomId").val();
    var username = "test user";
    // console.log(roomId + ", " + username);
    // connectSSE($('#currRoomId').val());

    //2. connection이 맺어지면 실행
    stomp.connect({}, function () {
        console.log("STOMP Connection")

        allRoomId.forEach(function (item){
            stomp.subscribe("/sub/chat/room/" + item, function (chat) {
                console.log("chat : " + chat);
                console.log("item : " + item);
                console.log("----------------")
                var content = JSON.parse(chat.body);

                var sender = content.senderName;
                var senderId = content.senderId;
                var createdAt = content.createdAt;
                var message = content.message;
                var profileImageUrl = content.profileImageUrl;
                var chatRoomId = content.chatRoomId;
                var str = '';

                if (senderId !== userId && chatRoomId === $('#currRoomId').val()) {
                    str = "<li class='clearfix'>";
                    str += "<div>";
                    str += "<div class='message-data'>";
                    str += "<img src='" + profileImageUrl + "' alt='avatar'>";
                    str += "<span class='message-data-time'>" + sender + "</span>";
                    str += "<span class='message-data-time'>" + createdAt + "</span>";
                    str += "</div>";
                    str += "<div class='message other-message'>" + message + "</div>";
                    str += "</div>";
                    str += "</li>";

                    $("#chatList").append(str);
                    const element = document.getElementById('chats');
                    element.scrollTop = element.scrollHeight;
                }

                if (senderId !== userId && chatRoomId !== $('#currRoomId').val()) {
                    // console.log(window.location.href);
                    console.log("curr room Id : " + $('#currRoomId').val());
                    getNewChatNotification(chatRoomId, senderId);
                }

            }, {id: item});
        })
        // 4. subscribe(path, callback)으로 메세지를 받을 수 있음

    });

    //3. send(path, header, message)로 메세지를 보낼 수 있음
    // stomp.send('/pub/chat/enter', {}, JSON.stringify({roomId: roomId, writer: username}))

}

// function subscribe(roomId) {
//     console.log("STOMP Connection");
//     console.log(roomId);
//     //4. subscribe(path, callback)으로 메세지를 받을 수 있음
//     stomp.subscribe("/sub/chat/room/" + roomId, function (chat) {
//         console.log("function chat : " + chat);
//         var content = JSON.parse(chat.body);
//
//         var sender = content.senderName;
//         var senderId = content.senderId;
//         var createdAt = content.createdAt;
//         var message = content.message;
//         var profileImageUrl = content.profileImageUrl;
//         var chatRoomId = content.chatRoomId;
//         var str = '';
//
//         if (senderId !== userId && chatRoomId === $('#currRoomId').val()) {
//             str = "<li class='clearfix'>";
//             str += "<div>";
//             str += "<div class='message-data'>";
//             str += "<img src='" + profileImageUrl + "' alt='avatar'>";
//             str += "<span class='message-data-time'>" + sender + "</span>";
//             str += "<span class='message-data-time'>" + createdAt + "</span>";
//             str += "</div>";
//             str += "<div class='message other-message'>" + message + "</div>";
//             str += "</div>";
//             str += "</li>";
//
//             $("#chatList").append(str);
//             const element = document.getElementById('chats');
//             element.scrollTop = element.scrollHeight;
//         }
//
//         if (senderId !== userId && chatRoomId !== $('#currRoomId').val()) {
//             getNewChatNotification(chatRoomId);
//         }
//
//     }, {id: roomId});
// }

function updateChatRoom() {
    var chatRoomBean = {
        roomName: $("#roomName").val(),
        postCid: 9,
    };
    $.ajax({
        url: "/chat/room/create",
        type: "POST",
        data: chatRoomBean,
    })
        .done(function (fragment) {
            $('#chatRoomTable').replaceWith(fragment);
        });
}
function exitChatRoom() {
    console.log("exit in")
    var exitChatRoomBean = {
        chatRoomId: $("#currRoomId").val()
    };
    $.ajax({
        url: "/chat/room/exit",
        type: "POST",
        data: exitChatRoomBean,
    })
        .done(function (fragment) {
            console.log("exit")
            // $("#alertMessage").replaceWith(fragment)
            window.location.href = 'room';
        });
}
function inviteChatRoom() {
    var inviteChatRoomBean = {
        userId: $("#inviteUserId").val(),
        chatRoomId: $("#currRoomId").val()
    };
    $.ajax({
        url: "/notification/invite/publish",
        type: "POST",
        data: inviteChatRoomBean,
    })
        .done(function (fragment) {
            console.log("invite")
            $("#alertMessage").replaceWith(fragment)
        });
}

function getUsers() {
    var inviteChatRoomBean = {
        chatRoomId: $("#currRoomId").val()
    };
    $.ajax({
        url: "/chat/room/users",
        type: "GET",
        data: inviteChatRoomBean,
    })
        .done(function (fragment) {
            console.log("get users")
            $("#usersInChatRoom").replaceWith(fragment)
        });
}



function getChats(roomId) {
    console.log("chat room click")
    if (roomId == $("#currRoomId").val()) {
        console.log("room is identical!");
        return;
    }
    changeSubs(roomId);
    getUsers();
    var chatRoomIdBean = {
        roomId: roomId,
    };



    $.ajax({
        url: "/chat/history",
        type: "Get",
        data: chatRoomIdBean,
    })
        .done(function (fragment) {
            $('#chats').replaceWith(fragment);
            const element = document.getElementById('chats');
            element.scrollTop = element.scrollHeight;
        });
}


function representChatRoomAbout(roomId){
    var chatRoomIdBean = {
        roomId: roomId
    };

    $.ajax({
        url: "/chat/room/represent",
        type: "GET",
        data: chatRoomIdBean
    })
        .done(function (fragment) {
            $('#representRoom').replaceWith(fragment);
        });
}

function changeChatRoom( roomId){
    connectSSE(roomId);
    getChats(roomId);
    representChatRoomAbout(roomId);

}

var source = null;
function connectSSE(roomId){
    source = new EventSource("/chat/last/" + roomId);
    console.log("create EventSource");
    console.log(source.url)
    source.onmessage = function(ev) {
        var content = JSON.parse(ev.data);
        console.log("on message: ", content);
        console.log("CHAT : ", content.content);
        console.log("----------------")

        var sender = content.senderName;
        var senderId = content.senderId;
        var createdAt = content.createdAt;
        var message = content.message;
        var profileImageUrl = content.profileImageUrl;
        var chatRoomId = content.chatRoomId;
        var str = '';

        if (senderId !== userId && chatRoomId === $('#currRoomId').val()) {
            str = "<li class='clearfix'>";
            str += "<div>";
            str += "<div class='message-data'>";
            str += "<img src='" + profileImageUrl + "' alt='avatar'>";
            str += "<span class='message-data-time'>" + sender + "</span>";
            str += "<span class='message-data-time'>" + createdAt + "</span>";
            str += "</div>";
            str += "<div class='message other-message'>" + message + "</div>";
            str += "</div>";
            str += "</li>";

            $("#chatList").append(str);
            const element = document.getElementById('chats');
            element.scrollTop = element.scrollHeight;
        }
    };
    source.onerror = function(err) {
        console.log("on err: ", err);
        stop();
    };
}
function stop() {
    if (source != null) {
        source.close();
        console.log("close EventSource");
        source = null;
    }
}

function changeSubs(roomId) {
    // if (roomId == $("#currRoomId").val()) {
    //     console.log("room is identical!");
    //     return;
    // }
    // stomp.unsubscribe($("#currRoomId").val());
    console.log("unsubscribe currRoomId : " + $("#currRoomId").val());
    $("#currRoomId").val(roomId);

    // subscribe(roomId);
    // var chatBean = {
    //     roomId: document.getElementById('roomId').value,
    //     roomName: $("#name").val()
    // };
    // stomp.disconnect(stompSetting(), {});
}



function sendMsg() {
    var msg = document.getElementById("msg");
    var today = new Date();
    var dateTime = today.toLocaleString().toString().substring(2, 21);


    var profileImageUrI;

    profileImageUrI = $("#creationAvatar").attr('src');

    if (msg.value !== "") {
        console.log("sendMessage:" + msg.value);
        stomp.send('/pub/chat/message', {}, JSON.stringify({
            chatRoomId: $("#currRoomId").val(),
            message: msg.value,
            createdAt: dateTime,
            senderName: username,
            senderId: userId, //사용중인 사용자로 변경 필요
            profileImageUrl: profileImageUrI
        }));
        var profileImageUrl = profileImageUrI
        var name = username;
        // var dateTime = date+' '+time;
        str = "<li class='clearfix'>";
        str += "<div>"
        str += "<div class='message-data text-end'>";
        str += "<span class='message-data-time'>" + name + "</span>";
        str += "<span class='message-data-time'> " + dateTime + "</span>";
        str += "<img src='" + profileImageUrl + "' alt='avatar'>";
        str += "</div>";
        str += "<div class='message my-message float-right'>" + msg.value + "</div>";
        str += "</div>";
        str += "</li>";

        $("#chatList").append(str);
        msg.value = '';
        const element = document.getElementById('chats');
        element.scrollTop = element.scrollHeight;
    }
}

function searchChatRoom(){

    var chatRoomNameBean = {
        chatRoomName: $("#searchChatRoomName").val()
    };

    $.ajax({
        url: "/chat/room/search",
        type: "GET",
        data: chatRoomNameBean
    })
        .done(function (fragment) {
            $('#chatRoomTable').replaceWith(fragment);
        });
}

function getNewChatNotification(chatRoomId, senderId) {
    console.log("new Chat");
    event.preventDefault();
    var chatRoomBean = {
        chatRoomId: chatRoomId,
        senderId : senderId,
    };
    $.ajax({
        url: "/notification/subscribe/chat/new",
        type: "GET",
        data: chatRoomBean,
    })
        .done(function (fragment) {
            $('#notifications').replaceWith(fragment);

        });
}

$('#msg').on("keyup", function (e) {
    if (e.key === "Enter") {
        console.log('msg send enter event');
        sendMsg()
    }
});

$('#searchChatRoomName').on("keyup", function (e) {
    if (e.key === "Enter") {
        console.log('search chat room enter event');
        searchChatRoom()
    }
});


$("#sendMessage").on("click", function (e) {
    sendMsg()
});

$("#createChatRoom").on("click", function (e) {
    e.preventDefault();
    updateChatRoom();
});

$("#inviteChatRoomButton").on("click", function (e) {
    e.preventDefault();
    inviteChatRoom();
});

$("#exitChatRoom").on("click", function (e) {
    e.preventDefault();
    exitChatRoom();
});