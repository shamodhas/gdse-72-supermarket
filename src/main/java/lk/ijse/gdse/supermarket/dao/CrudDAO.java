package lk.ijse.gdse.supermarket.dao;

import lk.ijse.gdse.supermarket.entity.SuperEntity;

import java.util.List;
import java.util.Optional;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 2/28/2025 12:34 PM
 * Project: Supermarket-72
 * --------------------------------------------
 **/

public interface CrudDAO<T extends SuperEntity, ID> extends SuperDAO {
    public boolean save(T entity);

    public boolean update(T entity);

    public boolean delete(ID pk);

    public List<T> getALl();

    public Optional<T> findById(ID pk);

    public Optional<String> getLastPK();
}
