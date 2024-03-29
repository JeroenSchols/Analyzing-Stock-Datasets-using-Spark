package Correlations;

import scala.Tuple2;

import java.util.Iterator;
import java.util.List;

public class PearsonCorrelation extends CorrelationFunction {

    public PearsonCorrelation(double treshold) {
        super(treshold);
    }

    @Override
    public double getCorrelation(List<Tuple2<String, List<Double>>> stocks) {
        if (stocks.size() != 2) throw new IllegalArgumentException(
                "PearsonCorrelation requires exactly 2 vectors, got " + stocks.size());
        List<Double> first = stocks.get(0)._2;
        List<Double> second = stocks.get(1)._2;

        // Retrieve averages
        double avgX = getAverage(first);
        double avgY = getAverage(second);

        // Prepare for simultaneous looping
        double cov = 0;
        double stdX = 0;
        double stdY = 0;
        Iterator<Double> iterX = first.iterator();
        Iterator<Double> iterY = second.iterator();

        // Process for all elements in a simultaneous loop
        while (iterX.hasNext() && iterY.hasNext()){
            double xi = iterX.next();
            double yi = iterY.next();

            cov += (xi-avgX)*(yi-avgY);
            stdX += Math.pow((xi-avgX), 2);
            stdY += Math.pow((yi-avgY), 2);
        }

        // Solve final equation
        stdX = Math.sqrt(stdX);
        stdY = Math.sqrt(stdY);
        return cov/(stdX*stdY);
    }

    private double getAverage(List<Double> data){
        double sum = 0;
        if(!data.isEmpty()) {
            for (Double point: data) {
                sum += point;
            }
            return sum / data.size();
        }
        return 0;
    }


}
