/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author pmoya
 */
public abstract  class  PrinterData {
 
    protected  byte[] _myBytes;
    protected  char mode = 'Z';
    protected int numberZ = 0;
    
    protected PrinterData()
    { }
    
     public byte[] getDataLoader() throws DeviceException
     {
         if(this._myBytes != null)  
         { 
             return this._myBytes;
         }else
         {
           throw new DeviceException("Sin Repuesta");
         }
     }
     
    public void setMode(char pMode) {
        this.mode = pMode;
    }
    
    public void setNumberZ(int pNumberZ)
    {
       this.numberZ = pNumberZ;
    }
     
     protected String StringFormatXero(String pString, int pSize)
     {
        while (pString.length() < pSize) {
			pString = "0" + pString;
		}
       return pString;
     }
     
     protected String StringDoubleFormat(String pString)
     {
       	int x = 0;
    	String[] fracion = new String[2];
    	fracion[0] = "";
    	fracion[1] = "";
    	
    	char[] caracteres = null;
    	caracteres = pString.toCharArray();
    	while(x < pString.length())
    	{
    		if(caracteres[x] != '.')
    		{
    			fracion[0] += caracteres[x];
    			++x;
    		}else
    		{
    			fracion[1] = pString.substring(x+1);
    			x = pString.length();
    		}
    		
    	}
        
        if(fracion[1].length() < 2)
            fracion[1] = this.StringFormatXero(fracion[1], 2);
        else if(fracion[1].length() > 2)
            fracion[1] = fracion[1].substring(0,2);
        
    	
    	return fracion[0] + fracion[1];
     }
}
