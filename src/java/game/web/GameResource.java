/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.web;

import Models.GameTables;
import Models.UnoCard;
import Models.UnoCardPlayers;
import java.util.UUID;
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
import javax.enterprise.context.RequestScoped;
//import javaxentercontextuilder;

//import jApplicationScoped.JsonObject;
import javax.json.JsonObjectBuilder;
//import javax.wsimport javax.enterprise.context.ApplicationScoped;
import javax.json.JsonArrayBuilder;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;

//.rs.Consumimport javax.ws.rs.Consumes;
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
       
        for (UnoGame g : gt.getTables().values()) {
            if (gId.equals(g.getGameId())) {
                //  jso = g.toJson();
                g.makePlayer(nId);
                System.out.println("add player to game");
                break;
            }

        }

        if (null == gId) 
        {
            return (Response.noContent().build());
        }
         
        return (Response.ok(nId).build());
    }
// 
//    
//    

    @POST
    @Produces("application/json")
    public Response getGameToStart(@FormParam("gname") String name) {

       
        String strgid = UUID.randomUUID().toString().substring(0, 8);
        UnoGame g = new UnoGame(name, strgid, "waiting");

        gt.getTables().put(strgid, g);
        JsonObject jso = Json.createObjectBuilder()
                .add("gid", strgid)
                .add("gname", g.getgName())
                .add("status", g.getStatus())
                //.add("game", g.toString())
                .build();

        return (Response.ok(jso).build());
    }

    @GET
    @Path("/status/{gid}/table")
    @Produces("application/json")
    public Response getGameToInitialise(@PathParam("gid") String gId) {
        System.out.println(">>> table: gid = " + gId);
        UnoGame g = gt.getTables().get(gId);
        g.setStatus("started");
        System.out.println(">>> g = " + g);
        System.out.println(">>> g = " + g.getStatus());
        JsonObjectBuilder ob = Json.createObjectBuilder();
        ob.add("gid", g.getGameId())
                .add("name", "table");

       
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (UnoCardPlayers p : g.getPlayers()) {
            jab.add(p.getName());
            
        }

        ob.add("players", jab.build());

       
        JsonArrayBuilder discardb = Json.createArrayBuilder();
        for(UnoCard c: g.getDiscardedCards()){
            discardb.add(c.toJson());
        }
        ob.add("Hand", discardb.build());
        ob.add("numCard",g.getDeck().calculateBalanceInPile(g.getDeck().getPileCard()));
   
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
       
        return (Response.ok(ob.build()).build());
    }


}

