package game.web;

import Models.GameTables;
import Models.UnoCard;
import Models.UnoCardPlayers;
import Models.UnoGame;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Dependent
@ServerEndpoint("/game/{gid}/{pName}")
public class ChatEndpoint {

    @Inject
    private GameTables gt;

    
    @OnOpen
    public void onOpen(Session session, @PathParam("gid") String gid, @PathParam("pName") String pName) {
        System.out.println(">> new connection: " + session.getId());
       
        Map<String, Object> sessObject = session.getUserProperties();


        UnoGame g = gt.getTables().get(gid);
        if (("table").equals(pName)) {
            g.setSession(session);
            sessObject.put("gid", gid);
            System.out.println(">> new table session: " + pName);
        } else {
            UnoCardPlayers p = g.getPlayer(pName);
            p.setSession(session);
            sessObject.put("pName", pName);
            System.out.println(">> new player session: " + pName);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println(">> close connection: " + session.getId());
        System.out.println("\t close reason: " + reason.getReasonPhrase());
    }

    @OnMessage
    public void onMessage(Session session, String msg) {

        System.out.println("...number of connections:" + session.getOpenSessions().size());
        System.out.println(msg);

        InputStream is = new ByteArrayInputStream(msg.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject data = reader.readObject();
        String gid = data.getString("gid");
        String pName = data.getString("pName");
        // String cmd = data.getString("cmd");

        System.out.println(">> incoming connection: " + msg);

        switch (data.getString("cmd")) {
            case "join":

                System.out.println(">> in join cmd");

                UnoGame g = gt.getTables().get(gid);
                Session tableSession = g.getSession();

            
                String newMessage = Json.createObjectBuilder()
                        .add("players", pName)
                        .add("cmd", "addingPlayers")
                        .build().toString();

                System.out.println(">> " + newMessage);

                try {
                    tableSession.getBasicRemote().sendText(newMessage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "discard":
                String cardName = data.getString("card");
                System.out.println(">> gid = " + gid + ", pid = " + pName);
                UnoGame discarding = gt.getTables().get(gid);
                

                UnoCardPlayers splayer = discarding.getPlayer(pName);
                UnoCard removedCard = splayer.subtractCardById(cardName);
                discarding.getDiscardedCards().add((removedCard));
                String tableMessage = Json.createObjectBuilder()
                        .add("cmd", "discard")
                        .add("gid", gid)
                        .add("pName", pName)
                        .add("dropCard", removedCard.toJson())
                        .build().toString();
                Session discardSession = discarding.getSession();
                try {
                    discardSession.getBasicRemote().sendText(tableMessage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "start":
                System.out.println(">> gid = " + gid + ", pid = " + pName);

                UnoGame game = gt.getTables().get(gid);
                for (UnoCardPlayers p : game.getPlayers()) {
                    Session playerSession = p.getSession();

                    JsonObjectBuilder ob = Json.createObjectBuilder();
                    ob.add("cmd", "start");
                    ob.add("gid", game.getGameId());
                    JsonArrayBuilder ja = Json.createArrayBuilder();

                    for (UnoCard c : p.getCards()) {
                        ja.add(c.toJson());
                    }
                    ob.add("Cards", ja.build());

                    ob.add("playerName", p.getName());

                    JsonArrayBuilder discardb = Json.createArrayBuilder();
                    for (UnoCard c : game.getDiscardedCards()) {
                        discardb.add(c.toJson());
                    }
                    ob.add("Hand", discardb.build());
                    String msgToSend = ob.build().toString();
//               
                    try {
                        playerSession.getBasicRemote().sendText(msgToSend);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "draw":
                System.out.println(">> gid = " + gid + ", pid = " + pName);
                UnoGame drawing = gt.getTables().get(gid);
                UnoCard c = drawing.getDeck().drawCard();
                UnoCardPlayers player = drawing.getPlayer(pName);
                player.addCard(c);
                String playerMessage = Json.createObjectBuilder()
                        .add("cmd", "draw")
                        .add("gid", gid)
                        .add("pName", pName)
                        .add("drawCard", c.toJson())
                        .build().toString();
                Session playerSession = player.getSession();
                try {
                    playerSession.getBasicRemote().sendText(playerMessage);
                    System.out.println(">> successfully sent:" + playerMessage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "withdrawCard":
                System.out.println(">> gid = " + gid + ", pid = " + pName);
                UnoGame withdraw = gt.getTables().get(gid);
                  int i = withdraw.getDiscardedCards().size();
                UnoCard withdrawCard = withdraw.getDiscardedCards().get(i-1);
                UnoCardPlayers wplayer = withdraw.getPlayer(pName);
                wplayer.addCard(withdrawCard);
                 String playerWithdrawMessage = Json.createObjectBuilder()
                        .add("cmd", "withdrawCard")
                        .add("gid", gid)
                        .add("pName", pName)
                        .add("withdrawCard", withdrawCard.toJson())
                        .build().toString();
                 Session playerWithdrawSession = wplayer.getSession();
                 try {
                    playerWithdrawSession.getBasicRemote().sendText(playerWithdrawMessage);
                    System.out.println(">> successfully sent:" + playerWithdrawMessage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            default:

                break;


        }
    }
}
