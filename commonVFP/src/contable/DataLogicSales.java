//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package contable;

import contable.*;
import data.Formats;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import common.DeviceException;
import elements.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import util.Utilities;

/**
 *
 * @author adrianromero
 */
public class DataLogicSales{
    
    private int m_iSeq;
    private Date m_dDateStart;
    private Date m_dDateEnd;         
    private Integer m_iPayments;
    private Double m_dPaymentsTotal;
    private java.util.List<PaymentsLine> m_lpayments;
    private Integer m_iSales;
    private Double m_dSalesBase;
    private Double m_dSalesTaxes;
    private java.util.List<SalesLine> m_lsales;
    private int m_idEquipmet = 0;


    /** Creates a new instance of SentenceContainerGeneric */
    public DataLogicSales(int pIdEquipmet) {
       this.m_idEquipmet = pIdEquipmet;
    }
    
    //Tickets and Receipt list
    public List<TicketInfo> getTicketsList() {
        
        return null;
		// return new StaticSentence(s
		// , new QBFBuilder(
		// "SELECT T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME, SUM(PM.TOTAL) "+
		// "FROM RECEIPTS R JOIN TICKETS T ON R.ID = T.ID LEFT OUTER JOIN PAYMENTS PM ON R.ID = PM.RECEIPT LEFT OUTER JOIN CUSTOMERS C ON C.ID = T.CUSTOMER LEFT OUTER JOIN PEOPLE P ON T.PERSON = P.ID "
		// +
		// "WHERE ?(QBF_FILTER) GROUP BY T.ID, T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME ORDER BY R.DATENEW DESC, T.TICKETID",
		// new String[] {"T.TICKETID", "T.TICKETTYPE", "PM.TOTAL", "R.DATENEW",
		// "R.DATENEW", "P.NAME", "C.NAME"})
		// , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.INT,
		// Datas.OBJECT, Datas.INT, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT,
		// Datas.TIMESTAMP, Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT,
		// Datas.STRING, Datas.OBJECT, Datas.STRING})
		// , new SerializerReadClass(FindTicketsInfo.class));
    }
    
   
    // Listados para combo
    public List<TaxInfo> getTaxList() {
        return null;
//        return new StaticSentence(s
//            , "SELECT ID, NAME, CATEGORY, CUSTCATEGORY, PARENTID, RATE, RATECASCADE, RATEORDER FROM TAXES ORDER BY NAME"
//            , null
//            , new SerializerRead() { public Object readValues(DataRead dr) throws DeviceException {
//                return new TaxInfo(
//                        dr.getString(1), 
//                        dr.getString(2),
//                        dr.getString(3),
//                        dr.getString(4),
//                        dr.getString(5),
//                        dr.getDouble(6).doubleValue(),
//                        dr.getBoolean(7).booleanValue(),
//                        dr.getInt(8));
//            }});
    }
  
  
    public final boolean isCashActive(String id) throws DeviceException {
             return false;
//        return new PreparedSentence(s,
//                "SELECT MONEY FROM CLOSEDCASH WHERE DATEEND IS NULL AND MONEY = ?",
//                SerializerWriteString.INSTANCE,
//                SerializerReadString.INSTANCE).find(id) != null;
    }

    public final TicketInfo loadTicket(final int tickettype, final int ticketid) throws DeviceException {
                    return null;
        //        TicketInfo ticket = (TicketInfo) new PreparedSentence(s
//                , "SELECT T.ID, T.TICKETTYPE, T.TICKETID, R.DATENEW, R.MONEY, R.ATTRIBUTES FROM RECEIPTS R JOIN TICKETS T ON R.ID = T.ID  WHERE T.TICKETTYPE = ? AND T.TICKETID = ?"
//                , SerializerWriteParams.INSTANCE
//                , new SerializerReadClass(TicketInfo.class))
//                .find(new DataParams() { public void writeValues() throws DeviceException {
//                    setInt(1, tickettype);
//                    setInt(2, ticketid);
//                }});
//        if (ticket != null) {
//      
//
//            ticket.setLines(new PreparedSentence(s
//                , "SELECT L.TICKET, L.LINE, L.PRODUCT, L.ATTRIBUTESETINSTANCE_ID, L.UNITS, L.PRICE, T.ID, T.NAME, T.CATEGORY, T.CUSTCATEGORY, T.PARENTID, T.RATE, T.RATECASCADE, T.RATEORDER, L.ATTRIBUTES " +
//                  "FROM TICKETLINES L, TAXES T WHERE L.TAXID = T.ID AND L.TICKET = ? ORDER BY L.LINE"
//                , SerializerWriteString.INSTANCE
//                , new SerializerReadClass(TicketLineInfo.class)).list(ticket.getId()));
//            ticket.setPayments(new PreparedSentence(s
//                , "SELECT PAYMENT, TOTAL, TRANSID FROM PAYMENTS WHERE RECEIPT = ?"
//                , SerializerWriteString.INSTANCE
//                , new SerializerReadClass(PaymentInfo.class)).list(ticket.getId()));
//        }
    //    return ticket;
    }
    
