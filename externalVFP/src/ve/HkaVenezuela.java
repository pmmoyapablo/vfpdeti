/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve;
//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.formatDate;
import common.*;
import utility.HkaBaseRaiz;
import contable.*;
import elements.Cashier;
import elements.ClosedCash.*;
import elements.Flags;
import elements.FooterHeader;
import elements.MeanPayment;
import elements.Taxes;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import elements.*;
import java.lang.reflect.Array;
/**
 *
 * @author pmoya
 */
import ticket.PrintItemBarcode;
import util.HibernateUtils;
public class HkaVenezuela extends HkaBaseRaiz implements IFiscalAccionable{
  public HkaVenezuela()
  {   super();
      this.setLocaleCurrencySimbol("Bs");
       _myTicket = new TicketInfo(this._localeCurrencySimbol);       
  }
  NumberFormat formatter = new DecimalFormat("###,##0.00"); 
  private ArrayList<String> commentsList = new ArrayList<>();
//  final private ArrayList<ArrayList<String>> commentsTicketList = new ArrayList<ArrayList<String>>();
//  public String commentsMegaList[];
//  String commentsStringList = "";
  public int productLinePrinted = 0;
  private boolean SubtotalCalled = false;
  private String lastTrama;
  private DeviceTicket _myDeviceTick;
  

//    public PrintItemBarcode getBarcodePrinterLine() {
//        return BarcodePrinterLine;
//    }
//
//    public void setBarcodePrinterLine(PrintItemBarcode BarcodePrinterLine) {
//        this.BarcodePrinterLine = BarcodePrinterLine;
//    }

    public String getLastTrama() {
        return lastTrama;
    }

    public void setLastTrama(String lastTrama) {
        this.lastTrama = lastTrama;
    }
  private double Subtotal = 0.0;
//  public int ticketProductsCounter = 0;
//  int num = 0;

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
                if(_client.getFirstname() == null)
                { _client.setFirstname(pCmd._tramaByte.substring(3)); }
                else if(_client.getAddress() == null)
                { _client.setAddress(pCmd._tramaByte.substring(3)); } 
                else if(_client.getPhone2() == null)
                { _client.setPhone2(pCmd._tramaByte.substring(3)); }
                else if(_client.getCity() == null)
                { _client.setCity(pCmd._tramaByte.substring(3)); } 
                else if(_client.getPhone() == null)
                { _client.setPhone(pCmd._tramaByte.substring(3)); }
                else if(_client.getFax() == null)
                { _client.setFax(pCmd._tramaByte.substring(3)); }
                else if(_client.getNotes() == null)
                { _client.setNotes(pCmd._tramaByte.substring(3)); }
                else if(_client.getEmail() == null)
                { _client.setEmail(pCmd._tramaByte.substring(3)); }                
                else if(_client.getAddress2() == null)
                { _client.setAddress2(pCmd._tramaByte.substring(3)); }                
                else if(_client.getRegion() == null)
                { _client.setRegion(pCmd._tramaByte.substring(3)); }                
                else if(_client.getPostal() == null)
                { _client.setPostal(pCmd._tramaByte.substring(3)); }               

                this._myTicket.setCustomer(_client);            
                break;
                
            case "programRifOrSocialReason":
                Result =  this.BeginFiscalDocument(null);
                if(_client == null)
                {
                    _client = new CustomerInfo();
                }
                if(_client.getSocialreason() == null && (pCmd._tramaByte.charAt(1) == 'S'))
                { _client.setSocialreason(pCmd._tramaByte.substring(3)); }
                else if(_client.getId() == null && (pCmd._tramaByte.charAt(1) == 'R'))
                { _client.setId(pCmd._tramaByte.substring(3)); } 
                else if(_client.getFiscalInvoice() == null && (pCmd._tramaByte.charAt(1) == 'F'))
                { _client.setFiscalInvoice(pCmd._tramaByte.substring(3)); } 
                else if(_client.getFiscalSerial() == null && (pCmd._tramaByte.charAt(1) == 'I'))
                { _client.setFiscalSerial(pCmd._tramaByte.substring(3)); } 
                else if(_client.getFiscalDate() == null && (pCmd._tramaByte.charAt(1) == 'D'))
                { _client.setFiscalDate(pCmd._tramaByte.substring(3)); } 
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
//                System.out.println("Todo fino");
                Result = this.PrintRecTotal(Integer.valueOf(pCmd._tramaByte.substring(1)));
//                                System.out.println("Apenas terminado print rec total: " + Integer.valueOf(pCmd._tramaByte.substring(1)));

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
                Result = this.PrintAnnulment(pCmd._tramaByte);
                break;
            
            case "anula-itemNC":
                Result = this.PrintAnnulment(pCmd._tramaByte);
                break;
            
            case "anula-factura":
                Result = this.VoidFiscalDocument();
                break;
            
            case "subtotal-display":
                Result = this.showSubtotalVisor();
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
                else if((pCmd._tramaByte.indexOf(0) == '@') && (this._myTicket.printLinesNoFiscal() == null)){
                Result = this.BeginNotFiscalDocument(pCmd._tramaByte.substring(0));
                }
                else
                Result = this.PrintRecItemNotFiscal(pCmd._tramaByte.substring(3));               
                break;
            
            case "printprogram":
                Result = this.PrintProgramation();
                break;
                
            case "comentario": 
                Result =  this.PrintComent(pCmd._tramaByte.substring(1));
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
                
            case "descQuantity":
                valueDecimal = pCmd._tramaByte.substring(2,9) + "." + pCmd._tramaByte.substring(9);                
                Result = this.PrintRecItemAdjustement("q-",Double.valueOf(valueDecimal));
                break;
                
            case "recaQuantity":
                valueDecimal = pCmd._tramaByte.substring(2,9) + "." + pCmd._tramaByte.substring(9);
                Result = this.PrintRecItemAdjustement("q+",Double.valueOf(valueDecimal));
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
                
            case "printSubtotal":
                Result = this.PrintSubTotal();
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
                PaymentInfo p = new PaymentInfo("Medio " + pCmd._tramaByte.substring(1,3), (Double.valueOf(pCmd._tramaByte.substring(3))/100), (Double.valueOf(pCmd._tramaByte.substring(3))/100), Integer.valueOf(pCmd._tramaByte.substring(1,3)));
                Result =  this.PrintRecTotal(p);
                break;
            
            case "factura":
                  if(this._myTicket != null)
                  Result =  this.PrintRecTotal(Integer.valueOf(pCmd._tramaByte.substring(1)));
                break;
            
            case "barcode-foot":
                this.PrintBarcodeFoot(pCmd._tramaByte.substring(1));
                break;
                
            case "barcode-plu":
            this.PrintBarcodePlu(pCmd._tramaByte.substring(1));
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
            
        this.setLastTrama(pCmd._tramaByte);
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
            
