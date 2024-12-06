package instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class InstanceKnapsack implements Instance {

    private String name;
    private int n;
    private int c;
    private int[] weights;
    private int[] values;

    public InstanceKnapsack(String name) throws IOException {
        this.name = name;
        this.importInstance(new BufferedReader(new FileReader(name)));
    }

    public void importInstance(BufferedReader reader) throws IOException {
        Scanner sc = new Scanner(reader).useLocale(Locale.US);
        this.n = sc.nextInt();
        this.c = sc.nextInt();
        this.weights = new int[n];
        this.values = new int[n];
        int i = 0;
        while (sc.hasNextInt()){
            this.values[i] = sc.nextInt();
            this.weights[i] = sc.nextInt();
            i++;
        }
        sc.close();
    }



}
