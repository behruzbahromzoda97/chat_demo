package apies;

import entities.User;
import responses.ServerResponse;
import service.MainQueryService;
import service.PersistenceService;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Path("user")
public class UserApi {

    @EJB
    PersistenceService persistenceService;
    @EJB
    MainQueryService mainQueryService;

    @POST
    @Path("/newUser")
    public Response newUser(@NotNull @Valid User user) {
        ServerResponse serverResponse = new ServerResponse();
        try {
            user = persistenceService.saveOrUpdate(user);
            serverResponse.setPayload(user);
            serverResponse.setCode(200);
            serverResponse.setMessage("User was successFully created");
        } catch (Exception exception) {
            serverResponse.setMessage("Error some thing is wrong");
            serverResponse.setCode(400);
        }
        return Response.ok(serverResponse).build();
    }

    @Path("/deleteUser")
    @POST
    public Response deleteUser(@QueryParam("userId") Integer userId) {
        ServerResponse response = new ServerResponse();

        try {
            Optional<User> user = persistenceService.findById(User.class, BigInteger.valueOf(userId));
            if (user.isPresent()) {
                Boolean deleteUser = persistenceService.delete(User.class, BigInteger.valueOf(userId));
                response.setCode(200);
                if (deleteUser) {
                    response.setMessage("User  successFully deleted");
                } else {
                    response.setMessage("Some think is wrong");
                }
            } else {

                response.setCode(400);
                response.setMessage("User was not found");
            }

        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setCode(500);

        }
        return Response.ok(response).build();
    }

    @Path("/updateUser")
    @POST
    public Response updateUser(@NotNull @Valid User user) {
        ServerResponse response = new ServerResponse();
        try {
            Optional<User> findUser = persistenceService.findById(User.class, BigInteger.valueOf(24));
            if (findUser.isPresent()) {
                user = persistenceService.saveOrUpdate(user);
                response.setMessage("User successFully updated");
                response.setCode(200);
                response.setPayload(user);
            } else {
                response.setCode(400);
                response.setPayload(new Object());
                response.setMessage("User was not found");
            }


        } catch (Exception exception) {

            response.setCode(500);
            response.setPayload(new Object());
            response.setMessage(exception.getMessage());

        }
        return Response.ok(response).build();
    }

    @Path("/getAllUser")
    @GET()
    public Response getAllUser() {
        ServerResponse serverResponse = new ServerResponse();
        try {
            Optional<List<User>> userList = mainQueryService.getAllUsers();
            if (userList.isPresent()) {
                serverResponse.setPayload(userList);
                serverResponse.setCode(200);
                serverResponse.setMessage("Success");

            } else {
                serverResponse.setCode(400);
                serverResponse.setMessage("Any user not found");

            }

        } catch (Exception exception) {
            serverResponse.setCode(500);
            serverResponse.setMessage(exception.getMessage());

        }
        Optional<List<User>> userList = mainQueryService.getAllUsers();

        return Response.ok(serverResponse).build();
    }

    @Path("/getUserByUsername")
    @POST()
    public Response getUserByUsername(@QueryParam("username") String username) {
        ServerResponse response = new ServerResponse();
        try {
            Optional<User> userOptional = mainQueryService.optionalUser(username);
            response.setCode(200);
            response.setPayload(userOptional);
            response.setMessage("User was successFully");

        } catch (Exception exception) {
           response.setMessage(exception.getMessage());
           response.setPayload(null);
           response.setCode(400);
        }
        return Response.ok(response).build();

    }
}
