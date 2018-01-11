import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for a collection of clusters.
 * It knows how to merge 2 clusters in the Hierarchical Clustering way.
 * @param <T> Type of elements in the clusters.
 */
public class ClustersCollection<T> {
    /**
     * Generic interface holding a function that calculates the distance between 2 elements.
     * @param <T> Type of elements
     */
    public interface ElementsRealDistanceCalculator<T> {
        double calculate(T e1, T e2);
    }

    /**
     * Generic interface holding a function that calculates the distance between 2 clusters.
     * @param <T> Type of the elements in the clusters
     */
    public interface ClustersRealDistanceCalculator<T> {
        double calculate(Cluster<T> c1, Cluster<T> c2, ElementsRealDistanceCalculator<T> calculator);
    }

    private List<Cluster<T>> clusters;
    private ElementsRealDistanceCalculator<T> eleCalc;
    private ClustersRealDistanceCalculator<T> clusCalc;

    /**
     * Ctor.
     * @param initial_elements Creates a different cluster from each element in this.
     * @param eleCalc Function to calculate distance between 2 elements from clusters
     * @param clusCalc Function to calculate distance between 2 clusters
     */
    public ClustersCollection(Collection<T> initial_elements, ElementsRealDistanceCalculator<T> eleCalc,
                              ClustersRealDistanceCalculator<T> clusCalc) {
        this.clusters = new LinkedList<>();
        for (T element : initial_elements) {
            List<T> tempList = new LinkedList<>();
            tempList.add(element);
            clusters.add(new Cluster<>(tempList));
        }
        this.eleCalc = eleCalc;
        this.clusCalc = clusCalc;
    }

    /**
     * @return The list of clusters.
     */
    public List<Cluster<T>> getClusters() {
        return clusters;
    }

    /**
     * @return how many clusters there are in the collection.
     */
    public int clustersNumber() {
        return clusters.size();
    }

    /**
     * Finds the 2 most similar clusters (the clusters with the minimal distance), and merges them into one cluster.
     */
    public void mergeTwoClosestClusters() {

        /* Find the 2 clusters with the minimum distances */
        int min_i = 0, min_j = 0;
        double min_distance = Double.POSITIVE_INFINITY;
        for (int i = 1; i < clusters.size(); i++) {
            for (int j = 0; j < i; j++) {
                double distance = clusCalc.calculate(clusters.get(i), clusters.get(j), eleCalc);
                if (distance < min_distance) {
                    min_distance = distance;
                    min_i = i;
                    min_j = j;
                }
            }
        }

        /* Remove the 2 clusters and add their merged cluster */
        Cluster<T> clus_i = clusters.get(min_i), clus_j = clusters.get(min_j);
        clusters.remove(min_i);
        if (min_j > min_i)
            min_j -= 1;
        clusters.remove(min_j);
        clusters.add(Cluster.merge(clus_i, clus_j));
    }

    /**
     * @return String repr of the clusters collection
     */
    @Override
    public String toString() {
        return "ClustersCollection" + clusters;
    }
}
