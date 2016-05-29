import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer 
{
	Registry reg; 		  	// rejestr nazw obiektow
	ChatServant servant; 	// klasa uslugowa
	
	public static void main(String[] args) 
	{
		try 
		{
			new ChatServer();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			System.exit(1);
		} 
	}
	
	protected ChatServer() throws RemoteException 
	{
		try 
		{
			reg = LocateRegistry.createRegistry(1085); 	// Utworzenie rejestru nazw
			servant = new ChatServant(); 				// utworzenie zdalnego obiektu
			reg.rebind("ChatServer", servant);			// zwiazanie nazwy z obiektem
			System.out.println("Chat Server Ready");
		} 
		catch(RemoteException e) 
		{
			e.printStackTrace();
			throw e;				//wyrzuci wyjatek
		}
	}
}
