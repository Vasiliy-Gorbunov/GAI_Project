package test.gai.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import test.gai.mapper.OwnerRowMapper;
import test.gai.model.Owner;

import java.util.List;

@Repository
@ConditionalOnProperty(name = "repository.type", havingValue = "jdbc")
public class OwnerJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Owner> findAll() {
        return jdbcTemplate.query("SELECT * FROM owner", new OwnerRowMapper());
    }

    public Owner findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM owner WHERE id = ?", new Object[]{id}, new OwnerRowMapper());
    }
}
