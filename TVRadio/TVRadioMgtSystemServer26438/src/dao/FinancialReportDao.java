package dao;
import java.util.List;
import model.FinancialReport;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class FinancialReportDao {
    public FinancialReport findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (FinancialReport) session.get(FinancialReport.class, id);
        } finally {
            session.close();
        }
    }
    @SuppressWarnings("unchecked")
    public List<FinancialReport> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from FinancialReport").list();
        } finally {
            session.close();
        }
    }
    public void saveOrUpdate(FinancialReport entity) {
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
            Object obj = session.get(FinancialReport.class, id);
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
