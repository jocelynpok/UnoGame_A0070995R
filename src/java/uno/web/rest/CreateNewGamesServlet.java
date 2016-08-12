/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.web.rest;

import java.io.IOException;
import java.util.UUID;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Models.UnoGame;
import javax.inject.Inject;
import uno.web.models.GameTable;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
/**
 *
 * @author jocelyn
 */
//@WebServlet
public class CreateNewGamesServlet {
     @Inject private GameTable tables;
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String strgname = req.getParameter("gname");
        String strgid = UUID.randomUUID().toString().substring(0,8);
        
        UnoGame g = new UnoGame(strgid, strgname, "waiting");
        tables.getTables().put(strgid, g);
        
       // GlobalHashMap.hm.put(strgid, g);
   
                JsonObject jso = Json.createObjectBuilder()
                .add("gid", strgid)
                .add("gname", g.getgName())
                .add("status", g.getStatus())
                .build();
    }
}
