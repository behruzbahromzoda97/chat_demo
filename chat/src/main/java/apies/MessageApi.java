package apies;

import entities.Message;
import entities.Room;
import entities.User;
import responses.ServerResponse;
import service.MainQueryService;
import service.PersistenceService;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

@Path("message")
public class MessageApi {

    @EJB
    PersistenceService persistenceService;
    @EJB
    MainQueryService mainQueryService;

    @Path("/newMessage")
    @POST()
    public Response newMessage(@QueryParam("userId") Integer userId, @QueryParam("room_id") Integer roomId, @QueryParam("message") String message) {
        ServerResponse response = new ServerResponse();
        Message newMessage = new Message();

        try {
            Optional<User> user = persistenceService.findById(User.class, BigInteger.valueOf( userId));
            Optional<Room> room = persistenceService.findById(Room.class, BigInteger.valueOf(roomId));
            if (user.isPresent() && room.isPresent()) {
                newMessage.setUser(user.get());
                newMessage.setRoom(room.get());
                newMessage.setText(message);
                persistenceService.saveOrUpdate(newMessage);
                response.setPayload(newMessage);
                response.setCode(200);
                response.setMessage("success");
            } else {
                response.setPayload(newMessage);
                response.setCode(400);
                response.setMessage("User or room was not found");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setPayload(newMessage);
            response.setCode(500);
            response.setMessage(exception.getMessage());

        }

        return Response.ok(response).build();

    }

    @Path("/getAllMessage")
    @POST()
    public Response getMessage(@QueryParam("room") Integer roomId, @QueryParam("user") Integer userId) {
        ServerResponse response = new ServerResponse();
        try {
            Optional<User> userOptional = persistenceService.findById(User.class, BigInteger.valueOf(roomId));
            Optional<Room> roomOptional = persistenceService.findById(Room.class, BigInteger.valueOf(userId));
            if (roomOptional.isPresent() && userOptional.isPresent()) {

                Optional<List<Message>> message = mainQueryService.getAllMessage(roomOptional.get(), userOptional.get());
                if (message.isPresent()) {
                    response.setMessage("AllMessage");
                    response.setCode(200);
                    response.setPayload(message);

                } else {
                    response.setMessage("Message not found");
                    response.setCode(400);
                    response.setPayload(null);
                }

            } else {
                response.setMessage("Room or user was not found");
                response.setCode(400);
                response.setPayload(null);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setMessage(ex.getMessage());
            response.setCode(500);
            response.setPayload(null);
        }
        return Response.ok(response).build();
    }

    @Path("/deleteMessage")
    @POST()
    public Response deleteMessage(@QueryParam("messageId") Integer messageId) {
        ServerResponse response = new ServerResponse();
        try {
            Boolean deleteResult = persistenceService.delete(Room.class, BigInteger.valueOf(messageId));
            if (deleteResult) {
                response.setCode(200);
                response.setMessage("Message was deleted");

            } else {
                response.setCode(400);
                response.setMessage("Some thing is wrong");
            }
            response.setPayload(null);

        } catch (Exception exception) {
            response.setCode(500);
            response.setMessage(exception.getMessage());
            response.setPayload(null);

        }

        return Response.ok(response).build();
    }

}
