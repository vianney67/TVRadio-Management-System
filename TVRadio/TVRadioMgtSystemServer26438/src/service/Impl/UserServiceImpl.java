package service.Impl;
import dao.UserDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import dao.HibernateUtil;
import service.UserService;
public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    private final UserDao dao = new UserDao();
    public UserServiceImpl() throws RemoteException { super(); }
    public User login(String username, String password) throws RemoteException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query q = session.createQuery("from User where username = :u and password = :p");
            q.setParameter("u", username);
            q.setParameter("p", password);
            return (User) q.uniqueResult();
        } finally {
            session.close();
        }
    }
    public void register(User user) throws RemoteException { dao.saveOrUpdate(user); }
    public void add(User user) throws RemoteException { dao.saveOrUpdate(user); }
    public void update(User user) throws RemoteException { dao.saveOrUpdate(user); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public User getById(int id) throws RemoteException { return dao.findById(id); }
    public List<User> getAll() throws RemoteException { return dao.findAll(); }
}
