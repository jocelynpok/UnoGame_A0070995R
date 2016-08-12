/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.web.models;
import Models.UnoGame;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

/**
 *
 * @author jocelyn
 */
@ApplicationScoped
public class GameTable {
     private Map<String, UnoGame> tables = new HashMap<>();
     
      public Map<String, UnoGame> getTables() {
        return tables;
    }
       @Override
    public String toString() {
        String result = "";
        for(String s: getTableNames())
            result += s + "\n";
        
        return result;
    }

//    public void add(String tableName,String tableId,String tableStatus ,Session session) {
//       
//        try {
//            UnoGame table = tables.get(tableName);
//            if(null == table){
//                table = new UnoGame(tableName,tableId,tableStatus);
//               // table.setTableName(tableName);
//                tables.put(tableName, table);
//               // table.getDisPipe().setSession(session);
//
//            }else{
//                Player p = new Player("", session);
//                table.addPlayer(p);
//            }
//           // System.out.println("+++ size: " +tables.get(tableName).getPlayers().size());
//        } finally {
//            //lock.unlock();
//        }
//    }

    public Set<String> getTableNames() {
      //  lock.lock();
        try {
            return (tables.keySet());
        } finally {
       //     lock.unlock();
        }
    }
}
