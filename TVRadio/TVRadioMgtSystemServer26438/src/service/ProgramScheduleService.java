package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.ProgramSchedule;
public interface ProgramScheduleService extends Remote {
    void add(ProgramSchedule schedule) throws RemoteException;
    void update(ProgramSchedule schedule) throws RemoteException;
    void delete(int id) throws RemoteException;
    ProgramSchedule getById(int id) throws RemoteException;
    List<ProgramSchedule> getAll() throws RemoteException;
}
