package service;

import entities.AbstractEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Behruz Bahromzoda
 * at 09:40
 */
@Stateless
@Transactional
public class PersistenceService {

    private static int count = 0;

    private static final Logger log = Logger.getLogger(PersistenceService.class.getName());

    @PersistenceContext(name = "chat_persistence_unit")
    private EntityManager entityManager;

    public PersistenceService() {
        count++;
        log.log(Level.INFO, " PersistenceService: {0} intilized\n now we have {1} PersistenceService", new Object[]{this.hashCode(), count});
    }

    public <T extends AbstractEntity> T saveOrUpdate(T entity) {
        if (entity.getId() != null) {
            entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
        return entity;

    }

    public <T extends AbstractEntity> Boolean delete(Class<T> clazz, BigInteger entityID) {
        Optional<T> en = findById(clazz, entityID);
        if (en.isPresent() && entityID.compareTo(BigInteger.ZERO) > 0) {
            entityManager.remove(en.get());
            return  true;
        }else {
            return false;
        }

    }

    public <T extends AbstractEntity> Optional<T> findById(Class<T> clazz, BigInteger entityID) {
        if (entityID.compareTo(BigInteger.ZERO) > 0) {
            try {
                return Optional.of(entityManager.find(clazz, entityID));
            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public void flush() {
        entityManager.flush();
    }


}
