package services;

import org.apache.log4j.Logger;
import java.util.LinkedHashMap;
import java.io.Serializable;
import java.util.ArrayList;
import entities.Record;
import java.util.List;
import java.util.Map;
import daos.DAO;

import javax.transaction.Transactional;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.ejb.Stateful;
import javax.inject.Named;

@Stateful
@LocalBean
@Named(value = "recordService")
public class RecordService implements Serializable {

    @Inject
    @Named(value = "recordDAO")
    private DAO<Record, Long> recordDAO;

    private Map<String, Record> history = new LinkedHashMap<>();

    private Logger logger = Logger.getLogger(RecordService.class);

    public Record findLastAction() {
        List<Map.Entry<String, Record>> entryList = new ArrayList<>(history.entrySet());
        if (entryList.isEmpty()) {
            return null;
        } else {
            return entryList.get(entryList.size() - 1).getValue();
        }
    }

    public void clearLastActions() {
        history.clear();
    }

    @Transactional
    public Record findById(Long id) {
        Record record = new Record();
        try {
            record = recordDAO.findById(id);
        } catch (Exception e) {
            logger.error(e);
        }
        history.put("find", record);
        return record;
    }

    public Record save(Record entity) {
        //If entity is not valid based on bean validation exception will be trowed
        Record record = new Record();
        try {
            record = recordDAO.save(entity);
        } catch (Exception e) {
            logger.error(e);
        }
        history.put("save", record);
        return record;
    }

    @Transactional
    public void delete(Record entity) {
        try {
            Record record = recordDAO.findById(entity.getId());
            recordDAO.delete(record);
        } catch (Exception e) {
            logger.error(e);
        }
        history.put("delete", entity);
    }
}
