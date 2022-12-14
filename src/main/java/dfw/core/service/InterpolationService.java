package dfw.core.service;

import java.util.List;

import dfw.core.Coordinate;
import dfw.core.InterpolationStrategy;
import dfw.core.Tract;
import dfw.util.MathUtil;

public final class InterpolationService {
	
 public static List<Tract> computeTracts(Coordinate c, double radius, InterpolationStrategy is) {
	 return null;
 }
 
 public static double computeAveragePopulation (List<Tract> tracts) {
	 return MathUtil.average(tracts.stream().map(x -> x.population() * x.ratio()));
 }
 
 public static double computeMedianIncome(List<Tract> tracts) {
	 return MathUtil.median(tracts.stream().map(x -> x.averageIncome() * x.ratio()));
 }
}
