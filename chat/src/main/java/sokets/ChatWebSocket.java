package sokets;

import apies.MessageApi;

import entities.Message;
import entities.Room;
import entities.User;
import service.MainQueryService;
import service.PersistenceService;
import javax.ejb.EJB;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/chat/{roomId}/{username}")
public class ChatWebSocket {
    @EJB
    PersistenceService persistenceService;
    @EJB
    MainQueryService mainQueryService;

    @OnOpen
    public void onOpen(Session session,
                       @PathParam("roomId") String roomId,
                       @PathParam("username") String username) {
        session.getUserProperties().put("roomId", roomId);
        session.getUserProperties().put("username", username);

    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            String username = (String) session.getUserProperties().get("username");
            Logger.getLogger("Testing").log(Level.SEVERE, null, username);
            String thisRoomId = (String) session.getUserProperties().get("roomId");
            Optional<User> user=mainQueryService.optionalUser(username);
            Optional<Room> room=persistenceService.findById(Room.class, BigInteger.valueOf(Long.valueOf(thisRoomId)));
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen() && s.getUserProperties().get("roomId").equals(thisRoomId)) {
                    s.getBasicRemote().sendText(message);
                    Message userMessage=new Message();
                    userMessage.setRoom(room.get());
                    userMessage.setUser(user.get());
                    userMessage.setText(message);
                    persistenceService.saveOrUpdate(userMessage);

                }
            }


        } catch (IOException ex) {
            Logger.getLogger(ChatWebSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        session.close();
    }


}
