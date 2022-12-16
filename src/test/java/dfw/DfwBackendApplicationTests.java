package dfw;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import dfw.core.model.Coordinate;
import dfw.core.model.InterpolationStrategy;
import dfw.core.model.Tract;
import dfw.database.DataRepository;

@SpringBootTest
class DfwBackendApplicationTests {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	DataRepository repo;
	
	Coordinate c = new Coordinate(33.045352, -96.781508);
	double radiusMeter = 2000;

	@Test
	void contextLoads(ApplicationContext context) {
		assertThat(context).isNotNull();
	}
	
	@Test
	void hasDBConnection() throws SQLException {
		assertThat(jdbc).isNotNull();
		assertThat(jdbc.getDataSource().getConnection()).isNotNull();
	}
	
	@Test
	void canQueryDB() {
		String sql = """
				select 1 as foo
				""";
		
		assertThat(jdbc.queryForMap(sql)).isNotNull().containsKey("foo");
	}
	
	@Test
	void canQueryTable() {
		String sql = """
					select * from dfw_demo
					limit 1
				""";
		
		SqlRowSet result = jdbc.queryForRowSet(sql);
		result.first();
		assertThat(result.getMetaData().getColumnNames()).contains("Key", "income", "population", "spatialobj");
	}
	
	@Test
	void testCentroidStrategy() {
		List<Tract> result = repo.findTracts(c, radiusMeter, InterpolationStrategy.CENTROID);
		assertThat(result).isNotNull().isNotEmpty();
		assertThat(result.size()).isEqualTo(18);
		assertThat(result.stream().map(Tract::ratio)).allMatch(x -> x == 1).noneMatch(x -> x > 1);
	}
	
	@Test
	void testArealProportionStrategy() {
		List<Tract> result = repo.findTracts(c, radiusMeter, InterpolationStrategy.AREAL_PROPORTION);
		assertThat(result).isNotNull().isNotEmpty();
		assertThat(result.size()).isEqualTo(28);
//		assertThat(result.stream().map(Tract::ratio)).noneMatch(x -> x > 1);
		
		result.stream().forEach(System.out::println);
		
		
	}
}
