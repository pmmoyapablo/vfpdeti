/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pa;
import common.*;
import utility.HkaBaseRaiz;
import contable.*;
import elements.Cashier;
import elements.Flags;
import elements.FooterHeader;
import elements.MeanPayment;
import elements.Taxes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pmoya
 */
public class HkaPanama extends HkaBaseRaiz implements IFiscalAccionable{
    public HkaPanama()
   {   super();
      this.setLocaleCurrencySimbol("B/");
       _myTicket = new TicketInfo(this._localeCurrencySimbol);
   }
     @Override
    public void setCommad(Command pCmd) throws DeviceException {
        
     if( pCmd.isValido(pCmd._contentResource))
      {          
            switch (pCmd._acction)
           {
            case "datoClient":
               Result =  this.BeginFiscalDocument(null);
                if(_client == null)
                {
                    _client = new CustomerInfo();
                }
                if(_client.getSocialreason() == null)
                { _client.setSocialreason(pCmd._tramaByte.substring(3)); }
                else if(_client.getId() == null)
                { _client.setId(pCmd._tramaByte.substring(3)); } 
                 else if(_client.getAddress() == null)
                { _client.setAddress(pCmd._tramaByte.substring(3)); }   
                 else if(_client.getCity() == null)
                { _client.setCity(pCmd._tramaByte.substring(3)); } 
                 else if(_client.getCountry() == null)
                { _client.setCountry(pCmd._tramaByte.substring(3)); } 
                 else if(_client.getPhone() == null)
                { _client.setPhone(pCmd._tramaByte.substring(3)); }
                else if(_client.getEmail() == null)
                { _client.setEmail(pCmd._tramaByte.substring(3)); }
                 else if(_client.getNotes() == null)
                { _client.setNotes(pCmd._tramaByte.substring(3)); }
                    
                    this._myTicket.setCustomer(_client);
                break;
                
            case "regItemFact": 
                if( this._state == STATE_WAIT)
                { this.BeginFiscalDocument("1");}
               Result =  this.PrintRecItem(pCmd._tramaByte);
                break; 
                
            case "regItemRefund": 
                if( this._state == STATE_WAIT)
                { this.BeginFiscalDocument("2");}
                Result = this.PrintRecItem(pCmd._tramaByte);
                break;
                
            case "regItemDebitNote": 
                if( this._state == STATE_WAIT)
                { this.BeginFiscalDocument("3");}
                Result =  this.PrintRecItem(pCmd._tramaByte);
                break;
                
            case "report-x": 
                Result = this.BeginFiscalDocument("4");
                Result = this.PrintXReport();
                break; 
                
            case "report-z": 
                Result = this.BeginFiscalDocument("5");
                Result = this.PrintZReport();
                break;
                
            case "pagoDirecto": 
                Result = this.PrintRecTotal(Integer.valueOf(pCmd._tramaByte.substring(1)));
                break; 
                
            case "statusS1":
                this.setData(pCmd._tramaByte);
                break;
                
            case "statusS2":
                this.setData(pCmd._tramaByte);
                break;  
                
            case "statusS3":
                this.setData(pCmd._tramaByte);
                break;   
                 
            case "statusS4":
                this.setData(pCmd._tramaByte);
                break;   
                
            case "statusS5":
                this.setData(pCmd._tramaByte);
                break;   
                
            case "statusS6":
                this.setData(pCmd._tramaByte);
                break;  
                
            case "statusS7":
                this.setData(pCmd._tramaByte);
                break;  
                
              case "statusS8P":
                this.setData(pCmd._tramaByte);
                break; 
                
              case "statusS8E":
                this.setData(pCmd._tramaByte);
                break; 
                  
              case "statusSV":
                this.setData(pCmd._tramaByte);
                break; 
            
              case "programar-tasas":
                Result = this.ProgramTaxe(pCmd._tramaByte);                    
                break;
                
            case "anula-item":
                Result = this.PrintRecItemVoid(pCmd._tramaByte);
                break;
                
            case "anula-factura":
                Result = this.VoidFiscalDocument();
                break;
                
            case "opendrawer":
                Result = this.OpenDrawer();
                break;
                
            case "fiscalizar":
                Result = this.Fiscalizar(pCmd._tramaByte);
                break;
                
             case "desbloquear":
                Result = this.UnLock(pCmd._tramaByte);
                break;
                
            case "subtotal":
                Result = this.PrintSubTotal();
                break;
                 
            case "cierreDNF":
                Result = this.PrintRecItemNotFiscal(pCmd._tramaByte.substring(3));
                if(pCmd._tramaByte.substring(1,3).equals("10"))
                Result =  this.EndNotFiscalDocument();
                break;
                
            case "lineDNF":
                 if(this._myTicket.printLinesNoFiscal() == null)
                Result = this.BeginNotFiscalDocument(pCmd._tramaByte.substring(3));
                else
                Result = this.PrintRecItemNotFiscal(pCmd._tramaByte.substring(3));
                break;
            
            case "printprogram":
                Result = this.PrintProgramation();
                break;
                
            case "comentario":
                Result = this.PrintComent(pCmd._tramaByte);
                break;
                
            case "correccion":
                Result = this.CorrecLineTransaction();
                break;
            
            case "descPorcentaje":
                String valueDecimal = pCmd._tramaByte.substring(2,4) + "." + pCmd._tramaByte.substring(4);
                Result = this.PrintRecItemAdjustement("p-",Double.valueOf(valueDecimal));
                break;
             
            case "recaPorcentaje":
                valueDecimal = pCmd._tramaByte.substring(2,4) + "." + pCmd._tramaByte.substring(4);
                Result = this.PrintRecItemAdjustement("p+",Double.valueOf(valueDecimal));
                break;
            
            case "descPorcentajeStl":
                valueDecimal = pCmd._tramaByte.substring(2,4) + "." + pCmd._tramaByte.substring(4);
                Result = this.PrintRecItemAdjustement("p*",Double.valueOf(valueDecimal));
                break;
            
            case "recaPorcentajeStl":
                 valueDecimal = pCmd._tramaByte.substring(2,4) + "." + pCmd._tramaByte.substring(4);
                Result = this.PrintRecItemAdjustement("p#",Double.valueOf(valueDecimal));
                break;
            
            case "programar-cajero":
                Result = this.ProgramCashier(pCmd._tramaByte);
                break;
            
            case "programar-medio":
                Result = this.ProgramMeansPayment(pCmd._tramaByte);
                break;
            
            case "programar-encabezPie":
                Result = this.ProgramHeaderFooter(pCmd._tramaByte);
                break;
                
            case "programar-flags":
                Result = this.ProgramFlags(pCmd._tramaByte);
                break;
                
            case "donacion":
                Result = this.PrintDotation();
                break;
                
            case "iniciar-cajero":
                Result = this.StartCahier(pCmd._tramaByte);
                break;
            
            case "fin-cajero":
                Result = this.EndCahier();
                break;
                
            case "printReportZNumber":
                Result = this.PrintMemotyReportFiscal(pCmd._tramaByte);
                break;
            
            case "dataReportX":
                 this.setData(pCmd._tramaByte);
                break;
            
            case "dataReportZ":
                 this.setData(pCmd._tramaByte);
                break;
                
            case "dataReportZNumber":
                 this.setData(pCmd._tramaByte);
                break;
                
            case "dataReportZDate":
                 this.setData(pCmd._tramaByte);
                break;
                
            case "parcial-factura":
                PaymentInfo p = new PaymentInfo(pCmd._tramaByte.substring(1,3), _state, _state, _state);
                Result =  this.PrintRecTotal(p);
                break;
            
            case "factura":
                  if(this._myTicket != null)
                   Result =  this.PrintRecTotal(Integer.valueOf(pCmd._tramaByte.substring(1)));
                break;
                
            case "reg-inoutcash":
                double amount = Double.valueOf(pCmd._tramaByte.substring(4))/100;
                if(pCmd._tramaByte.substring(1,2).equals("1"))
                { 
                    Result = this.BeginNotFiscalDocument("FONDO DE CAJA");
                    Result = this.PrintRecItemNotFiscal("Efectivo:");
                    Result = this.PrintRecItemNotFiscal("BsF +" + amount);
                }
                else
                {
                    Result = this.BeginNotFiscalDocument("RETIRO DE CAJA");
                    Result = this.PrintRecItemNotFiscal("Efectivo:");
                    Result = this.PrintRecItemNotFiscal("BsF -" + amount);
                }
                                
            break;
                
            case "close-inoutcash":
                 Result =  this.EndNotFiscalDocument();
            break;
        
          }
      }
        else
        { 
           throw new DeviceException(pCmd._cmdError);
        }
             
    }

