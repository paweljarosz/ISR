import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class ChatServant extends UnicastRemoteObject implements IChat 
{
	private static final long serialVersionUID = 1L;
	
	private Map<String , ICallback > obecni = new HashMap <String , ICallback >();
	
	public ChatServant() throws RemoteException 
	{}

	// Metoda implementujaca funkcje komunikuj() interfejsu IChat
	public boolean komunikuj(String nick, String text) throws RemoteException 
	{
		System.out.println("Server.komunikuj(): " + text);
		ICallback callback = obecni.get(nick);
		if(callback != null) 
		{
			callback.komunikuj(nick, text);
			return true;
		}
		return false;
	}

	//Metoda implementujaca funkcje zarejestruj() interfejsu IChat
	public boolean zarejestruj(String nick, ICallback n) throws RemoteException 
	{
		System.out.println("Server.zarejestruj(): " + nick);
		if (!obecni.containsKey(nick)) 
		{
			obecni.put(nick, n);
			return true;
		}
	return false;
	}

	//Metoda implementujaca funkcje wyrejestruj() interfejsu IChat
	public boolean wyrejestruj(String nick) throws RemoteException 
	{
		if(obecni.remove(nick) != null) 
		{
			System.out.println("Server.wyrejestruj(): " + nick);
			return true;
		}
		return false;
	}


	//Metoda implementujaca funkcje informuj() interfejsu IChat
	public Vector<String> infromuj(String nick) throws RemoteException 
	{
		Set<String > set = obecni.keySet();
		Vector <String > v = new Vector <String >();
		for(String s : set)
		{
			if(nick.equals("*") || s.equals(nick))
				v.add(s);
		}
		return v;
	}
}