            switch (pType) {
                case "1":
                    _myTicket.setTicketType(1);
                    _myTicket.setNameType("FACTURA");
                    this._state = STATE_TRANSACCTION;
                    break;
                case "2":
                    _myTicket.setTicketType(2);
                    _myTicket.setNameType("NOTA DE CREDITO");
                    this._state = STATE_TRANSACCTION;
                    break;
                case "3":
                    _myTicket.setTicketType(3);
                    _myTicket.setNameType("NOTA DE DEBITO");
                    this._state = STATE_TRANSACCTION;
                    break;
                case "4":
                    _myTicket.setTicketType(4);
                    _myTicket.setNameType("REPORTE X");
                    break;
                case "5":
                    _myTicket.setTicketType(5);
                    _myTicket.setNameType("REPORTE Z");
                    break;
                case "6":
                    _myTicket.setTicketType(6);
                    _myTicket.setNameType("REPORTE DE MEMORIA FISCAL");
                    break;
                case "7":
                    _myTicket.setTicketType(7);
                    _myTicket.setNameType("NO FISCAL");
                    break;
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
        DivisorPrice = 100;
        DivisorQuantity = 1000; 
        
        try
        {
            if(pTrama.substring(0,1).equals("d"))
            {       n = 1;  
                switch (pTrama.substring(1,2)) {
                    case "0":
                        tax = 0;
                        break;
                    case "1":
                        tax = 1;
                        break;
                    case "2":
                        tax = 2;
                        break;
                    case "3":
                        tax = 3;
                        break;
                }
            }
            if(pTrama.substring(0,1).equals("`"))
            {       n = 1;  
                switch (pTrama.substring(1,2)) {
                    case "0":
                        tax = 0;
                        break;
                    case "1":
                        tax = 1;
                        break;
                    case "2":
                        tax = 2;
                        break;
                    case "3":
                        tax = 3;
                        break;
                }
            }
            else
            {
                switch (pTrama.substring(0,1)) {
                    case " ":
                        tax = 0;
                        break;
                    case "!":
                        tax = 1;
                        break;
                    case "\"":
                        tax = 2;
                        break;
                    case "#":
                        tax = 3;
                        break;
                }
            }
        SubtotalCalled = false;
        _myLine = new TicketLineInfo();
        _myTicket.sumProductID();
        _myLine.setProductId(_myTicket.getProductID());
        Taxes taxInfoItem = new Taxes();
        taxInfoItem = taxInfoItem.getTaxeById(String.valueOf(tax),_myTicket.getEquipmet().getId());
        _myLine.setTaxInfo(new TaxInfo(taxInfoItem.getId(),taxInfoItem.getNombre(), taxInfoItem.getRate(), taxInfoItem.getRateCascade()));
       
        int sum = 0;
        if(DivisorPrice == 1000)
            sum = 1;
        
        double quantity = Double.valueOf(pTrama.substring(11 + n + sum,16 + n + sum));   
        double quantityDec = Double.valueOf(pTrama.substring(16 + n + sum,19 + n + sum))/DivisorQuantity;
        quantity = quantity + quantityDec;       
          
        double priceDec =  (Double.valueOf(pTrama.substring(9 + n,11 + n + sum))/DivisorPrice);
        double price =  (Double.valueOf(pTrama.substring(1 + n,9 + n)));
        price = price + priceDec;
        
        String productName = "";
        String productCode = "";
        if(pTrama.charAt(19 + n + sum) == '|'){
            productCode = pTrama.substring(19 + n + sum + 1, pTrama.lastIndexOf('|'));
            productName = pTrama.substring(pTrama.lastIndexOf('|') + 1);
                    _myLine.setProductCode(productCode);
        }
        else {
            productName = pTrama.substring(19 + n + sum);
        }
        
                    
        
        if(!commentsList.isEmpty()){
            ArrayList<String> commentsListOfProduct = new ArrayList<String>();
            for ( int i = 0; i < commentsList.size(); ++i ) { 
                commentsListOfProduct.add("");
                commentsListOfProduct.set(i,commentsList.get(i));
                }
            _myLine.setCommentsLines(commentsListOfProduct); 
            commentsList.clear();
        }   
        
        //ESTO ES DE PRUEBA:!!!        
         if(SubtotalCalled == false){
             _myTicket.addSubtotalLine("");
         }        
        //HASTA AQUI
         
        _myLine.setPrice(price);
        _myLine.setMultiply(quantity);
        
        switch(tax){
        case 0: productName += " (E)";
            break;
        case 1: productName += " (G)";
            break;
        case 2: productName += " (R)";
            break;
        case 3: productName += " (A)";
            break;
        }
        
            if (productName.length() > 24){
            if(productName.length() > 42){
            _myLine.setProductNameHigher(productName.substring(0, 42));
            productName = productName.substring(42,productName.length());
            }
            else if(productName.length() < 42){
            _myLine.setProductNameHigher(productName.substring(0, 25));
            productName = productName.substring(24,productName.length());
            }
        }  
        _myLine.setProductID(productName);
        
        this._myTicket.addLine(_myLine);
        TicketTaxInfo tickTaxInf = new TicketTaxInfo(_myLine.getTaxInfo());
        tickTaxInf.setSubTotal(_myLine.getSubValue());
        tickTaxInf.setTax(_myLine.getTax());
        this._myTicket.addTaxeInfo(tickTaxInf);
        
        double subtotalBaseAmount = _myLine.getSubValue();
        _myLine.setBaseAmount(subtotalBaseAmount);
        
        this.setTypeDisplay(NOT_PRINTABLE);
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getTypeAdjustment().size() + 1)){
              _myTicket.addTypeAdjustment("");
        }
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getDiscountAmount().size() + 1)){
              _myTicket.addDiscountAmount("");
        }   
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getRechargeAmount().size() + 1)){
              _myTicket.addRechargeAmount("");
        }
        //productLinePrinted = 1;
         return RESPONSE_OK;
         
        }catch(Exception ex)
        {
//            System.out.println("Error");
            return RESPONSE_ERROR;
        }        
    }

    @Override
    public int PrintRecTotal(int pIdMean) { 
        //Aumentar números de Subtotal, Recargos y Descuentos por producto registrado
        if(_myTicket.getSubtotalList().isEmpty()){
            _myTicket.addSubtotalLine("");
        }
        while(_myTicket.getProductID() >= (_myTicket.getSubtotalList().size())){
                          _myTicket.addSubtotalLine("");
                    }        
        while(_myTicket.getSubtotalList().size()>_myTicket.getNameAdjustmentCorrected().size()){
            _myTicket.setSubtotalAdjustmentCorrected("",0.0);
            }
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getTypeAdjustment().size())){
                          _myTicket.addTypeAdjustment("");
                    }
                    while(_myTicket.getSubtotalList().size() >= (_myTicket.getDiscountAmount().size())){
                          _myTicket.addDiscountAmount("");
                    }   
                    while(_myTicket.getSubtotalList().size() >= (_myTicket.getRechargeAmount().size())){
                          _myTicket.addRechargeAmount("");
                    }
        
        if(this._myTicket.getLinesCount() > 0)
        {
            //Si hay productos registrados
         this.setTypeDisplay(PRINTABLE);
               _meanPayment = new MeanPayment();
                MeanPayment[] listMeans = _meanPayment.mediaList(this._myTicket.getEquipmet().getId());
                if(_myTicket.getActivoCash() == 0.0){
                    _myTicket.setActivoCash(_myTicket.getTotal());
                }                    
                for(MeanPayment m : listMeans)
                {
                    if(pIdMean == m.getId_mean())
                    {
                        //Se crea el registro del pago en la base de datos
                     PaymentInfo payment = new PaymentInfo(m.getDescription(), _myTicket.getActivoCash(),_myTicket.getActivoCash(), Integer.valueOf(m.getId_mean()));
                     if(_myTicket.getPayments().isEmpty()){
                    _paymentsList = new ArrayList<PaymentInfo>();
                    _paymentsList.add(payment);
                    _myTicket.setPayments(_paymentsList);}
                     else{                        
                         _paymentsList.add(payment);
                     }
                    }
                }    
                //Prueba si esta lista de pago está llenandose
            for(int num = 0; num < _paymentsList.size(); num++){
            }
            //Termina
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
//            try{
//            this._paymentsModel = PaymentsModel.loadInstance(this._paymentsModel.getCountReport(),this._myTicket.getEquipmet().getId());
//            }
//            catch (DeviceException e){                
//            }
            this._dataLogicSales.cerrarCajaData(this._paymentsModel.getMoney(), this._paymentsModel.getHost());
            this.setTypeDisplay(PRINTABLE);
            return RESPONSE_OK;
        }else
        {
            return RESPONSE_ERROR;
        }
    }
