package dbTools;

import Models.PurchaseList;
import org.hibernate.Session;
import org.hibernate.mapping.Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderService {
    public static  ArrayList<OrderEntity> getUserAllPurchases(String userName){
        Session session =  HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List purchases = session.createQuery("FROM OrderEntity").list();
        ArrayList<OrderEntity> orders = new ArrayList<>();
        for(Iterator iterator = purchases.iterator(); iterator.hasNext();){
            OrderEntity order = (OrderEntity)iterator.next();
            if(order.getUserName().equals(userName)) orders.add(order);
        }

        session.getTransaction().commit();
        session.close();
        return orders;
    }

    public static void saveOrder(OrderEntity order){
        Session session =  HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();
        session.close();
    }
}