    @Override
    public int BeginFiscalDocument(String pType) {                  
        
        if(pType != null)
        {
            if(!IsVerifyFlags21)
            {
              Flags fl = new Flags();
              fl = fl.getFlags(21,this._myTicket.getEquipmet().getId());
              
              if(fl != null)
              {
                  this.IsVerifyFlags21 = true;
                if(fl.getValue() != 0)
                    DivisorPrice = 1000;
                else
                    DivisorPrice = 100;
              }
            }
            
         if(pType.equals("1"))
         { 
             _myTicket.setTicketType(1);
             _myTicket.setNameType("Factura");
             this._state = STATE_TRANSACCTION;
         }
         else if(pType.equals("2"))
         {
              _myTicket.setTicketType(2);
             _myTicket.setNameType("Nota de Credito");
             this._state = STATE_TRANSACCTION;
         }
         else if(pType.equals("3"))
         {
              _myTicket.setTicketType(3);
             _myTicket.setNameType("Nota de Debito");
             this._state = STATE_TRANSACCTION;
         }
          else if(pType.equals("4"))
          { 
             _myTicket.setTicketType(4);
             _myTicket.setNameType("Reporte X");
          }
          else if(pType.equals("5"))
          { 
             _myTicket.setTicketType(5);
             _myTicket.setNameType("Reporte Diario Z");
          }
          else if(pType.equals("6"))
          { 
             _myTicket.setTicketType(6);
             _myTicket.setNameType("REPORTE DE MEMORIA FISCAL");
          }
          else if(pType.equals("7"))
          { 
             _myTicket.setTicketType(7);
             _myTicket.setNameType("NO FISCAL");
          }
         
        }
          
         this.setTypeDisplay(NOT_PRINTABLE);
         
        return RESPONSE_OK;
    }

