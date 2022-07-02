package views;

import org.apache.log4j.Logger;
import services.CarService;
import entities.Car;

import javax.enterprise.context.RequestScoped;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named(value = "carView")
public class CarView {

    @Inject
    //@EJB
    private CarService carService;

    @Getter
    @Setter
    private Car car;

    private Logger logger = Logger.getLogger(CarView.class);

    @PostConstruct
    public void onCreate() {
        this.car = carService.findById(1L);
    }

    @PreDestroy
    public void onDestroy() {
        this.car = null;
    }

    public void createNewCar() {
        Car car = Car.builder().brand("Y").type("X").build();
        carService.save(car);
        logger.info(car);
        this.car = car;
    }

    public void deleteCar() {
        carService.delete(car);
    }
}
