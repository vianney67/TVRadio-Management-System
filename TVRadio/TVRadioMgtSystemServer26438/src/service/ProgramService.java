package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Program;
public interface ProgramService extends Remote {
    void add(Program program) throws RemoteException;
    void update(Program program) throws RemoteException;
    void delete(int id) throws RemoteException;
    Program getById(int id) throws RemoteException;
    List<Program> getAll() throws RemoteException;
}
