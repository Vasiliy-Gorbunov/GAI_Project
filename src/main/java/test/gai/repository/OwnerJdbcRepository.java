package test.gai.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import test.gai.mapper.OwnerRowMapper;
import test.gai.model.Owner;

import java.util.List;
import java.util.Optional;

@Repository
public class OwnerJdbcRepository implements OwnerRepository{

    private final JdbcTemplate jdbcTemplate;

    public OwnerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Owner> findAll() {
        String sql = "SELECT * FROM owner";
        return jdbcTemplate.query(sql, new OwnerRowMapper());
    }

    @Override
    public Optional<Owner> findById(Long id) {
        String sql = "SELECT * FROM owner WHERE id = ?";
        List<Owner> owners = jdbcTemplate.query(sql, new Object[]{id}, new OwnerRowMapper());
        return owners.stream().findFirst();
    }

    @Override
    public Owner save(Owner owner) {
        if (owner.getId() == null) {
            String sql = "INSERT INTO owner (name, dob, gender, license_categories) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    owner.getName(),
                    owner.getDob(),
                    owner.getGender().name(),
                    owner.getLicenseCategories());
        } else {
            String sql = "UPDATE owner SET name = ?, dob = ?, gender = ?, license_categories = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    owner.getName(),
                    owner.getDob(),
                    owner.getGender().name(),
                    owner.getLicenseCategories(),
                    owner.getId());
        }
        return owner;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM owner WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
