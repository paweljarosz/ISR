import java.rmi.*;
import java.util.Vector;

public interface IChat extends Remote 
{
	boolean zarejestruj(String nick, ICallback n) throws RemoteException;
	boolean wyrejestruj(String nick) throws RemoteException;
	boolean komunikuj(String nick, String message) throws RemoteException;
	Vector<String> infromuj(String nick) throws RemoteException;
}
