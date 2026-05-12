package service.Impl;
import dao.ProgramAssignmentDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.ProgramAssignment;
import service.ProgramAssignmentService;
public class ProgramAssignmentServiceImpl extends UnicastRemoteObject implements ProgramAssignmentService {
    private final ProgramAssignmentDao dao = new ProgramAssignmentDao();
    public ProgramAssignmentServiceImpl() throws RemoteException { super(); }
    public void add(ProgramAssignment assignment) throws RemoteException { dao.saveOrUpdate(assignment); }
    public void update(ProgramAssignment assignment) throws RemoteException { dao.saveOrUpdate(assignment); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public ProgramAssignment getById(int id) throws RemoteException { return dao.findById(id); }
    public List<ProgramAssignment> getAll() throws RemoteException { return dao.findAll(); }
}
