/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {

	//var rowTemple = Handlebars.compile($("#rowTemplate").html());
	var tbodyTemplate = Handlebars.compile($("#tbodyTemplate").html());

	$("#getStud").on("click", function() {
		//Async
		var promise = $.getJSON("api/games");
		promise.done(function(result) { //200 - 
			var tbody = tbodyTemplate({ games: result })
			$("#resultTable").append(tbody);
			/*
			for (var i = 0; i < result.length; i++) {
				var student = result[i];
				console.info(">> " + JSON.stringify(student));
				/* 
				 * <tr>
				 * 		<td>id</td>
				 * 		<td>name</td>
				 * </tr>
				 */
				//$("#resultBody").append(rowTemple(student));
			//}
		});
			
	});
});
