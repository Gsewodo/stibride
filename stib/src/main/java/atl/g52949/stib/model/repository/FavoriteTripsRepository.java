package atl.g52949.stib.model.repository;

import atl.g52949.stib.model.dao.FavoriDao;
import atl.g52949.stib.model.dto.FavoriDto;
import atl.g52949.stib.model.utlils.RepositoryException;
import java.util.List;

public class FavoriteTripsRepository implements Repository<Integer, FavoriDto> {

    private final FavoriDao dao;

    public FavoriteTripsRepository() throws RepositoryException {
        dao = FavoriDao.getInstance();
    }

    @Override
    public List<FavoriDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FavoriDto get(Integer key) throws RepositoryException {
        FavoriDto result = dao.select(key);
        return result;
    }

    public FavoriDto get(String origin, String destination) throws RepositoryException {
        FavoriDto result = dao.select(origin, destination);
        return result;
    }

    public FavoriDto get(FavoriDto item) throws RepositoryException {
        FavoriDto result = dao.select(item);
        return result;
    }

    public Integer add(FavoriDto item) throws RepositoryException {
        Integer key = item.getKey();
        if (!contains(item)) {
            key = dao.insert(item);
        }
        return key;
    }

    public void update(FavoriDto item) throws RepositoryException {
        //Integer key = item.getKey();
        //if (contains(key) && !contains(item)) {
            dao.update(item);
        //}
    }

    public void delete(Integer key) throws RepositoryException {
        dao.delete(key);
    }

    public void delete(FavoriDto item) throws RepositoryException {
        dao.delete(item);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        FavoriDto results = dao.select(key);
        return results != null;
    }

    public boolean contains(FavoriDto item) throws RepositoryException {
        FavoriDto results = dao.select(item);
        return results != null;
    }

}