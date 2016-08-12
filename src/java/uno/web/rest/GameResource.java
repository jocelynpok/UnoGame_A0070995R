/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.web.rest;

import Models.GameTables;
import Models.UnoCard;
import Models.UnoCardPlayers;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
//import uno.web.business.gameManager;
//import uno.web.model.game;
import javax.inject.Inject;
import Models.UnoGame;
import java.math.BigDecimal;
import java.util.List;
import javax.json.JsonArrayBuilder;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jocelyn
 */
@RequestScoped
@Path("/game")
public class GameResource {

    @Inject
    private GameTables gt;

    @POST
    @Path("/getgame")
    @Produces("application/text")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getGame(@FormParam("gameId") String gId, @FormParam("pName") String nId) {
        //Optional<game> opt = gameMgr.findGameByName(game);
//		if (!opt.isPresent())
//			return (Response.status(Response.Status.NOT_FOUND).build());

        // JsonObject jso = null;
        for (UnoGame g : gt.getTables().values()) {
            if (gId.equals(g.getGameId())) {
                //  jso = g.toJson();
                g.makePlayer(nId);
                System.out.println("add player to game");
                break;
            }

        }

        if (null == gId) // return (Response.status(Response.Status.NOT_FOUND).build());
        {
            return (Response.noContent().build());
        }
//            JsonArrayBuilder arrB = Json.createArrayBuilder();
//            JsonObjectBuilder jso = Json.createObjectBuilder();
//            for(UnoGame g:gt.getTables().values()){
//                
//                if(g.getStatus()=="waiting"){
//                    jso.add("gid",g.getGameId());
//                    jso.add("gname",g.getgName());
//                }
//                JsonObject json = jso.build();
//                arrB.add(json);
//            }
//            
//            
        return (Response.ok(nId).build());
    }
// 
//    
//    

    @POST
    @Produces("application/json")
    public Response getGameToStart(@FormParam("gname") String name) {
//                gameMgr.createGame(name);
//		Optional<game> opt = gameMgr.findGameByName(name);
//		if (!opt.isPresent())
//			return (Response.status(Response.Status.NOT_FOUND).build());
        String strgid = UUID.randomUUID().toString().substring(0, 8);
        UnoGame g = new UnoGame(name, strgid, "waiting");

        gt.getTables().put(strgid, g);
        JsonObject jso = Json.createObjectBuilder()
                .add("gid", strgid)
                .add("gname", g.getgName())
                .add("status", g.getStatus())
                .build();
//	return (Response.ok(opt.get()).build());
        return (Response.ok(jso).build());
    }

    @GET
    @Path("/status/{gid}/table")
    @Produces("application/json")
    public Response getGameToInitialise(@PathParam("gid") String gId) {
        System.out.println(">>> table: gid = " + gId);
        UnoGame g = gt.getTables().get(gId);
        System.out.println(">>> g = " + g);
        JsonObjectBuilder ob = Json.createObjectBuilder();
        ob.add("gid", g.getGameId())
                .add("name", "table");

       
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (UnoCardPlayers p : g.getPlayers()) {
            jab.add(p.getName());
            
        }

        ob.add("players", jab.build());
//         ob.add("Hand",g.getDiscardedCards().toString());
       
        JsonArrayBuilder discardb = Json.createArrayBuilder();
        for(UnoCard c: g.getDiscardedCards()){
            discardb.add(c.toJson());
        }
        ob.add("Hand", discardb.build());
        ob.add("numCard",g.getDeck().calculateBalanceInPile(g.getDeck().getPileCard()));
//         JsonArrayBuilder numCard = Json.createArrayBuilder();
//        for(UnoCard c: g.getDeck().getPileCard()){
//            numCard.add(c.toJson());
//        }
//        ob.add("DrawPile", numCard.build());
        return (Response.ok(ob.build()).build());
    }
    @GET
    @Path("/status/{gid}/{pName}")
    @Produces("application/json")
    public Response getPlayersHand(@PathParam("gid") String gId, @PathParam("pName")String pId) {
        System.out.println(">> gid = " + gId + ", pid = " + pId);
        UnoGame g = gt.getTables().get(gId);
        JsonObjectBuilder ob = Json.createObjectBuilder();
        ob.add("gid", g.getGameId());
                JsonArrayBuilder jab = Json.createArrayBuilder();
      UnoCardPlayers p = g.getPlayer(pId);
        for(UnoCard c:p.getCards() )
            jab.add(c.toJson());
       ob.add("Cards", jab.build());
       
        

        ob.add("playerName", p.getName());

         JsonArrayBuilder discardb = Json.createArrayBuilder();
        for(UnoCard c: g.getDiscardedCards()){
            discardb.add(c.toJson());
        }
        ob.add("Hand", discardb.build());
//            ob.add("Hand",g.getDiscardedCards().toString());
        
//         JsonArrayBuilder numCard = Json.createArrayBuilder();
//        for(UnoCard c: g.getDeck().getPileCard()){
//            numCard.add(c.toJson());
//        }
//        ob.add("DrawPile", numCard.build());
        return (Response.ok(ob.build()).build());
    }


}

