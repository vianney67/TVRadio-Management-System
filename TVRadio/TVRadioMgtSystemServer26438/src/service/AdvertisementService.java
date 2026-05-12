package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Advertisement;
public interface AdvertisementService extends Remote {
    void add(Advertisement advertisement) throws RemoteException;
    void update(Advertisement advertisement) throws RemoteException;
    void delete(int id) throws RemoteException;
    Advertisement getById(int id) throws RemoteException;
    List<Advertisement> getAll() throws RemoteException;
}
