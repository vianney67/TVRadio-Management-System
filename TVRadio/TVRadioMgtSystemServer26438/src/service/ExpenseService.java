package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Expense;
public interface ExpenseService extends Remote {
    void add(Expense expense) throws RemoteException;
    void update(Expense expense) throws RemoteException;
    void delete(int id) throws RemoteException;
    Expense getById(int id) throws RemoteException;
    List<Expense> getAll() throws RemoteException;
}
