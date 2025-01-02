package algorithms;
import problems.Problem;

public interface Solver<T extends Problem> {
    Object solve(T instance);
}
