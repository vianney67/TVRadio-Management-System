package service.Impl;
import dao.EmployeeDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Employee;
import service.EmployeeService;
public class EmployeeServiceImpl extends UnicastRemoteObject implements EmployeeService {
    private final EmployeeDao dao = new EmployeeDao();
    public EmployeeServiceImpl() throws RemoteException { super(); }
    public void add(Employee employee) throws RemoteException { dao.saveOrUpdate(employee); }
    public void update(Employee employee) throws RemoteException { dao.saveOrUpdate(employee); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public Employee getById(int id) throws RemoteException { return dao.findById(id); }
    public List<Employee> getAll() throws RemoteException { return dao.findAll(); }
}
