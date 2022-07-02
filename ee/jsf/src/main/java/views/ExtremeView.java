package views;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import services.ExtremeService;
import services.CarService;
import java.util.List;
import entities.Car;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named(value = "extremeView")
public class ExtremeView {

    @Inject
    //@EJB
    private ExtremeService extremeService;

    @Inject
    //@EJB
    private CarService carService;

    @Getter
    @Setter
    private Car car;

    private Logger logger = Logger.getLogger(ExtremeView.class);

    public void createNewCar() {
        Car car = Car.builder().brand("YX").type("X").build();
        carService.save(car);
        logger.info(car);
    }

    public void start() {
        try {
            Future<List<Car>> future = extremeService.getAll();
            while (!future.isDone()) {
                this.car = future.get().get(0);
                Thread.sleep(2500);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e);
        }
    }
}
