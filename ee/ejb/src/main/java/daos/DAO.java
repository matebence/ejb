package daos;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DAO<T, ID> {

    List<T> findAll() throws Exception;

    T findById(ID id) throws Exception;

    T save(T entity) throws Exception;

    void delete(T entity) throws Exception;
}