package motorvfp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JCheckBox;


//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageDecoder;
//import com.sun.imageio.plugins.jpeg.JPEG;

import common.*;
import device.DeviceFiscal;
import elements.*;
import elements.ListPorts;
//import gnu.io.SerialPortEvent;
//import gnu.io.SerialPortEventListener;
import jssc.*;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.border.CompoundBorder;
import javax.swing.JComponent;
import java.awt.SystemColor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;


public class ViewMain extends JFrame implements SerialPortEventListener{

	private JPanel contentPane;
        private JComponent _displayPanel;
    ListPorts listp = new ListPorts();
    Countries countries = new Countries();
    private DeviceFiscal _myEfiscal = new DeviceFiscal();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
				try {
					ViewMain frame = new ViewMain();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public ViewMain() {           
		setAutoRequestFocus(false);
		BtnFirmWare = new JButton("Firmware");
		BtnFirmWare.setBounds(119, 495, 91, 28);
		CbxPort = new JComboBox();
		CbxPort.setBounds(215, 495, 73, 28);
		CbxPais = new JComboBox(); 
		CbxPais.setBounds(10, 495, 105, 28);
		JPanelColor = new JPanel();
		JPanelColor.setBounds(367, 495, 30, 28);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 421, 709);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnConfiguracion = new JMenu("Configuration");
		menuBar.add(mnConfiguracion);
		
		menuBar_1 = new JMenuBar();
		mnConfiguracion.add(menuBar_1);
		
		mntmRamClear = new JMenuItem("Ram Clear");
		mnConfiguracion.add(mntmRamClear);
                //mntmRamClear.setEnabled(false);
                mntmRamClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
			ejectRamClear(e);
		    } catch (DeviceException ex) {
			MessageInf msg = new MessageInf(MessageInf.SGN_CAUTION, " OK",
						ex.getMessage());
				msg.show(new Frame());
		      }             
            }
        });
		
		mntmRecargarPapel = new JMenuItem("Reload Paper");
                mntmRecargarPapel.setEnabled(false);
		mnConfiguracion.add(mntmRecargarPapel);
		mntmRecargarPapel.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                                     showLoadPapelView(evt);					
			}

            private void showLoadPapelView(ActionEvent evt) {
                             ReloadPaper rp = new ReloadPaper(_myEfiscal);					     
                                        rp.setVisible(true);
            }
		});
		
		mntmAgregarRecurso = new JMenuItem("Add Resource");
		mntmAgregarRecurso.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent evt) {
				try {
					mnAddRecursoActionPerformed(evt);
				} catch (DeviceException e) {
					e.printStackTrace();
				} catch (Throwable ex) {
                    Logger.getLogger(ViewMain.class.getName()).log(Level.SEVERE, null, ex);
                }
			}
		});
		mnConfiguracion.add(mntmAgregarRecurso);
                
                mntmCleanBandeja = new JMenuItem("Clean Tray");
                mntmCleanBandeja.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent evt) {
				try {
					mntmCleanActionPerformed(evt);
				} catch (DeviceException e) {
					e.printStackTrace();
				}
			}
		});
		
                mnConfiguracion.add(mntmCleanBandeja);
		//mntmAgregarPais = new JMenuItem("Agregar Pais");
		//mnConfiguracion.add(mntmAgregarPais);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane(); 
		scrollPane.setViewportBorder(new CompoundBorder(new CompoundBorder(null, new CompoundBorder()), null));
		scrollPane.setBounds(10, 89, 387, 400);
		scrollPane.setViewportView(this._myEfiscal.getDevicePrinter().getPrinterComponent());
                
		CbxPais.setFont(new Font("Tahoma", Font.BOLD, 11));
		CbxPais.setForeground(Color.BLACK);
		Countries[] paises = countries.listCountries();
		for (int i = 0; i < paises.length; i++) {
			CbxPais.addItem(paises[i].getDescription());
		}
		
		BtnFirmWare.setFont(new Font("Tahoma", Font.BOLD, 11));
		BtnFirmWare.setForeground(Color.BLACK);
		BtnFirmWare.setEnabled(false);
		BtnFirmWare.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButtonFirmwareActionPerformed(evt);
				} catch (DeviceException e) {
					e.printStackTrace();
				} catch (Throwable ex) {
                    Logger.getLogger(ViewMain.class.getName()).log(Level.SEVERE, null, ex);
                }
			}
		});
		
		CbxPort.setFont(new Font("Tahoma", Font.BOLD, 11));
		CbxPort.setForeground(Color.BLACK);
