package service.Impl;
import dao.ProgramDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Program;
import service.ProgramService;
public class ProgramServiceImpl extends UnicastRemoteObject implements ProgramService {
    private final ProgramDao dao = new ProgramDao();
    public ProgramServiceImpl() throws RemoteException { super(); }
    public void add(Program program) throws RemoteException { dao.saveOrUpdate(program); }
    public void update(Program program) throws RemoteException { dao.saveOrUpdate(program); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public Program getById(int id) throws RemoteException { return dao.findById(id); }
    public List<Program> getAll() throws RemoteException { return dao.findAll(); }
}
