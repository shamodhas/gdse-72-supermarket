package lk.ijse.gdse.supermarket.dao.custom.impl;

import lk.ijse.gdse.supermarket.config.FactoryConfiguration;
import lk.ijse.gdse.supermarket.dao.custom.CustomerDAO;
import lk.ijse.gdse.supermarket.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

public class CustomerDAOImpl implements CustomerDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration
            .getInstance();

    @Override
    public boolean save(Customer entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Customer entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(String pk) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Customer customer = session.find(Customer.class, pk);
//            Customer customer = session.get(Customer.class, pk);
            if (customer != null) {
                session.remove(customer);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Customer> getALl() {
        Session session = factoryConfiguration.getSession();
        List<Customer> customers = session.createQuery("FROM Customer", Customer.class)
                .list();
        session.close();
        return customers;
    }

    @Override
    public Optional<Customer> findById(String pk) {
        Session session = factoryConfiguration.getSession();
        Customer customer = session.get(Customer.class, pk);
        session.close();
//        if (customer == null) {
//            return Optional.empty();
//        }
//        return Optional.of(customer);

        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = factoryConfiguration.getSession();
        String lastPk = session.createQuery(
                        "SELECT c.id FROM Customer c ORDER BY c.id DESC",
                        String.class
                )
                .setMaxResults(1)
                .uniqueResult();
        return Optional.ofNullable(lastPk);
    }
}




