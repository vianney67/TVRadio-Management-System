package service.Impl;
import dao.FinancialReportDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.FinancialReport;
import service.FinancialReportService;
public class FinancialReportServiceImpl extends UnicastRemoteObject implements FinancialReportService {
    private final FinancialReportDao dao = new FinancialReportDao();
    public FinancialReportServiceImpl() throws RemoteException { super(); }
    public void add(FinancialReport report) throws RemoteException { dao.saveOrUpdate(report); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public FinancialReport getById(int id) throws RemoteException { return dao.findById(id); }
    public List<FinancialReport> getAll() throws RemoteException { return dao.findAll(); }
}
