package com.inspirion.stockmarket.dao;

import com.inspirion.stockmarket.entity.AppSettings;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppSettingsDAO extends BaseDAO {
     public AppSettings get() {
        String SQL = "select * from app_settings";
        List<AppSettings> settings = jdbcTemplate.query(SQL, new AppSettingsDAO.SettingsRowMapper());
        return settings.get(0);
    }

    public void update(AppSettings settings) {
        String SQL =
                "update app_settings\n" +
                        "set " +
                        "start_hour = :startHour,\n" +
                        "start_min = :startMinutes,\n" +
                        "end_hour = :endHour,\n" +
                        "end_min = :endMinute,\n" +
                        "working_days = :workingDays\n" +
                        "where id = :id;";
        namedJdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(settings));
    }

    public class SettingsRowMapper implements RowMapper<AppSettings> {
        @Override
        public AppSettings mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AppSettings(
                    rs.getInt("id"),
                    rs.getInt("start_hour"),
                    rs.getInt("start_min"),
                    rs.getInt("end_hour"),
                    rs.getInt("end_min"),
                    (Integer[]) rs.getArray("working_days").getArray()
            );
        }
    }
}
