package service.Impl;
import dao.ChannelDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Channel;
import service.ChannelService;
public class ChannelServiceImpl extends UnicastRemoteObject implements ChannelService {
    private final ChannelDao dao = new ChannelDao();
    public ChannelServiceImpl() throws RemoteException { super(); }
    public void add(Channel channel) throws RemoteException { dao.saveOrUpdate(channel); }
    public void update(Channel channel) throws RemoteException { dao.saveOrUpdate(channel); }
    public void delete(int id) throws RemoteException { dao.deleteById(id); }
    public Channel getById(int id) throws RemoteException { return dao.findById(id); }
    public List<Channel> getAll() throws RemoteException { return dao.findAll(); }
}