    @Override
    public int EndFiscalDocument() {
       
         return RESPONSE_OK;
    }

    @Override
    public int PrintRecItem(String pTrama) {
        int tax = 0;
        int n = 0;
        try
        {
            if(pTrama.substring(0,1).equals("d"))
            {        n = 1;
                 if(pTrama.substring(1,2).equals("1"))
                tax = 1;
                 else  if(pTrama.substring(1,2).equals("2"))
                tax = 2;
                 else  if(pTrama.substring(1,2).equals("3"))
                tax = 3;
            }
            if(pTrama.substring(0,1).equals("`"))
            {      n = 1;
                     if(pTrama.substring(1,2).equals("1"))
                tax = 1;
                 else  if(pTrama.substring(1,2).equals("2"))
                tax = 2;
                 else  if(pTrama.substring(1,2).equals("3"))
                tax = 3;
            }
            else
            {

                if(pTrama.substring(0,1).equals("!"))
                tax = 1;
                 else  if(pTrama.substring(0,1).equals("\""))
                tax = 2;
                 else  if(pTrama.substring(0,1).equals("#"))
                tax = 3;
            }
            
        _myLine = new TicketLineInfo();
        Taxes taxInfoItem = new Taxes();
        Taxes[] listTaxs = taxInfoItem.getTaxesAll();
        taxInfoItem = taxInfoItem.getTaxeById(String.valueOf(tax) ,_myTicket.getEquipmet().getId());
        _myLine.setTaxInfo(new TaxInfo(taxInfoItem.getId(),taxInfoItem.getNombre(), taxInfoItem.getRate(), taxInfoItem.getRateCascade()));
            int sum = 0;
        if(DivisorPrice == 1000)
            sum = 1;
        double quantity = Double.valueOf(pTrama.substring(11 + n + sum,16 + n + sum));
        double quantityDec = Double.valueOf(pTrama.substring(16 + n + sum,19 + n + sum))/DivisorQuantity;
        quantity = quantity + quantityDec;
                      
        double priceDec =  Double.valueOf(pTrama.substring(9 + n + sum,11 + n + sum))/DivisorPrice;
        double price =  Double.valueOf(pTrama.substring(1 + n,9 + n));
        price = price + priceDec;
                        
        String productName = pTrama.substring(19 + n + sum);
        _myLine.setPrice(price);
        _myLine.setMultiply(quantity);
        _myLine.setProductID(productName);    
        
        this._myTicket.addLine(_myLine);
           TicketTaxInfo tickTaxInf = new TicketTaxInfo(_myLine.getTaxInfo());
        tickTaxInf.setSubTotal(_myLine.getSubValue());
        tickTaxInf.setTax(_myLine.getTax());
        this._myTicket.addTaxeInfo(tickTaxInf);
        
        
        this.setTypeDisplay(NOT_PRINTABLE);
        
         return RESPONSE_OK;
         
        }catch(Exception ex)
        {
            return RESPONSE_ERROR;
        }
    }

