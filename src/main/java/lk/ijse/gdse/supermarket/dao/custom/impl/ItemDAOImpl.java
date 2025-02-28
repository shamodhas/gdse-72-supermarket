package lk.ijse.gdse.supermarket.dao.custom.impl;

import lk.ijse.gdse.supermarket.dao.custom.ItemDAO;
import lk.ijse.gdse.supermarket.entity.Item;

import java.util.List;
import java.util.Optional;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 2/28/2025 12:59 PM
 * Project: Supermarket-72
 * --------------------------------------------
 **/

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(Item entity) {
        return false;
    }

    @Override
    public boolean update(Item entity) {
        return false;
    }

    @Override
    public boolean delete(String pk) {
        return false;
    }

    @Override
    public List<Item> getALl() {
        return List.of();
    }

    @Override
    public Optional<Item> findById(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return Optional.empty();
    }
}
