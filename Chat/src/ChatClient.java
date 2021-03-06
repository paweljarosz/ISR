import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Vector;

@SuppressWarnings("deprecation")
public class ChatClient
{
	public static void main(String[] args)
	{
		if (System.getSecurityManager() == null)	//zarzadca bezpieczenstwa
			System.setSecurityManager(new RMISecurityManager());
		if(args.length < 2)
		{
			System.out.println("Usage: ChatClient <server host name> <msg>");
			System.exit(-1);
		}
		else
			System.out.println("Chat Client Ready");

		IChat remoteObject; // referencja do zdalnego obiektu
		Registry reg; // rejestr nazw obiektow
		try
		{
			// pobranie referencji do rejestru nazw obiektow
			reg = LocateRegistry.getRegistry(args[0]);
			// odszukanie zdalnego obiektu po jego nazwie
			remoteObject = (IChat) reg.lookup("ChatServer");
			ICallback callback = new ClientCallback();
			// wywolanie metod zdalnego obiektu
			//remoteObject.zarejestruj("LM", callback);
			System.out.println(" |               Pawel Jarosz Chat 1.0               |");
			System.out.println(" |                    Who you are?                   |");

			Scanner scan = new Scanner(System.in);
			String user = null;
			if(scan.hasNextLine())
				user = scan.nextLine();
			remoteObject.zarejestruj(user, callback);

				System.out.println(" | Welcome "+user+"!");
				System.out.println(" |  [+ nick]      - regiser user with name 'nick' or log in     |");
				System.out.println(" |  [- nick]      - unregiser user with name 'nick' or log out  |");
				System.out.println(" |  [?]           - give informations about registered users]   |");
				System.out.println(" |  [/ nick msg]  - send message 'msg' to user 'nick']          |");
				System.out.println(" |  [exit]        - type to quit the program]                   |");

			boolean a=true;
			while(a)
			{
				String line = null;
				if(scan.hasNextLine())
					line = scan.nextLine();

				String[] kod = line.split("[ ]");
				switch(kod[0])
				{
					case "+":
						remoteObject.zarejestruj(kod[1], callback);
						user=kod[1];
						System.out.println("+ Logged in as: "+user);
						break;
					case "-":
						remoteObject.wyrejestruj(kod[1]);
						System.out.println("- Deleted user: "+kod[1]);
						user="-Guest-";
						break;
					case "?":
						Vector <String > vec = remoteObject.infromuj("*");
						System.out.println("? You are: "+user+". There are " + vec.size() + " user(s): "+vec.toString());
						break;
					case "/":
						remoteObject.komunikuj(kod[1], kod[2]);
						System.out.println("/ "+user+" said: '"+kod[2]+"' to: "+kod[1]);
						break;
					case "exit":
						a=false;
						System.out.println(user+" left chat.");
						break;
					default:
						System.out.println("No action");
						break;
				}
			}
			System.out.println("Good bye!");
			System.out.println("Chat is closed");
			System.exit(0);
		}
		catch(RemoteException e)
		{
			e.printStackTrace();
		}
		catch(NotBoundException e)
		{
			e.printStackTrace();
		}
	}
}