    @Override
    public int PrintRecTotal(int pIdMean) {
       
        if(this._myTicket.getLinesCount() > 0)
        {
         this.setTypeDisplay(PRINTABLE);
               _meanPayment = new MeanPayment();
              MeanPayment[] listMeans = _meanPayment.mediaList(this._myTicket.getEquipmet().getId());

                for(MeanPayment m : listMeans)
                {
                    if(pIdMean == m.getId_mean())
                    {
                     PaymentInfo payment = new PaymentInfo( m.getDescription(), _myTicket.getTotal(),_myTicket.getTotal(), Integer.valueOf(m.getId_mean()));              
                    _paymentsList = new ArrayList<PaymentInfo>();
                    _paymentsList.add(payment);
                    _myTicket.setPayments(_paymentsList);
                    }
                }
         
         return RESPONSE_OK;
        }else
        {
          return RESPONSE_ERROR;
        }
    }

    @Override
    public int PrintXReport() {
        if(this._state == STATE_WAIT)
        {
         this.setTypeDisplay(PRINTABLE);
         List<String> lineNotFiscal = new ArrayList<String>();
         lineNotFiscal.add("Exento = 2");
         lineNotFiscal.add("BIG = 12");
         lineNotFiscal.add("FIN");
         this._myTicket.setNotFiscalLines(lineNotFiscal);
         
         return RESPONSE_OK;
        }else
        {
            return RESPONSE_ERROR;
        }
    }
    @Override
    public int PrintZReport() {
        if(this._state == STATE_WAIT)
        {
          this._dataLogicSales.cerrarCajaData(this._paymentsModel.getMoney(), this._paymentsModel.getHost());
         this.setTypeDisplay(PRINTABLE);
         
         return RESPONSE_OK;
        }else
        {
            return RESPONSE_ERROR;
        }
    }
    @Override
    public void setData(String pNameCmd) throws DeviceException{
        
        this._state = STATE_UPDATA;
        
		try {
			if (pNameCmd.equals("S1")) {
				this._mPrinterdata = new S1PrinterData();
			} else if (pNameCmd.equals("S2")) {
				this._mPrinterdata = new S2PrinterData();
			}else if (pNameCmd.equals("S3")) {
				this._mPrinterdata = new S3PrinterData();
			}else if (pNameCmd.equals("S4")) {
				this._mPrinterdata = new S4PrinterData();
			}else if (pNameCmd.equals("S5")) {
				this._mPrinterdata = new S5PrinterData();
			}else if (pNameCmd.equals("S6")) {
				this._mPrinterdata = new S6PrinterData();
			}else if (pNameCmd.equals("S7")) {
				this._mPrinterdata = new S7PrinterData();
			}else if (pNameCmd.equals("S8E")) {
				this._mPrinterdata = new S8EPrinterData();
			}else if (pNameCmd.equals("S8P")) {
				this._mPrinterdata = new S8PPrinterData();
			}else {
					throw new DeviceException("Sin Repuesta");
			}
			
		} catch(DeviceException de)
        {
            throw de;
        }
    }

    @Override
    public int getTypeDisplay() {
        return this._typeDisplay;
    }
    
    @Override
    public void ResetFiscalDocument()
    {
        this.resetTicket();
        _myTicket = new TicketInfo(this._localeCurrencySimbol);
        this.setTypeDisplay(NOT_PRINTABLE);
        this._state = STATE_WAIT;
        this._dataBufferRead = null;
        
    }
    @Override
    public String getNameResourceXML() {
        return "comandosPa";
    }

