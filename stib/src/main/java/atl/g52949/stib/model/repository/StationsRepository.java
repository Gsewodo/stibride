package atl.g52949.stib.model.repository;

import atl.g52949.stib.model.dao.StationsDao;
import atl.g52949.stib.model.dto.StationsDto;
import atl.g52949.stib.model.utlils.RepositoryException;
import java.util.List;


public class StationsRepository implements Repository<Integer, StationsDto> {

    private final StationsDao dao;

    public StationsRepository() throws RepositoryException {
        dao = StationsDao.getInstance();
    }

    StationsRepository(StationsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<StationsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StationsDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    //@Override
    public StationsDto get(String stationName) throws RepositoryException {
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
