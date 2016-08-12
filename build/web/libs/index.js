/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
//        var connection = null;
//        var gid = null;
//        var display_cards = function(msg){
//        //       $("displayCards").append($(""))
//
//        }
    var gid = null;

    var rowTemple = Handlebars.compile($("#rowTemplate").html());
    var playerTemple = Handlebars.compile($("#playerTemplate").html());

    //var tbodyTemplate = Handlebars.compile($("#tbodyTemplate").html());

//        $("#createGameBtn").on("click", function() {
//            $.post("api/game",{gname:$("#gname").val())
//                        .done("#resultBody").append(rowTemple({game:result}));
//        	});
//	});
    $("#createGameBtn").on("singletap", function () {
        var promise = $.post("api/game", {gname: $("#gname").val()});
        promise.done(function (result) {
            gid = result.gid;
            $.UIGoToArticle("#gaming");
            $("#join").empty();
            $("#join").append(rowTemple({game: result}));
//                var obj = JSON.stringify(result);
//                $("#Game").text(obj);

        });
    });
    $("#backToCreate").on("singletap", function () {
        var promise = $.post("api/game", {gname: $("#gname").val()});
        $("gname").empty();

    });



    $("#Start").on("singletap", function () {


////            var gid =
////            connection = new WebSocket("ws://localhost:8080/UnoGameTeam9/game/"  );
////            connection.onopen = function(){
//             var promise = $.post("api/game/initialise/{gid}", {gid: gid});
//             promise.done(function (result) {
//            $.UIGoToArticle("#display");
//            console.log("returned result");
////            $("#playerList").append(playerTemple({players: result}));
// $("#playerList").append(playerTemple(result.players));
//            $("#cards").append(result.numCards);
//             $("#topcard").append(result.topcard);
        var promise = $.getJSON("api/game/status/" + gid + "/table", {gId: gid});
        promise.done(function (result) {
            $.UIGoToArticle("#display");
            $("#topcard").attr("src", "images/" + result.Hand[0].id + ".png")
           // $("#topcard").attr("src", "images/" + result.Hand.id + ".png")
            $("#playerList").append(playerTemple({player: result.players}));
//           
            $("#cards").append(result.numCard);
            $("#gameId").append(result.gid);
               $("#topcard").attr("src", "images/" + result.Hand[0].id + ".png")
//            $("#topcard").append(result.topcard);
              //  var obj = JSON.stringify(result);
                //$("#cards").append(obj);

        });
    });

});