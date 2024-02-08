/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.*;
import java.awt.image.BufferedImage;
import java.applet.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import display.*;
import elements.Cashier;
import elements.Countries;
import elements.Equipments;
import elements.Flags;
import elements.FooterHeader;
import elements.MeanPayment;
import elements.Resources;
import elements.Taxes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
/**
 *
 * @author pmoya
 */
public class TicketParser extends DefaultHandler{
      private static SAXParser m_sp = null;
    
    private DeviceTicket m_printer;
    
    private StringBuffer text;
    
    private String bctype;
    private String bcposition;
    private int m_iTextAlign;
    private int m_iTextLength;
    private int m_iTextStyle;
    
    private StringBuffer m_sVisorLine;
    private int m_iVisorAnimation;
    private String m_sVisorLine1;
    private String m_sVisorLine2;
    
    private double m_dValue1;
    private double m_dValue2;
    private int attribute3;
    private String atributeAux = "";
    private Command m_command;
    private String tramaTemp;
    private String tagEntityName = "";
    private List<String> datosEntities;
    public boolean isNewFirmware = false;
    
    private int m_iOutputType;
    private static final int OUTPUT_NONE = 0;
    private static final int OUTPUT_DISPLAY = 1;
    private static final int OUTPUT_TICKET = 2;
    private static final int OUTPUT_FISCAL = 3;
    private static final int OUTPUT_COMANDO = 4;
    private static final int OUTPUT_ENTITY = 5;
    private DevicePrinter m_oOutputPrinter;
    
    
    /** Creates a new instance of TicketParser */
    public TicketParser(DeviceTicket printer) {
        m_printer = printer;
    }
    
    public void printTicket(String sIn) throws TicketPrinterException {
    	
        printTicket(new StringReader(sIn));
    	
    }
    
    public void setComando(Command comand)
    {
       this.m_command = comand;
    }
    
    public Command getRepComando()
    {
       return this.m_command;
    }
    
    public void printTicket(Reader in) throws TicketPrinterException  {
        
        try {
            
            if (m_sp == null) {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                m_sp = spf.newSAXParser();
            }
            m_sp.parse(new InputSource(in), this);
                        
        } catch (ParserConfigurationException ePC) {
            throw new TicketPrinterException("Error de parseo." , ePC);
        } catch (SAXException eSAX) {
            throw new TicketPrinterException("Error en el formato del recurso." , eSAX);
        } catch (IOException eIO) {
            throw new TicketPrinterException("Error en entrada y salida de buffer.", eIO);
        }
    }    
    
    @Override
    public void startDocument() throws SAXException {
        // inicalizo las variables pertinentes
        text = null;
        bctype = null;
        bcposition = null;
        m_sVisorLine = null;
        m_iVisorAnimation = DeviceDisplayBase.ANIMATION_NULL;
        m_sVisorLine1 = null;
        m_sVisorLine2 = null;
        m_iOutputType = OUTPUT_NONE;
        m_oOutputPrinter = null;
    }

