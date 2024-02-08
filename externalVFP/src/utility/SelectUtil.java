/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;
import common.DeviceException;
import common.IFiscalAccionable;
import cw.HkaCurazao;
import pa.HkaPanama;
import ve.HkaVenezuela;
import rd.HkaRepDominicana;
/**
 *
 * @author pmoya
 */
public  class  SelectUtil {
   private static IFiscalAccionable _myHKA;
   
public static IFiscalAccionable getHKA(int pIndex) throws DeviceException
{
    if(pIndex == 1)
    {
        _myHKA = new HkaVenezuela();
    }
    else if(pIndex == 2)
    {
        _myHKA = new HkaPanama();
    }
    else if(pIndex == 3)
    {
        _myHKA = new HkaRepDominicana();
    }
     else if(pIndex == 4)
    {
        _myHKA = new HkaCurazao();
    }
    
    return _myHKA;
}
    
}
