package algorithms.evolutionary.crossover;

import java.util.Random;

public class PMX implements Crossover<Integer> {

    private int p1;
    private int p2;

    public Integer[][] crossover(Integer[] parent1, Integer[] parent2) {
        generatePoints(parent1.length);

        Integer[] hijo1 = new Integer[parent1.length];
        Integer[] hijo2 = new Integer[parent2.length];

        for(int i = 0; i < parent1.length; i++) {
            hijo1[i] = -1;
            hijo2[i] = -1;
        }

        boolean[] p1Bool = new boolean[parent1.length];
        boolean[] p2Bool = new boolean[parent2.length];

        rellenadoInicial(parent1, parent2, hijo1, hijo2, p1Bool, p2Bool);
        rellenadoHijo(parent1, parent2, hijo1, p2Bool);
        rellenadoHijo(parent2, parent1, hijo2, p1Bool);


        return new Integer[][]{hijo1, hijo2};
    };

    private void generatePoints(int max){
        int min = 0; // Valor mínimo del rango
        Random random = new Random();
        this.p1 = (int) (Math.random() * max);
        this.p2 = (int) (Math.random() * max);
        if (this.p1 > this.p2){
            int aux = this.p1;
            this.p1 = this.p2;
            this.p2 = aux;
        }
    }

    private void rellenadoInicial(Integer[] parent1, Integer[] parent2,
                                  Integer[] hijo1, Integer[] hijo2,
                                  boolean[] p1Bool, boolean[] p2Bool) {

        for (int i = p1; i <= p2; i++) {
            p2Bool[parent1[i]] = true;
            p1Bool[parent2[i]] = true;
            hijo1[i] = parent1[i];
            hijo2[i] = parent2[i];
        }

    }

    private void rellenadoHijo(Integer[] parent1, Integer[] parent2,
                               Integer[] hijo,
                               boolean[] pBool) {

        for (int i = p1; i <= p2; i++) {
            int elemAcolocar = parent2[i];
            if (!pBool[elemAcolocar]){
                int newIdx = buscaPosicion(parent1, parent2, i);
                hijo[newIdx] = elemAcolocar;
                pBool[elemAcolocar] = true;
            }
        }

        for (int i = 0; i < parent2.length; i++) {
            if ((i < p1 || i > p2) && !pBool[parent2[i]]){
                for (int j = 0; j < hijo.length; j++) {
                    if (hijo[j] == -1){
                        hijo[j] = parent2[i];
                        break;
                    }
                }
            }
        }
    }

    private int buscaPosicion(Integer[] parent1, Integer[] parent2, int idx) {
        int elemABuscar = parent1[idx];
        int j = 0;
        boolean stop = false;
        while (!stop){
            for (int i = 0; i < parent1.length; i++) {
                if (parent2[i] == elemABuscar) {
                    if (i < p1 || i > p2) {
                        j = i;
                        stop = true;
                    }else {
                        elemABuscar = parent1[i];
                    }
                    break;
                }
            }
        }
        return j;
    }
}
