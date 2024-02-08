/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.ArrayList;
import java.util.List;
import scripting.*;

/**
 *
 * @author pmoya
 */
public class Command {
    public int _time;
    public String _tramaByte;
    public String _acction;
    public String _description; 
    private TicketParser _myTicketParser;
    private List<String> _partVar;
    public String _cmdError;
    public String _contentResource;
    
    public Command(DeviceTicket pDevTicket)
    {
       this._acction = null;
       this._description = null;
       this._time = 0;
       this._tramaByte = null;
       this._myTicketParser = new TicketParser(pDevTicket);
       this._contentResource = null;
       this._cmdError = null;
    }
    
    public boolean isValido(String pNameResourceXML)
    {
        this.FindRequetsResource(pNameResourceXML);
        
        if(this._acction != null)
           return true;
        else
        { 
            this._cmdError = "Invalid Command";
            return false;
        }
    }
    
     public List<String> getChars()
     {
        String parte = this._tramaByte.substring(1);   
        this._partVar = new ArrayList<String>();
        char[] chares = parte.toCharArray();     
        int i = 0;
        while(i < chares.length)
        {
            this._partVar.add(chares[i] + "");
           ++i;
        }
        
        return this._partVar;
        
     }
     
     public String getTramaByte()
     {
         return this._tramaByte;
     }
        
     public String getHexaToString(char Hexa){
         System.err.println("Char: " + Hexa + "String value: " + String.valueOf(Hexa));
         return String.valueOf(Hexa);
     }
     
     public String getPartIni()
     {
            char[] ch =this._tramaByte.toCharArray();

            int u = ch[0];
            if(ch[0] == '\u00a0')
                return "-";
            else if(ch[0] == '\u00a1')
                return "*";
            else if(ch[0] == '\u00a2')
                return "/";
            else if(ch[0] == '\u00a3')
                return "+";
            else
                return String.valueOf(ch[0]);
                    
    }
    
    private void FindRequetsResource(String pNameResourceXML)
    {         
                     if (pNameResourceXML == null) {
                         this._acction = null;
                         this._cmdError = "Comando invalido.";
           // m_TP.getDeviceDisplay().writeVisor(AppLocal.APP_NAME, AppLocal.APP_VERSION);
        } else {
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                script.put("comando", this);
                script.put("ticket", new contable.TicketInfo(""));
                this._myTicketParser.setComando(this);
                 // _myTicketParser.printTicket(sresource);
                _myTicketParser.printTicket(script.eval(pNameResourceXML).toString());
                 this._acction = _myTicketParser.getRepComando()._acction;
                 this._time = _myTicketParser.getRepComando()._time;

            } catch (TicketPrinterException eTP) {
                 this._cmdError = "Error de Comunicacion. " + eTP.getMessage();
                 this._acction = null;
                //m_TP.getDeviceDisplay().writeVisor(AppLocal.APP_NAME, AppLocal.APP_VERSION);
            }catch (ScriptException Se) {
                  this._cmdError = "Error de Comunicacion. " + Se.getMessage();
                 this._acction = null;
            }
        }  
    }
}
