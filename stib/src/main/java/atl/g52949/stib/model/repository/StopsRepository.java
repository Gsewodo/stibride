package atl.g52949.stib.model.repository;

import atl.g52949.stib.model.dao.StopsDao;
import atl.g52949.stib.model.dto.StopsDto;
import atl.g52949.stib.model.utlils.RepositoryException;
import java.util.List;



public class StopsRepository implements Repository<Integer, StopsDto> {

    private final StopsDao dao;

    public StopsRepository() throws RepositoryException {
        dao = StopsDao.getInstance();
    }

    StopsRepository(StopsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<StopsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopsDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    //@Override
    public StopsDto get(String stationName) throws RepositoryException {
        return dao.select(stationName);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        var results = dao.select(key);
        return results != null;
    }

    //@Override
    public boolean contains(String stationName) throws RepositoryException {
        var results = dao.select(stationName);
        return results != null;
    }

}
