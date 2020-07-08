package service;

import entities.Message;
import entities.Room;
import entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Behruz Bahromzoda
 * at 09:40
 */
@Stateless
@Transactional
public class MainQueryService {

    private static int count = 0;

    private static final Logger log = Logger.getLogger(PersistenceService.class.getName());

    @PersistenceContext(name = "chat_persistence_unit")
    private EntityManager entityManager;


    public MainQueryService() {
        count++;
        log.log(Level.INFO, " PersistenceService: {0} intilized\n now we have {1} PersistenceService", new Object[]{this.hashCode(), count});
    }

    public Optional<List<Message>> getAllMessage(Room room, User user) {
        try {
            return Optional.ofNullable(entityManager.createNamedQuery(Message.GET_ALL_MESSAGE_BY_ROOM_USER, Message.class)
                    .setParameter("room", room)
                    .setParameter("user", user)
                    .getResultList());

        } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<List<User>> getAllUsers() {

        try {
            return Optional.ofNullable(entityManager.createNamedQuery(User.GET_ALL_USERS, User.class).getResultList());
        } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.empty();
        }


    }

    public List<Room> getRoomByUsers(User firstUser, User secondUser) {
        try {
            return entityManager.createNamedQuery(Room.GET_ROOM_BY_USERS, Room.class)
                    .setParameter("firstUser", firstUser)
                    .setParameter("secondUser", secondUser)
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public Optional<User> optionalUser(String username){

        try{
            return Optional.ofNullable(entityManager.createNamedQuery(User.GET_USER_BY_USERNAME, User.class)
                    .setParameter("username", username)
                    .getSingleResult());

        }catch (Exception e){
            return  Optional.empty();
        }

    }

    public Optional<User> userOptional(Integer id){
        try{
            return Optional.ofNullable(entityManager.createNamedQuery(User.GET_USER_BY_ID, User.class)
                    .setParameter("userId", id)
                    .getSingleResult());

        }catch (Exception e){
            return  Optional.empty();
        }

    }

    public Optional<Room> roomOptional(Integer id){
        try{
            return Optional.ofNullable(entityManager.createNamedQuery(Room.FIND_ROOM_BY_ID, Room.class)
                    .setParameter("roomId", id)
                    .getSingleResult());

        }catch (Exception e){
            return  Optional.empty();
        }

    }



}