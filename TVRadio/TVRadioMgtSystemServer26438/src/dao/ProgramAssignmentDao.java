package dao;
import java.util.List;
import model.ProgramAssignment;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class ProgramAssignmentDao {
    public ProgramAssignment findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (ProgramAssignment) session.get(ProgramAssignment.class, id);
        } finally {
            session.close();
        }
    }
    @SuppressWarnings("unchecked")
    public List<ProgramAssignment> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from ProgramAssignment").list();
        } finally {
            session.close();
        }
    }
    public void saveOrUpdate(ProgramAssignment entity) {
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
            Object obj = session.get(ProgramAssignment.class, id);
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
