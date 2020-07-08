package tj.behruz.chat.resources;

import entities.Room;
import entities.User;
import service.PersistenceService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

/**
 * @author
 */
@Path("javaee8")
public class JavaEE8Resource {
    @EJB
    PersistenceService persistenceService;


    @GET
    public Response ping() {

        return Response
                .ok("ping")
                .build();
    }
}
