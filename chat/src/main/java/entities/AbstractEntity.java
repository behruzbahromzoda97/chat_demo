package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author Behruz Bahromzoda
 * at 09:42
 */
@MappedSuperclass
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id = null;

    // -------------------Getter and Setter ----------------
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}
