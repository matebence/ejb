package views;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import services.RecordService;
import java.io.Serializable;
import entities.Record;

import javax.enterprise.context.SessionScoped;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@SessionScoped
@Named(value = "recordView")
public class RecordView implements Serializable {

    @Inject
    //@EJB
    private RecordService recordService;

    @Getter
    @Setter
    private Record record;

    private Logger logger = Logger.getLogger(RecordView.class);

    @PostConstruct
    public void onCreate() {
        this.record = recordService.findById(1L);
    }

    @PreDestroy
    public void onDestroy() {
        this.record = null;
    }

    public void createNewRecord() {
        Record record = Record.builder().name("Y").artist("X").build();
        recordService.save(record);
        this.record = record;
    }

    public void deleteCurrentSession() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(true);
        session.invalidate();
    }

    public void logLastRecord() {
        Record record = recordService.findLastAction();
        this.record = record;
        logger.info(record);
    }

    public void deleteRecord() {
        recordService.delete(this.record);
    }
}