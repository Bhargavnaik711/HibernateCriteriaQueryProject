package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Create SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Customer.class).buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            // Insert Records
            Customer c1 = new Customer();
            c1.setName("John Doe");
            c1.setEmail("john.doe@example.com");
            c1.setAge(28);
            c1.setLocation("New York");

            Customer c2 = new Customer();
            c2.setName("Jane Smith");
            c2.setEmail("jane.smith@example.com");
            c2.setAge(34);
            c2.setLocation("Los Angeles");

            session.save(c1);
            session.save(c2);

            // Query using Criteria
            Criteria criteria = session.createCriteria(Customer.class);

            System.out.println("Customers aged less than 30:");
            criteria.add(Restrictions.lt("age", 30));
            List<Customer> customersUnder30 = criteria.list();
            customersUnder30.forEach(c -> System.out.println(c.getName()));

            System.out.println("Customers in New York:");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.eq("location", "New York"));
            List<Customer> customersInNY = criteria.list();
            customersInNY.forEach(c -> System.out.println(c.getName()));

            System.out.println("Customers with name starting with 'J':");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.like("name", "J%"));
            List<Customer> customersWithJ = criteria.list();
            customersWithJ.forEach(c -> System.out.println(c.getName()));

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
