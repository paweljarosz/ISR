import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientCallback extends UnicastRemoteObject implements ICallback 
{
	private static final long serialVersionUID = 1L;

	public ClientCallback() throws RemoteException 
	{
		 super();
	}
	
	public void komunikuj(String nick, String text) throws RemoteException 
	{
		System.out.println("odebrano komunikat: "+text);
	}
}
