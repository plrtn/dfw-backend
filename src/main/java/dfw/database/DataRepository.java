package dfw.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dfw.core.mapper.TractMapper;
import dfw.core.model.Coordinate;
import dfw.core.model.InterpolationStrategy;
import dfw.core.model.Shape;
import dfw.core.model.Tract;

@Repository
public class DataRepository {

	@Autowired(required = true)
	private JdbcTemplate jdbc;

	public List<Shape> findAllShapes() {
		return null;
	}

	public List<Tract> findTracts(Coordinate c, double radiusMeter, InterpolationStrategy strategy) {
		return jdbc.query(strategy.getSql(), pps -> {
			pps.setDouble(1, c.latitude());
			pps.setDouble(2, c.longitude());
			pps.setDouble(3, radiusMeter);
		}, new TractMapper());
	}

}
