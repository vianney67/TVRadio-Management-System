package dao;
import java.util.List;
import model.ProgramSchedule;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class ProgramScheduleDao {
    public ProgramSchedule findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (ProgramSchedule) session.get(ProgramSchedule.class, id);
        } finally {
            session.close();
        }
    }
    @SuppressWarnings("unchecked")
    public List<ProgramSchedule> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from ProgramSchedule").list();
        } finally {
            session.close();
        }
    }
    public void saveOrUpdate(ProgramSchedule entity) {
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
            Object obj = session.get(ProgramSchedule.class, id);
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
