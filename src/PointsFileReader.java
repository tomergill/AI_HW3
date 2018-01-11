import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Class that reads files in the following format:
 * 1st row is method to caculate the distance between clusters.
 * 2nd row is how many clusters we want to end up with.
 * 3rd and every row beyond is a point in the format of x,y.
 */
public class PointsFileReader {
    private String distance_method;
    private int clusters_num;
    private Point[] points;

    /**
     * Reads the file and keeps the data in members.
     * Use getDistance_method(), getClusters_num() and getPoints() to access them.
     * @param path Path to input file.
     * @throws IOException If reading the file goes wrong.
     */
    public PointsFileReader(String path) throws IOException{
        distance_method = null;
        clusters_num = -1;
        points = null;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        distance_method = reader.readLine();
        clusters_num = Integer.parseInt(reader.readLine());
        String line;
        LinkedList<String> strPoints = new LinkedList<>();
        while ((line = reader.readLine()) != null)
            strPoints.add(line);

        points = new Point[strPoints.size()];
        for (int i = 0; i < strPoints.size(); i++) {
            String[] coordinates = strPoints.get(i).split(",");
            int x = Integer.parseInt(coordinates[0]), y = Integer.parseInt(coordinates[1]);
            points[i] = new Point(x, y);
        }
    }

    /**
     * The method to calculate the distance between clusters.
     * @return A String representation of it from the file.
     */
    public String getDistance_method() {
        return distance_method;
    }

    /**
     * @return How many clusters we want at the end of the program.
     */
    public int getClusters_num() {
        return clusters_num;
    }

    /**
     * @return Array of points to cluster.
     */
    public Point[] getPoints() {
        return points;
    }
}