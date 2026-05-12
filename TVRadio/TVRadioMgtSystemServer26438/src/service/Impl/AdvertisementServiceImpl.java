package service.Impl;
import dao.AdvertisementDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Advertisement;
import service.AdvertisementService;
public class AdvertisementServiceImpl extends UnicastRemoteObject implements AdvertisementService {
    private final AdvertisementDao dao = new AdvertisementDao();
    public AdvertisementServiceImpl() throws RemoteException { super(); }
    public void add(Advertisement advertisement) throws RemoteException { dao.saveOrUpdate(advertisement); }
    public void update(Advertisement advertisement) throws RemoteException { dao.saveOrUpdate(advertisement); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public Advertisement getById(int id) throws RemoteException { return dao.findById(id); }
    public List<Advertisement> getAll() throws RemoteException { return dao.findAll(); }
}
