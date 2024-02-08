/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package device;

import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import scripting.*;
import utility.*;
//import gnu.io.*;
import jssc.*;
import data.Formats;
import common.*;
import display.DeviceDisplay;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
import contable.TicketLineInfo;
import elements.Cashier;
import elements.Countries;
import elements.Equipments;
import elements.Flags;
import elements.FooterHeader;
import elements.MeanPayment;
import elements.Resources;
import elements.Taxes;
import java.util.List;
import javax.sound.sampled.*;
import jssc.SerialPortException;
import resources.config.AppConfig;

/**
 * 
 * @author pmoya
 */
public class DeviceFiscal {
	private Equipments _myEquipment;
	public boolean _state;
	private IFiscalAccionable _hkaFiscalSelect;
        private int _statePre = -1;
	public String _logError;
	private Resources _myResource;
	private PortReadWrite _myPort;
	private Command _myCmd;
        private DeviceTicket _myDeviceTick;
        private DataLogicSales _mDlSales;
        private PaymentsModel _mPaymentsModel;
        private Cashier _user;
        private boolean _coverOpen = false;
        private boolean _errorDrawer = false;
        private boolean _notPapel = false;
        private boolean _errorFiscalMemory = false;
        private int _ite = 0;
        public static  String VERSION = "";
        private AppConfig _config = null;

	public DeviceFiscal() {
		this._myEquipment = null;
                this._mDlSales = null;
                this._mPaymentsModel = null;
		this._state = false;
		this._logError = "";
                this._user = null;
		this._myResource = new Resources();
		this._myPort = new PortReadWrite();
                this._myDeviceTick = new DeviceTicket();
                 try {
                String args[] = new String[0];
                _config = new AppConfig(args);
                _config.load();
                _config.save();
                VERSION = this._config.getProperty("vfp.version");
            } catch (IOException ex) {
               this.log(ex.getMessage() + ". " + ex.getLocalizedMessage());
            }
	}

	public boolean startEquiment(String portName) {
		// Proceso iterativo de check Puertos Serial Virtuales Automaticamente
		// disponibles
		boolean OpenPort = this._myPort.openPort(portName);
		if (OpenPort) {                                       
			this._state = true;
                        this.displayStart();
			return true;
		} else {
			return false;
		}
	}
        
        public AppConfig getAppConfig()
        {
           return this._config;
        }
        
        private void generatePrinterStatus()
        {
            if(this.isCoverOpen())
            {
                  byte[]  arrayRep = {0x0060,0x0042};
                try {
                    this._myPort.sendBytes(arrayRep,2,false);
                } catch (SerialPortException ex) {
                    Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                }
                  this._logError = null;
            }
            else if(this.isNotPapel())
            {
                  byte[]  arrayRep = {0x0060,0x0041};
                try {
                    this._myPort.sendBytes(arrayRep ,2,false);
                } catch (SerialPortException ex) {
                    Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                }
                  this._logError = null;
            }
            else if(this.isErrorFiscalMemory())
            {
                  byte[]  arrayRep = {0x0042,0x0064};
                try {
                    this._myPort.sendBytes(arrayRep,2,false);
                } catch (SerialPortException ex) {
                    Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                }
                  this._logError = null;
            }
            else if(this.isErrorDrawer())
            {
                  byte[]  arrayRep = {0x0060,0x0070};
                try {
                    this._myPort.sendBytes(arrayRep,2,false);
                } catch (SerialPortException ex) {
                    Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                }
                  this._logError = null;
            }
            else if(this._hkaFiscalSelect.getState() == IFiscalAccionable.STATE_WAIT)
            {
                  byte[]  arrayRep = {0x0060,0x0040};
                try {
                    this._myPort.sendBytes(arrayRep,2,false);
                } catch (SerialPortException ex) {
                    Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                }
                  this._logError = null;
            }
            else if(this._hkaFiscalSelect.getState() == IFiscalAccionable.STATE_TRANSACCTION)
            {
                  byte[]  arrayRep = {0x0061,0x0040};
                try {
                    this._myPort.sendBytes(arrayRep,2,false);
                } catch (SerialPortException ex) {
                    Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                }
                  this._logError = null;
            }
            else if(this._hkaFiscalSelect.getState() == IFiscalAccionable.STATE_LOCK)
            {
                  byte[]  arrayRep = {0x0042,0x0060};
                try {
                    this._myPort.sendBytes(arrayRep,2,false);
                } catch (SerialPortException ex) {
                    Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                }
                  this._logError = null;
            }           
                
        }
        
