package services;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import java.util.Set;
import entities.Car;
import daos.DAO;

import javax.transaction.TransactionScoped;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Stateless
@LocalBean
@Named(value = "carService")
public class CarService {

    @Inject
    @Named(value = "carDAO")
    private DAO<Car, Long> carDAO;

    private Logger logger = Logger.getLogger(CarService.class);

    public Car findById(Long id) {
        Car car = new Car();
        try {
            car = carDAO.findById(id);
        } catch (Exception e) {
            logger.error(e);
        }
        return car;
    }

    @TransactionScoped
    //The javax.transaction.TransactionScoped annotation provides the ability to specify a standard CDI scope
    // to define bean instances whose lifecycle is scoped to the currently active JTA transaction.
    public Car save(Car entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Car>> violations = validator.validate(entity);
        for (ConstraintViolation<Car> violation : violations) {
            logger.info(violation.getMessage());
        }

        Car car = new Car();
        try {
            car = carDAO.save(entity);
        } catch (Exception e) {
            logger.error(e);
        }
        return car;
    }

    //Because @TransactionManagement(TransactionManagementType.BEAN)
    //@Transactional is not possible
    public void delete(Car entity) {
        try {
            carDAO.delete(entity);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
