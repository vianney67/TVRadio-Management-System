package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Equipment;
public interface EquipmentService extends Remote {
    void add(Equipment equipment) throws RemoteException;
    void update(Equipment equipment) throws RemoteException;
    void delete(int id) throws RemoteException;
    Equipment getById(int id) throws RemoteException;
    List<Equipment> getAll() throws RemoteException;
}