//    
//    public void resetPayments(){
//        Payments resetPayment = new Payments();
//        ResultQueries.TotalPayment[] ListaDePrueba = resetPayment.SumPayments(this._paymentsModel.getMoney());
//        for(int num = 0; num < Array.getLength(ListaDePrueba); num++){
//            ListaDePrueba[num].setPago(0);
//            ListaDePrueba[num].setTotalpago(0.0);
//            }        
//    }
    
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
			}else if (pNameCmd.equals("SV")) {
				this._mPrinterdata = new SVPrinterData();
			}else if(pNameCmd.equals("U0X"))
                        {
                          this._mPrinterdata = new ReportData();
                          this._mPrinterdata.setMode('X');
                        }else if(pNameCmd.equals("U0Z"))
                        {
                          this._mPrinterdata = new ReportData();
                          this._mPrinterdata.setMode('Z');
                        }else if(pNameCmd.substring(0,2).equals("U3"))
                        { //Por rango de Numeros
                            int zi = Integer.valueOf(pNameCmd.substring(3,9));
                            int zf = Integer.valueOf(pNameCmd.substring(9));
                            int dim = zf - zi + 1;
                            this._mPrinterdataArray = new ArrayList<>();
                            int w = zi, y = 0;
                            while(y < dim)
                            {
                                this._mPrinterdata = new ReportData();
                                this._mPrinterdata.setNumberZ(w);
                                this._mPrinterdata.setMode('L');
                                this._mPrinterdataArray.add(_mPrinterdata);
                              ++w;
                              ++y;
                            }
                            

                        }  
                         else if(pNameCmd.substring(0,2).equals("U2"))
                        { //Por rango de Fechas
                          this._mPrinterdata = new ReportData();
                            this._mPrinterdata.setMode('Z');
                        }  
                        else {
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
        return "comandosVe";
    }
    @Override
    public int PrintRecTotal(PaymentInfo pPayment) {
         if(_myTicket.getSubtotalList().isEmpty()){
            _myTicket.addSubtotalLine("");
        }
        while(_myTicket.getSubtotalList().size()>_myTicket.getNameAdjustmentCorrected().size()){
            _myTicket.setSubtotalAdjustmentCorrected("",0.0);
        }
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
               if(_myTicket.getActivoCash() == 0.0){
                        _myTicket.setActivoCash(_myTicket.getTotal());
                    }
               
                _paymentsList = _myTicket.getPayments();
                if(pPayment.getPaid() > _myTicket.getActivoCash()){
                        _myTicket.setChange(pPayment.getPaid() - _myTicket.getActivoCash());
                    }                
                _paymentsList.add(pPayment);
                _myTicket.setPayments(_paymentsList);
                
            //Prueba si esta lista de pago está llenandose
//            for(int num = 0; num < _paymentsList.size(); num++){
            System.err.println("Total Payment Number " + 0 + ": " + _paymentsList.get(0) + ", size: " + _paymentsList.size());
//            }
            //Termina
                
                if(this._myTicket.getTotalPaid() >= _myTicket.getTotal())
                {
                 this.setTypeDisplay(PRINTABLE);
                }else
                {
                    if(_myTicket.getActivoCash() == 0.0 ){
                        _myTicket.setActivoCash(_myTicket.getTotal() - pPayment.getPaid());
                    }
                    else{
                        _myTicket.setActivoCash(_myTicket.getActivoCash()- pPayment.getPaid());
                    }
                  this.setTypeDisplay(NOT_PRINTABLE);
                }                
         return RESPONSE_OK;
        }else
        {
//            System.out.println("Error");
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
        
        if(pTrama.length() > 46){
            pTrama = pTrama.substring(0, 46);
        }
        
        if(listFH.length == 0)
        {  return RESPONSE_ERROR;}
        
        fh = listFH[0];
        
        if(Integer.valueOf(pTrama.substring(2,4)) > 0 && Integer.valueOf(pTrama.substring(2,4)) < 9)
        {  //Programo el Encabezado Correspondiente            
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
        else if(Integer.valueOf(pTrama.substring(2,4)) > 90 && Integer.valueOf(pTrama.substring(2,4)) < 99)
        {  //Programo el Pie de Pagina Correspondiente
           if(Integer.valueOf(pTrama.substring(2,4)) == 91)
              fh.setP1(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 92)
              fh.setP2(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 93)
              fh.setP3(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 94)
              fh.setP4(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 95)
              fh.setP5(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 96)
              fh.setP6(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 97)
              fh.setP7(pTrama.substring(4));
           else if(Integer.valueOf(pTrama.substring(2,4)) == 98)
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
        if(this._state == STATE_TRANSACCTION){
        String Name = "";
         this.setTypeDisplay(PRINTABLE);
         List<String> lineNotFiscal = new ArrayList<>();
            switch (_myTicket.getTicketType()) {
                case 1:
                    Name = "FACTURA ANULADA";
                    break;
                case 2:
                    Name = "NOTA DE CREDITO ANULADA";
                    break;
                case 3:
                    Name = "NOTA DE DEBITO ANULADA";
                    break;
                case 7:
                    break;
                default:
                    Name = "ANULADO";
                    break;
            }
            if(!"".equals(Name)){
                 lineNotFiscal.add(Name);}
                 this._myTicket.setNotFiscalLines(lineNotFiscal);
        return RESPONSE_OK;
        }
        else{
           return RESPONSE_ERROR;
        }         
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
         //this.setTypeDisplay(PRINTABLE);
//                System.err.println("PRINCIPIO SUBTOTAL METHOD Subtotal size: " + _myTicket.getSubtotalList() 
//        + ", cantidad de productos " + _myTicket.getProductID() + ", recargos: " + _myTicket.getRechargeAmount() + ", descuento: " + _myTicket.getDiscountAmount());
////        System.out.println("PrintSubTotal() a: Subtotal size: " + _myTicket.getSubtotalList().size()+ ". Recharge Size: " + _myTicket.getRechargeAmount().size() + ". Discount Size: " + _myTicket.getDiscountAmount().size());

        while(_myTicket.getSubtotalList().size() > (_myTicket.getTypeAdjustment().size() + 1)){
                          _myTicket.addTypeAdjustment("");
                    }
        while(_myTicket.getSubtotalList().size() > (_myTicket.getDiscountAmount().size() + 1)){
                          _myTicket.addDiscountAmount("");
                    }   
        while(_myTicket.getSubtotalList().size() > (_myTicket.getRechargeAmount().size() + 1)){
                          _myTicket.addRechargeAmount("");
                    }
//                System.out.println("PrintSubTotal() b: Subtotal size: " + _myTicket.getSubtotalList().size()+ ". Recharge Size: " + _myTicket.getRechargeAmount().size() + ". Discount Size: " + _myTicket.getDiscountAmount().size());

        SubtotalCalled = true;
         if(SubtotalCalled == true){
             if("".equals(_myTicket.getSubtotalList().get(_myTicket.getSubtotalList().size()-1))){
                _myTicket.subtotalList.set(_myTicket.getSubtotalList().size()-1, "Bs " + formatter.format(round((_myTicket.getPresentSubTotal(_myLine.getProductId())),2)));  
             }
             else{
                 _myTicket.addSubtotalLine("Bs " + formatter.format(round((_myTicket.getPresentSubTotal(_myLine.getProductId())),2)));  
             }
            Subtotal = _myTicket.getPresentSubTotal(_myLine.getProductId());
            _myTicket.setPresentSubTotal(0.0);
                    }
//                 System.out.println("PrintSubTotal() c: Subtotal size: " + _myTicket.getSubtotalList().size()+ ". Recharge Size: " + _myTicket.getRechargeAmount().size() + ". Discount Size: " + _myTicket.getDiscountAmount().size());
//        System.err.println("FINAL SUBTOTAL METHOD Subtotal size: " + _myTicket.getSubtotalList() 
//        + ", cantidad de productos " + _myTicket.getProductID() + ", recargos: " + _myTicket.getRechargeAmount() + ", descuento: " + _myTicket.getDiscountAmount());
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
                 lineNotFiscal.add(this.getPreCodeOper("FISCALIZACION", pTrama.substring(3,13)));
                 lineNotFiscal.add("");
                 this._myTicket.setNotFiscalLines(lineNotFiscal);
            }else  if(pTrama.equals("PMFS"))      //Fiscalización Vieja Venezolana              
            {
                 this.BeginFiscalDocument("7");
                 this.setTypeDisplay(PRINTABLE);
                 List<String> lineNotFiscal = new ArrayList<String>();
                 lineNotFiscal.add("Inicio Fiscal");
                 lineNotFiscal.add("Exitoso");
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
        this.BeginNotFiscalDocument("Bsf");
                 this.setTypeDisplay(PRINTABLE);
                 List<String> lineNotFiscal = new ArrayList<>();                 
                 lineNotFiscal.add(this._myTicket.getEquipmet().getVersion_firmware());
                 Flags fl = new Flags();
                 Flags[] listFlags = fl.listFlags(this._myTicket.getEquipmet().getId());
                  int j = 0;     
                  while(j < 7)
                        {
                            int w = 0;
                            String lineFlags = "";
                            outerloop:while(w < 10)
                               {
                                int indice = w +(j*10);
                                if (indice == 64){
                                          break outerloop;
                                      }
                                String flag = String.valueOf(listFlags[indice].getValue());
                                while(flag.length() < 2)
                                   { flag = "0" + flag; }
                                  lineFlags = lineFlags + flag + " ";
                                      ++w;
                                }
                                if (j == 6){
                                lineNotFiscal.add("F" + String.valueOf(j)+"0->F"+String.valueOf(j)+"3: " + lineFlags);
                                j = 7;
                                }
                                else{                                 
                                lineNotFiscal.add("F" + String.valueOf(j)+"0->F"+String.valueOf(j)+"9: " + lineFlags);
                                }
                           ++j;
                        }
                        lineNotFiscal.add("------------------------------------------");
                    
                        Taxes ta = new Taxes();
                        Taxes[] listTaxes = ta.getTaxesByIdEquipment(this._myTicket.getEquipmet().getId());
                        
                        for (Taxes t : listTaxes)
                        {
                          lineNotFiscal.add(t.getNombre() + " (" + String.valueOf(t.getRate()*100) +"0%) (A)");
                        }
                        
                        lineNotFiscal.add("------------------------------------------");
                        
                        MeanPayment mp = new MeanPayment();
                        MeanPayment[] listMeans = mp.mediaList(this._myTicket.getEquipmet().getId());
                        
                        for(MeanPayment m : listMeans)
                        {
                            lineNotFiscal.add(util.Utilities.StringFormatXero(String.valueOf(m.getId_mean()), 2) + ": " + m.getDescription());
                        }
                        
                        //Eliminar Tasa Exenta 
                        lineNotFiscal.remove(9);
                 this._myTicket.setNotFiscalLines(lineNotFiscal);
                 this.EndNotFiscalDocument();
        return RESPONSE_OK;
    }

    @Override
    public int PrintComent(String pMessanger) { 
        Flags fl = new Flags();
        int idFlComments = 49;
        fl = fl.getFlags(idFlComments, this._myTicket.getEquipmet().getId());
        if(fl.getValue() == 02){
            commentsList.add(pMessanger);
        }
        else{
            if(pMessanger.length() > 40){
                pMessanger = "|" + pMessanger.substring(0, 40) + "|";
            }
            else{
                pMessanger = "|" + pMessanger + "|";
            }
            commentsList.add(pMessanger);
        }
        return RESPONSE_OK;
    }

    @Override
    public int CorrecLineTransaction() {
        if(!(this.getLastTrama().startsWith("k") || this.getLastTrama().startsWith("q") || this.getLastTrama().startsWith("p"))){
        this.PrintRecItemCorrected(this.getLastTrama());  
         return RESPONSE_OK;
        }
        else if(this.getLastTrama().startsWith("q")){
            if((_myLine.isAdjustment()==true) && ("".equals(_myTicket.getSubtotalList().get(_myTicket.getSubtotalList().size()-1)))){
                //Corregir recargo/descuento sobre producto, guardar en set adjustment name amount in line
                _myLine.setAdjustmentCorrected(_myLine.getProductAttSetInstDesc(), (-1*(_myLine.rechargeAmount + _myLine.DiscountAmount)));
                _myLine.setBaseAmount(_myLine.getProductBaseAmount() + (-1*(_myLine.rechargeAmount + _myLine.DiscountAmount)));
                return RESPONSE_OK;
            }
            else{
                //Corregir recargo/descuento sobre subtotal, guardar en set subtotaladjustment corrected, save in a list for each subtotal
                while(_myTicket.getSubtotalList().size()>_myTicket.getNameAdjustmentCorrected().size()+1){
                _myTicket.setSubtotalAdjustmentCorrected("",0.0);
                }
                _myTicket.setSubtotalAdjustmentCorrected();
//                String valueDecimal = this.getLastTrama().substring(2,9) + "." + this.getLastTrama().substring(9);
                double totalPrice = _myTicket.getAmountAdjustmentCorrected().get(_myTicket.getAmountAdjustmentCorrected().size()-1);
                for(TicketLineInfo line : this._myTicket.getLines()){
                      double subtotalBaseAmount = 0.0;
                      double agregado = 0.0;
//                      if(totalPrice < 0){
//                          totalPrice = totalPrice * -1;
//                      }
//                      System.err.println("APrecio sin recargo: " + (line.getPrice()*line.getMultiply()));
//                      System.err.println("TPrecio con recargo: " + line.getProductBaseAmount());
                      agregado = totalPrice;// * (line.getProductBaseAmount()/Subtotal); 
                      subtotalBaseAmount = line.getProductBaseAmount();
                      if(this.getLastTrama().charAt(1) == '-'){
                          if(line.getProductBaseAmount() == 0.0){
                          subtotalBaseAmount += totalPrice;
                          }
                          else {
                          subtotalBaseAmount = (subtotalBaseAmount*Subtotal)/(Subtotal-totalPrice);}
                      }
                      else if(this.getLastTrama().charAt(1) == '+'){
                          subtotalBaseAmount = (subtotalBaseAmount*Subtotal)/(Subtotal-totalPrice);
                      }   
                      line.setBaseAmount(subtotalBaseAmount);
//                      System.err.println("Subtotal: " + Subtotal + ", recargo/descuento: " + totalPrice);
//                      System.err.println("DPrecio sin recargo: " + subtotalBaseAmount);
//                      System.err.println(line.getProductBaseAmount() + line.get);
                }   
                return RESPONSE_OK;
            }    
        }
        else if(this.getLastTrama().startsWith("p")){
            if((_myLine.isAdjustment()==true) && ("".equals(_myTicket.getSubtotalList().get(_myTicket.getSubtotalList().size()-1)))){
                //Corregir recargo/descuento sobre producto, guardar en set adjustment name amount in line
                _myLine.setAdjustmentCorrected(_myLine.getProductAttSetInstDesc(), -1*(_myLine.rechargeAmount + _myLine.DiscountAmount));
                _myLine.setBaseAmount(_myLine.getProductBaseAmount() + (-1*(_myLine.rechargeAmount + _myLine.DiscountAmount)));
                return RESPONSE_OK;
            }
            else{
                //Corregir recargo/descuento sobre subtotal, guardar en set subtotaladjustment corrected, save in a list for each subtotal
                while(_myTicket.getSubtotalList().size()>_myTicket.getNameAdjustmentCorrected().size()+1){
                _myTicket.setSubtotalAdjustmentCorrected("",0.0);
                }
                _myTicket.setSubtotalAdjustmentCorrected();
                //PRUEBA PARA SUMAR LAS CORRECCIONES DE SUBTOTAL A TODOS LOS PRODUCTOS AFECTADOS
                for(TicketLineInfo line : this._myTicket.getLines()){
                      double subtotalBaseAmount = 0.0;
                      double agregado = 0.0;                      
                      subtotalBaseAmount = line.getProductBaseAmount();
//                      System.err.println("APrecio sin recargo: " + (line.getPrice()*line.getMultiply()));
//                      System.err.println("TPrecio con recargo: " + line.getProductBaseAmount());
                      String valueDecimal = this.getLastTrama().substring(2,4) + "." + this.getLastTrama().substring(4);
                      double recargo_porc = Double.valueOf(valueDecimal)/100;
                      agregado = recargo_porc * subtotalBaseAmount;
                      if(this.getLastTrama().charAt(1) == '-'){
                          subtotalBaseAmount = subtotalBaseAmount/(1-recargo_porc);
                      }
                      else if(this.getLastTrama().charAt(1) == '+'){
                          subtotalBaseAmount = subtotalBaseAmount/(1+recargo_porc);
                      }
//                      System.err.println("DPrecio sin recargo: " + subtotalBaseAmount);
                      line.setBaseAmount(subtotalBaseAmount);
                }             
                return RESPONSE_OK;
            }   
        }
        else if(this.getLastTrama().startsWith("k")){
            return RESPONSE_ERROR;
        }
        else
           return RESPONSE_ERROR;
    }
    
     public int PrintRecItemCorrected(String pTrama) {
        int tax = 0;
        int n = 0;
        DivisorPrice = 100;
        DivisorQuantity = 1000; 
        
        try
        {
            if(pTrama.substring(0,1).equals("d"))
            {       n = 1;  
                 if(pTrama.substring(1,2).equals("1"))
                tax = 1;
                 else  if(pTrama.substring(1,2).equals("2"))
                tax = 2;
                 else  if(pTrama.substring(1,2).equals("3"))
                tax = 3;
            }
            if(pTrama.substring(0,1).equals("`"))
            {       n = 1;  
                if(pTrama.substring(1,2).equals("1"))
                tax = 1;
                else  if(pTrama.substring(1,2).equals("2"))
                tax = 2;
                 else  if(pTrama.substring(1,2).equals("3"))
                tax = 3;
            }
            else
            {
                if(pTrama.substring(0,1).equals(" "))
                tax = 0;
                else if(pTrama.substring(0,1).equals("!"))
                tax = 1;
                 else  if(pTrama.substring(0,1).equals("\""))
                tax = 2;
                 else  if(pTrama.substring(0,1).equals("#"))
                tax = 3;
            }
            
        _myLine = new TicketLineInfo();
        _myLine.setCorrection(true);
        _myTicket.sumProductID();
        _myLine.setProductId(_myTicket.getProductID());
        Taxes taxInfoItem = new Taxes();
        taxInfoItem = taxInfoItem.getTaxeById(String.valueOf(tax),_myTicket.getEquipmet().getId());
        _myLine.setTaxInfo(new TaxInfo(taxInfoItem.getId(),taxInfoItem.getNombre(), taxInfoItem.getRate(), taxInfoItem.getRateCascade()));
       
        int sum = 0;
        if(DivisorPrice == 1000)
            sum = 1;
        
        double quantity = Double.valueOf(pTrama.substring(11 + n + sum,16 + n + sum));   
        double quantityDec = Double.valueOf(pTrama.substring(16 + n + sum,19 + n + sum))/DivisorQuantity;
        quantity = quantity + quantityDec;       
          
        double priceDec =  (Double.valueOf(pTrama.substring(9 + n,11 + n + sum))/DivisorPrice);
        double price =  (Double.valueOf(pTrama.substring(1 + n,9 + n)));
        price = price + priceDec;
        price *= -1;
        
        String productName = "";
        String productCode = "";
        if(pTrama.charAt(19 + n + sum) == '|'){
            productCode = pTrama.substring(19 + n + sum + 1, pTrama.lastIndexOf('|'));
            productName = pTrama.substring(pTrama.lastIndexOf('|') + 1);
                    _myLine.setProductCode(productCode);
        }
        else {
            productName = pTrama.substring(19 + n + sum);
        }
        
        if (productName.length() > 20){
            if(productName.length() > 42){
            _myLine.setProductNameHigher(productName.substring(0, 42));
            productName = productName.substring(42,productName.length());
            }
            else if(productName.length() < 42){
            _myLine.setProductNameHigher(productName.substring(0, 42));
            productName = productName.substring(productName.length() - 1,productName.length());
            }
        }                      
        
        if(!commentsList.isEmpty()){
            ArrayList<String> commentsListOfProduct = new ArrayList<String>();
            for ( int i = 0; i < commentsList.size(); ++i ) { 
                commentsListOfProduct.add("");
                commentsListOfProduct.set(i,commentsList.get(i));
                }
            _myLine.setCommentsLines(commentsListOfProduct); 
            commentsList.clear();
        }   
        
        //ESTO ES DE PRUEBA:!!!        
         if(SubtotalCalled == false){
             _myTicket.addSubtotalLine("");
         }        
        //HASTA AQUI
         
        _myLine.setPrice(price);
        _myLine.setMultiply(quantity);
        
        switch(tax){
        case 0: productName += " (E)";
            break;
        case 1: productName += " (G)";
            break;
        case 2: productName += " (R)";
            break;
        case 3: productName += " (A)";
            break;
        }
        
        _myLine.setProductID(productName);
        
        this._myTicket.addLine(_myLine);
        TicketTaxInfo tickTaxInf = new TicketTaxInfo(_myLine.getTaxInfo());
        tickTaxInf.setSubTotal(_myLine.getSubValue());
        tickTaxInf.setTax(_myLine.getTax());
        this._myTicket.addTaxeInfo(tickTaxInf);
        
        double subtotalBaseAmount = _myLine.getSubValue();
        _myLine.setBaseAmount(subtotalBaseAmount);
        
        this.setTypeDisplay(NOT_PRINTABLE);
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getTypeAdjustment().size() + 1)){
              _myTicket.addTypeAdjustment("");
        }
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getDiscountAmount().size() + 1)){
              _myTicket.addDiscountAmount("");
        }   
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getRechargeAmount().size() + 1)){
              _myTicket.addRechargeAmount("");
        }
        //productLinePrinted = 1;
         return RESPONSE_OK;
         
        }catch(Exception ex)
        {
//            System.out.println("Error");
            return RESPONSE_ERROR;
        }        
    }

    @Override
    public int PrintRecItemAdjustement(String pType, double pAmount) {
        int returna = 0;
        List<TicketLineInfo> linesInfo = this._myTicket.getLines();
        if(linesInfo.size() > 0)
        {
            
           DecimalFormat formato = new DecimalFormat("############0.00"); 
           if(pType.equals("p-") || pType.equals("p+"))
           {
              int t = 0;
              double recargo_porc = pAmount/100;
              double sustraendro = 0.00;
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getTypeAdjustment().size() + 1)){
              _myTicket.addTypeAdjustment("");
        }
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getDiscountAmount().size() + 1)){
              _myTicket.addDiscountAmount("");
        }   
        while(_myTicket.getSubtotalList().size() >= (_myTicket.getRechargeAmount().size() + 1)){
              _myTicket.addRechargeAmount("");
        }
              if(SubtotalCalled == true){//Recargo/Descuento al Subtotal
                  {     
//                     System.out.println("PrintRecItemAdjustement() a: Subtotal size: " + _myTicket.getSubtotalList().size()+ ". Recharge Size: " + _myTicket.getRechargeAmount().size() + ". Discount Size: " + _myTicket.getDiscountAmount().size());

                      String attr = "RECAR (";                      
                      sustraendro = (double) _myTicket.toDouble(_myTicket.getSubtotalList().get(_myTicket.getSubtotalList().size()-1)) * (double) recargo_porc;
//                      System.out.println("presentSubtotal a: " + _myTicket.getPresentSubTotal(_myTicket.getProductID()));
                      if(pType.equals("p-"))
                              {                                
                                  sustraendro *= -1;
                                  attr = "DESC (";
                                  _myTicket.DiscountAmount.set(_myTicket.getDiscountAmount().size()-1,"Bs " + formatter.format(round(sustraendro,2)));
                              }
                      else if(pType.equals("p+")){
                          _myTicket.rechargeAmount.set(_myTicket.getRechargeAmount().size()-1,"Bs " + formatter.format(round(sustraendro,2)));
                      }
                      _myTicket.typeAdjustment.set(_myTicket.getTypeAdjustment().size()-1,attr + (formato.format(recargo_porc * 100)) + " %)");
//                      System.out.println("PrintRecItemAdjustement() b: Subtotal size: " + _myTicket.getSubtotalList().size()+ ". Recharge Size: " + _myTicket.getRechargeAmount().size() + ". Discount Size: " + _myTicket.getDiscountAmount().size());
//                      System.out.println("presentSubtotal b: " + _myTicket.getPresentSubTotal(_myTicket.getProductID()));
                  }
                  for(TicketLineInfo line : this._myTicket.getLines()){
                      double subtotalBaseAmount = 0.0;
                      double agregado = 0.0;                      
                      subtotalBaseAmount = line.getProductBaseAmount();
                      agregado = recargo_porc * subtotalBaseAmount;
                      if(pType.equals("p-")){
//                          System.err.println("1) Linea producto precio hasta el recargo/desc: " + formatter.format(subtotalBaseAmount) +", porcentaje de recargo: " + recargo_porc + ", el recargo/desc: "+ formatter.format(agregado));
                          subtotalBaseAmount -= agregado;
//                          System.err.println("2) Linea producto precio hasta el recargo/desc: " + formatter.format(subtotalBaseAmount) +", porcentaje de recargo: " + recargo_porc + ", el recargo/desc: "+ formatter.format(agregado));

                      }
                      else if(pType.equals("p+")){
                          subtotalBaseAmount += agregado;
                      }
                      line.setBaseAmount(subtotalBaseAmount);
                  }
                  SubtotalCalled = false;
                  returna = RESPONSE_OK;
                  }
              else{
                  if(_myLine.getRechargeAmount() != 0.0 || _myLine.getDiscountAmount() != 0.0){
                      returna =  RESPONSE_ERROR;
                  }
                  else{
              for(TicketLineInfo line : this._myTicket.getLines())
              {     
                  
                      if(t == linesInfo.size() -1)
                  { //Si es el ultimo Items registrado                      
                      String attr = "RECAR (";                      
                      //if(this.PrintSubTotal() == RESPONSE_OK){
                          //sustraendro = _myTicket.getSubTotal() * recargo_porc;
                      //}
                      //else{
                  //    sustraendro = (double)line.getSubValue() * (double)recargo_porc;
                      //}
                      sustraendro = (double) line.getSubValue() * (double) recargo_porc;                      
                      if(pType.equals("p-"))
                              {   
                              //if(this.PrintSubTotal() == RESPONSE_OK){
                              //sustraendro = _myTicket.getSubTotal() * recargo_porc;
                              //}
                              //else{
                                //  sustraendro = (double) line.getSubValue() * (double) recargo_porc;
                              //}                              
                                  sustraendro *= -1;
                                  attr = "DESC (";
                                 line.setDiscountAmount((double) sustraendro);
                              }
                      else if(pType.equals("p+")){
                          line.setRechargeAmount(sustraendro);
//                          System.out.println("Recargo: " + sustraendro);
                      }                      
                      line.setProductAttSetInstId(pType);
                      line.setProductAttSetInstDesc(attr + (formato.format(recargo_porc * 100)) + " %)");
                  }
                      line.setBaseAmount(line.getProductBaseAmount() + sustraendro);                      
                line.setAdjustment(true);
                ++t;
              }
                  returna =  RESPONSE_OK;}}
           }
           else if(pType.equals("q-") || pType.equals("q+"))
           {              
              int t = 0;
              double amount = pAmount;
              double totalPrice = 0.00;
              if(SubtotalCalled == true){//Recargo/Descuento al Subtotal
//                  System.out.println("PrintRecItemAdjustement() a: Subtotal size: " + _myTicket.getSubtotalList().size()+ ". Recharge Size: " + _myTicket.getRechargeAmount().size() + ". Discount Size: " + _myTicket.getDiscountAmount().size());

                  String attr = "";
//                      System.out.println("presentSubtotal a: " + _myTicket.getPresentSubTotal(_myTicket.getProductID()));

                      if(pType.equals("q-"))
                              {
                                  totalPrice = amount * -1;
                                  attr = "DESC";
                                  _myTicket.DiscountAmount.set(_myTicket.getDiscountAmount().size()-1,"Bs " + formatter.format(round(totalPrice,2)));
                              }
                      else if(pType.equals("q+")){
                                  totalPrice = amount;
                                  attr = "RECAR";
                                  _myTicket.rechargeAmount.set(_myTicket.getRechargeAmount().size()-1,"Bs " + formatter.format(round(totalPrice,2)));
                      }
                    _myTicket.typeAdjustment.set(_myTicket.getTypeAdjustment().size()-1,attr);
                    SubtotalCalled = false;
//                    System.out.println("PrintRecItemAdjustement() b: Subtotal size: " + _myTicket.getSubtotalList().size()+ ". Recharge Size: " + _myTicket.getRechargeAmount().size() + ". Discount Size: " + _myTicket.getDiscountAmount().size());
//                     System.out.println("presentSubtotal b: " + _myTicket.getPresentSubTotal(_myTicket.getProductID()));
                    for(TicketLineInfo line : this._myTicket.getLines()){
                      double subtotalBaseAmount = 0.0;
                      double agregado = 0.0;
                      if(totalPrice < 0){
                          totalPrice = totalPrice * -1;
                      }
                      agregado = totalPrice * (line.getProductBaseAmount()/Subtotal); 
//                      System.out.println(totalPrice + " + "+ Subtotal + " = " + agregado);
                      subtotalBaseAmount = line.getProductBaseAmount();
                      if(pType.equals("q-")){
                          subtotalBaseAmount -= agregado;
                      }
                      else if(pType.equals("q+")){
                          subtotalBaseAmount += agregado;
                      }    
                      line.setBaseAmount(subtotalBaseAmount);
//                      "Base Imponible: " + subtotalBaseAmount);
                  }returna =  RESPONSE_OK;
              }
              else{
                  if(_myLine.getRechargeAmount() != 0.0 || _myLine.getDiscountAmount() != 0.0){
                      returna = RESPONSE_ERROR;
                  }
                  else{
              for(TicketLineInfo line : this._myTicket.getLines())
              {
                  if(t == linesInfo.size() -1)
                  { //Si es el ultimo Items registrado
                      String attr = "";
                      if(pType.equals("q-"))
                              {
                                  totalPrice = amount * -1;
                                  attr = "DESC ";
                                  _myLine.setDiscountAmount((double)totalPrice);
                              }
                      else if(pType.equals("q+")){
                                  totalPrice = amount;
                                  attr = "RECAR ";
                                  _myLine.setRechargeAmount((double)totalPrice);
                      }                      
                      _myLine.setProductAttSetInstId(pType);
                      _myLine.setProductAttSetInstDesc(attr); 
                       line.setBaseAmount(line.getProductBaseAmount() + totalPrice);
                       line.setAdjustment(true);

                  }
                ++t;
              }returna =  RESPONSE_OK;}
            }
           }
            while(_myTicket.getSubtotalList().size() >= (_myTicket.getTypeAdjustment().size()+1)){
                  _myTicket.addTypeAdjustment("");
            }
            while(_myTicket.getSubtotalList().size() >= (_myTicket.getDiscountAmount().size()+1)){
                  _myTicket.addDiscountAmount("");
            }   
            while(_myTicket.getSubtotalList().size() >= (_myTicket.getRechargeAmount().size()+1)){
                  _myTicket.addRechargeAmount("");
            }
           
        }else
        {
           returna = RESPONSE_ERROR;
        }
        if(returna == RESPONSE_OK){
            return RESPONSE_OK;
        }
        else {
            return RESPONSE_ERROR;
        }
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
    public int PrintRecItemVoid(String pTrama) {
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
                 List<String> lineNotFiscal = new ArrayList<>();
                 lineNotFiscal.add("Codigo Desbloqueo");
                 lineNotFiscal.add(this.getPreCodeOper("DESBLOQUEO",  pSerial));
                 lineNotFiscal.add("");
                 this._myTicket.setNotFiscalLines(lineNotFiscal);                 
                 return RESPONSE_OK;
    }
    /*public MeanPayment[] meansOfPayment(MeanPayment[] paymentList){
        paymentList = this._meanPayment.ListMeanPayment();
        return paymentList;
    }
    public boolean meansOfPaymentIsUsed(MeanPayment[] listOfMeans){
        if(listOfMeans.)
    }*/
        public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);        