    @Override
    public void endDocument() throws SAXException {
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
        
        switch (m_iOutputType) {
        case OUTPUT_NONE:
            if ("opendrawer".equals(qName)) {
                m_printer.getDevicePrinter(readString(attributes.getValue("printer"), "2")).openDrawer();
               //  m_printer.getFiscalPrinter().printMessage("0");
            } else if ("play".equals(qName)) {
                 text = new StringBuffer();    
            } else if ("ticket".equals(qName)) {
                if(this.m_command == null)
                { 
                m_iOutputType = OUTPUT_TICKET;
                String id = readString(attributes.getValue("printer"), "2");
                m_oOutputPrinter = m_printer.getDevicePrinter("1");
               // m_oOutputPrinter = m_printer.getDevicePrinter(readString(attributes.getValue("printer"), "2"));
                m_oOutputPrinter.beginReceipt();
                }
            } else if ("display".equals(qName)) {
                m_iOutputType = OUTPUT_DISPLAY;
                String animation = attributes.getValue("animation");
                if ("scroll".equals(animation)) {
                    m_iVisorAnimation = DeviceDisplayBase.ANIMATION_SCROLL;
                } else if ("flyer".equals(animation)) {
                    m_iVisorAnimation = DeviceDisplayBase.ANIMATION_FLYER;
                } else if ("blink".equals(animation)) {
                    m_iVisorAnimation = DeviceDisplayBase.ANIMATION_BLINK;
                } else if ("curtain".equals(animation)) {
                    m_iVisorAnimation = DeviceDisplayBase.ANIMATION_CURTAIN;
                } else { // "none"
                    m_iVisorAnimation = DeviceDisplayBase.ANIMATION_NULL;
                }
                m_sVisorLine1 = null;
                m_sVisorLine2 = null;                
                m_oOutputPrinter = null;
            } else if ("fiscalreceipt".equals(qName)) {
                m_iOutputType = OUTPUT_FISCAL;
               // m_printer.getFiscalPrinter().beginReceipt();
            } else if ("fiscalzreport".equals(qName)) {
               // m_printer.getFiscalPrinter().printZReport();
            } else if ("fiscalxreport".equals(qName)) {
               // m_printer.getFiscalPrinter().printXReport();
            }
            else if ("comando".equals(qName)) {
                if(this.m_command != null)
                { m_iOutputType = OUTPUT_COMANDO;}
            }
            else if ("entity".equals(qName)) {             
                 m_iOutputType = OUTPUT_ENTITY;
            }
            break;
        case OUTPUT_TICKET:
            if ("image".equals(qName)){
                text = new StringBuffer();           
            } else if ("barcode".equals(qName)) {
                text = new StringBuffer();
                bctype = attributes.getValue("type");
                bcposition = attributes.getValue("position");
            } else if ("line".equals(qName)) {
                m_oOutputPrinter.beginLine(parseInt(attributes.getValue("size"), DevicePrinter.SIZE_0));
            } else if ("text".equals(qName)) {
                text = new StringBuffer();
                m_iTextStyle = ("true".equals(attributes.getValue("bold")) ? DevicePrinter.STYLE_BOLD : DevicePrinter.STYLE_PLAIN)
                             | ("true".equals(attributes.getValue("italic")) ? DevicePrinter.STYLE_ITALIC : DevicePrinter.STYLE_PLAIN)
                             | ("true".equals(attributes.getValue("underline")) ? DevicePrinter.STYLE_UNDERLINE : DevicePrinter.STYLE_PLAIN);
                String sAlign = attributes.getValue("align");
                if ("right".equals(sAlign)) {
                    m_iTextAlign = DevicePrinter.ALIGN_RIGHT;
                } else if ("center".equals(sAlign)) {
                    m_iTextAlign = DevicePrinter.ALIGN_CENTER;
                } else {
                    m_iTextAlign = DevicePrinter.ALIGN_LEFT;
                }
                m_iTextLength = parseInt(attributes.getValue("length"), 0);
            }
            break;
        case OUTPUT_DISPLAY:
            if ("line".equals(qName)) { // line 1 or 2 of the display
                m_sVisorLine = new StringBuffer();
            } else if ("line1".equals(qName)) { // linea 1 del visor
                m_sVisorLine = new StringBuffer();
            } else if ("line2".equals(qName)) { // linea 2 del visor
                m_sVisorLine = new StringBuffer();
            } else if ("text".equals(qName)) {
                text = new StringBuffer();
                String sAlign = attributes.getValue("align");
                if ("right".equals(sAlign)) {
                    m_iTextAlign = DevicePrinter.ALIGN_RIGHT;
                } else if ("center".equals(sAlign)) {
                    m_iTextAlign = DevicePrinter.ALIGN_CENTER;
                } else {
                    m_iTextAlign = DevicePrinter.ALIGN_LEFT;
                }
                m_iTextLength = parseInt(attributes.getValue("length"));
            }
            break;
        case OUTPUT_FISCAL:
            if ("line".equals(qName)) {
                text = new StringBuffer();   
                m_dValue1 = parseDouble(attributes.getValue("price"));
                m_dValue2 = parseDouble(attributes.getValue("units"), 1.0);
                attribute3 = parseInt(attributes.getValue("tax"));
                atributeAux = attributes.getValue("atributo");
            } else if ("message".equals(qName)) {
                text = new StringBuffer();               
            } else if ("total".equals(qName)) {
                text = new StringBuffer();    
                m_dValue1 = parseDouble(attributes.getValue("paid"));
            }
            break;
        case  OUTPUT_COMANDO:
             if ("id".equals(qName)) {
                text = new StringBuffer();               
            }else if ("accion".equals(qName)) {
                text = new StringBuffer();               
            }else if ("tiempo".equals(qName)) {
                text = new StringBuffer();               
            }
            break;         
         case  OUTPUT_ENTITY:
             if(!qName.equals("entity"))
             {
                 if(isNewFirmware)
                 {
                     if(qName.equals("country") || qName.equals("equipment")|| qName.equals("flag") || qName.equals("cashier") || qName.equals("meanpayment") || qName.equals("taxe") || qName.equals("footerheader"))
                     {
                         tagEntityName = qName; 
                         datosEntities = new ArrayList<String>();
                     }
                      text = new StringBuffer();   
                 }
             }
            break;
        }
    } 
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        switch (m_iOutputType) {
        case OUTPUT_NONE:
            if ("play".equals(qName)) {
                try { 
                    AudioClip oAudio = Applet.newAudioClip(getClass().getClassLoader().getResource(text.toString()));
                    oAudio.play();        
                } catch (Exception fnfe) {
                    //throw new ResourceNotFoundException( fnfe.getMessage() );
                }
                text = null;
            } 
            break;
        case OUTPUT_TICKET:
        	
            if ("image".equals(qName)){
                try { 
                    // BufferedImage image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(m_sText.toString()));
                    BufferedImage image = null;//m_system.getResourceAsImage(text.toString());
                     Resources resourDat = new Resources();
                    Resources[] list = resourDat.ResourceByName(text.toString());                    
      
                    if(list.length > 0)
                    {
                        int limit = (int) list[0].getContent().length();
                        byte[] img = list[0].getContent().getBytes(1, limit);
                        image = ImageIO.read(new ByteArrayInputStream(img));
                    }
                    if (image != null) {                      	                   
                    		m_oOutputPrinter.printImage(image);                	
                    }
                } catch (Exception fnfe) {
                    //throw new ResourceNotFoundException( fnfe.getMessage() );
                }
                text = null;
            } else if ("barcode".equals(qName)) {
                m_oOutputPrinter.printBarCode(
                        bctype,
                        bcposition,    
                        text.toString());
                text = null;
            } else if ("text".equals(qName)) {
                if (m_iTextLength > 0) {
                    switch(m_iTextAlign) {
                    case DevicePrinter.ALIGN_RIGHT:
                        m_oOutputPrinter.printText(m_iTextStyle, DeviceTicket.alignRight(text.toString(), m_iTextLength));
                        break;
                    case DevicePrinter.ALIGN_CENTER:
                        m_oOutputPrinter.printText(m_iTextStyle, DeviceTicket.alignCenter(text.toString(), m_iTextLength));
                        break;
                    default: // DevicePrinter.ALIGN_LEFT
                       // if(m_iTextLength == 40)
                       // { atributeAux = text.toString();}
                        m_oOutputPrinter.printText(m_iTextStyle, DeviceTicket.alignLeft(text.toString(), m_iTextLength));
                        break;
                    }
                } else {
                    m_oOutputPrinter.printText(m_iTextStyle, text.toString());
                }
                text = null;
            } else if ("line".equals(qName)) {
                m_oOutputPrinter.endLine();
            } else if ("ticket".equals(qName)) {
                m_oOutputPrinter.endReceipt();             
                m_iOutputType = OUTPUT_NONE;
                m_oOutputPrinter = null;
            }
        	
            break;
        case OUTPUT_DISPLAY:
            if ("line".equals(qName)) { // line 1 or 2 of the display
                if (m_sVisorLine1 == null) {
                    m_sVisorLine1 = m_sVisorLine.toString();
                } else {
                    m_sVisorLine2 = m_sVisorLine.toString();
                }
                m_sVisorLine = null;
            } else if ("line1".equals(qName)) { // linea 1 del visor
                m_sVisorLine1 = m_sVisorLine.toString();
                m_sVisorLine = null;
            } else if ("line2".equals(qName)) { // linea 2 del visor
                m_sVisorLine2 = m_sVisorLine.toString();
                m_sVisorLine = null;
            } else if ("text".equals(qName)) {
                if (m_iTextLength > 0) {
                    switch(m_iTextAlign) {
                    case DevicePrinter.ALIGN_RIGHT:
                        m_sVisorLine.append(DeviceTicket.alignRight(text.toString(), m_iTextLength));
                        break;
                    case DevicePrinter.ALIGN_CENTER:
                        m_sVisorLine.append(DeviceTicket.alignCenter(text.toString(), m_iTextLength));
                        break;
                    default: // DevicePrinter.ALIGN_LEFT
                        m_sVisorLine.append(DeviceTicket.alignLeft(text.toString(), m_iTextLength));
                        break;
                    }
                } else {
                    m_sVisorLine.append(text);
                }
                text = null;
            } else if ("display".equals(qName)) {
                m_printer.getDeviceDisplay().writeVisor(m_iVisorAnimation, m_sVisorLine1, m_sVisorLine2);        
                m_iVisorAnimation = DeviceDisplayBase.ANIMATION_NULL;                
                m_sVisorLine1 = null;
                m_sVisorLine2 = null;
                m_iOutputType = OUTPUT_NONE;
                m_oOutputPrinter = null;
            }
            break;
        case OUTPUT_FISCAL:
            if ("fiscalreceipt".equals(qName)) {           
                m_iOutputType = OUTPUT_NONE;
            } else if ("line".equals(qName)) {
               // Omitido 
            } else if ("message".equals(qName)) {
                // Omitido
            } else if ("total".equals(qName)) {
             //Omitido
            }
            break;
         case OUTPUT_COMANDO:
             if ("id".equals(qName))
             {
               this.tramaTemp = text.toString();
               this.tramaTemp = this.tramaTemp.replace("\n", "");
             }else if ("accion".equals(qName))
             {    
               this.m_command._tramaByte = this.m_command._tramaByte.replace("\r", "");
               if(this.tramaTemp.equals(this.m_command._tramaByte))
               {
                this.m_command._tramaByte = this.tramaTemp;
                this.m_command._acction = text.toString();
               }
             }else if ("tiempo".equals(qName))
             { 
                 if(this.tramaTemp.equals(this.m_command._tramaByte))
                 { this.m_command._time = Integer.valueOf(text.toString());}
             }else if ("comando".equals(qName)) {
                 m_iOutputType = OUTPUT_NONE;
             }
            break;
         case OUTPUT_ENTITY: 
             if ("entity".equals(qName)) {
                 m_iOutputType = OUTPUT_NONE;
             }else if (tagEntityName.equals("country") || tagEntityName.equals("equipment")|| tagEntityName.equals("flag") || tagEntityName.equals("cashier") || tagEntityName.equals("meanpayment") || tagEntityName.equals("taxe") || tagEntityName.equals("footerheader"))
             {  
               if(isNewFirmware)
               {
                 if(!tagEntityName.equals(qName))
                 { datosEntities.add(text.toString()); }
               }            
             }
             if(isNewFirmware)
             {
                 if ("country".equals(qName))
                 { //Inserto la Entidad en Base de dato
                    Countries c = new Countries();
                    c.addCountries(Integer.valueOf(datosEntities.get(0)), datosEntities.get(1));
                 }else  if ("equipment".equals(qName))
                 { //Inserto el Equipo
                    Equipments e = new Equipments();
                    e.InsertEquipment(Integer.valueOf(datosEntities.get(0)),datosEntities.get(1), datosEntities.get(2), datosEntities.get(3), datosEntities.get(4), datosEntities.get(5), Double.valueOf(datosEntities.get(6)), datosEntities.get(7), Integer.valueOf(datosEntities.get(8)));
                 }else  if ("flag".equals(qName))
                 {
                     Flags f = new Flags();
                     f.setId(Integer.valueOf(datosEntities.get(0)));
                     f.setValue(Integer.valueOf(datosEntities.get(1)));
                     f.setDescription(datosEntities.get(2));
                     f.setEquipoId(Integer.valueOf(datosEntities.get(3)));
                     f.Insert_Flags(f);
                 }else  if ("cashier".equals(qName))
                 {
                     Cashier c = new Cashier();
                     c.setId_Cashier(Integer.valueOf(datosEntities.get(0)));
                     c.setName(datosEntities.get(1));
                     c.setPass(datosEntities.get(2));
                     c.setId_equipment(Integer.valueOf(datosEntities.get(3)));
                     c.Schedule_Cashier(c);
                 }else  if ("meanpayment".equals(qName))
                 {
                     MeanPayment m = new MeanPayment();
                     m.InsertMeanPayment(Integer.valueOf(datosEntities.get(0)), datosEntities.get(1), Double.valueOf(datosEntities.get(2)), Integer.valueOf(datosEntities.get(3)));
                 }else  if ("taxe".equals(qName))
                 {
                     Taxes t = new Taxes();
                     t.insertar(datosEntities.get(0), datosEntities.get(1),datosEntities.get(2),datosEntities.get(3), datosEntities.get(4),Double.valueOf(datosEntities.get(5)), Integer.valueOf(datosEntities.get(6)), Integer.valueOf(datosEntities.get(7)),Integer.valueOf(datosEntities.get(8)));
                 }else  if ("footerheader".equals(qName))
                 {
                     FooterHeader fh = new FooterHeader();
                     fh.setE1(datosEntities.get(0));
                     fh.setE2(datosEntities.get(1));
                     fh.setE3(datosEntities.get(2));
                     fh.setE4(datosEntities.get(3));
                     fh.setE5(datosEntities.get(4));
                     fh.setE6(datosEntities.get(5));
                     fh.setE7(datosEntities.get(6));
                     fh.setE8(datosEntities.get(7));
                     fh.setP1(datosEntities.get(8));
                     fh.setP2(datosEntities.get(9));
                     fh.setP3(datosEntities.get(10));
                     fh.setP4(datosEntities.get(11));
                     fh.setP5(datosEntities.get(12));
                     fh.setP6(datosEntities.get(13));
                     fh.setP7(datosEntities.get(14));
                     fh.setP8(datosEntities.get(15));
                     fh.setEquipment_id(Integer.valueOf(datosEntities.get(16)));                 
                     fh.setHeader_Footer(fh);
                 }
             }
             break;
        }          
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (text != null) {
            text.append(ch, start, length);
        }
    }
    
    private int parseInt(String sValue, int iDefault) {
        try {
            return Integer.parseInt(sValue);
        } catch (NumberFormatException eNF) {
            return iDefault;
        }
    }
    
    private int parseInt(String sValue) {
        return parseInt(sValue, 0);
    }
    
    private double parseDouble(String sValue, double ddefault) {
        try {
            return Double.parseDouble(sValue);
        } catch (NumberFormatException eNF) {
            return ddefault;
        }
    }
    
    private double parseDouble(String sValue) {
        return parseDouble(sValue, 0.0);
    }
    
    private String readString(String sValue, String sDefault) {
        if (sValue == null || sValue.equals("")) {
            return sDefault;
        } else {
            return sValue;
        }
    }
}
