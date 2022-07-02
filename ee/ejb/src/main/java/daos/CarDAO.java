package daos;

import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import entities.Car;

import javax.persistence.PersistenceContext;
import javax.ejb.TransactionManagement;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@LocalBean
@Stateless
@Named(value = "carDAO")
@TransactionManagement(TransactionManagementType.BEAN)
public class CarDAO implements DAO<Car, Long> {

    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Car findById(Long id) {
        return entityManager.find(Car.class, 1L);
    }

    @Override
    public List<Car> findAll() {
        TypedQuery<Car> query = entityManager.createNamedQuery("Car.findAll", Car.class);
        return query.getResultList();
    }

    @Override
    public Car save(Car entity) throws Exception {
        Car car = null;
        try{
            userTransaction.begin();
            entityManager.persist(entity);
            car = findById(entity.getId());
            userTransaction.commit();
        } catch (NullPointerException e) {
            userTransaction.commit();
        } catch (IllegalArgumentException e) {
            userTransaction.rollback();
        }
        return car;
    }

    @Override
    public void delete(Car entity) throws Exception {
        try{
            userTransaction.begin();
            Car car = findById(entity.getId());
            entityManager.remove(car);
            userTransaction.commit();
        } catch (NullPointerException e) {
            userTransaction.commit();
        } catch (IllegalArgumentException e) {
            userTransaction.rollback();
        }
    }
}
