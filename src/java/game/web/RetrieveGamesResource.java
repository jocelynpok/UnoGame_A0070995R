/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.web;

import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Models.UnoGame;
import Models.GameTables;

import javax.inject.Inject;


/**
 *
 * @author jocelyn
 */
@ApplicationScoped
@Path("/games")
public class RetrieveGamesResource {

    @Inject
    private GameTables gt;

    @GET
    @Path("/waiting")
    @Produces(MediaType.APPLICATION_JSON) //application/json
    public Response getAllGames() {

        //System.out.println(">>> query parameter: " + display);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (UnoGame g : gt.getTables().values()) {
            if ("waiting".equals(g.getStatus())) {
                arrBuilder.add(g.toJson());
            }
        }

		
        return (Response.ok(arrBuilder.build()).build());
    }
}
		
