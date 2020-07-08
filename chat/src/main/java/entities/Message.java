package entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message")
@NamedQuery(name = Message.GET_ALL_MESSAGE_BY_ROOM_USER, query = "select m from Message m where m.room=:room and m.user=:user")

public class Message extends AbstractEntity implements Serializable {

    public static final String GET_ALL_MESSAGE_BY_ROOM_USER = "GET_ALL_MESSAGE_BY_ID";


    @Column(name = "text", nullable = false, length = 3000)
    private String text;

    @ManyToOne
    private User user;
    @ManyToOne
    private Room room;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonbTransient
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @JsonbTransient
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
