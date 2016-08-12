/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.web.rest;

import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.PathParam;
import Models.UnoGame;

/**
 *
 * @author jocelyn
 */
@Dependent
@ServerEndpoint("/game/{gid}")
public class GameConnection {
    @OnOpen
    public void onOpen(Session session, @PathParam("gid")String gid){
        System.out.println("...new game" + session.getId());
        Map<String,Object> sessObj = session.getUserProperties();
        sessObj.put("gid", gid);
        System.out.println("gid" + gid);
        
        
    }
    @OnClose
    public void onClose(Session session, CloseReason reason){
        System.out.println("...close connection" + session.getId());
        System.out.println("...close reason"+ reason.getReasonPhrase());
    }
    @OnMessage
    public void onMessage(Session session, String msg){
        
    }
}
