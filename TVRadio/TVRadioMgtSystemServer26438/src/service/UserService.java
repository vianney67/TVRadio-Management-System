package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.User;
public interface UserService extends Remote {
    User login(String username, String password) throws RemoteException;
    void register(User user) throws RemoteException;
    void add(User user) throws RemoteException;
    void update(User user) throws RemoteException;
    void delete(int id) throws RemoteException;
    User getById(int id) throws RemoteException;
    List<User> getAll() throws RemoteException;
}
