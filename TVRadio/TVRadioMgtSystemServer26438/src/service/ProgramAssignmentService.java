package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.ProgramAssignment;
public interface ProgramAssignmentService extends Remote {
    void add(ProgramAssignment assignment) throws RemoteException;
    void update(ProgramAssignment assignment) throws RemoteException;
    void delete(int id) throws RemoteException;
    ProgramAssignment getById(int id) throws RemoteException;
    List<ProgramAssignment> getAll() throws RemoteException;
}
