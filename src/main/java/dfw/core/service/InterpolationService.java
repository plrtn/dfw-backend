package dfw.core.service;

import java.util.List;

import dfw.core.model.Tract;
import dfw.util.MathUtil;

public final class InterpolationService {
 
 public static double computeTotalPopulation (List<Tract> tracts) {
	 return MathUtil.sum(tracts.stream().map(x -> x.population() * x.ratio()));
 }
 
 public static double computeMedianIncome(List<Tract> tracts) {
	 return MathUtil.median(tracts.stream().map(x -> x.averageIncome() * x.ratio()));
 }
}
