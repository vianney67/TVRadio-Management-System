package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.FinancialReport;
public interface FinancialReportService extends Remote {
    void add(FinancialReport report) throws RemoteException;
    void delete(int id) throws RemoteException;
    FinancialReport getById(int id) throws RemoteException;
    List<FinancialReport> getAll() throws RemoteException;
}
