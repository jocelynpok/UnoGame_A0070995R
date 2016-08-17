/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    var GameTemplate = Handlebars.compile($("#GameTemplate").html());
    var gid = null;
    var pName = null;
    var cardTemple = Handlebars.compile($("#cardTemplate").html());
    var oneCardTemplate = Handlebars.compile($("#oneCardTemplate").html());
    var connection = null;
    var cmd = null;
    $("#reloadBtn").on("singletap", function () {
        var ip = "http://localhost:8080/testWebSocket/api/games/waiting";
        var promise = $.getJSON(ip);
        promise.done(function (result) {
            console.log("connected");
            var members = GameTemplate({games: result});
            $("#gameList").empty();
            $("#gameList").append(members);
            $.UIGoToArticle("#joinGame");
        });
    });
    $("#reloadBtn").trigger("singletap");
    $("#gameList").on("singletap", "li", function () {
        gid = $(this).attr("data-gameId");
        console.info(">> gid = " + gid);
    });
    $("#Join").on("singletap", function () {
        $.UIGoToArticle("#waitGame");
        cmd = "join";
        pName = $("#pName").val();
        var promise = $.post("http://localhost:8080/testWebSocket/api/game/getgame", {pName: pName, gameId: gid})
                .done(function (result) {
                    //added result
                    console.info("player response");
                    $.UIGoToArticle("#waitGame");
                    connection = new WebSocket("ws://localhost:8080/testWebSocket/game/" + gid + "/" + pName);
                    connection.onopen = function () {
                        var message = {
                            pName: pName,
                            gid: gid,
                            cmd: "join"
                        };

                        connection.send(JSON.stringify(message));
                    };
                    connection.onclose = function () {

                    };
                    connection.onmessage = function (evt) {
                        
                        var result = JSON.parse(evt.data);
                        switch (result.cmd) {
                            case "start":
                                $.UIGoToArticle("#playersHand");
                                $("#cardList").append(cardTemple({cards: result.Cards}));
                                $("#gid").append(result.gid);
                                $("#pid").append(result.playerName);                              
                                break;
                           case "draw":
                              
                                $("#cardList").prepend(oneCardTemplate({ id: result.drawCard.id}));
//                              
                           break;
                       case "withdrawCard":
                            $("#cardList").prepend(oneCardTemplate({ id: result.withdrawCard.id}));
                            break;
                            default:
                                break;
                        }

                    };
                });
    });
     $("#cardList").on("singletap", "li", function () {
               
                var cardName = $(this).attr("data-cardId");
                console.info(">> card = " + cardName);
                 var message = {
                        pName: pName,
                        gid: gid,
                        card: cardName,
                        cmd: "discard"
                        
                    };
                     connection.send(JSON.stringify(message));
                this.parentNode.removeChild(this);
            });


});
