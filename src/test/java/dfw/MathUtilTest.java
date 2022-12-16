package dfw;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dfw.util.MathUtil;

class MathUtilTest {

	@Test
	void testMedian() {
		List<Integer> ls = List.of(1,2,3,4,5,6,7,8,9);
		assertThat(MathUtil.median(ls)).isEqualTo(5);
		assertThat(MathUtil.median(ls)).isEqualTo(MathUtil.median(ls.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())));
		
		ls = List.of(1,2,3,4,5,6,7,8,9,10);
		assertThat(MathUtil.median(ls)).isEqualTo((5 + 6) / 2d);
		assertThat(MathUtil.median(ls)).isEqualTo(MathUtil.median(ls.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())));
	}
	
	@Test
	void testSum() {
		List<Integer> ls = List.of(0,0,0,0,0);
		assertThat(MathUtil.sum(ls)).isEqualTo(0);
		
		ls = List.of(1,2,3,4);
		assertThat(MathUtil.sum(ls)).isEqualTo(10);
	}

}
