package dao;
import java.util.List;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class EmployeeDao {
    public Employee findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Employee) session.get(Employee.class, id);
        } finally {
            session.close();
        }
    }
    @SuppressWarnings("unchecked")
    public List<Employee> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Employee").list();
        } finally {
            session.close();
        }
    }
    public void saveOrUpdate(Employee entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deleteById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Object obj = session.get(Employee.class, id);
            if (obj != null) session.delete(obj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
