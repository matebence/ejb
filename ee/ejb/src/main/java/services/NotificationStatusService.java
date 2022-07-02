package services;

import org.apache.log4j.Logger;
import entities.TimerEvent;

import javax.enterprise.event.Observes;
import javax.annotation.Priority;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Stateless
@LocalBean
@Named("crashNotificationBot")
public class NotificationStatusService {

    private Logger logger = Logger.getLogger(NotificationStatusService.class);

    public void action(@Observes @Priority(1) TimerEvent timerEvent) {
        logger.info(timerEvent);
    }
}
