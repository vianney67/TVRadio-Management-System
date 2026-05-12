package service.Impl;
import dao.ExpenseDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Expense;
import service.ExpenseService;
public class ExpenseServiceImpl extends UnicastRemoteObject implements ExpenseService {
    private final ExpenseDao dao = new ExpenseDao();
    public ExpenseServiceImpl() throws RemoteException { super(); }
    public void add(Expense expense) throws RemoteException { dao.saveOrUpdate(expense); }
    public void update(Expense expense) throws RemoteException { dao.saveOrUpdate(expense); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public Expense getById(int id) throws RemoteException { return dao.findById(id); }
    public List<Expense> getAll() throws RemoteException { return dao.findAll(); }
}
