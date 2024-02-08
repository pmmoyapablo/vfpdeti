/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author pmoya
 */
public class CodeOperation {
    
     private long lAux;
     private char[] letras = {'J','A','B','C','D','E','F','G','H','I'}; 
     public String codeValidate = null;
     
    public CodeOperation()
    {
       codeValidate = "";
       lAux = 0;
    }
     
    private long calc_hash(String cBuf)
    {
      int max, i;
      this.lAux = 0;
      long lAux2, lAux3;
      max = cBuf.length();

      for (i = 0; i < max; i++)
      {
        lAux2 = cBuf.toCharArray()[i];
        lAux3 = cBuf.toCharArray()[max - i - 1];
        this.lAux = (lAux << 3) ^ lAux2;
        this.lAux = (lAux << 4) ^ lAux3;
        lAux = (lAux % 999999);
      }

      return this.lAux;
    }

    public String getCodeFinal(String typeOperation, String preCodigo, String Serial)
    {
      this.lAux = calc_hash(preCodigo);
      this.lAux = (this.lAux << 3) ^ calc_hash(Serial);
      this.lAux = (this.lAux << 7) ^ calc_hash(typeOperation);
      this.lAux = (this.lAux % 999999999);

      String code = String.valueOf(this.lAux);
      
          while(code.length() < 9)
          {
             code = "0" + code;
          }

      return code;
        
    }
    
    public String getPreCode(long num)
    {
        String code = "", data = null, indat = "";
        
        data = String.valueOf(num);
        char[] chrs = data.toCharArray();
        int y = 0;
        while(indat.length() < 9)
        {
           indat = indat + chrs[y];
           ++y;
        }
        
        chrs = indat.toCharArray();
        
        int i = 0;
        while(i < chrs.length)
        {
            char ch = chrs[i];
           int ind = Integer.valueOf(ch+ "");
            code = code + letras[ind];
        ++i;
        }
        
        return code;
    
    }
}