//    @POST
//    @Path("/showcards")
//    @Produces("application/json")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response getPlayersHand(@FormParam("gameId") String gId, @FormParam("pName") String nId) {
//        //Optional<game> opt = gameMgr.findGameByName(game);
////		if (!opt.isPresent())
////			return (Response.status(Response.Status.NOT_FOUND).build());
//        JsonObject pObj;
//        JsonArrayBuilder jab = Json.createArrayBuilder();
//        // JsonObject jso = null;
//        for (UnoGame g : gt.getTables().values()) {
//            if (gId.equals(g.getGameId())) {
//                //  jso = g.toJson();
//                List<UnoCardPlayers> plyList = g.getPlayers();
//                for (int i = 0; i < plyList.size(); i++) {
//
//                    UnoCardPlayers player = plyList.get(i);
//                    if (player.getName() == nId) {
//
//                        pObj = Json.createObjectBuilder()
//                                .add("card0", player.getCards().get(0).getImage())
//                                .add("card1", player.getCards().get(1).getImage())
//                                .add("card3", player.getCards().get(2).getImage())
//                                .add("card4", player.getCards().get(3).getImage())
//                                .add("card5", player.getCards().get(4).getImage())
//                                .add("card6", player.getCards().get(5).getImage())
//                                .add("card7", player.getCards().get(6).getImage())
//                                //                            .add("name", player.getName())
//                                //.add("status", g.getStatus())
//                                .build();
//                        // jab.add(pObj);
//                        break;
//                    }
//
//                }
//
//                if (null == gId) // return (Response.status(Response.Status.NOT_FOUND).build());
//                {
//                    return (Response.status(Response.Status.NOT_FOUND).build());
//                }
//                  break;
//            }
//
//        }
//        return (Response.ok().build());
//    }
//    @GET
//    @Path("/getplayers/{gid}")
//    @Produces("application/json")
//    public Response getGamePlayers(@PathParam("gid") String gId) {
////            
//        JsonObject gObj;
//
//        JsonArrayBuilder jab = Json.createArrayBuilder();
////        
//
//        for (UnoGame g : gt.getTables().values()) {
//            if (gId.equals(g.getGameId())) {
//               
//
//                List<UnoCardPlayers> playerList = g.getPlayers();
//                for (int i = 0; i < playerList.size(); i++) {
//                    UnoCardPlayers player = playerList.get(i);
//
//                    gObj = Json.createObjectBuilder()
//                            .add("name", player.getName())
//                            
//                            .build();
//
//                    jab.add(gObj);
////                   
//                    break;
//                }
////              
//            }
//
//        }
//        return (Response.ok(jab).build());
//    }