    @Override
    public int PrintRecItemVoid(String pTrama) {
        return RESPONSE_OK;
    }

    @Override
    public int PrintRecTotal(PaymentInfo pPayment) {
        if(this._myTicket.getLinesCount() > 0)
        {        
               _meanPayment = new MeanPayment();
               MeanPayment[] listPays = _meanPayment.mediaList(this._myTicket.getEquipmet().getId());
               for(MeanPayment m : listPays)
               {
                   if(pPayment.getPaid_Id() ==  m.getId_mean())
                   {
                        pPayment.setName(m.getDescription());                      
                        break;
                   }
               }
                _paymentsList = _myTicket.getPayments();
                _paymentsList.add(pPayment);              
                _myTicket.setPayments(_paymentsList);
                
                if(this._myTicket.getTotalPaid() >= _myTicket.getTotal())
                {
                 this.setTypeDisplay(PRINTABLE);
                }else
                {
                  this.setTypeDisplay(NOT_PRINTABLE);
                }
         
         return RESPONSE_OK;
        }else
        {
          return RESPONSE_ERROR;
        }
    }

    @Override
    public int ProgramTaxe(String pTrama) {
         if(this._paymentsModel.getTotal() == 0)
        {  //Si la caja esta cerrada o Z en 0
            if(!pTrama.equals("Pt"))
            {
                 String tramaData = pTrama.substring(2);
                    int n = 0, i = 0;
                    Taxes Tax = new Taxes();
                    Tax = Tax.getTaxeById("0",_myTicket.getEquipmet().getId());
                    Tax.setRate(0.00);
                    Tax.setRateCascade(0);
                    Tax.EditTaxes(Tax);
                    while(n < tramaData.length())
                    {
                       String porcion = tramaData.substring(n, 5 + n);
                        double valueDoub = Double.valueOf(porcion.substring(1))/10000;
                        Tax = Tax.getTaxeById( String.valueOf(i+1),_myTicket.getEquipmet().getId());
                        Tax.setRate(valueDoub);
                        Tax.setRateCascade(Integer.valueOf(porcion.substring(0,1)));
                        Tax.EditTaxes(Tax);
                        n = n + 5;
                        ++i;
                    }
            }
                    return RESPONSE_OK;
        }else
        {
             return RESPONSE_ERROR;
        }
    }

    @Override
    public int ProgramCashier(String pTrama) {
         Cashier ca = new Cashier(this._myTicket.getEquipmet().getId());
        ca.setId_Cashier(Integer.valueOf(pTrama.substring(2,4)));
        ca.setPass(pTrama.substring(4,9));
        ca.setName(pTrama.substring(9));
        
        ca.Schedule_Cashier(ca);
        return RESPONSE_OK;
    }

    @Override
    public int ProgramMeansPayment(String pTrama) {
         MeanPayment mp = new MeanPayment();
        mp = mp.meanEntity(Integer.valueOf( pTrama.substring(2,4)), this._myTicket.getEquipmet().getId());
        if(mp != null)
        {
            mp.setDescription(pTrama.substring(4));
            mp.Edit_Value(mp);
         return RESPONSE_OK;
        }else
          return RESPONSE_ERROR;
    }

    @Override
    public int ProgramHeaderFooter(String pTrama) {
      FooterHeader fh = new FooterHeader();
        FooterHeader[] listFH = fh.ListFooterHeaderById(this._myTicket.getEquipmet().getId());
        
        if(listFH.length == 0)
        {  return RESPONSE_ERROR;}
        
        fh = listFH[0];
        
        if(Integer.valueOf(pTrama.substring(2,4)) > 1 || Integer.valueOf(pTrama.substring(2,4)) < 9)
        {  //Programo el Encabezado correspondiente
           if(Integer.valueOf(pTrama.substring(2,4)) == 1)
              fh.setE1(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 2)
              fh.setE2(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 3)
              fh.setE3(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 4)
              fh.setE4(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 5)
              fh.setE5(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 6)
              fh.setE6(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 7)
              fh.setE7(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 8)
              fh.setE8(pTrama.substring(4));
           
        }
        else if(Integer.valueOf(pTrama.substring(2,4)) > 91 || Integer.valueOf(pTrama.substring(2,4)) < 99)
        {  //Programo el Pie de Pagina Correspondiente
           if(Integer.valueOf(pTrama.substring(2,4)) == 1)
              fh.setP1(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 2)
              fh.setP2(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 3)
              fh.setP3(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 4)
              fh.setP4(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 5)
              fh.setP5(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 6)
              fh.setP6(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 7)
              fh.setP7(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 8)
              fh.setP8(pTrama.substring(4));
        }
        else
        {
          return RESPONSE_ERROR;
        }
           fh.setHeader_Footer(fh);
        
         return RESPONSE_OK;
    }

