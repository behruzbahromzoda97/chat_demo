package entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
@NamedQuery(name = Room.GET_ROOM_BY_USERS, query = "select r from Room  r where :firstUser member of r.users and :secondUser member of r.users")
@NamedQuery(name = Room.FIND_ROOM_BY_ID, query = "select  r from  Room  r where r.id=:roomId ")
public class Room extends AbstractEntity implements Serializable {

    public static final String GET_ROOM_BY_USERS = "GET_ROOM_BY_USERS";
    public static final String FIND_ROOM_BY_ID = "FIND_ROOM_BY_ID";

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "room_users",
            joinColumns = {
                    @JoinColumn(name = "room_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})

    private Set<User> users = new HashSet<>();
    @Column(name = "created_at")
    private LocalDateTime created_at = LocalDateTime.now();

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @JsonbTransient
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
