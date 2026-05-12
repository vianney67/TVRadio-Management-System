package dao;
import java.util.List;
import model.Advertisement;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class AdvertisementDao {
    public Advertisement findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Advertisement) session.get(Advertisement.class, id);
        } finally {
            session.close();
        }
    }
    @SuppressWarnings("unchecked")
    public List<Advertisement> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Advertisement").list();
        } finally {
            session.close();
        }
    }
    public void saveOrUpdate(Advertisement entity) {
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
            Object obj = session.get(Advertisement.class, id);
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