//		List<String> ports = listp.PortsAvailable();
//		for (int i = 0; i < ports.size(); i++) {
//			CbxPort.addItem(ports.get(i));
//		}
                CbxPort.setEnabled(false);
		
		JButton BtnOnOff = new JButton("On/Off");
		BtnOnOff.setBounds(292, 495, 73, 28);
		BtnOnOff.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnOnOffActionPerformed(evt);
			}
		});

		BtnOnOff.setFont(new Font("Tahoma", Font.BOLD, 11));
		BtnOnOff.setForeground(Color.BLACK);
		BtnOnOff.setText("ON/OFF");
		
		JPanelColor.setBackground(Color.RED);
                
                chckbxErrorDrawe = new JCheckBox("MEFIS Exhaustion");
		chckbxErrorDrawe.setBackground(SystemColor.controlHighlight);
		chckbxErrorDrawe.setBounds(280, 66, 130, 23);
		chckbxErrorDrawe.setFont(new Font("Tahoma", Font.BOLD, 11));
                chckbxErrorDrawe.addActionListener(new java.awt.event.ActionListener() {
                @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        checkSeletedErrorDrawe(evt);
                    }
           
                });
   
                chckbxErrorMeFis = new JCheckBox("Error MEFISC");
		chckbxErrorMeFis.setBackground(SystemColor.controlHighlight);
		chckbxErrorMeFis.setBounds(280, 45, 108, 23);
		chckbxErrorMeFis.setFont(new Font("Tahoma", Font.BOLD, 11));
                chckbxErrorMeFis.addActionListener(new java.awt.event.ActionListener() {
                @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        checkSeletedErrorMefisc(evt);
                    }
           
                });
		
		chckbxTapaAbierta = new JCheckBox("Open Cover");
		chckbxTapaAbierta.setBackground(SystemColor.controlHighlight);
		chckbxTapaAbierta.setBounds(280, 24, 100, 23);
		chckbxTapaAbierta.setFont(new Font("Tahoma", Font.BOLD, 11));
                chckbxTapaAbierta.addActionListener(new java.awt.event.ActionListener() {
                @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        checkSeletedTapaAbierta(evt);
                    }
           
                });
		
		chckbxQuitarRoyo = new JCheckBox("Remove Roll");
		chckbxQuitarRoyo.setBackground(SystemColor.controlHighlight);
		chckbxQuitarRoyo.setBounds(280, 3, 100, 23);
		chckbxQuitarRoyo.setFont(new Font("Tahoma", Font.BOLD, 11));
                chckbxQuitarRoyo.addActionListener(new java.awt.event.ActionListener() {
                @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        checkSeletedQuitarPapel(evt);
                    }
                });
                
		contentPane.setLayout(null);
		contentPane.add(chckbxTapaAbierta);
		contentPane.add(chckbxQuitarRoyo);
                contentPane.add(chckbxErrorMeFis);
                contentPane.add(chckbxErrorDrawe);
		contentPane.add(CbxPais);
		contentPane.add(BtnFirmWare);
		contentPane.add(CbxPort);
		contentPane.add(BtnOnOff);
		contentPane.add(JPanelColor);
		contentPane.add(scrollPane);
		
		lblNewLabel = new JLabel("");
		File f = new File("C:\\VFPHKA-"+this._myEfiscal.getAppConfig().getProperty("vfp.version")+"\\dist\\image.jpg");   //Windows
               // File f = new File("/opt/VFPHKA-"+this._myEfiscal.getAppConfig().getProperty("vfp.version")+"/dist/image.jpg"); // Linux
		//System.out.println(f.getAbsolutePath());
		lblNewLabel.setIcon(new ImageIcon(f.getAbsolutePath()));
		//lblNewLabel.setIcon(new ImageIcon("C:\\Users\\jmelendez.FACTORY421\\workspace\\motorVFP\\src\\Img\\Logo.jpg"));
		lblNewLabel.setBounds(10, 528, 385, 122);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 248, 88);
                _displayPanel = this._myEfiscal.getDeviceDisplay().getDisplayComponent();
                _displayPanel.setVisible(false);
                panel.add(_displayPanel);
		contentPane.add(panel);
		
	}

	private JButton BtnFirmWare;
	private JComboBox CbxPort;
	private JComboBox CbxPais;
	private JPanel JPanelColor;
	private JMenuBar menuBar;
	private JMenu mnConfiguracion;
	private JMenuItem mntmAgregarPais;
	private JMenuBar menuBar_1;
	private JMenuItem mntmAgregarRecurso;
        private JMenuItem mntmCleanBandeja;
	private JMenuItem mntmRecargarPapel;
	private JMenuItem mntmRamClear;
	private JLabel lblNewLabel;
        private JCheckBox chckbxErrorDrawe;
        private JCheckBox chckbxErrorMeFis;
        private JCheckBox chckbxTapaAbierta;
        private JCheckBox chckbxQuitarRoyo;
	
	private void BtnOnOffActionPerformed(ActionEvent evt) {
		if (!_myEfiscal._state) {
                    countries = countries.getContryByName((String)CbxPais.getItemAt(CbxPais.getSelectedIndex()));    
                       if(countries == null)
                       { return; }
			_myEfiscal.loadFirmwareSelected(countries.getId());
			//String puerto =  this._myEfiscal.getAppConfig().getProperty("vfp.portnamelnx"); 
                        String puerto =  this._myEfiscal.getAppConfig().getProperty("vfp.portnamewin");
			boolean rep = _myEfiscal.startEquiment(puerto);
			if (rep) {
                            this.CbxPort.addItem(_myEfiscal.getEquipment().getModel());
                             CbxPort.setEnabled(true);
                             _displayPanel.setVisible(true);
				_myEfiscal.addEventDevice(this);
				JPanelColor.setBackground(Color.GREEN);
				BtnFirmWare.setEnabled(true);     
                                mntmRecargarPapel.setEnabled(true);
                                _myEfiscal.setErrorDrawer(chckbxErrorDrawe.isSelected());                                                         
                                _myEfiscal.setErrorFiscalMemory(chckbxErrorMeFis.isSelected());
                                _myEfiscal.setCoverOpen(chckbxTapaAbierta.isSelected());
                                if(!_myEfiscal.isNotPapel())
                                _myEfiscal.setNotPapel(chckbxQuitarRoyo.isSelected());
                                
			}
		} else {
                    
			_myEfiscal.offEquiment();
                         CbxPort.setEnabled(false);
                         CbxPort.removeAllItems();
			BtnFirmWare.setEnabled(false);
                        mntmRecargarPapel.setEnabled(false);
                         _displayPanel.setVisible(false);
                         this._myEfiscal.getDevicePrinter().reset();
			JPanelColor.setBackground(Color.RED);
		}

	}             
        
        private void ejectRamClear(ActionEvent evt)throws DeviceException
        {
         int res = JOptionPane.showConfirmDialog(new Frame(), "Do you want to do Ram Clear?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
              if (res == JOptionPane.YES_OPTION) 
              {
                  try
                  {
                  this._myEfiscal.lockEquipment(CbxPais.getSelectedIndex() + 1);   
                  }catch(DeviceException dex){
                         throw dex;            
                  }
              }
        }
        
        private void checkSeletedQuitarPapel(ActionEvent evt) {
            if(this._myEfiscal.getEquipment() != null)
            {
                if(this.chckbxQuitarRoyo.isSelected())
                {
                   this._myEfiscal.setNotPapel(true);
                }
                else
                {
                   this._myEfiscal.setNotPapel(false);
                }
            }
        }
        
        private void checkSeletedTapaAbierta(ActionEvent evt) {
            if(this._myEfiscal.getEquipment() != null)
            {
                if(this.chckbxTapaAbierta.isSelected())
                {
                   this._myEfiscal.setCoverOpen(true);
                }
                else
                {
                   this._myEfiscal.setCoverOpen(false);
                }
            }
        }
        
        private void checkSeletedErrorMefisc(ActionEvent evt) {
            if(this._myEfiscal.getEquipment() != null)
            {
                if(this.chckbxErrorMeFis.isSelected())
                {
                   this._myEfiscal.setErrorFiscalMemory(true);
                }
                else
                {
                   this._myEfiscal.setErrorFiscalMemory(false);
                }
            }
        }
        
        private void checkSeletedErrorDrawe(ActionEvent evt) {
          if(this._myEfiscal.getEquipment() != null)
            {
                if(this.chckbxErrorDrawe.isSelected())
                {
                   this._myEfiscal.setErrorDrawer(true);
                }
                else
                {
                   this._myEfiscal.setErrorDrawer(false);
                }
            }
        }

	private void jButtonFirmwareActionPerformed(ActionEvent evt)
			throws DeviceException {
		javax.swing.JFileChooser fileop = new javax.swing.JFileChooser();
		ImgFileFilter filtro = new ImgFileFilter();
		fileop.setFileFilter(filtro);
		int returnVal = fileop.showOpenDialog(null);
		if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
			try {
				String direc = fileop.getSelectedFile().getPath();
				this._myEfiscal.UpdateFirmware(direc);
//                                this._myEfiscal.UpdateFirmware("C:\\Users\\dsanchez\\Desktop\\Integration\\General\\Proyects\\Current proyects\\Current VFP TFHKA ESTABLE Factura 10-2-15\\Proyecto\\tf_hka_vfp\\V-2.0.3.0\\motorVFP3\\src\\resources\\firmwares\\comandosVe.xml");
				MessageInf msg = new MessageInf(MessageInf.SGN_SUCCESS, " Firmware Updated",
						" Firmware Updated.");
				msg.show(this);
			} catch (IOException ex) {
				MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, "Error",
						ex);
				msg.show(this);
			}
		}

	}
        
        private void mntmCleanActionPerformed(ActionEvent evt)throws DeviceException {
            if(this._myEfiscal._state)
            this._myEfiscal.getDevicePrinter().reset();
        }

	private void mnAddRecursoActionPerformed(ActionEvent evt)
			throws DeviceException {//Take out comments after Venezuela is done !!!
		javax.swing.JFileChooser fileop = new javax.swing.JFileChooser();
		ImgFileFilter filtro = new ImgFileFilter();
		fileop.setFileFilter(filtro); 
		int returnVal = fileop.showOpenDialog(null);
		if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
			try {
				String direc = fileop.getSelectedFile().getPath();
//                                this._myEfiscal.addFirmware("C:\\Users\\dsanchez\\Desktop\\VFP TFHKA ESTABLE Factura 10-2-15\\Proyecto\\tf_hka_vfp\\V-2.0.3.0\\motorVFP3\\src\\resources\\firmwares\\comandosVe.xml");
				this._myEfiscal.addFirmware(direc);
                                MessageInf msg = new MessageInf(MessageInf.SGN_SUCCESS, " Added Firmware successfully. Please restart the aplication.",
						"Added Firmware successfully. Please restart the aplication.");
//                                ViewMain frame = new ViewMain();
//                                frame.setVisible(true);
//				frame.setLocationRelativeTo(null);
				msg.show(this);
                            try {
                                System.exit(0);
                            } catch (Throwable ex) {
                                Logger.getLogger(ViewMain.class.getName()).log(Level.SEVERE, null, ex);
                            }
			} catch (IOException ex) {
				MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, "Error",
						ex);
				msg.show(this);
			}
		}

	}

	@Override
	public void serialEvent(SerialPortEvent e) {
		switch (e.getEventType()) {
//		case SerialPortEvent.BI:
//		case SerialPortEvent.OE:
//		case SerialPortEvent.FE:
//		case SerialPortEvent.PE:
//		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
//		case SerialPortEvent.RI:
//		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                case SerialPortEvent.TXEMPTY:
			break;
		case SerialPortEvent.RXCHAR:
			this._myEfiscal.setEvenDataAvalie(e);
			this._myEfiscal.capturedCommand();
			break;
		}

	}

}
