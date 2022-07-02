package services;

import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import javax.ejb.AsyncResult;
import java.util.Collections;
import java.util.List;
import entities.Car;
import daos.DAO;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Stateless
@LocalBean
@Asynchronous
@Named(value = "extremeService")
public class ExtremeService {

    @Inject
    @Named(value = "carDAO")
    private DAO<Car, Long> carDAO;

    private Logger logger = Logger.getLogger(ExtremeService.class);

    public Future<List<Car>> getAll() {
        List<Car> cars = Collections.emptyList();
        try {
            cars = carDAO.findAll();
            Thread.sleep(10000);
        } catch (Exception e) {
            logger.error(e);
        }
        return new AsyncResult<>(cars);
    }
}
