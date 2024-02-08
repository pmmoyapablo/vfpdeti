/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;
import contable.*;
import java.util.List;

/**
 *
 * @author pmoya
 */
public interface IFiscalAccionable {
    public static final int STATE_WAIT = 0;
    public static final int STATE_TRANSACCTION = 1;
    public static final int STATE_UPDATA = 2;
    public static final int STATE_ENDUPDATA = 4;
    public static final int STATE_UNLOCK = 3;
    public static final int STATE_LOCK = -1;
    public static final int RESPONSE_OK = 6;
    public static final int RESPONSE_ERROR = 15;
    public static final int NOT_RESPONSE = -1;
    public static final int PRINTABLE = 100;
    public static final int NOT_PRINTABLE = 200;
    
    public void setCommad(Command pCmd) throws DeviceException;
    public int BeginFiscalDocument(String pType);
    public int EndFiscalDocument();
    public int PrintRecItem(String pTrama);
    public int PrintRecItemVoid(String pTrama);
    public int PrintRecTotal(int pIdMean);
    public int PrintRecTotal(PaymentInfo pPayment);
    public int PrintXReport();
    public int PrintZReport();
    public void setData(String pNameCmd) throws DeviceException;
    public byte[] getDataBufferRead();
    public void setDataBufferRead(byte[] pBytes);
    public int getTypeDisplay();
    public int getState();
    public int getResult();
    public void setState(int pState);
    public TicketInfo getTicket();
    public IPrinterData getPrinterData();
    public List<IPrinterData> getListPrinterData();
    public void setTicket(TicketInfo pTicket);
    public void setDataLogicSales(DataLogicSales pDlSales);
    public void setPaymentsModel(PaymentsModel pPaymentsModel);
    public String getNameResourceXML();
    public void ResetFiscalDocument();
    public int ProgramTaxe(String pTrama);
    public int ProgramCashier(String pTrama);
    public int ProgramMeansPayment(String pTrama);
    public int ProgramHeaderFooter(String pTrama);
    public int ProgramFlags(String pTrama);
    public int VoidFiscalDocument();
    public int OpenDrawer();
    public int PrintSubTotal();
    public int Fiscalizar(String pTrama);
    public int UnLock(String pCode);
    public int Lock(String pSerial);
    public int BeginNotFiscalDocument(String pTitle);
    public int EndNotFiscalDocument();
    public int PrintRecItemNotFiscal(String pTrama);
    public int PrintProgramation();
    public int PrintComent(String pMessanger);
    public int CorrecLineTransaction();
    public int PrintRecItemAdjustement(String pType, double pAmount);
    public int PrintDotation();
    public int PrintComition(double pAmount);
    public int PrintPropine(double pAmount);
    public int StartCahier(String pTrama);
    public int EndCahier();
    public int PrintMemotyReportFiscal(String pTrama);
}
