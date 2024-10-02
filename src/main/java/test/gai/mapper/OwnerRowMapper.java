package test.gai.mapper;

import org.springframework.jdbc.core.RowMapper;
import test.gai.model.Owner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class OwnerRowMapper implements RowMapper<Owner> {

    @Override
    public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
        Owner owner = new Owner();
        owner.setId(rs.getLong("id"));
        owner.setName(rs.getString("name"));
        owner.setDob(rs.getDate("dob").toLocalDate());
        owner.setGender(rs.getString("gender"));

        String categories = rs.getString("license_categories");
        owner.setLicenseCategories(Arrays.asList(categories.split(",")));

        return owner;
    }
}
