package lk.ijse.gdse.supermarket.dao.custom.impl;

import lk.ijse.gdse.supermarket.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.supermarket.entity.OrderDetails;

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

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean save(OrderDetails entity) {
        return false;
    }

    @Override
    public boolean update(OrderDetails entity) {
        return false;
    }

    @Override
    public boolean delete(String pk) {
        return false;
    }

    @Override
    public List<OrderDetails> getALl() {
        return List.of();
    }

    @Override
    public Optional<OrderDetails> findById(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return Optional.empty();
    }
}
