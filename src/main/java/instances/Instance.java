package instances;

import problems.knapsack.KnapsackSolution;

import java.lang.reflect.GenericDeclaration;

public interface Instance<R, T> {
    R evaluate(T solution);
}
