package apies;


import entities.Room;
import entities.User;
import responses.ServerResponse;
import service.MainQueryService;
import service.PersistenceService;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Path("room")
public class RoomApi {
    @EJB
    MainQueryService mainQueryService;
    @EJB
    PersistenceService persistenceService;

    @Path("createRoom")
    @POST()
    public Response createRoom(@QueryParam("firstUserId") Integer firstUserId, @QueryParam("secondUserId") Integer secondUserId) {
        ServerResponse serverResponse = new ServerResponse();

        try {
            Optional<User> firstUser = persistenceService.findById(User.class, BigInteger.valueOf(firstUserId));
            Optional<User> secondUser = persistenceService.findById(User.class, BigInteger.valueOf(secondUserId));
            if (firstUser.isPresent() && secondUser.isPresent()) {
                List<Room> usersRoom = mainQueryService.getRoomByUsers(firstUser.get(), secondUser.get());
                serverResponse.setCode(200);
                if (usersRoom.isEmpty()) {
                    Room chat = new Room();
                    Set<User> userSet = new HashSet<>();
                    userSet.add(firstUser.get());
                    userSet.add(secondUser.get());
                    chat.setCreated_at(LocalDateTime.now());
                    chat.setUsers(userSet);
                    chat = persistenceService.saveOrUpdate(chat);
                    serverResponse.setMessage("Room successFully created");
                    serverResponse.setPayload(chat);

                } else {
                    serverResponse.setMessage("Room successFully created");
                    serverResponse.setPayload(usersRoom);

                }
            } else {
                serverResponse.setPayload(null);
                serverResponse.setCode(400);
                serverResponse.setMessage("one of user was not found");
            }


        } catch (Exception exception) {
            serverResponse.setPayload(null);
            serverResponse.setCode(500);
            serverResponse.setMessage(exception.getMessage());
        }

        return Response.ok(serverResponse).build();
    }


    @Path("/deleteRoom")
    @POST()
    public Response deleteRoom(@QueryParam("roomId") Integer roomId) {
        ServerResponse response = new ServerResponse();
        try {
            Boolean deleteResult = persistenceService.delete(Room.class, BigInteger.valueOf(roomId));
            if (deleteResult) {
                response.setCode(200);
                response.setMessage("Room was deleted ");
                response.setPayload(null);

            } else {
                response.setCode(400);
                response.setMessage("Some thing is wrong ");
                response.setPayload(null);
            }

        } catch (Exception exception) {
            response.setCode(500);
            response.setMessage(exception.getMessage());
            response.setPayload(null);
        }
        return Response.ok(response).build();
    }

    @Path("/findRoom")
    @POST()
    public Response findRoomById(@QueryParam("roomId") Integer roomId) {
        Optional<Room> roomOptional = persistenceService.findById(Room.class, BigInteger.valueOf(2));

        return Response.ok(roomOptional).build();


    }


}
 