        public void setCoverOpen(boolean pValue)
        {
           this._coverOpen = pValue;
        }
        
        public boolean isCoverOpen()
        {
           return this._coverOpen;
        }
        
        public void setErrorFiscalMemory(boolean pValue)
        {
           this._errorFiscalMemory = pValue;
        }
        
        public boolean isErrorFiscalMemory()
        {
           return this._errorFiscalMemory;
        }
        
        public void setNotPapel(boolean pValue)
        {
           this._notPapel = pValue;
        }
        
        public boolean isNotPapel()
        {
           return this._notPapel;
        }
        
        public void setErrorDrawer(boolean pValue)
        {
          this._errorDrawer = pValue;
        }
        
        public boolean isErrorDrawer()
        {
           return this._errorDrawer;
        }
        
        public void reloadPapel(double pQuantity)
        {
            if(pQuantity > 0.0)
            {
              this._myEquipment.setPapel_quantity(pQuantity);
              this._myEquipment.UpdateEquipments(_myEquipment);
              this.setNotPapel(false);
            }
        }

	public void offEquiment() {
		this._state = false;
                this._mPaymentsModel = null;
                this._myPort.closedPort();
	}
        
        private void unLockEquipment()
        {
              this._myEquipment.setStatus(IFiscalAccionable.STATE_UNLOCK);
              this._myEquipment.UpdateEquipments(_myEquipment);
              this._hkaFiscalSelect.ResetFiscalDocument();
        }
        
        public void lockEquipment(int pEquipId) throws DeviceException
        {
          if(this._state)
          {
              throw new DeviceException("El equipo debe estar apagado para esta accion.");
          }else
          {
                         this._myEquipment = new Equipments();   
                         this._myEquipment = this._myEquipment.ListEquipmentsById(pEquipId)[0];
                         this._myEquipment.setStatus(IFiscalAccionable.STATE_LOCK);
                         this._myEquipment.UpdateEquipments(_myEquipment);
          }
        }

	public void loadFirmwareSelected(int indexSelect) {
		try {   
			_hkaFiscalSelect = SelectUtil.getHKA(indexSelect);  
                         this._myEquipment = new Equipments();
                         this._myEquipment = this._myEquipment.ListEquipmentsById(indexSelect)[0];
               
                         if(this._myEquipment.getStatus() == IFiscalAccionable.STATE_LOCK)
                         { //El Equipo Esta Bloqueado
                              TicketInfo ticket = this._hkaFiscalSelect.getTicket();  
                             ticket.setEquipmet(_myEquipment);
                             this._hkaFiscalSelect.setState(IFiscalAccionable.STATE_LOCK);
                             this._hkaFiscalSelect.Lock(_myEquipment.getMachine_register());
               
                             // Paso el comando al objeto fiscal activo
			     this._myResource.ResourceByName(this._hkaFiscalSelect.getNameResourceXML());
			     int limit = (int) this._myResource.getContent().length();
			     String sresource = Formats.BYTEA.formatValue(this._myResource.getContent().getBytes(1, limit));
                            
                                    FooterHeader footHead = new FooterHeader();
                                    footHead = footHead.ListFooterHeaderById(_myEquipment.getId())[0];
                                    ticket.setFooterHeader(footHead);
                                    
                                    TicketParser m_TTP = new TicketParser(_myDeviceTick);
   
                                     ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                                     script.put("ticket", ticket);
                                     script.put("payments", this._mPaymentsModel);

                                      // m_TTP.printTicket(sresource);
                                     m_TTP.printTicket(script.eval(sresource).toString());
                                     
                                     this._hkaFiscalSelect.ResetFiscalDocument();
                                     this.playSound();
                                    
                            return;
                         }
                        if(this._myEquipment.getPapel_quantity() <= 0.0)
                        {
                            if(this._myEquipment.getPapel_quantity() < 0)
                            {
                               this._myEquipment.setPapel_quantity(0);
                               this._myEquipment.UpdateEquipments(_myEquipment);
                            }
                           this.setNotPapel(true);
                        }
                         this._mDlSales = new DataLogicSales(this._myEquipment.getId());    
                         int zActual = this._mDlSales.getSequence();
                         this._mPaymentsModel = PaymentsModel.loadInstance(zActual,this._myEquipment.getId());
                        _hkaFiscalSelect.setDataLogicSales(_mDlSales);
                        _hkaFiscalSelect.setPaymentsModel(_mPaymentsModel);
		} catch (TicketPrinterException ex) {
                 this._logError = ex.getMessage();
               } catch (ScriptException ex) {
                 this._logError = ex.getMessage();
               } catch (SerialException ex) {
                        this._logError = ex.getMessage();
                } catch (DeviceException de) {
			this._logError = de.getMessage();
		} catch (NullPointerException npe){
			 this._logError = "Equipo sin informacion. " + npe.getMessage();		
                }
	}
        
