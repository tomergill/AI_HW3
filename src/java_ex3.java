import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Main class.
 */
public class java_ex3 {

    /**
     * Reads the input file, makes the clusters, merges them until the there are the requested number of clusters
     * there and then writes to the output file the cluster number of each point in order read from the input file.
     * Also prints the clusters at the end to STDOUT.
     *
     * @param args If the 1st argument is specified, it is treated as the path to the input file. And if a 2nd
     *             argument is specified, it is treated as the path to the output file.
     */
    public static void main(String[] args) {

        /* Determining the input and output files paths */
        String input_file_path = "input.txt", output_file_path = "output.txt";
        if (args != null && args.length >= 1) {
            input_file_path = args[0];
            if (args.length >= 2)
                output_file_path = args[1];
        }

        /* Reading the input file */
        PointsFileReader reader = null;
        try {
            reader = new PointsFileReader(input_file_path);
        } catch (IOException e) {
            System.out.println("Error reading file " + input_file_path + ":");
            e.printStackTrace();
            System.exit(1);
        }


        ClustersCollection.ClustersRealDistanceCalculator<Point> clusCalc;

        /* Determine the method to calculate the distance between clusters */
        switch (reader.getDistance_method()) {
            default:
                clusCalc = new AverageLinkDistance<>();
                break;
            case "single link":
                clusCalc = new SingleLinkDistance<>();
                break;
        }

        int max_clusters = reader.getClusters_num();  // how many clusters we want the end

        /* Creating the ClustersCollection from the points read */
        Point[] points = reader.getPoints();
        //Euclidean distance:
        ClustersCollection.ElementsRealDistanceCalculator<Point> eleCalc = (Point e1, Point e2) ->
                Math.sqrt(Math.pow(e1.getX() - e2.getX(), 2) + Math.pow(e1.getY() - e2.getY(), 2));
        ClustersCollection<Point> clustersCollection =
                new ClustersCollection<>(Arrays.asList(points), eleCalc, clusCalc);

        /* Keep merging clusters until reaching the wanted number of clusters */
        while (clustersCollection.clustersNumber() > max_clusters)
            clustersCollection.mergeTwoClosestClusters();

        /* Index each cluster in order, and determine the cluster index of each point read from the input file */
        List<Cluster<Point>> clusters = clustersCollection.getClusters();
        List<Cluster<Point>> orderedClusters = new LinkedList<>();
        int[] clusterNumOfPoint = new int[points.length];

        for (int i = 0; i < points.length; ++i) {
            Point point = points[i];
            Cluster<Point> cluster = null;
            for (Cluster<Point> c : clusters) {
                if (c.contains(point)) {
                    cluster = c;
                    break;
                }
            }
            int index = orderedClusters.indexOf(cluster);
            if (index < 0) {
                index = orderedClusters.size();
                orderedClusters.add(cluster);
            }
            clusterNumOfPoint[i] = index + 1;
        }

        /* For debugging purposes - prints the clusters we got at the end */
        System.out.println("Clusters:\n" + clusters);

        /* Write the index of each point's cluster in the order they appeared in the input file */
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(output_file_path));
            for (int i = 0; i < clusterNumOfPoint.length - 1; i++) {
                writer.write(clusterNumOfPoint[i] + "\n");
            }
            writer.write(Integer.toString(clusterNumOfPoint[clusterNumOfPoint.length - 1]));
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to output file " + output_file_path + ":");
            e.printStackTrace();
            System.exit(2);
        }
    }
}
