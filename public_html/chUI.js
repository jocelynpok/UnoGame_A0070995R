"use strict";

$(function () {
    var GameTemplate = Handlebars.compile($("#GameTemplate").html());
    var gid = null;
    var pName = null;
      var cardTemple = Handlebars.compile($("#cardTemplate").html());
    //var tbodyTemplate = Handlebars.compile($("#tbodyTemplate").html());

//        $("#createGameBtn").on("click", function() {
//            $.post("api/game",{gname:$("#gname").val())
//                        .done("#resultBody").append(rowTemple({game:result}));
//        	});
//	});
//    $("#createGameBtn").on("click", function () {
//        var promise = $.post("api/game", {gname: $("#gname").val()});
//        promise.done(function (result) {
//            $("#resultBody")
//                    .append(rowTemple({game: result}));
//        });
//    });
    //  var ip = "http://localhost:8080/UnoGameTeam9/api/games/waiting";


//    var promise = $.getJSON(ip);
//
//    promise.done(function (result) {
//        console.log("connected");
//
//        var members = GameTemplate({Game: result});
//        $("#gameList").empty();
//        $("#gameList").append(members);
//        $.UIGoToArticle("#joinGame");
    $("#reloadBtn").on("singletap", function () {
        var ip = "http://localhost:8080/UnoGameTeam9/api/games/waiting";
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
        // var promise = $.post("api/game", {gname: $("#gname").val(),});
//            var promise =$.getJSON("api/game/" + gid )
//            .done(function(result) {
//				
//				$.UIGoToArticle("#waitGame");
//			})
    })
    $("#Join").on("singletap", function () {
        pName = $("#pName").val();
        var promise = $.post("http://localhost:8080/UnoGameTeam9/api/game/getgame", {pName: pName, gameId: gid})
                .done(function (result) {
                    //added result
                    console.info("player response");
                    $.UIGoToArticle("#waitGame");
                })
    });
    $("#Start").on("singletap", function () {
        console.info("gid = %s, pName = %s", gid, pName);
        var promise = $.getJSON("http://localhost:8080/UnoGameTeam9/api/game/status/"+ gid + "/" + pName )
                .done(function (result) {
                    console.info("player cards");
//                    var members = GameTemplate({cards: result});
//                    $("#cardList").empty();
//                    $("#cardList").append(members);

                    $.UIGoToArticle("#playersHand");
                 //    $("#cardList").attr("src", "images/" + result.Hand[0].id + ".png")
                 var cardMembers = cardTemple({cards:result.Cards});
                 $("#cardList").append(cardMembers);
                 $("#gid").append(result.gid);
                 $("#pid").append(result.playerName);
//                    var obj = JSON.stringify(result);
//                $("#cardList").append(obj);
                })
    });
});
