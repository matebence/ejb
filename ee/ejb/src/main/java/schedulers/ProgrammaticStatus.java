package schedulers;

import javax.enterprise.event.Event;
import javax.ejb.TimerService;
import entities.TimerEvent;
import javax.ejb.Timer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ejb.Timeout;

@LocalBean
@Stateless
@Named(value = "programmaticStatus")
public class ProgrammaticStatus {

    @Inject
    private Event<TimerEvent> event;

    @Resource
    private TimerService timerService;

    @PostConstruct
    public void initialize() {
        timerService.createTimer(0, 5000, "Every five second timer");
    }

    @Timeout
    public void programmaticTimeout(Timer timer) {
        event.fire(new TimerEvent(timer.getInfo().toString()));
    }
}