    @Override
    public int ProgramFlags(String pTrama) {
         Flags fl = new Flags();
        int idFl = Integer.valueOf(pTrama.substring(2,4));
        fl = fl.getFlags(idFl, this._myTicket.getEquipmet().getId());
        if(fl != null)
        { 
            int val = Integer.valueOf(pTrama.substring(4));
            fl.setValue(val);
            fl.Edit_Flags(fl);
            
            if(fl.getId() == 21)
                this.IsVerifyFlags21 = false;
        }
       return RESPONSE_OK;
    }

    @Override
    public int VoidFiscalDocument() {
        this.setTypeDisplay(PRINTABLE);
         List<String> lineNotFiscal = new ArrayList<>();
                 lineNotFiscal.add("ANULADO");
                 this._myTicket.setNotFiscalLines(lineNotFiscal);
       return RESPONSE_OK;
    }

    @Override
    public int OpenDrawer() {
        this.BeginFiscalDocument("7");
                 this.setTypeDisplay(PRINTABLE);
                 List<String> lineNotFiscal = new ArrayList<>();
                 lineNotFiscal.add("Apertura de Gaveta");

                 this._myTicket.setNotFiscalLines(lineNotFiscal);
        return RESPONSE_OK;
    }

    @Override
    public int PrintSubTotal() {
        return RESPONSE_OK;
    }

    @Override
    public int Fiscalizar(String pTrama) {
         if(this._state == STATE_WAIT)
        {       
            if(pTrama.substring(0,3).equals("PAR"))
            {
                 this.BeginFiscalDocument("7");
                 this.setTypeDisplay(PRINTABLE);
                 List<String> lineNotFiscal = new ArrayList<String>();
                 lineNotFiscal.add("Codigo Fiscalizacion");
                 lineNotFiscal.add(this.getPreCodeOper("FISCALIZACIONPANAMA20015", pTrama.substring(3,16)));
                 lineNotFiscal.add("");
                 this._myTicket.setNotFiscalLines(lineNotFiscal);
            }
            else  if(pTrama.substring(0,2).equals("PB") )                    
            {
                //Validamos los Codigos
                if(pTrama.substring(2).equals(this.getIntoCodeValido()))
                {
                  this.BeginFiscalDocument("7");
                  this.setTypeDisplay(PRINTABLE);
                  List<String> lineNotFiscal = new ArrayList<String>();
                  lineNotFiscal.add("Inicio Fiscal");
                  lineNotFiscal.add("Exitoso");
                  this._myTicket.setNotFiscalLines(lineNotFiscal);
                }else
                {
                     return RESPONSE_ERROR;
                }
            }
        
            return RESPONSE_OK;
        }else
         {
           return RESPONSE_ERROR;
         }
    }

    @Override
    public int BeginNotFiscalDocument(String pTitle) {
         _myTicket.setTicketType(7);
             _myTicket.setNameType("NO FISCAL");
             List<String> linesDNF = new ArrayList<>();
             linesDNF.add(pTitle);
             this._myTicket.setNotFiscalLines(linesDNF);
             this.setTypeDisplay(NOT_PRINTABLE);
             this._state = STATE_TRANSACCTION;
        return RESPONSE_OK;
    }

    @Override
    public int EndNotFiscalDocument() {
        this.setTypeDisplay(PRINTABLE);
        return RESPONSE_OK;
    }

    @Override
    public int PrintRecItemNotFiscal(String pTrama) {
         this.setTypeDisplay(NOT_PRINTABLE);
         List<String> linesDNF = this._myTicket.printLinesNoFiscal();
         linesDNF.add(pTrama);
         this._myTicket.setNotFiscalLines(linesDNF);
        return RESPONSE_OK;
    }

