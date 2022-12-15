package dfw;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

@SpringBootTest
class DfwBackendApplicationTests {
	
	@Autowired
	JdbcTemplate jdbc;

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
}
