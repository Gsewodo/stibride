package atl.g52949.stib.model.Djikstra;

import atl.g52949.stib.config.ConfigManager;
import atl.g52949.stib.model.dto.FavoriDto;
import atl.g52949.stib.model.dto.StationsDto;
import atl.g52949.stib.model.repository.FavoriteTripsRepository;
import atl.g52949.stib.model.repository.StationsRepository;
import atl.g52949.stib.model.utlils.RepositoryException;
import atl.g52949.stib.utils.observer.Observable;
import atl.g52949.stib.utils.observer.Observer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * The PathFinder class is used to find the shortest path between two stations
 * but also to get update and delete the favorite trips of the user.
 * 
 */
public class PathFinder extends Observable implements Model {

    private Network network;

    private List<StationNode> shortestPath;

    public PathFinder() throws RepositoryException {
        network = new Network();
        shortestPath = new LinkedList<>();
    }
    
    @Override
    public Network getNetwork() {
        return network;
    }

    @Override
    public List<String> getStations(){
        List<String> stations = new ArrayList<>();

        try {
            ConfigManager.getInstance().load();
            String dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);

            StationsRepository repository = new StationsRepository();
            List<StationsDto> dtos = repository.getAll();

            for (StationsDto dto : dtos) {
                stations.add(dto.getName());
            }

        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        } catch (RepositoryException ex) {
            System.out.println("Erreur Repository " + ex.getMessage());
        }

        return stations;
    }

    @Override
    public void search(int idOrigin, int idDestination) {
        StationGraph graph = network.getGraphStations();

        StationNode source = graph.search(idDestination);
        Dijkstra.calculateShortestPathFromSource(graph, source);
        shortestPath = graph.search(idOrigin).getShortestPath();
        System.out.println("Taille du plus cours chemin " + shortestPath.size());
        notifyObservers();
        // graph.clearResearch();
    }

    @Override
    public void search(String origin, String destination) {
        network.getGraphStations().clearResearch();
        StationGraph graph = network.getGraphStations();

        StationNode destinationNode = graph.search(destination);

        graph = Dijkstra.calculateShortestPathFromSource(graph, destinationNode);

        StationNode originNode = graph.search(origin);
        shortestPath = new LinkedList<>(originNode.getShortestPath());

        System.out.println("source =" + destinationNode.getStation().getName());
        System.out.println("origin =" + graph.search(origin).getStation().getName());
        System.out.println("Taille du plus cours chemin " + shortestPath.size());
        notifyObservers();
    }

    @Override
    public List<StationsDto> getSearchResult() {
        List<StationsDto> path = new ArrayList<>();

        for (StationNode node : shortestPath) {
            StationsDto station = node.getStation();
            path.add(station);
        }

        return path;
    }

    public List<FavoriDto> getFavoriteTrips() {
        List<FavoriDto> favoriteTrips = new ArrayList<>();

        try {
            ConfigManager.getInstance().load();
            String dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);

            FavoriteTripsRepository repository = new FavoriteTripsRepository();
            List<FavoriDto> dtos = repository.getAll();

            for (FavoriDto dto : dtos) {
                favoriteTrips.add(dto);
            }

        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        } catch (RepositoryException ex) {
            System.out.println("Erreur Repository " + ex.getMessage());
        }
        return favoriteTrips;
    }

    public void addFavoriteTrip(String origin, String destination) {
        FavoriDto dto = null;
        try {
            ConfigManager.getInstance().load();
            String dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);

            FavoriteTripsRepository repository = new FavoriteTripsRepository();
            repository.add(new FavoriDto(0, origin, destination));

        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        } catch (RepositoryException ex) {
            System.out.println("Erreur Repository " + ex.getMessage());
        }
    }

    public void updateFavoriteTrip(int key, String origin, String destination) {
        FavoriDto dto = new FavoriDto(key, origin, destination);
        try {
            ConfigManager.getInstance().load();
            String dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);

            FavoriteTripsRepository repository = new FavoriteTripsRepository();
            repository.update(dto);

        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        } catch (RepositoryException ex) {
            System.out.println("Erreur Repository " + ex.getMessage());
        }
    }

    public void deleteFavoriteTrip(FavoriDto trip) {
        FavoriDto dto = trip;
        try {
            ConfigManager.getInstance().load();
            String dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);

            FavoriteTripsRepository repository = new FavoriteTripsRepository();
            repository.delete(dto);

        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        } catch (RepositoryException ex) {
            System.out.println("Erreur Repository " + ex.getMessage());
        }
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }
}
