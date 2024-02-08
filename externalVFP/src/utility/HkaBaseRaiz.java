/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;
/**
 *
 * @author pmoya
 */
import common.IFiscalAccionable;
import common.IPrinterData;
import common.CodeOperation;
import contable.*;
import elements.MeanPayment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 *
 * @author pmoya
 */
public abstract  class HkaBaseRaiz{
    
protected TicketInfo _myTicket;
protected TicketLineInfo _myLine;
protected int _typeDisplay;
protected int _state;
protected int Result;
protected CustomerInfo _client;
protected MeanPayment _meanPayment;
protected String _localeCurrencySimbol;
protected List<PaymentInfo> _paymentsList;
protected DataLogicSales _dataLogicSales;
protected PaymentsModel _paymentsModel;
protected byte[] _dataBufferRead;
protected IPrinterData _mPrinterdata;
protected List<IPrinterData> _mPrinterdataArray;
private CodeOperation _mCodeOper;
protected boolean IsVerifyFlags21 = false;
protected long DivisorPrice = 100;
protected long DivisorQuantity = 1000;

public HkaBaseRaiz()
{ 
   this._myLine = null;
   this._myTicket = null;
   this._client = null;
   this._dataLogicSales = null;
   this._paymentsModel = null;
   this._meanPayment = null;
   this._mCodeOper = new CodeOperation();
   this._state = IFiscalAccionable.STATE_WAIT;
   this.Result = IFiscalAccionable.RESPONSE_OK;
   this._mPrinterdataArray = new ArrayList<>();
}

protected void setTypeDisplay(int pType) {
       this._typeDisplay = pType;
    }

protected void setLocaleCurrencySimbol(String pSimbol)
{
     this._localeCurrencySimbol = pSimbol;
}

public void setDataBufferRead(byte[] pBytes)
{
  this._dataBufferRead = pBytes;
}

public byte[] getDataBufferRead()
{
  return this._dataBufferRead;
}

protected void resetTicket()
{
   this._myLine = null;
   this._myTicket = null;
   this._client = null;
}

public void setTicket(TicketInfo pTicket)
{
    this._myTicket = pTicket;
}

public TicketInfo getTicket()
{
    return this._myTicket;
}

public int getResult()
{
  return Result;
}

public int getState()
{
    return this._state;
}

public void setState(int pState)
{
    this._state = pState;
}

public void setDataLogicSales(DataLogicSales pDlSales)
{
    this._dataLogicSales = pDlSales;
}

public void setPaymentsModel(PaymentsModel pPaymentsModel)
{        
    this._paymentsModel = pPaymentsModel;
}

public IPrinterData getPrinterData()
{
   return this._mPrinterdata;
}

public List<IPrinterData> getListPrinterData()
{
   return this._mPrinterdataArray;
}

 protected  String getPreCodeOper(String pTypeOper,  String serial)
 {
     Calendar cal = Calendar.getInstance();

     String preCode = this._mCodeOper.getPreCode(cal.getTimeInMillis());
     this._mCodeOper.codeValidate = this._mCodeOper.getCodeFinal(pTypeOper, preCode, serial);

     return preCode;
 }
         
protected  String getIntoCodeValido()
{
   return this._mCodeOper.codeValidate;
}

}