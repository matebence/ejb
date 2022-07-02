package daos;

import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import entities.Record;
import java.util.List;

import javax.persistence.PersistenceContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionAttribute;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@LocalBean
@Stateless
@Named(value = "recordDAO")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RecordDAO implements DAO<Record, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Record findById(Long id) {
        return entityManager.find(Record.class, 1L);
    }

    @Override
    public List<Record> findAll() {
        TypedQuery<Record> query = entityManager.createNamedQuery("Record.findAll", Record.class);
        return query.getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Record save(Record entity) {
        entityManager.persist(entity);
        return findById(entity.getId());
    }

    @Override
    public void delete(Record entity) {
        entityManager.remove(entity);
    }
}
