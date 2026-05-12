package service;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Channel;
public interface ChannelService extends Remote {
    void add(Channel channel) throws RemoteException;
    void update(Channel channel) throws RemoteException;
    void delete(int id) throws RemoteException;
    Channel getById(int id) throws RemoteException;
    List<Channel> getAll() throws RemoteException;
}
