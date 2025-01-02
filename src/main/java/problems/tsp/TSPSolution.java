package problems.tsp;

import instances.InstanceTSP;

public class TSPSolution {
    InstanceTSP instance;
    private Integer[] route;
    private double value;

    public TSPSolution(Integer[] route, InstanceTSP instance) {
        this.instance = instance;
        this.route = route;
    }

    public Integer[] getRoute() {return this.route;}

    public InstanceTSP getInstance() {return this.instance;}

    public double getValue() {return this.value;}

    public void setValue(double value) {this.value = value;}

    public void setRoute(Integer[] route) {this.route = route;}

    public void setPointAtPosition(int position, int point) {route[position] = point;}

    public int getPointAtPosition(int position) {return route[position];}

    public boolean isBeter(TSPSolution other, boolean minimize) {
        if (minimize) {
            return this.value < other.value;
        } else {
            return this.value > other.value;
        }
    }

}
