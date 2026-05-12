package dao;
import java.util.List;
import model.Channel;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class ChannelDao {
    public Channel findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Channel) session.get(Channel.class, id);
        } finally {
            session.close();
        }
    }
    @SuppressWarnings("unchecked")
    public List<Channel> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Channel").list();
        } finally {
            session.close();
        }
    }
    public void saveOrUpdate(Channel entity) {
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
            Object obj = session.get(Channel.class, id);
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
