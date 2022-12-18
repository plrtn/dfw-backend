package dfw.database;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import dfw.core.mapper.ShapeMapper;
import dfw.core.mapper.TractMapper;
import dfw.core.model.Coordinate;
import dfw.core.model.InterpolationStrategy;
import dfw.core.model.Shape;
import dfw.core.model.Tract;

@Repository
public class DataRepository {

    @Autowired(required = true)
    private JdbcTemplate jdbc;

    @Autowired(required = true)
    private NamedParameterJdbcTemplate namedParamJdbc;

    public List<Shape> findAllShapes() {
        final String sql = """
                select "Key", ST_AsGeoJSON(spatialobj) from dfw_demo
                """;

        return jdbc.query(sql, new ShapeMapper());
    }

    public List<Tract> findTracts(Coordinate c, double radiusMeter, InterpolationStrategy strategy) {
        return jdbc.query(strategy.getSql(), pps -> {
            pps.setDouble(1, c.latitude());
            pps.setDouble(2, c.longitude());
            pps.setDouble(3, radiusMeter);
        }, new TractMapper());
    }

    // https://stackoverflow.com/a/1327222
    public List<Shape> findShapes(List<Long> id) {
        final String sql = """
                select "Key", ST_AsGeoJSON(spatialobj) from dfw_demo
                where "Key" in (:ids);
                """;

        return namedParamJdbc.query(
                sql,
                new MapSqlParameterSource("ids", id.stream().map(String::valueOf).collect(Collectors.toList())),
                new ShapeMapper());
    }

}