        public void addFirmware(String rutaFile) throws IOException,
			DeviceException {
  
		FileInputStream fis = new FileInputStream(rutaFile);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		File file = new File(rutaFile);
		String name = rutaFile.substring(rutaFile.lastIndexOf('\\') + 1,
				rutaFile.length() - ".xml".length());               
		byte[] buf = new byte[(int) file.length()];
		for (int readNum; (readNum = fis.read(buf)) != -1;) {
			bos.write(buf, 0, readNum);
		}
		SerialBlob blob = null;
		try {
			blob = new SerialBlob(buf);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int id = this._myResource.listMaxId();
		id++;
		this._myResource.InsertResources(id, name, 0, blob);
 
                //Cargo loa elementos de datos correspondientes
                try {
                    this._myResource.ResourceByName(name);   
                    int limit = (int) this._myResource.getContent().length();
                    String sresource  = Formats.BYTEA.formatValue(this._myResource.getContent().getBytes(1, limit));                   
                    if (sresource == null) {
                      throw new DeviceException("No se pudo cargar los datos de entidades fiscales, revisar el Firmware.");
                      } else {
                        ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                        this._myCmd = new Command(_myDeviceTick);
                        script.put("comando", this._myCmd);   
                        _myCmd._tramaByte = "XXY";
                         TicketParser m_TTP = new TicketParser(_myDeviceTick);
                         m_TTP.isNewFirmware = true;
                          m_TTP.setComando(_myCmd);
                          m_TTP.printTicket(sresource); //script.eval(name).toString()
              }
          } catch (ScriptException ex) {
            this._logError = ex.getMessage();
        }catch (SerialException ex) {
            this._logError = ex.getMessage();
          }catch (TicketPrinterException eTP) {
                  _myDeviceTick.getDeviceDisplay().writeVisor("VFP HKA", "V-1.0.0.1");
                  this._logError = eTP.getMessage();
          }

	}

	public void UpdateFirmware(String rutaFile) throws IOException, DeviceException {

		FileInputStream fis = new FileInputStream(rutaFile);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//System.out.println(rutaFile);
                
                File file = new File(rutaFile);

		String name = rutaFile.substring(rutaFile.lastIndexOf('\\') + 1, rutaFile.length() - ".xml".length());
		//System.out.println(name);
		byte[] buf = new byte[(int)file.length()];
		for (int readNum; (readNum = fis.read(buf)) != -1;) {
			bos.write(buf, 0, readNum);
		}
		Resources[] re = this._myResource.ResourceByName(name);
		Resources res = re[0];
		//System.out.println(res.getId() + " " + res.getName() + " "+ res.getResType() + " " + res.getContent());
		SerialBlob blob = null;
		try {
			blob = new SerialBlob(buf);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.setContent(blob);
		//System.out.println(res.getContent().toString());
		this._myResource.UpdateResource(res);

	}

	public void addEventDevice(SerialPortEventListener se) {
		this._myPort.addSerialPortEvent(se);
	}

	public void setEvenDataAvalie(SerialPortEvent e) {
		this._myPort.serialEvent(e);
	}

	public void capturedCommand() {
		String trx = "";              

		byte[] bytesRead = null;
		bytesRead = this._myPort.readBytes();
		while (bytesRead == null) {
			bytesRead = this._myPort.readBytes();
		}
                
                byte bit = 0;
                //Mono Byte
                if(bytesRead.length == 1)
                {
                  bit = bytesRead[0];
                  //ENQ
                   if(bit == 0x0005)
                   {
                       this.generatePrinterStatus();                    
                       return;
                   }else if(bit == 0x0006)
                   {  
                       if(_statePre == IFiscalAccionable.STATE_UPDATA)
                       {
                         if(this._myCmd._tramaByte.substring(0,2).equals("U0"))
                         {
                             try { 
                                 this._myPort.sendBytes(this._hkaFiscalSelect.getDataBufferRead(),100,false);
                             } catch (SerialPortException ex) {
                                 Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                             }
                             _statePre = IFiscalAccionable.STATE_ENDUPDATA;
                             if(this._myCmd._tramaByte.length() == 3)
                             {
                                   _statePre = IFiscalAccionable.STATE_WAIT;
                                   this._hkaFiscalSelect.setState(IFiscalAccionable.STATE_WAIT);
                             }
                         }
                           else
                         {
                             int limit = this._hkaFiscalSelect.getListPrinterData().size();
                            if(_ite < limit)
                            {
                              try
                                {
                                if(_ite == limit - 1)
                                {                                
                                    this._hkaFiscalSelect.setDataBufferRead(this._hkaFiscalSelect.getListPrinterData().get(_ite).getDataLoader());
                                    try {
                                        this._myPort.sendBytes(this._hkaFiscalSelect.getDataBufferRead(),0,false);
                                    } catch (SerialPortException ex) {
                                        Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    _ite = 0;
                                    this._hkaFiscalSelect.getListPrinterData().clear();
                                     _statePre = IFiscalAccionable.STATE_ENDUPDATA;                             
                                }
                                 else
                                {
                                    this._hkaFiscalSelect.setDataBufferRead(this._hkaFiscalSelect.getListPrinterData().get(_ite).getDataLoader());
                                    try {  
                                        this._myPort.sendBytes(this._hkaFiscalSelect.getDataBufferRead(),0,true);
                                    } catch (SerialPortException ex) {
                                        Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                  _statePre = IFiscalAccionable.STATE_UPDATA;
                                }
                                
                              ++_ite;
                                }
                              catch (DeviceException ex) {
                                    this._logError = ex.getMessage();
                                }
                            }
                         }
                       }else if(_statePre == IFiscalAccionable.STATE_ENDUPDATA)
                       {    
                           if(!this._myCmd._tramaByte.substring(0,2).equals("U0"))
                           {
                             byte[]  arrayRep = {0x0004};
                               try {
                                   this._myPort.sendBytes(arrayRep,0,false);
                               } catch (SerialPortException ex) {
                                   Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                               }
                           }
                            this._logError = null;           
                            _statePre = IFiscalAccionable.STATE_WAIT;
                             this._hkaFiscalSelect.setState(IFiscalAccionable.STATE_WAIT);
                       }
                       else
                       {                                          
                        byte[]  arrayRep = {0x0006};
                           try {
                               this._myPort.sendBytes(arrayRep,0,false);
                           } catch (SerialPortException ex) {
                               Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                           }
                        this._hkaFiscalSelect.setState(IFiscalAccionable.STATE_WAIT);
                        this._logError = null; 
                       }
                            
                        return;
                   }
                }
                //Poli Bytes
		if (this._myPort.validateBytes(bytesRead)) {
			int x = 0;		
			while (x < bytesRead.length) {
				bit = bytesRead[x];
				if (bytesRead.length > 0) {
					if (bit != 0x0002 && bit != 0x0003
							&& x != (bytesRead.length - 1)) {
						if ((char) bit == '\uffa0') {
							trx += (char) 160;
						} else if ((char) bit == '\uffa1') {
							trx += (char) 161;
						} else if ((char) bit == '\uffa2') {
							trx += (char) 162;
						} else if ((char) bit == '\uffa3') {
							trx += (char) 163;
						} else {
							trx += (char) bit;
						}
					}
				}

				++x;
			}
                           if(this._hkaFiscalSelect.getState() == IFiscalAccionable.STATE_LOCK || this.isCoverOpen() || this.isErrorFiscalMemory() || this.isNotPapel())
                           {
                              byte[] rep = new byte[1];
			       rep[0] = 0x0015;
                            try {
                                this._myPort.sendBytes(rep,0,false);
                            } catch (SerialPortException ex) {
                                Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                            }
			       this._logError = "Error de Comunicacion.";
                               
                               return;
                           }
				this._myCmd = new Command(this._myDeviceTick);
				this._myCmd._tramaByte = trx;
				byte[] rep = new byte[1];
                                _statePre = this._hkaFiscalSelect.getState();
				if (this.procecedCommand(this._myCmd)) {
                                      
                                    if(this._hkaFiscalSelect.getState() == IFiscalAccionable.STATE_UPDATA)
                                      {
                                          if(this._myCmd._tramaByte.substring(0,1).equals("U"))
                                          {                                              
                                              _statePre = this._hkaFiscalSelect.getState();
                                              rep = new byte[1];
			                      rep[0] = 0x0005;
                                              try {
                                                  this._myPort.sendBytes(rep,10,false);
                                              } catch (SerialPortException ex) {
                                                  Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                                              }
                                              return;
                                          }
                                            else
                                          {
                                              try {                                   
                                                  this._myPort.sendBytes(this._hkaFiscalSelect.getDataBufferRead(),0,false);
                                              } catch (SerialPortException ex) {
                                                  Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                                              }
                                          }  
                                         
                                          if(_statePre  == IFiscalAccionable.STATE_TRANSACCTION)
                                          {
                                              this._hkaFiscalSelect.setState(IFiscalAccionable.STATE_TRANSACCTION); 
                                          }else if(_statePre  == IFiscalAccionable.STATE_UPDATA)
                                          {
                                                 return;
                                          }
                                           else
                                          {   
                                              _ite = 0;
                                              this._hkaFiscalSelect.setState(IFiscalAccionable.STATE_WAIT);
                                              this._hkaFiscalSelect.ResetFiscalDocument();
                                          }
                                          
                                         this._logError = null;
                                      }
                                      else
                                      {
					rep[0] = 0x0006;
                                        try {
                                            this._myPort.sendBytes(rep,0,false);
                                        } catch (SerialPortException ex) {
                                            Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                                        }
					this._logError = null;
                                      }
				} else {
					rep[0] = 0x0015;
                            try {
                                this._myPort.sendBytes(rep,0,false);
                            } catch (SerialPortException ex) {
                                Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                            }
					this._logError = "Comando invalido.";
				}
			
		} else {
			byte[] rep = new byte[1];
			rep[0] = 0x0015;
                    try {
                        this._myPort.sendBytes(rep,0,false);
                    } catch (SerialPortException ex) {
                        Logger.getLogger(DeviceFiscal.class.getName()).log(Level.SEVERE, null, ex);
                    }
			this._logError = "Comando invalido.";
		}
	}

	public boolean procecedCommand(Command pCmd) {
		// Valido Formato de Protocolo
		try { 
                       TicketInfo ticket = this._hkaFiscalSelect.getTicket();  
                       ticket.setEquipmet(_myEquipment);

                       // Paso el comando al objeto fiscal activo
			this._myResource.ResourceByName(this._hkaFiscalSelect.getNameResourceXML());
			int limit = (int) this._myResource.getContent().length();
			pCmd._contentResource = Formats.BYTEA.formatValue(this._myResource.getContent().getBytes(1, limit));     
			this._hkaFiscalSelect.setCommad(pCmd);  
                       
                       if(this._hkaFiscalSelect.getState() == IFiscalAccionable.STATE_UPDATA)
                        {     
                            if(ticket.getUser() != null)
                            {
                              _user = ticket.getUser();
                              ticket.setUser(_user);
                            }
                              FooterHeader footHead = new FooterHeader();
                              footHead = footHead.ListFooterHeaderById(_myEquipment.getId())[0];
                              ticket.setFooterHeader(footHead);
                              if(this._hkaFiscalSelect.getListPrinterData().size() > 0)
                              {
                                  int h = 0;
                                  while(h < this._hkaFiscalSelect.getListPrinterData().size())
                                  {
                                      this._hkaFiscalSelect.getListPrinterData().get(h).loadData(_mDlSales, ticket, _mPaymentsModel);
                                    ++h;
                                  }
                              }
                              else
                              {
                                this._hkaFiscalSelect.getPrinterData().loadData(_mDlSales, ticket, _mPaymentsModel);     
                                this._hkaFiscalSelect.setDataBufferRead(this._hkaFiscalSelect.getPrinterData().getDataLoader());
                              }
			
                        }else
                         {    
                                   if(ticket.getUser() != null)
                                     {
                                       _user = ticket.getUser();
                                       ticket.setUser(_user);
                                      }
                            if (this._hkaFiscalSelect.getTypeDisplay() == IFiscalAccionable.PRINTABLE)
                            {       
                                    ticket.setTicketId( this._mDlSales.getNewTicket_Id(ticket.getTicketType()));                                   
                                    FooterHeader footHead = new FooterHeader();
                                    footHead = footHead.ListFooterHeaderById(_myEquipment.getId())[0];
                                    ticket.setFooterHeader(footHead);
                                    // Imprimo en Dysplay

                                    ticket.setActiveCash(_mPaymentsModel.getMoney());
                                    //Guardo los datos del Documento o Recibo en la Memoria de Auditoria y Fiscal
                                    this._mDlSales.saveTicket(ticket, null);     
                                    TicketParser m_TTP = new TicketParser(_myDeviceTick);
                                    
                                     String sresource = pCmd._contentResource;    
                                     ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                                     script.put("ticket", ticket);
                                     script.put("payments", this._mPaymentsModel);

                                      // m_TTP.printTicket(sresource);
                                     m_TTP.printTicket(script.eval(sresource).toString());
                                     //Consumo Papel
                                     double qPapel = this._myEquipment.getPapel_quantity();
                                     this._myEquipment.setPapel_quantity(qPapel - 15.15);
                                     this._myEquipment.UpdateEquipments(_myEquipment);
                                     if(this._myEquipment.getPapel_quantity() <= 0.0)
                                     {
                                         this.setNotPapel(true);
                                     }
                                     this._hkaFiscalSelect.ResetFiscalDocument();
                                     this.playSound();
                            }else
                            {
                                if(this._hkaFiscalSelect.getState() == IFiscalAccionable.STATE_TRANSACCTION)
                                {
                                   this.displayLine();
                                }
                                else if(this._hkaFiscalSelect.getState() == IFiscalAccionable.STATE_UNLOCK)
                                {
                                   this.unLockEquipment();
                                }
                            }
                         }
                       
                           if(this._hkaFiscalSelect.getResult() == IFiscalAccionable.RESPONSE_OK)
			     return true;
                            else
                             return false;
                        
		} catch (SerialException ex) {
			this._logError = ex.getMessage();
			return false;
		} catch (DeviceException de) {
			this._logError = de.getMessage();
			return false;
		} catch (TicketPrinterException etp) {
                        this._logError = etp.getMessage();
			return false;
                //m_TP.getDeviceDisplay().writeVisor(AppLocal.APP_NAME, AppLocal.APP_VERSION);
                }catch (ScriptException se) {
                        this._logError = se.getMessage();
			return false;
                }
	}
        
        public DevicePrinter getDevicePrinter()
        {
          return this._myDeviceTick.getDevicePrinter("1");
        }
        
        public DeviceDisplay getDeviceDisplay()
        {
           return this._myDeviceTick.getDeviceDisplay();
        }
        
        public Equipments getEquipment()
        {
           return this._myEquipment;
        }
        
        private void displayStart()
        {
        try {
            this._myResource.ResourceByName("Printer.Start");   
            int limit = (int) this._myResource.getContent().length();
            String sresource  = Formats.BYTEA.formatValue(this._myResource.getContent().getBytes(1, limit));
            TicketParser m_TTP = new TicketParser(_myDeviceTick);
            if (sresource == null) {
              _myDeviceTick.getDeviceDisplay().writeVisor("VFP HKA", "V-1.0.0.1");
              } else {
                  m_TTP.printTicket(sresource);            
              }
          }catch (SerialException ex) {
            this._logError = ex.getMessage();
          }catch (TicketPrinterException eTP) {
                  _myDeviceTick.getDeviceDisplay().writeVisor("VFP HKA", "V-1.0.0.1");
                  this._logError = eTP.getMessage();
          }
        }
        
        private void displayLine()
        {
            TicketInfo ticket = this._hkaFiscalSelect.getTicket();  
                       ticket.setEquipmet(_myEquipment);
                       
              if(ticket.getLinesCount() > 0)
              {
           try {
             
                 TicketLineInfo ticketline =  ticket.getLine(ticket.getLinesCount() -1);              
                       
                    this._myResource.ResourceByName("Printer.TicketLine");   
                    int limit = (int) this._myResource.getContent().length();
                    String sresource  = Formats.BYTEA.formatValue(this._myResource.getContent().getBytes(1, limit));
                    TicketParser m_TTP = new TicketParser(_myDeviceTick);
                    if (sresource == null) {
                      _myDeviceTick.getDeviceDisplay().writeVisor("VFP HKA", "V-3.0.0.0");
                      } else {
                         ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                                             script.put("ticketline",ticketline);
                          m_TTP.printTicket(script.eval(sresource).toString());            
                      }
                  } catch (ScriptException ex) {
                    this._logError = ex.getMessage();
                  }catch (SerialException ex) {
                    this._logError = ex.getMessage();
                  }catch (TicketPrinterException eTP) {
                          _myDeviceTick.getDeviceDisplay().writeVisor("VFP HKA", "V-3.0.0.0");
                          this._logError = eTP.getMessage();
                  }
            }
        
        
        }
        
       private void playSound()
        { 
              // File sf=new File("/opt/VFPHKA-"+ VERSION +"/resources/sounds/Dot_Matrix_Printer.wav");   //LINUX
             File sf=new File("C:\\VFPHKA-"+ VERSION +"\\resources\\sounds\\Dot_Matrix_Printer.wav");  //WINDOWS
              AudioFileFormat aff; 
              AudioInputStream ais; 

              try{ 
                 aff=AudioSystem.getAudioFileFormat(sf); 
                 ais=AudioSystem.getAudioInputStream(sf); 
                 AudioFormat af=aff.getFormat(); 
                 DataLine.Info info = new DataLine.Info( 
                                Clip.class, 
                                ais.getFormat(), 
                                ((int) ais.getFrameLength() * 
                                af.getFrameSize())); 
                 Clip ol = (Clip) AudioSystem.getLine(info); 
                 ol.open(ais); 
                 ol.loop(1);//Clip.LOOP_CONTINUOUSLY 
                 Thread.sleep(3000);
                 ol.close();
              } 
              catch(UnsupportedAudioFileException ee){
                String mjs =   ee.getMessage();
              } 
              catch(IOException ea){
              String mjs =   ea.getMessage();
              } 
              catch(LineUnavailableException LUE){
           String mjs =   LUE.getMessage();
              }catch(InterruptedException ie)
              {}
        }    

    private void log(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
