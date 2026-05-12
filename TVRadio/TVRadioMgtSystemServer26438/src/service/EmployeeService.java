package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Employee;
public interface EmployeeService extends Remote {
    void add(Employee employee) throws RemoteException;
    void update(Employee employee) throws RemoteException;
    void delete(int id) throws RemoteException;
    Employee getById(int id) throws RemoteException;
    List<Employee> getAll() throws RemoteException;
}
