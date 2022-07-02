package schedulers;

import org.apache.log4j.Logger;
import services.RecordService;
import entities.Record;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ejb.Schedule;
import javax.inject.Named;

@LocalBean
@Stateless
@Named(value = "status")
public class Status {

    @Inject
    private RecordService recordService;

    private Logger logger = Logger.getLogger(Status.class);

    @Schedule(hour = "*", minute = "*", second = "*/5", info = "Every 5 seconds timer")
    public void check() {
        Record record = recordService.findLastAction();
        if (record == null) {
            logger.info("No last action found");
        } else {
            logger.info(record);
        }
    }
}
