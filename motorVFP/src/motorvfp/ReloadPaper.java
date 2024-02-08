package motorvfp;

import java.awt.*;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import device.DeviceFiscal;
import elements.Equipments;

public class ReloadPaper extends JFrame {

	private JPanel contentPane;
        private DeviceFiscal _myDevice;
        private SpinnerNumberModel sp;

	/**
	 * Launch the application.
	 */
        /*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
				try {
					ReloadPaper frame = new ReloadPaper();                                    
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);                                       
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
        */
      

	/**
	 * Create the frame.
	 */
	public ReloadPaper(DeviceFiscal pDevice) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 215, 146);
                Dimension dim =  this.getToolkit().getScreenSize();
                Rectangle abounds = this.getBounds();
                this.setLocation((dim.width - abounds.width) / 2, (dim.height - abounds.height) / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setForeground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("Trebuchet MS", Font.BOLD, 26));
		spinner.setForeground(Color.WHITE);
		spinner.setBackground(Color.WHITE);
		spinner.setBounds(39, 45, 91, 42);
		panel.add(spinner);
		sp = new SpinnerNumberModel();
		sp.setMaximum(3000);
		sp.setMinimum(0);
		sp.setStepSize(500);
		spinner.setModel(sp);
		
		JLabel lblPapelPorMetros = new JLabel("Centimeter Paper");
		lblPapelPorMetros.setForeground(Color.WHITE);
		lblPapelPorMetros.setBackground(Color.BLACK);
		lblPapelPorMetros.setBounds(10, 11, 115, 14);
		panel.add(lblPapelPorMetros);
		
		JButton btnCerrar = new JButton("");
                this._myDevice = pDevice;
          if(_myDevice != null)
          {
              btnCerrar.setIcon(new ImageIcon("C:\\VFPHKA-"+this._myDevice.getAppConfig().getProperty("vfp.version")+"\\dist\\reload.png")); //Windows
                //btnCerrar.setIcon(new ImageIcon("/opt/VFPHKA-"+this._myDevice.getAppConfig().getProperty("vfp.version")+"/dist/reload.png"));  //Linux
              Equipments eq = this._myDevice.getEquipment();
              if(eq != null)
              {
                  sp.setValue(eq.getPapel_quantity()); 
              }
           }
		
		btnCerrar.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                            reloadPapelDevice(e);				
			}
         
		});
		btnCerrar.setBounds(148, 2, 41, 23);
		panel.add(btnCerrar);
	}
        
         private void reloadPapelDevice(ActionEvent e) {
                if(this._myDevice != null)
                {
                    this._myDevice.reloadPapel(Double.valueOf(sp.getValue().toString()));
                }
                this.dispose();
            }
}
