package service.Impl;
import dao.EquipmentDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Equipment;
import service.EquipmentService;
public class EquipmentServiceImpl extends UnicastRemoteObject implements EquipmentService {
    private final EquipmentDao dao = new EquipmentDao();
    public EquipmentServiceImpl() throws RemoteException { super(); }
    public void add(Equipment equipment) throws RemoteException { dao.saveOrUpdate(equipment); }
    public void update(Equipment equipment) throws RemoteException { dao.saveOrUpdate(equipment); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public Equipment getById(int id) throws RemoteException { return dao.findById(id); }
    public List<Equipment> getAll() throws RemoteException { return dao.findAll(); }
}
