/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    var gid = null;
    var rowTemple = Handlebars.compile($("#rowTemplate").html());
    var playerTemple = Handlebars.compile($("#playerTemplate").html());
   
    var pName = null;
    var cmd = null;
    
     $(".special").draggable();

  
   
    var connection = null;
    $("#createGameBtn").on("singletap", function () {

      
        console.info(">>> clicked create game btn");
        var promise = $.post("api/game", {gname: $("#gname").val()});
        promise.done(function (result) {
            console.info(">> result returned")
            gid = result.gid;
            $.UIGoToArticle("#gaming");
            $("#join").empty();
            $("#join").append(rowTemple({game: result}));
            pName = "table";
            console.info("creating web socket")
            connection = new WebSocket("ws://localhost:8080/testWebSocket/game/" + gid + "/" + pName);
            connection.onopen = function () {
                console.info("connection opened")
                var message = {
                    pName: pName,
                    gid: gid,
                    cmd: "join-game"
                };
                connection.send(JSON.stringify(message));
            };
            connection.onclose = function () {
//
            };
            connection.onmessage = function (evt) {

                var result = JSON.parse(evt.data);
                switch (result.cmd) {
                    case "addingPlayers":
                        $.UIGoToArticle("#gaming");
                        var msg = JSON.parse(evt.data);
                        console.info(">>> adding players: " + evt.data);
                        $("#playerListSummary").append(playerTemple({player: [msg.players]}));
                        break;
                    case "discard":
                        $.UIGoToArticle("#display");
                        var $newCard = $("<img src='images/" + result.dropCard.id+".png' data-element data-discard = 'x' class='card special'>");
                        $("#tableDiscard").append($newCard);
                        $newCard.draggable();
                      //  $("#cardList").prepend(oneCardTemplate({id: result.drawCard.id}));
//                              
                        break;
                    default:
                        break;
                }
            };
        });
        return true;
  //   }
    });
    $("#backToCreate").on("singletap", function () {

        $("gname").empty();
    });


    $("#Start").on("singletap", function () {
        var promise = $.getJSON("api/game/status/" + gid + "/table", {gId: gid});
        promise.done(function (result) {
            $.UIGoToArticle("#display");
            $("#tableDiscardCard").attr("src", "images/" + result.Hand[0].id + ".png");

            
            console.info(">> start: " + JSON.stringify(result))
            $("#playerListSummary").empty();
            $("#playerList").append(playerTemple({player: result.players}));

            var count = result.numCard;
            console.info(">>>count:" + count);
            $("#tableDrawCard").attr("src", "images/back.png");


            for (var i = 0; i < count; i++) {
               
                $("#tableDraw").append($("<img src='images/back.png' data-element data-draw = 'x' class='card special'>"))


            }
            $("#cards").append(result.numCard);

            $("#gameId").append(result.gid);

            $("#playerList").on("singletap", "li", function () {
                var pName = $(this).attr("data-player");
                console.info(">> gid = " + pName);
            });

//            var $dataElement = $("[data-element]");
//            $dataElement.draggable();
//inserted special is draggable here           
            $(".special").draggable();
            var $dropTarget = $("[data-target]");
            $dropTarget.droppable({
                // accept: "[data-element]",
                accept: ".special",
                tolerance: "pointer"

            });
            $dropTarget.on("drop", function (event, ui) {
                // console.log("Position:top = " + ui.position.top + ",left = " + ui.position.left);
                var pName = $(this).attr("data-target");
                console.info(">> gid = " + pName);

                console.info(gid + ", " + $(ui.draggable).attr("data-draw"));

                if ($(ui.draggable).attr("data-draw")) {
                    //draw
                    console.info("sending draw card msg")
                    var message = {
                        pName: pName,
                        gid: gid,
                        cmd: "draw"
                    };
                    connection.send(JSON.stringify(message));
                } else {
                    //discard
                       console.info("sending withdraw discard card msg")
  //                      var pName = $(this).attr("data-discard");
  //                      
                    var message = {
                        pName: pName,
                        gid: gid,
                        cmd: "withdrawCard"
                    };
                    connection.send(JSON.stringify(message));
                }
                $(ui.draggable).fadeOut(300).queue(function () {
                    $(ui.draggable).remove();
                });
            });

            $dropTarget.on("dropover", function (event, ui) {
                $dropTarget.effect("bounce", "slow");


            });
            
            var message = {
                pName: pName,
                gid: gid,
                cmd: "start"
            };
            connection.send(JSON.stringify(message));
           
        });
    });
});