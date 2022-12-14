package dfw.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MathUtil {
	
	public static <T extends Number> double average(List<T> list) {
		return list.isEmpty() ? 0d : MathUtil.average(list.stream());
	}
	
	public static <T extends Number> double average(Stream<T> stream) {
		return Objects.requireNonNull(stream)
				.map(Number::doubleValue)
				.collect(Collectors.averagingDouble(x -> x));
	}
	
	public static <T extends Number> double median(Stream<T> stream) {
		return MathUtil.median(stream.toList());
	}
	
	public static <T extends Number> double median(List<T> list) {
			int size = Objects.requireNonNull(list).size();
			
			if (size == 0) {
				return 0d;
			}
			
			return list.stream()
				.map(Number::doubleValue)
				.sorted()
				.skip(size % 2 == 0 ? size - 1 : size)
				.limit(size % 2 == 0 ? 2 : 1)
				.collect(Collectors.averagingDouble(x -> x));
	}
}
