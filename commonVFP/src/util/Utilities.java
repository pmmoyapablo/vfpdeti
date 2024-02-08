/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author pmoya
 */
public class Utilities {
    
    public static String getLocalHostName() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException eUH) {
            return "localhost";
        }
    }
    
    public static String StringFormatXero(String pString, int pSize)
        {
          while (pString.length() < pSize) {
			pString = "0" + pString;
		}
           return pString;
        }
    
}
