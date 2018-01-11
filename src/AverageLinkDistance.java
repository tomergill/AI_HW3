import java.util.LinkedList;
import java.util.List;

/**
 * Class holding a function that calculates the distance between clusters in a Average Link way:
 * dist(c1, c2) := Average({dist(p1, p2) where p1 in c1, p2 in c2}).
 *
 * @param <T> The type of the elements in the clusters
 */
public class AverageLinkDistance<T> implements ClustersCollection.ClustersRealDistanceCalculator<T> {

    /**
     * Calculates the distance between clusters in a Average Link way
     *
     * @param c1         One of the clusters to calculate the distance between
     * @param c2         The second cluster to calculate the distance between.
     * @param calculator A function that calculates the distance between 2 elements.
     * @return The average of all the distances between a point from c1 to a point in c2.
     */
    @Override
    public double calculate(Cluster<T> c1, Cluster<T> c2,
                            ClustersCollection.ElementsRealDistanceCalculator<T> calculator) {
        List<Double> distances = new LinkedList<>();
        for (T p1 : c1.getElements()) {
            for (T p2 : c2.getElements()) {
                distances.add(calculator.calculate(p1, p2));
            }
        }
        return distances.stream().reduce(0.0, (x, y) -> x + y) / (double) distances.size();
    }
}
