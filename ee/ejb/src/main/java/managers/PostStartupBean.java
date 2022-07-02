package managers;

import javax.ejb.ConcurrencyManagementType;
import org.apache.log4j.Logger;

import javax.ejb.ConcurrencyManagement;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.inject.Named;
import javax.ejb.Startup;

@Startup
@Singleton
@LocalBean
@DependsOn("StartupBean")
@Named(value = "postStartupBean")
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PostStartupBean {

    private Logger logger = Logger.getLogger(PostStartupBean.class);

    private boolean state = true;

    @PostConstruct
    public void init() {
        logger.info("This will be executed only after StartupBean");
    }

    private synchronized void setState () {
        state = false;
    }
}