    @Override
    public int PrintProgramation() {
        this.BeginFiscalDocument("7");
                 this.setTypeDisplay(PRINTABLE);
                 List<String> lineNotFiscal = new ArrayList<>();
                 lineNotFiscal.add(this._myTicket.getEquipmet().getVersion_firmware());
                 Flags fl = new Flags();
                 Flags[] listFlags = fl.listFlags(this._myTicket.getEquipmet().getId());
                  int j = 0;
                        while(j < 2)//6
                        {
                            int w = 0;
                            String lineFlags = "";
                             while(w < 10)
                               {
                                   int indice = w +(j*10);
                                 String flag = String.valueOf(listFlags[indice].getValue());
                                  while(flag.length() < 2)
                                   { flag = "0" + flag; }
                                  lineFlags = lineFlags + flag + " ";
                                      ++w;
                                }
                                 lineNotFiscal.add("F" + String.valueOf(j)+"0->F"+String.valueOf(j)+"9: " + lineFlags);
                           ++j;
                        }
                        lineNotFiscal.add(".......................................");
                    
                        Taxes ta = new Taxes();
                        Taxes[] listTaxes = ta.getTaxesByIdEquipment(this._myTicket.getEquipmet().getId());
                        
                        for (Taxes t : listTaxes)
                        {
                          lineNotFiscal.add(t.getNombre() + "(" + String.valueOf(t.getRate()*100) +"%)");
                        }
                        
                        lineNotFiscal.add(".......................................");
                        
                        MeanPayment mp = new MeanPayment();
                        MeanPayment[] listMeans = mp.mediaList(this._myTicket.getEquipmet().getId());
                        
                        for(MeanPayment m : listMeans)
                        {
                            lineNotFiscal.add(util.Utilities.StringFormatXero(String.valueOf(m.getId_mean()), 2) + ": " + m.getDescription());
                        }
                        
                        lineNotFiscal.add(".......................................");

                 this._myTicket.setNotFiscalLines(lineNotFiscal);
        return RESPONSE_OK;
    }

    @Override
    public int PrintComent(String pMessanger) {
        return RESPONSE_OK;
    }

    @Override
    public int CorrecLineTransaction() {
        return RESPONSE_OK;
    }

    @Override
    public int PrintRecItemAdjustement(String pType, double pAmount) {
        return RESPONSE_OK;
    }

    @Override
    public int PrintDotation() {
        return RESPONSE_OK;
    }

    @Override
    public int PrintComition(double pAmount) {
        return RESPONSE_OK;
    }

    @Override
    public int PrintPropine(double pAmount) {
       return RESPONSE_OK;
    }

    @Override
    public int StartCahier(String pTrama) {
         Cashier ca = new Cashier();       
        ca = ca.Start_Cashier(this._myTicket.getEquipmet().getId(), pTrama.substring(1));
        
        if(ca != null)
        { this._myTicket.setUser(ca);
         return RESPONSE_OK;
        }else
            return RESPONSE_ERROR;
    }

    @Override
    public int EndCahier() {
        this._myTicket.setUser(null);
       return RESPONSE_OK;
    }

    @Override
    public int PrintMemotyReportFiscal(String pTrama) {
       return RESPONSE_OK;
    }
     @Override
    public int UnLock(String pCode) {
        //Validamos los Codigos
                if(pCode.substring(1).equals(this.getIntoCodeValido()))
                {
                  this._state = STATE_UNLOCK;
                  
                  return RESPONSE_OK;
                }else
                {
                     return RESPONSE_ERROR;
                }
    }

    @Override
    public int Lock(String pSerial) {
       this.BeginFiscalDocument("7");
                 this.setTypeDisplay(PRINTABLE);
                 List<String> lineNotFiscal = new ArrayList<String>();
                 lineNotFiscal.add("Codigo Desbloqueo");
                 lineNotFiscal.add(this.getPreCodeOper("DESBLOQUEOPANAMA2015", pSerial));
                 lineNotFiscal.add("");
                 this._myTicket.setNotFiscalLines(lineNotFiscal);
                 
                 return RESPONSE_OK;
    }
}
