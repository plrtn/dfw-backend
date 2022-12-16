package dfw.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dfw.core.model.Tract;

public class TractMapper implements RowMapper<Tract>{

	@Override
	public Tract mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Tract(rs.getLong(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4));
	}
}
