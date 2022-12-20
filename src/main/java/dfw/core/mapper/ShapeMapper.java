package dfw.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dfw.core.model.Shape;

public class ShapeMapper implements RowMapper<Shape> {

    @Override
    public Shape mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Shape(rs.getLong(1), rs.getString(2));
    }
}
