package managers;

import javax.ejb.ConcurrencyManagementType;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import services.RecordService;
import javax.ejb.LockType;
import entities.Record;

import javax.ejb.ConcurrencyManagement;
import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.inject.Inject;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.inject.Named;
import javax.ejb.Startup;
import javax.ejb.Lock;

@Startup
@Singleton
@LocalBean
@Named(value = "startupBean")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class StartupBean {

    @Inject
    private RecordService recordService;

    private Logger logger = Logger.getLogger(StartupBean.class);

    @PostConstruct
    @Lock(LockType.READ)
    public void init() {
        Record record = recordService.findLastAction();
        if (record == null) {
            logger.info("No last action found");
        }
    }

    @Lock(LockType.WRITE)
    @AccessTimeout(value = 15, unit = TimeUnit.MINUTES)
    public void clear() {
        recordService.clearLastActions();
    }
}
