package elements;
//import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ListPorts {
//Metodo para listar los puertos disponibles.
	public List<String> PortsAvailable() {
		Enumeration ports = null; //CommPortIdentifier.getPortIdentifiers();
		List<String> ListPort = new ArrayList<String>();
		while (ports.hasMoreElements()) {
			//CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
			//ListPort.add(port.getName());
		}
		//System.out.println(ListPort);
		return ListPort;
	}
}
