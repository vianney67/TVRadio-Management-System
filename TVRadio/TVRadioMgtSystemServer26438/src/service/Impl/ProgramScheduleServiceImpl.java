package service.Impl;
import dao.ProgramScheduleDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.ProgramSchedule;
import service.ProgramScheduleService;
public class ProgramScheduleServiceImpl extends UnicastRemoteObject implements ProgramScheduleService {
    private final ProgramScheduleDao dao = new ProgramScheduleDao();
    public ProgramScheduleServiceImpl() throws RemoteException { super(); }
    public void add(ProgramSchedule schedule) throws RemoteException { dao.saveOrUpdate(schedule); }
    public void update(ProgramSchedule schedule) throws RemoteException { dao.saveOrUpdate(schedule); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public ProgramSchedule getById(int id) throws RemoteException { return dao.findById(id); }
    public List<ProgramSchedule> getAll() throws RemoteException { return dao.findAll(); }
}
