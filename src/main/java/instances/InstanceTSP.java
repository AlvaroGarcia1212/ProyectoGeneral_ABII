package instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class InstanceTSP implements Instance {

    private String name;
    private int nPuntos;
    private double[][] coordinates;
    private double[][] distances;


    public InstanceTSP(String filename) throws IOException {
        this.name = filename;
        this.importInstance(new BufferedReader(new FileReader(filename)));
    }

    public void importInstance(BufferedReader reader) throws IOException {
        Scanner sc = new Scanner(reader).useLocale(Locale.US);
        this.nPuntos = Integer.parseInt(sc.nextLine().split(" ")[1].trim());
        this.coordinates = new double[nPuntos][2];
        while (sc.hasNextInt()){
            int id = sc.nextInt() - 1;
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            this.coordinates[id][0] = x;
            this.coordinates[id][1] = y;
        }
        distances = new double[nPuntos][nPuntos];
        for (int i = 0; i < nPuntos; i++) {
            for (int j = i; j < nPuntos; j++) {
                this.distances[i][j] = calculateDistance(coordinates[i], coordinates[j]);
                this.distances[j][i] = distances[i][j];
            }
        }
        sc.close();
    }

    private double calculateDistance(double[] a, double[] b) {
        double d1 = a[0] - b[0];
        double d2 = a[1] - b[1];
        return Math.sqrt(d1 * d1 + d2 * d2);
    }

}