//        NumberFormat formatter = new DecimalFormat("#0.00");     
//        return Double.valueOf(formatter.format((double) tmp / factor));
        return (double) tmp / factor;
    }
        
    public int PrintAnnulment(String pTrama) {
        double returna = 0;
        System.err.println("Trama starts with: " + pTrama.substring(0));
        if(pTrama.startsWith("ä")){            
            for(int ite = 0; ite < 21; ite++){  
                char[] charArray = pTrama.toCharArray();
                char num = charArray[ite];
                for(int i = 2; i < 21; i++){
                    if((!(num == '0' || num =='1' || num =='2' || num =='3' || num =='4' || num =='5' || num =='6' || num =='7' || num =='8' || num =='9')) && ite == i){                    
                        returna = RESPONSE_ERROR;
                    }
                    else {
                        returna = RESPONSE_OK;
                    }
                }                
            }
                char[] charArray = pTrama.toCharArray();
                String num = pTrama.substring(1);
                    if((!(num == "0" || num =="1" || num =="2" || num =="3"))){                    
                        returna = RESPONSE_ERROR;
                    }
                    else {
                        returna = RESPONSE_OK;
                    }                
        }
        
        try{
        String productName = "";
        String productCode = "";
        int tax = 0;
        int n = 0;
        DivisorPrice = 100;
        DivisorQuantity = 1000; 
        String taxs = "";
        switch (pTrama.substring(0,1)) {
                    case " ":
                        taxs = " ";
                        tax = 0;
                        break;
                    case "¡":
                        tax = 1;
                        taxs = "!";
                        break;
                    case "¢":
                        tax = 2;
                        taxs = "\"";
                        break;
                    case "£":
                        tax = 3;
                        taxs = "#";
                        break;
                    case "0":
                        taxs = "0";
                        tax = 0;
                        break;
                    case "1":
                        tax = 1;
                        taxs = "1";
                        break;
                    case "2":
                        tax = 2;
                        taxs = "2";
                        break;
                    case "3":
                        tax = 3;
                        taxs = "3";
                        break;
                    
        }
        int sum = 0;
        if(DivisorPrice == 1000)
            sum = 1;
        
        double quantity = Double.valueOf(pTrama.substring(11 + n + sum,16 + n + sum));   
        double quantityDec = Double.valueOf(pTrama.substring(16 + n + sum,19 + n + sum))/DivisorQuantity;
        quantity = quantity + quantityDec;       
          
        double priceDec =  (Double.valueOf(pTrama.substring(9 + n,11 + n + sum))/DivisorPrice);
        double price =  (Double.valueOf(pTrama.substring(1 + n,9 + n)));
        price = price + priceDec;
        pTrama = tax + pTrama.substring(1);
        if(pTrama.charAt(19 + n + sum) == '|'){
            productCode = pTrama.substring(19 + n + sum + 1, pTrama.lastIndexOf('|'));
            productName = pTrama.substring(pTrama.lastIndexOf('|') + 1);
//                    _myLine.setProductCode(productCode);
        }
        else {
            productName = pTrama.substring(19 + n + sum);
        }
        
        if (productName.length() > 20){
            if(productName.length() > 42){
//            _myLine.setProductNameHigher(productName.substring(0, 42));
            productName = productName.substring(42,productName.length());
            }
            else if(productName.length() < 42){
//            _myLine.setProductNameHigher(productName.substring(0, 42));
            productName = productName.substring(productName.length() - 1,productName.length());
            }
        }  
        for(TicketLineInfo line : _myTicket.getLines()){
//            System.err.println("Price " + line.getPrice() + "," + price);
//            System.err.println("Quantity " + line.getMultiply() + "," + quantity);
//            System.err.println("Tax " + line.getTaxInfo().getId() + "," + String.valueOf(tax));
//            System.err.println("Description " + line.getProductID().substring(0,line.getProductID().length()-4) + "," + productName);
            if((line.getPrice() == price)
                && (line.getMultiply() == quantity)
                && (line.getTaxInfo().getId() == null ? String.valueOf(tax) == null : line.getTaxInfo().getId().equals(String.valueOf(tax)))
                && (line.getProductID().substring(0,line.getProductID().length()-4) == null ? productName == null : line.getProductID().substring(0,line.getProductID().length()-4).equals(productName))){
                Double amountAdjusted = 0.0;
                String nameAdjustment = "";
                if(line.getDiscountAmount() != 0.0){
                    amountAdjusted = line.getDiscountAmount();
                }
                else if(line.getRechargeAmount() != 0.0){
                    amountAdjusted = line.getRechargeAmount();
                }                
                nameAdjustment = line.getProductAttSetInstDesc();
            n = 0;
            DivisorPrice = 100;
            DivisorQuantity = 1000; 

            try
            {
                if(pTrama.substring(0,1).equals("d"))
                {       n = 1;  
                    switch (pTrama.substring(1,2)) {
                        case "1":
                            tax = 1;
                            break;
                        case "2":
                            tax = 2;
                            break;
                        case "3":
                            tax = 3;
                            break;
                    }
                }
                if(pTrama.substring(0,1).equals("`"))
                {       n = 1;  
                    switch (pTrama.substring(1,2)) {
                        case "1":
                            tax = 1;
                            break;
                        case "2":
                            tax = 2;
                            break;
                        case "3":
                            tax = 3;
                            break;
                    }
                }
                else
                {
                    switch (pTrama.substring(0,1)) {
                        case " ":
    //                        taxs = " ";
                            tax = 0;
                            break;
                        case "¡":
                            tax = 1;
    //                        taxs = "!";
                            break;
                        case "¢":
                            tax = 2;
    //                        taxs = "\"";
                            break;
                        case "£":
                            tax = 3;
    //                        taxs = "#";
                            break;
                    }
                }

            _myLine = new TicketLineInfo();
            _myLine.setAnulado(true);
            _myTicket.sumProductID();
            _myLine.setProductId(_myTicket.getProductID());
            Taxes taxInfoItem = new Taxes();
            taxInfoItem = taxInfoItem.getTaxeById(String.valueOf(tax),_myTicket.getEquipmet().getId());
            _myLine.setTaxInfo(new TaxInfo(taxInfoItem.getId(),taxInfoItem.getNombre(), taxInfoItem.getRate(), taxInfoItem.getRateCascade()));

            sum = 0;
            if(DivisorPrice == 1000)
                sum = 1;

            quantity = Double.valueOf(pTrama.substring(11 + n + sum,16 + n + sum));   
            quantityDec = Double.valueOf(pTrama.substring(16 + n + sum,19 + n + sum))/DivisorQuantity;
            quantity = quantity + quantityDec;       

            priceDec =  (Double.valueOf(pTrama.substring(9 + n,11 + n + sum))/DivisorPrice);
            price =  (Double.valueOf(pTrama.substring(1 + n,9 + n)));
            price = price + priceDec;


            if(pTrama.charAt(19 + n + sum) == '|'){
                productCode = pTrama.substring(19 + n + sum + 1, pTrama.lastIndexOf('|'));
                productName = pTrama.substring(pTrama.lastIndexOf('|') + 1);
                        _myLine.setProductCode(productCode);
            }
            else {
                productName = pTrama.substring(19 + n + sum);
            }

            if (productName.length() > 20){
                if(productName.length() > 42){
                _myLine.setProductNameHigher(productName.substring(0, 42));
                productName = productName.substring(42,productName.length());
                }
                else if(productName.length() < 42){
                _myLine.setProductNameHigher(productName.substring(0, 42));
                productName = productName.substring(productName.length() - 1,productName.length());
                }
            }                      

            if(!commentsList.isEmpty()){
                ArrayList<String> commentsListOfProduct = new ArrayList<String>();
                for ( int i = 0; i < commentsList.size(); ++i ) { 
                    commentsListOfProduct.add("");
                    commentsListOfProduct.set(i,commentsList.get(i));
                    }
                _myLine.setCommentsLines(commentsListOfProduct); 
                commentsList.clear();
            }   

            //ESTO ES DE PRUEBA:!!!        
             if(SubtotalCalled == false){
                 _myTicket.addSubtotalLine("");
             }        
            //HASTA AQUI

            _myLine.setPrice(price*-1);
            _myLine.setMultiply(quantity);

            switch(tax){
            case 0: productName += " (E)";
                break;
            case 1: productName += " (G)";
                break;
            case 2: productName += " (R)";
                break;
            case 3: productName += " (A)";
                break;
            }

            _myLine.setProductID(productName);

            this._myTicket.addLine(_myLine);
            TicketTaxInfo tickTaxInf = new TicketTaxInfo(_myLine.getTaxInfo());
            tickTaxInf.setSubTotal(_myLine.getSubValue());
            tickTaxInf.setTax(_myLine.getTax());
            this._myTicket.addTaxeInfo(tickTaxInf);

            double subtotalBaseAmount = _myLine.getSubValue();
            _myLine.setBaseAmount(subtotalBaseAmount);

            this.setTypeDisplay(NOT_PRINTABLE);
            while(_myTicket.getSubtotalList().size() >= (_myTicket.getTypeAdjustment().size() + 1)){
                  _myTicket.addTypeAdjustment("");
            }
            while(_myTicket.getSubtotalList().size() >= (_myTicket.getDiscountAmount().size() + 1)){
                  _myTicket.addDiscountAmount("");
            }   
            while(_myTicket.getSubtotalList().size() >= (_myTicket.getRechargeAmount().size() + 1)){
                  _myTicket.addRechargeAmount("");
            }
            if(nameAdjustment != ""){
            _myLine.setAdjustment(true);
            _myLine.setProductAttSetInstId(line.getProductAttSetInstId());
            _myLine.setProductAttSetInstDesc(nameAdjustment);
            amountAdjusted = amountAdjusted*-1;
            if(line.getDiscountAmount() != 0.0){
                    _myLine.setDiscountAmount(amountAdjusted);
                }
            else if(line.getRechargeAmount() != 0.0){
                _myLine.setRechargeAmount(amountAdjusted);
                }
            }          
            _myLine.setBaseAmount(subtotalBaseAmount + amountAdjusted);            
            //productLinePrinted = 1;
             
            return RESPONSE_OK;
            }catch(Exception ex)
            {
    //            System.out.println("Error");
                return RESPONSE_ERROR;
            } 
                }
            else{
                Result =RESPONSE_ERROR;}
            }
            if(returna == RESPONSE_ERROR){
                return RESPONSE_ERROR;
            }
            else{
                return RESPONSE_OK;
            }
        }
        catch (Exception e){
            return RESPONSE_ERROR;
        }
//        
//        tax = 0;
//        n = 0;
//        DivisorPrice = 100;
//        DivisorQuantity = 1000; 
//        
//        try
//        {
//            if(pTrama.substring(0,1).equals("d"))
//            {       n = 1;  
//                switch (pTrama.substring(1,2)) {
//                    case "1":
//                        tax = 1;
//                        break;
//                    case "2":
//                        tax = 2;
//                        break;
//                    case "3":
//                        tax = 3;
//                        break;
//                }
//            }
//            if(pTrama.substring(0,1).equals("`"))
//            {       n = 1;  
//                switch (pTrama.substring(1,2)) {
//                    case "1":
//                        tax = 1;
//                        break;
//                    case "2":
//                        tax = 2;
//                        break;
//                    case "3":
//                        tax = 3;
//                        break;
//                }
//            }
//            else
//            {
//                switch (pTrama.substring(0,1)) {
//                    case " ":
////                        taxs = " ";
//                        tax = 0;
//                        break;
//                    case "¡":
//                        tax = 1;
////                        taxs = "!";
//                        break;
//                    case "¢":
//                        tax = 2;
////                        taxs = "\"";
//                        break;
//                    case "£":
//                        tax = 3;
////                        taxs = "#";
//                        break;
//                }
//            }
//            
//        _myLine = new TicketLineInfo();
//        _myLine.setAnulado(true);
//        _myTicket.sumProductID();
//        _myLine.setProductId(_myTicket.getProductID());
//        Taxes taxInfoItem = new Taxes();
//        taxInfoItem = taxInfoItem.getTaxeById(String.valueOf(tax),_myTicket.getEquipmet().getId());
//        _myLine.setTaxInfo(new TaxInfo(taxInfoItem.getId(),taxInfoItem.getNombre(), taxInfoItem.getRate(), taxInfoItem.getRateCascade()));
//       
//        sum = 0;
//        if(DivisorPrice == 1000)
//            sum = 1;
//        
//        quantity = Double.valueOf(pTrama.substring(11 + n + sum,16 + n + sum));   
//        quantityDec = Double.valueOf(pTrama.substring(16 + n + sum,19 + n + sum))/DivisorQuantity;
//        quantity = quantity + quantityDec;       
//          
//        priceDec =  (Double.valueOf(pTrama.substring(9 + n,11 + n + sum))/DivisorPrice);
//        price =  (Double.valueOf(pTrama.substring(1 + n,9 + n)));
//        price = price + priceDec;
//        
//        
//        if(pTrama.charAt(19 + n + sum) == '|'){
//            productCode = pTrama.substring(19 + n + sum + 1, pTrama.lastIndexOf('|'));
//            productName = pTrama.substring(pTrama.lastIndexOf('|') + 1);
//                    _myLine.setProductCode(productCode);
//        }
//        else {
//            productName = pTrama.substring(19 + n + sum);
//        }
//        
//        if (productName.length() > 20){
//            if(productName.length() > 42){
//            _myLine.setProductNameHigher(productName.substring(0, 42));
//            productName = productName.substring(42,productName.length());
//            }
//            else if(productName.length() < 42){
//            _myLine.setProductNameHigher(productName.substring(0, 42));
//            productName = productName.substring(productName.length() - 1,productName.length());
//            }
//        }                      
//        
//        if(!commentsList.isEmpty()){
//            ArrayList<String> commentsListOfProduct = new ArrayList<String>();
//            for ( int i = 0; i < commentsList.size(); ++i ) { 
//                commentsListOfProduct.add("");
//                commentsListOfProduct.set(i,commentsList.get(i));
//                }
//            _myLine.setCommentsLines(commentsListOfProduct); 
//            commentsList.clear();
//        }   
//        
//        //ESTO ES DE PRUEBA:!!!        
//         if(SubtotalCalled == false){
//             _myTicket.addSubtotalLine("");
//         }        
//        //HASTA AQUI
//         
//        _myLine.setPrice(price);
//        _myLine.setMultiply(quantity);
//        
//        switch(tax){
//        case 0: productName += " (E)";
//            break;
//        case 1: productName += " (G)";
//            break;
//        case 2: productName += " (R)";
//            break;
//        case 3: productName += " (A)";
//            break;
//        }
//        
//        _myLine.setProductID(productName);
//        
//        this._myTicket.addLine(_myLine);
//        TicketTaxInfo tickTaxInf = new TicketTaxInfo(_myLine.getTaxInfo());
//        tickTaxInf.setSubTotal(_myLine.getSubValue());
//        tickTaxInf.setTax(_myLine.getTax());
//        this._myTicket.addTaxeInfo(tickTaxInf);
//        
//        double subtotalBaseAmount = _myLine.getSubValue();
//        _myLine.setBaseAmount(subtotalBaseAmount);
//        
//        this.setTypeDisplay(NOT_PRINTABLE);
//        while(_myTicket.getSubtotalList().size() >= (_myTicket.getTypeAdjustment().size() + 1)){
//              _myTicket.addTypeAdjustment("");
//        }
//        while(_myTicket.getSubtotalList().size() >= (_myTicket.getDiscountAmount().size() + 1)){
//              _myTicket.addDiscountAmount("");
//        }   
//        while(_myTicket.getSubtotalList().size() >= (_myTicket.getRechargeAmount().size() + 1)){
//              _myTicket.addRechargeAmount("");
//        }
//        //productLinePrinted = 1;
//         return RESPONSE_OK;
//         
//        }catch(Exception ex)
//        {
////            System.out.println("Error");
//            return RESPONSE_ERROR;
//        } 
        

    }
    
    public int showSubtotalVisor(){
//        
//        try{
//            //"SUBTTL" +" Bs " + formatter.format(_myTicket.getPresentSubTotal(_myTicket.getProductID()))
//        _myDeviceTick.getDeviceDisplay().writeVisor(,"HOLA", "HOLA");
////        _myTicket.setPresentSubtotalVisor(_myTicket.getPresentSubTotal(_myTicket.getProductID()));        
////        _myTicket.setSubtotalVisor(true);
//        
//        }
//        catch (Exception e){
////            return RESPONSE_ERROR;
//        }
        return RESPONSE_OK;
    }
    
   public int PrintBarcodeFoot(String pTrama){
       String position = "";
       Flags fl = new Flags();
       Flags flag43 = fl.getFlags(43,this._myTicket.getEquipmet().getId());
       Flags flag30 = fl.getFlags(30,this._myTicket.getEquipmet().getId());
       if(flag30.getValue() == 0){
           position = "none";
       }
       else{
           position = "bottom";
       } 
       System.err.println(position);
       if((flag43.getValue() == 0) && (pTrama.length() == 12)){//type = EAN13         
           _myTicket.setFootBarcode(pTrama,"EAN13", position);
           return RESPONSE_OK;
       }
       else if(((flag43.getValue() == 1) || (fl.getValue() == 2)) && (pTrama.length() > 0)&& (pTrama.length() < 22)){//Check Code 128 or ITF sets it to Code 128 type
           _myTicket.setFootBarcode(pTrama,"CODE128",position);
           return RESPONSE_OK;
       }
       else{
           return RESPONSE_ERROR;
       }
       
   }
      public int PrintBarcodePlu(String pTrama){
       String position = "";
       Flags fl = new Flags();
       Flags flag43 = fl.getFlags(43,this._myTicket.getEquipmet().getId());
       Flags flag30 = fl.getFlags(30,this._myTicket.getEquipmet().getId());
       if(flag30.getValue() == 0){
           position = "none";
       }
       else{
           position = "bottom";
       } 
       System.err.println(position);
       if((flag43.getValue() == 0) && (pTrama.length() == 12)){//type = EAN13         
           _myLine.setFootBarcode(pTrama,"EAN13", position);
           return RESPONSE_OK;
       }
       else if(((flag43.getValue() == 1) || (fl.getValue() == 2)) && (pTrama.length() > 0)&& (pTrama.length() < 22)){//Check Code 128 or ITF sets it to Code 128 type
           _myLine.setFootBarcode(pTrama,"CODE128",position);
           return RESPONSE_OK;
       }
       else{
           return RESPONSE_ERROR;
   }
      }
}
