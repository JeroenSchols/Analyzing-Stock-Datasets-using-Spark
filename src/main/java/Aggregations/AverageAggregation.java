package Aggregations;

import scala.Tuple2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AverageAggregation implements AggregationFunction {

    @Override
    public List<Tuple2<String, List<Double>>> aggregate(List<Tuple2<String, List<Double>>> in) {
        StringBuilder stockname = new StringBuilder();
        Double[] prices = new Double[in.get(0)._2.size()];

        for (Tuple2<String, List<Double>> stock : in) {
            stockname.append("(").append(stock._1).append(") + ");
            double minPrice = Double.MAX_VALUE;
            double maxPrice = Double.MIN_VALUE;
            for (int i = 0; i < prices.length; i++) {
                minPrice = Math.min(minPrice, stock._2.get(i));
                maxPrice = Math.max(maxPrice, stock._2.get(i));
            }

            for (int i = 0; i < prices.length; i++) {
                prices[i] += (stock._2.get(i) - minPrice) / (maxPrice - minPrice) / prices.length;
            }
        }

        return Collections.singletonList(new Tuple2<>(stockname.toString(), Arrays.asList(prices)));
    }
}