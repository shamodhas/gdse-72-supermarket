package lk.ijse.gdse.supermarket;

import config.FactoryConfiguration;
import entity.Customer;
import org.hibernate.Session;

import java.util.List;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 2/28/2025 9:13 AM
 * Project: Query
 * --------------------------------------------
 **/

public class Query {
    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();

        // HQL, JPQL
//        Query query = session.createQuery("FROM Customer");
//        List<Customer> customers = query.list();

//        select * from customer_table - sql
//        select * from Customer - hql, jpql

//        Query query = session.createQuery("SELECT c FROM Customer c");
//        List<Customer> customers = query.list();

//        Query query = session.createQuery(
//                "FROM Customer WHERE name = :hello OR name = :hi"
//        );
//
//        query.setParameter("hi","joe");
//        query.setParameter("hello","bob");

//        "FROM Customer WHERE name = bob OR name = joe"

//        List<Customer> customers = query.list();

//        Native SQL
//        createQuery - can't use

        org.hibernate.query.Query query = session.createNativeQuery("SELECT * FROM customer_table")
                .addEntity(Customer.class);
        List<Customer> customers = query.list();
//        List<Object[]>

        System.out.println(customers);

//        Query query = session.createNativeQuery("SELECT * FROM customer_table");
//        List<Object[]> data = query.list();
//
//        for (Object[] row : data) {
//            for (int i = 0; i < row.length; i++) {
//                System.out.println(row[i]);
//            }
//        }


//        System.out.println(data);

//        Native SQL
//          Raw SQL queries directly on database tables
//        HQL
//          Object-oriented query language for Hibernate
//        JPQL
//          Object-oriented query language for JPA


        session.close();
    }
}
