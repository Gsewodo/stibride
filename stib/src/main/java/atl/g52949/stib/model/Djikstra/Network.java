package atl.g52949.stib.model.Djikstra;

import atl.g52949.stib.config.ConfigManager;
import atl.g52949.stib.model.dto.StationsDto;
import atl.g52949.stib.model.dto.StopsDto;
import atl.g52949.stib.model.repository.StationsRepository;
import atl.g52949.stib.model.repository.StopsRepository;
import atl.g52949.stib.model.utlils.RepositoryException;
import java.io.IOException;
import java.util.List;


/**
 * The Network class creates a graph of stations
 * 
 */
public class Network {

    private StationGraph graphStations;

    public Network(StationGraph graphStations) {
        this.graphStations = graphStations;
    }

    public Network() throws RepositoryException {
        this.graphStations = new StationGraph();
        initGraph();
        initDestinations();
    }

    /**
     * It creates a graph of stations.
     */
    public void initGraph() throws RepositoryException {
        try {
            ConfigManager.getInstance().load();
            String dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);

            StationsRepository repository = new StationsRepository();
            List<StationsDto> dtos = repository.getAll();

            for (StationsDto dto : dtos) {
                graphStations.addNode(new StationNode(dto));
            }

        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        } catch (RepositoryException ex) {
            System.out.println("Erreur Repository " + ex.getMessage());
        }
    }

    /**
     * - For each stop in the database, we check if the next stop is on the same line and if the order
     * is one less. 
     * - If it is, we add the destination to the list of destinations for the origin
     */
    private void initDestinations() {
        try {
            ConfigManager.getInstance().load();
            String dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);

            StopsRepository repository = new StopsRepository();
            List<StopsDto> dtos = repository.getAll();

            for (int i = 0; i < dtos.size(); i++) {
                if (i == dtos.size() - 1) {
                    break;
                }

                int originStationKey = dtos.get(i).getStationKey();
                int originLine = dtos.get(i).getLine();
                int originOrder = dtos.get(i).getKey();

                int destinationStationKey = dtos.get(i + 1).getStationKey();
                int destinationLine = dtos.get(i + 1).getLine();
                int destinationOrder = dtos.get(i + 1).getKey();

                if (originLine == destinationLine && originOrder == destinationOrder - 1) {
                    addDestination(originStationKey, destinationStationKey);
                }
            }

        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        } catch (RepositoryException ex) {
            System.out.println("Erreur Repository " + ex.getMessage());
        }
    }

    /**
     * Add a destination to the graph
     * 
     * @param originStationKey The station key of the origin station.
     * @param destinationStationKey The station key of the destination station.
     */
    private void addDestination(int originStationKey, int destinationStationKey) {
        StationNode originNode = graphStations.search(originStationKey);
        StationNode destinationNode = graphStations.search(destinationStationKey);

        if (!originNode.containsDestination(destinationNode)) {
            originNode.addDestination(destinationNode, 1);
        }

        if (!destinationNode.containsDestination(originNode)) {
            destinationNode.addDestination(originNode, 1);
        }
    }

    /**
     * Returns the graph of stations
     * 
     * @return The graph of stations.
     */
    public StationGraph getGraphStations() {
        return graphStations;
    }

   /**
    * The function sets the graphStations variable to the graph passed in
    * 
    * @param graphStations The graph of stations.
    */
    public void setGraphStations(StationGraph graphStations) {
        this.graphStations = graphStations;
    }
}