    public void cerrarCajaData(String pActiveCashIndex, String pHost)
    {      
        ClosedCash c = new ClosedCash();
        //Cierro la Caja Actual
        m_dDateEnd = new Date();
        int idZ = getSequence() + 1;
        c.UpdateClosedCash(m_dDateEnd, pActiveCashIndex, pHost);

        //Creo una Caja Nueva
       c.InsertClosedCash(Utilities.StringFormatXero(String.valueOf(idZ) , 4) + "-" + String.valueOf(this.m_idEquipmet), Utilities.getLocalHostName(), idZ, new Date(), null, this.m_idEquipmet);
    }
    
    public void loadCajaData(String ActiveCashIndex) 
    {
//        try {
//            // Pagos
//        Object[] valtickets = (Object []) new StaticSentence(app.getSession()
//            , "SELECT COUNT(*), SUM(PAYMENTS.TOTAL) " +
//              "FROM PAYMENTS, RECEIPTS " +
//              "WHERE PAYMENTS.RECEIPT = RECEIPTS.ID AND RECEIPTS.MONEY = ?"
//            , SerializerWriteString.INSTANCE
//            , new SerializerReadBasic(new Datas[] {Datas.INT, Datas.DOUBLE}))
//            .find(app.getActiveCashIndex());
//        
//         if (valtickets == null) {
//            m_iPayments = new Integer(0);
//            m_dPaymentsTotal = new Double(0.0);
//        } else {
//            m_iPayments = (Integer) valtickets[0];
//            m_dPaymentsTotal = (Double) valtickets[1];
//        }
//         
//           List l = new StaticSentence(app.getSession()            
//            , "SELECT PAYMENTS.PAYMENT, SUM(PAYMENTS.TOTAL) " +
//              "FROM PAYMENTS, RECEIPTS " +
//              "WHERE PAYMENTS.RECEIPT = RECEIPTS.ID AND RECEIPTS.MONEY = ? " +
//              "GROUP BY PAYMENTS.PAYMENT"
//            , SerializerWriteString.INSTANCE
//            , new SerializerReadClass(PaymentsLine.class)) //new SerializerReadBasic(new Datas[] {Datas.STRING, Datas.DOUBLE}))
//            .list(app.getActiveCashIndex()); 
//        
//        if (l == null) {
//            m_lpayments = new ArrayList();
//        } else {
//            m_lpayments = l;
//        }  
//        
//         //Tax Mode  
//     
//          boolean isIncluido = false; //getTaxDispositivo():isCascade()
//          Object[] recsales = null;
//          
//           // Sales
//          if(isIncluido)
//          {
//            recsales = (Object []) new StaticSentence(app.getSession(),
//            "SELECT COUNT(DISTINCT RECEIPTS.ID), SUM(TICKETLINES.UNITS * (TICKETLINES.PRICE/(1.0+TAXES.RATE))) " +
//            "FROM RECEIPTS, TICKETLINES, TAXES WHERE RECEIPTS.ID = TICKETLINES.TICKET AND TAXES.ID = TICKETLINES.TAXID AND RECEIPTS.MONEY = ?",
//            SerializerWriteString.INSTANCE,
//            new SerializerReadBasic(new Datas[] {Datas.INT, Datas.DOUBLE}))
//            .find(app.getActiveCashIndex());
//          }else
//          {
//              recsales = (Object []) new StaticSentence(app.getSession(),
//            "SELECT COUNT(DISTINCT RECEIPTS.ID), SUM(TICKETLINES.UNITS * TICKETLINES.PRICE) " +
//            "FROM RECEIPTS, TICKETLINES WHERE RECEIPTS.ID = TICKETLINES.TICKET AND RECEIPTS.MONEY = ?",
//            SerializerWriteString.INSTANCE,
//            new SerializerReadBasic(new Datas[] {Datas.INT, Datas.DOUBLE}))
//            .find(app.getActiveCashIndex());
//          }
//          
//        Object []  recsalesAjuste = (Object []) new StaticSentence(app.getSession(),
//            "SELECT  SUM(MONTO) FROM AJUSTE WHERE  MONEY = ?",
//            SerializerWriteString.INSTANCE,
//            new SerializerReadBasic(new Datas[] { Datas.DOUBLE}))
//            .find(app.getActiveCashIndex());
//        
//       
//        if (recsales == null) {
//            m_iSales = null;
//            m_dSalesBase = null;
//        } else {
//             m_iSales = (Integer) recsales[0];
//            if(recsalesAjuste[0] != null)
//            {      Double aj =       (Double) recsalesAjuste[0];
//                   aj = 0.0;
//                   Double bas = (Double) recsales[1];
//                   bas = 0.0;
//            m_dSalesBase = (Double) recsales[1] + (Double) recsalesAjuste[0];
//            }
//            else
//            {
//            m_dSalesBase = (Double) recsales[1];
//            }
//        }             
//        
//        // Taxes
//        Object[] rectaxes = (Object []) new StaticSentence(app.getSession(),
//            "SELECT SUM(TAXLINES.AMOUNT) " +
//            "FROM RECEIPTS, TAXLINES WHERE RECEIPTS.ID = TAXLINES.RECEIPT AND RECEIPTS.MONEY = ?"
//            , SerializerWriteString.INSTANCE
//            , new SerializerReadBasic(new Datas[] {Datas.DOUBLE}))
//            .find(app.getActiveCashIndex());            
//        if (rectaxes == null) {
//            m_dSalesTaxes = null;
//        } else {
//            m_dSalesTaxes = (Double) rectaxes[0];
//        } 
//                
//        List<SalesLine> asales = new StaticSentence(app.getSession(),
//                "SELECT TAXES.NAME, SUM(TAXLINES.AMOUNT) " +
//                "FROM RECEIPTS, TAXLINES, TAXES WHERE RECEIPTS.ID = TAXLINES.RECEIPT AND TAXLINES.TAXID = TAXES.ID " +
//                "AND RECEIPTS.MONEY = ?" +
//                "GROUP BY TAXES.NAME"
//                , SerializerWriteString.INSTANCE
//                , new SerializerReadClass(SalesLine.class))
//                .list(app.getActiveCashIndex());
//        if (asales == null) {
//            m_lsales = new ArrayList<SalesLine>();
//        } else {
//            m_lsales = asales;
//        }
//         
//         
//        } catch (DeviceException ex) {
//            Logger.getLogger(DataLogicSales.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public int getNewTicket_Id(int tipoTicket)
    {
        elements.Tickets tk = new elements.Tickets();
        elements.Tickets[] list =  tk.ListTickets(tipoTicket);
        
        if(list != null)
            return list.length + 1;
        else
            return 1;

    }

    public void saveTicket(TicketInfo ticket, final String location) throws DeviceException {
              Receipts re = new Receipts();       
              re.AddReceipts(ticket.getId(), ticket.getActiveCash(), ticket.getDate(), null);              
              
             //Salvo el Ticket
              Tickets tk = new Tickets();  
              int idNew = this.getNewTicket_Id(ticket.getTicketType());
              ticket.setTicketId(idNew);
              String user = "";
              if(ticket.getUser() != null)
                  user = ticket.getUser().getName();
              
              tk.InsertTickets(ticket.getId(), ticket.getTicketType(), ticket.getTicketId(), user, null, ticket.getStatus());
              
              List<TicketLineInfo> lines = ticket.getLines();
              
              for(TicketLineInfo l : lines)
              { //Guardo los items si los hay
                TicketLines linDat = new TicketLines();
                linDat.InsertTicketLines(UUID.randomUUID().toString(),ticket.getId(), l.getTicketLine(),l.getProductName(), l.getProductAttSetId(), l.getMultiply(), l.getPrice(), l.getTaxInfo().getId(), l.getProductAttSetInstId());
              }
              //Guardo los Pagos
              Payments pay  = new Payments();
              List<PaymentInfo> pyms = ticket.getPayments();
              
              for(PaymentInfo p : pyms)
              { 
                  pay.AddPayment(UUID.randomUUID().toString(), ticket.getId() , p.getPaid_Id(), p.getTotal(), p.getName(),null);
              }
              
              //Guardo las informaciones de las tasas
              for(TicketTaxInfo tickettax: ticket.getTaxLines())
              {
                Taxlines tl = new Taxlines();
                tl.InsertTickets(UUID.randomUUID().toString(), ticket.getId(), tickettax.getTaxInfo().getId(), tickettax.getSubTotal(), tickettax.getTax());
              }
//		                      
//		if(ticket.getProperty("A1")!= null)
//                {
//                 Double  stotl = ticket.getSubTotal(); 
//                 Double ajuste =  Double.valueOf(ticket.getProperty("A1").substring(5,10));
//                 Double SubtotalOriginal = stotl/(1.0 - (ajuste/100));
//                 final  Double  monto = SubtotalOriginal*ajuste/100;
//               new PreparedSentence(s
//                    , "INSERT INTO AJUSTE (ID, DENOMINACION, MONTO, MONEY) VALUES (?, ?, ?, ?)"
//                    , SerializerWriteParams.INSTANCE
//                    ).exec(new DataParams() { public void writeValues() throws DeviceException {
//                        setString(1, ticket.getId());
//                        setString(2, ticket.getProperty("A1"));
//                        setDouble(3, -monto);
//                        setString(4, ticket.getActiveCash());                
//                    }});
//                }else if(ticket.getProperty("B1")!= null)
//                {
//                  Double  stotl = ticket.getSubTotal(); 
//                 Double ajuste =  Double.valueOf(ticket.getProperty("B1").substring(5,10));
//                 Double SubtotalOriginal = stotl/(1.0 + (ajuste/100));
//                 final  Double  monto = SubtotalOriginal*ajuste/100;
//                   new PreparedSentence(s
//                    , "INSERT INTO AJUSTE (ID, DENOMINACION, MONTO, MONEY) VALUES (?, ?, ?, ?)"
//                    , SerializerWriteParams.INSTANCE
//                    ).exec(new DataParams() { public void writeValues() throws DeviceException {
//                        setString(1, ticket.getId());
//                        setString(2, ticket.getProperty("B1"));
//                        setDouble(3, monto);
//                        setString(4, ticket.getActiveCash());                       
//                    }});
//                }

            
    }

    public final void deleteTicket(final TicketInfo ticket, final String location) throws DeviceException {

//        Transaction t = new Transaction(s) {
//            @Override
//            public Object transact() throws DeviceException {
//                       
//                // and delete the receipt
//                new StaticSentence(s
//                    , "DELETE FROM TAXLINES WHERE RECEIPT = ?"
//                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
//                new StaticSentence(s
//                    , "DELETE FROM PAYMENTS WHERE RECEIPT = ?"
//                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
//                new StaticSentence(s
//                    , "DELETE FROM TICKETLINES WHERE TICKET = ?"
//                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
//                new StaticSentence(s
//                    , "DELETE FROM TICKETS WHERE ID = ?"
//                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
//                new StaticSentence(s
//                    , "DELETE FROM RECEIPTS WHERE ID = ?"
//                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
//                return null;
//            }
//        };
//        t.execute();
    }
    
     public int getPayments() {
        return m_iPayments.intValue();
    }
    public double getTotal() {
        return m_dPaymentsTotal.doubleValue();
    }
    
     public double getSalesTotal() {            
        return (m_dSalesBase == null || m_dSalesTaxes == null)
                ? 0.0
                : m_dSalesBase + m_dSalesTaxes;
    }   

    public int getSequence() {
        
        ClosedCash c = new ClosedCash();
        m_iSeq =   c.getHostSequenceActual(m_idEquipmet);
        
        return m_iSeq;
    }
    public Date getDateStart() {
        return m_dDateStart;
    }
    public void setDateEnd(Date dValue) {
        m_dDateEnd = dValue;
    }
    public Date getDateEnd() {
        return m_dDateEnd;
    }
    
    public String printSequence() {
        return Formats.INT.formatValue(m_iSeq);
    }
    public String printDateStart() {
        return Formats.TIMESTAMP.formatValue(m_dDateStart);
    }
    public String printDateEnd() {
        return Formats.TIMESTAMP.formatValue(m_dDateEnd);
    }  
    
    public String printPayments() {
        return Formats.INT.formatValue(m_iPayments);
    }

    public String printPaymentsTotal() {
        return Formats.CURRENCY.formatValue(m_dPaymentsTotal);
    }     
    
    public List<PaymentsLine> getPaymentLines() {
        return m_lpayments;
    }
    
    public int getSales() {
        return m_iSales == null ? 0 : m_iSales.intValue();
    }    
    public String printSales() {
        return Formats.INT.formatValue(m_iSales);
    }
    public String printSalesBase() {
        return Formats.CURRENCY.formatValue(m_dSalesBase);
    }     
    public String printSalesTaxes() {
        return Formats.CURRENCY.formatValue(m_dSalesTaxes);
    }     
    public String printSalesTotal() {            
        return Formats.CURRENCY.formatValue((m_dSalesBase == null || m_dSalesTaxes == null)
                ? null
                : m_dSalesBase + m_dSalesTaxes);
    }     
    public List<SalesLine> getSaleLines() {
        return m_lsales;
    }

    public final Integer getNextTicketIndex() throws DeviceException {
        elements.Tickets tk = new elements.Tickets();
        tk =  tk.LastTicket();
        
        if(tk != null)
            return tk.getTicketId();
        else
            return 1;
    }

    public final Integer getNextTicketRefundIndex() throws DeviceException {
        return 0;
       // return (Integer) s.DB.getSequenceSentence(s, "TICKETSNUM_REFUND").find();
    }

    public final Integer getNextTicketPaymentIndex() throws DeviceException {
        return 0;
        //return (Integer) s.DB.getSequenceSentence(s, "TICKETSNUM_PAYMENT").find();
    }

 
    public final void doPaymentMovementInsert() {
//        return new SentenceExecTransaction(s) {
//            public int execInTransaction(Object params) throws DeviceException {
//                new PreparedSentence(s
//                    , "INSERT INTO RECEIPTS (ID, MONEY, DATENEW) VALUES (?, ?, ?)"
//                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {0, 1, 2})).exec(params);
//                return new PreparedSentence(s
//                    , "INSERT INTO PAYMENTS (ID, RECEIPT, PAYMENT, TOTAL) VALUES (?, ?, ?, ?)"
//                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {3, 0, 4, 5})).exec(params);
//            }
//        };
    }

    public final void doPaymentMovementDelete() {
//        return new SentenceExecTransaction(s) {
//            public int execInTransaction(Object params) throws DeviceException {
//                new PreparedSentence(s
//                    , "DELETE FROM PAYMENTS WHERE ID = ?"
//                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {3})).exec(params);
//                return new PreparedSentence(s
//                    , "DELETE FROM RECEIPTS WHERE ID = ?"
//                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {0})).exec(params);
//            }
//        };
    }

     public static class PaymentsLine  {
        
        private String m_PaymentType;
        private Double m_PaymentValue;
        
        public void readValues(String[] dr) throws DeviceException {
            m_PaymentType = dr[0];
            m_PaymentValue = Double.valueOf(dr[1]);
        }
        
        public String printType() {
           // return AppLocal.getIntString("transpayment." + m_PaymentType);
            return "";
        }
        public String getType() {
            return m_PaymentType;
        }
        public String printValue() {
            return Formats.CURRENCY.formatValue(m_PaymentValue);
        }
        public Double getValue() {
            return m_PaymentValue;
        }        
    }
  
     public static class SalesLine {
        
        private String m_SalesTaxName;
        private Double m_SalesTaxes;
        
        public void readValues(String[] dr) throws DeviceException {
            m_SalesTaxName = dr[0];
            m_SalesTaxes = Double.valueOf(dr[1]);
        }
        public String printTaxName() {
            return m_SalesTaxName;
        }      
        public String printTaxes() {
            return Formats.CURRENCY.formatValue(m_SalesTaxes);
        }
        public String getTaxName() {
            return m_SalesTaxName;
        }
        public Double getTaxes() {
            return m_SalesTaxes;
        }  
        
        
    }
}
