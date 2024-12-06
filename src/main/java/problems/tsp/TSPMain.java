package problems.tsp;
import instances.InstanceTSP;

import java.io.IOException;

public class TSPMain {
    public static void main(String[] args) throws IOException {
        String filepath = "src/main/resources/instances/tsp/instancia1.txt";
        InstanceTSP instance = new InstanceTSP(filepath);

    }
}
