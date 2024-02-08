/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package device;

//import gnu.io.*;
import jssc.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author pmoya
 */
public class PortReadWrite implements SerialPortEventListener {
	// Atributos Globals
	private SerialPort puertoSerie;
	private static Enumeration listaPuertos;
//	private static CommPortIdentifier idPuerto;
//	private InputStream entrada;
//	private OutputStream salida;
	private String Terminar;
	public String Estado;
	public byte[] byporLeidos;
	private boolean isBloqsZ = false;
	private int n = 0, bytyporleer = 0, xor = 0;
	private static int m_iStatusScale = 0;
	private static final int SCALE_READY = 0;
	private static final int SCALE_READING = 1;
	public boolean IndPuerto = false, Bandera = true, CTS = false,
			inTransaccion = false;
	private static String statusReceiver = null, errorReceiver = null,
			lrcReceiver = null, transacctionReceiver = null;
	private ArrayList listaBytes;
	private static int[] recibidosBytes = null;
	private static final char STX = 2;
	private static final char ETX = 3;
	private static final char ETB = 23;
	private static char LRC;

	// Constructor
	public PortReadWrite() {
		listaBytes = new ArrayList();
	}

	// Manejadores de Eventos
	@Override
	public void serialEvent(SerialPortEvent e) {
		// Determine type of event.
		switch (e.getEventType()) {
//		case SerialPortEvent.BI:
//		case SerialPortEvent.OE:
//		case SerialPortEvent.FE:
//		case SerialPortEvent.PE:
//		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
//		case SerialPortEvent.RI:
		case SerialPortEvent.TXEMPTY:
			break;
		case SerialPortEvent.RXCHAR:
			try {
				int b = 0;
                     
				while (/*entrada.available() > 0*/puertoSerie.getInputBufferBytesCount() > 0) {
                                 
                                        //					b = entrada.read();
                                        Thread.sleep(100);
                                    
                                        byporLeidos = puertoSerie.readBytes();
					Object obj = (Object) String.valueOf(b);
//					listaBytes.add(obj);
					m_iStatusScale = SCALE_READING;

					++n;
				}

				recibidosBytes = new int[n];

				int x = 0;
				int q = listaBytes.size();
				Object[] lista = listaBytes.toArray();
				while (x < q) {
					String J = (String) lista[x];
					recibidosBytes[x] = Integer.valueOf(J).intValue();
					++x;
				}

				// Fin de lectura
				synchronized (this) {
					m_iStatusScale = SCALE_READY;
					notifyAll();
				}
			}catch (SerialPortException spe) {
				spe.printStackTrace();
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}catch (InterruptedException ex) {
                                        Logger.getLogger(PortReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                                    }

			break;
		}
	}

	/**
	 * Metodo para vaciar el bufer de salida
	 */
//	private void flush() {
//		try {
//			salida.flush();
//		} catch (IOException e) {
//		}
//	}

	/**
	 * Metodo para reiniciar los elementos de repuesta
	 */
	private void resetVars() {
		recibidosBytes = null;
		this.n = 0;
		this.xor = 0;
		statusReceiver = null;
		errorReceiver = null;
		lrcReceiver = null;
		transacctionReceiver = null;
		listaBytes = new ArrayList();
		this.byporLeidos = null;
	}
	// Metodos Privados
	private void createXor(String cmd) {
		byte[] vector;
		String A = "", B = "";
		int N1 = 0, RX = 0;
		vector = cmd.getBytes();
		for (int i = 0; i < vector.length; i++) {
			int p = vector[i];
			if (p == -95) {
				p = (byte) ((char) 161);
			}
			A = String.valueOf(p);
			N1 = p;
			if (!B.equals(""))
				RX = RX ^ N1;
			else
				RX = N1;
			B = A;
		}
		int ival = RX ^ 03;
		if (vector[0] == 63)
			LRC = '?';
		else
			LRC = (char) ival;
	}

	public boolean validateBytes(byte[] palabra) {
		if (palabra.length > 1) {
			String trama = "";
			int p = 0;
			while (p < palabra.length) {
				if (palabra[p] != 0x0002 && palabra[p] != 0x0003
						&& p != (palabra.length - 1)) {
					char ch = (char) palabra[p];
					if (ch == '\uffa0') {
						trama += (char) 160;
					} else if (ch == '\uffa1') {
						trama += (char) 161;
					} else if (ch == '\uffa2') {
						trama += (char) 162;
					} else if (ch == '\uffa3') {
						trama += (char) 163;
					} else {
						trama += (char) palabra[p];
					}
				}
				++p;
			}
			this.createXor(trama);
			char xorCmd = (char) palabra[palabra.length - 1];
			if (xorCmd == LRC && palabra[palabra.length - 2] == 0x0003
					&& palabra[0] == 0x0002) {
				return true;
			} else {
				return false;
			}
		} else if (palabra[0] == 0x0005) {
			return true;
		} else if (palabra[0] == 0x0006) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * M�todo para leer los bytes en el buffer
	 */
	private void readBytes(byte[] palabra) throws IOException {
		synchronized (this) {
                    try {
                        //			salida.write(palabra);
                        puertoSerie.writeBytes(palabra);
//			flush();
                          Thread.sleep(10);
                        // Esperamos un ratito
                        while (m_iStatusScale != SCALE_READY) {
                       
                                wait(10);
                          
                        }
                        /*
                        * if(huboSenCmd) { int q = 0, limite = 10; if(is_Z) { limite = 20;
                        * is_Z = false; } while(huboSenCmd && q<limite ) { try {
                        * wait(1000); } catch (InterruptedException e) { } ++q; }
                        * huboSenCmd = true; }else { int q = 0, limite = 5; if(is_Z) {
                        * limite = 10; is_Z = false; } while(recibidosBytes== null &&
                        * q<limite ) { try { wait(1000); } catch (InterruptedException e) {
                        * } ++q; } }
                        */
                    } catch (SerialPortException ex) {
                        Logger.getLogger(PortReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (InterruptedException e) {
                            }
		}
	}
	
	// Retorna
	public void addSerialPortEvent(SerialPortEventListener se) {
		try {
			this.puertoSerie.addEventListener(se);
		} catch (SerialPortException ex) {
                Logger.getLogger(PortReadWrite.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	// Metodos Publicos
	public boolean openPort(String IpPortName) {
		this.Terminar = IpPortName;
		boolean puerto = false;
		/*try {   puertoSerie = new SerialPort(IpPortName);
			listaPuertos = CommPortIdentifier.getPortIdentifiers();
			while (listaPuertos.hasMoreElements()) {
				idPuerto = (CommPortIdentifier) listaPuertos.nextElement();
				if (idPuerto.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					// idPuerto.getName().equals("/dev/term/a") )
					// idPuerto.getName().equals("COM1") )
					if (idPuerto.getName().equals(IpPortName)) {
						// Si el puerto no esta en uso, se intenta abrir
						puertoSerie = (SerialPort) idPuerto.open(
								"AplEscritura", 2000);
						puerto = true;
						// Se obtiene un canal de salida
//						salida = puertoSerie.getOutputStream();
//						entrada = puertoSerie.getInputStream();
						puerto = puerto && true;
						// Se fijan los parametros de comunicacion del puerto
						puertoSerie.setSerialPortParams(9600,
								SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
								SerialPort.PARITY_EVEN);
						// puertoSerie.enableReceiveTimeout(10000);
						// puertoSerie.notifyOnCTS(true);
						puerto = puerto && true;
						// Se establecen las notificaciones de Eventos
						puertoSerie.notifyOnOutputEmpty(true);
						// puertoSerie.addEventListener(this);
						puertoSerie.notifyOnDataAvailable(true);
						//puertoSerie.notifyOnCTS(true);
                                                puertoSerie.notifyOnCTS;
						puertoSerie.setRTS(true);
						puerto = puerto && true;
					}
				}
			}
			if (puerto == true)
				Estado = "Puerto Abierto";
			else
				Estado = "Error al Abrir puerto";
			this.IndPuerto = puerto;
			return puerto;
		}/*
		 * catch (java.util.TooManyListenersException e) { Estado =
		 * e.getMessage(); return false; }
		 */
            try {
            // TODO code application logic here
            puertoSerie = new SerialPort(IpPortName);
                     puerto = puertoSerie.openPort();
            puertoSerie.setParams(9600,
                                SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                                SerialPort.PARITY_EVEN);
            
        } 
                catch (SerialPortException e) {
			Estado = e.getMessage();
			return false;
//		} catch (IOException e) {
//			Estado = e.getMessage();
//			return false;
//		} catch (UnsupportedCommOperationException e) {
//			Estado = e.getMessage();
//			return false;
		} catch (Exception ioe) {
			Estado = ioe.getMessage();
			return false;
		}
            return puerto;
	}

	public void closedPort() {
		try {
			puertoSerie.closePort();
			this.IndPuerto = false;

		} catch (SerialPortException e) {
		}
	}

	public byte[] readBytes() {
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//		}
//		if (recibidosBytes != null) {
//			this.byporLeidos = new byte[recibidosBytes.length];
//			int y = 0;
//			while (y < recibidosBytes.length) {
//				this.byporLeidos[y] = (byte) recibidosBytes[y];
//				++y;
//			}
//		}
		return this.byporLeidos;
	}
        
	public void sendBytes(byte[] bytesSalida, int pDimension, boolean pBloqZ) throws SerialPortException {
		this.resetVars();
                this.isBloqsZ = pBloqZ;
		String cmd = "";
		byte[] vectorbyte = null;
		if (bytesSalida != null) {
			if (bytesSalida.length > 1) {

				vectorbyte = new byte[bytesSalida.length + 3];
				vectorbyte[0] = (byte) STX;
                         
				int x = 0;
				while (x < bytesSalida.length) {
					vectorbyte[x + 1] = bytesSalida[x];
					cmd += (char) bytesSalida[x];
					++x;
				}
				this.createXor(cmd);
				if (!this.isBloqsZ) {
					vectorbyte[x + 1] = (byte) ETX;
				} else {
					vectorbyte[x + 1] = (byte) ETB;
				}
				vectorbyte[x + 2] = (byte) LRC;
			} else {
				vectorbyte = bytesSalida;
			}
			// Se envia la repuesta
			try {
                            Thread.sleep(pDimension);
				readBytes(vectorbyte);
			} catch (IOException e) {
				Estado = "Error... " + e.getMessage();
				this.closedPort();
				this.openPort(Terminar);

			} catch (NullPointerException e1) {
				Estado = "Error... " + e1.getMessage();
				if (Bandera == true && puertoSerie != null)
					puertoSerie.setRTS(false);
			} catch (Exception e8) {
				Estado = "Error... " + e8.getMessage();
				if (Bandera == true && puertoSerie != null)
					puertoSerie.setRTS(false);
			}
		}
	}
}
