import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Class for a generic semi-immutable cluster, holding a list of elements.
 *
 * @param <T> Type of the elements in the cluster.
 */
public class Cluster<T> {
    private List<T> elements;

    /**
     * Ctor.
     *
     * @param elements The elements in the cluster.
     */
    public Cluster(List<T> elements) {
        if (elements != null)
            this.elements = elements;
        else
            this.elements = new LinkedList<>();  // empty list
    }

    /**
     * @return The actual list of the elements in the cluster.
     */
    public List<T> getElements() {
        return elements;
    }

    /**
     * @return how many elements are in the cluster.
     */
    public int size() {
        return elements.size();
    }

    /**
     * Checks if the other object is a the same cluster as this (has the same elements).
     *
     * @param o Other object to check.
     * @return true if equals. false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cluster<?> cluster = (Cluster<?>) o;
        return elements.size() == cluster.elements.size() && elements.containsAll(cluster.getElements());
    }

    /**
     * generic hashCode func.
     *
     * @return hashCode of the elements' list.
     */
    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    /**
     * @return string repr of this cluster.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (T element : elements) {
            builder.append(element.toString());
            builder.append(", ");
        }
        return "Cluster{" +
                builder.substring(0, builder.length() - 2) +
                '}';
    }

    /**
     * Create a new cluster from the 2 clusters given
     *
     * @param cluster1 One of the clusters to merge.
     * @param cluster2 Second cluster to merge.
     * @param <T>      Type of elements in the cluster.
     * @return The new merged cluster.
     */
    public static <T> Cluster<T> merge(Cluster<T> cluster1, Cluster<T> cluster2) {
        LinkedList<T> list = new LinkedList<>(cluster1.elements);
        list.addAll(cluster2.elements);
        return new Cluster<>(list);
    }

    /**
     * @param element Element to check if contained in cluster
     * @return true if the element is in the cluster, otherwise false.
     */
    public boolean contains(T element) {
        return elements.contains(element);
    }
}
