package test.gai.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import test.gai.mapper.OwnerRowMapper;
import test.gai.entity.Owner;

import java.util.List;
import java.util.Optional;

@Repository
public class OwnerJdbcRepository implements OwnerRepository{

    private final JdbcTemplate jdbcTemplate;
    private final CarJdbcRepository carJdbcRepository;

    public OwnerJdbcRepository(JdbcTemplate jdbcTemplate, CarJdbcRepository carJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.carJdbcRepository = carJdbcRepository;
    }

    @Override
    public List<Owner> findAll() {
        String sql = "SELECT * FROM owner";
        List<Owner> owners = jdbcTemplate.query(sql, new OwnerRowMapper());
        owners.forEach(owner -> owner.setCars(carJdbcRepository.findByOwnerId(owner.getId())));
        return owners;
    }

    @Override
    public Optional<Owner> findById(Long id) {
        String sql = "SELECT * FROM owner WHERE id = ?";
        List<Owner> owners = jdbcTemplate.query(sql, new Object[]{id}, new OwnerRowMapper());
        return owners.stream().findFirst();
    }

    @Override
    public Owner save(Owner owner) {
        if (owner.getId() == null || findById(owner.getId()).isEmpty()) {
            String sql = "INSERT INTO owner (name, dob, gender, license_categories) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    owner.getName(),
                    owner.getDob(),
                    owner.getGender().name(),
                    owner.getLicenseCategories());
            String sqlSelect = "SELECT * FROM Owner ORDER BY id DESC LIMIT 1";
            List<Owner> owners = jdbcTemplate.query(sqlSelect, new OwnerRowMapper());
            return owners.stream().findFirst().get();
        } else {
            String sql = "UPDATE owner SET name = ?, dob = ?, gender = ?, license_categories = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    owner.getName(),
                    owner.getDob(),
                    owner.getGender().name(),
                    owner.getLicenseCategories(),
                    owner.getId());
        }
        return findById(owner.getId()).get();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM owner WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
