/**
 * @author pmoya
 * @version 1.0
 * @created 14-ene-2015 02:13:12 p.m.
 */

package resources.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author adrianromero
 */
public class AppConfig{

    private static Logger logger = Logger.getLogger("vfphka.config.AppConfig");
     
    private Properties m_propsconfig;
    private File configfile;
      
    public AppConfig(String[] args) {
        if (args.length == 0) {
            init(getDefaultConfig());
        } else {
            init(new File(args[0]));
        }
        
    }
    
    public AppConfig(File configfile) {
        init(configfile);
    }
    
    private void init(File configfile) {
        this.configfile = configfile;
        m_propsconfig = new Properties();

        logger.info("Reading configuration file: " + configfile.getAbsolutePath());
    }
    
    private File getDefaultConfig() {
        return new File(new File(System.getProperty("user.home")), "vfphka" + ".properties");
    }
    
    public String getProperty(String sKey) {
        return m_propsconfig.getProperty(sKey);
    }
    
    public String getHost() {
        return getProperty("machine.hostname");
    } 
    
    public File getConfigFile() {
        return configfile;
    }
    
    public void setProperty(String sKey, String sValue) {
        if (sValue == null) {
            m_propsconfig.remove(sKey);
        } else {
            m_propsconfig.setProperty(sKey, sValue);
        }
    }
    
    private String getLocalHostName() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException eUH) {
            return "localhost";
        }
    }
   
    public boolean delete() {
        loadDefault();
        return configfile.delete();
    }
    
    public void load() {

        loadDefault();

        try {
            InputStream in = new FileInputStream(configfile);
            if (in != null) {
                m_propsconfig.load(in);
                in.close();
            }
        } catch (IOException e){
            loadDefault();
        }
    
    }
    
    public void save() throws IOException {
        
        OutputStream out = new FileOutputStream(configfile);
        if (out != null) {
            m_propsconfig.store(out, "vfphka" + ". Configuration file.");
            out.close();
        }
    }
    
    private void loadDefault() {
        
        m_propsconfig = new Properties();
        
        String dirname = System.getProperty("dirname.path");
        dirname = dirname == null ? "./" : dirname;
        
        m_propsconfig.setProperty("vfp.pathfile", new  File(dirname).getAbsolutePath());      
        m_propsconfig.setProperty("vfp.portnamewin", "COM98");
        m_propsconfig.setProperty("vfp.portnamelnx", "/dev/pts/6");
        m_propsconfig.setProperty("vfp.version", "3.0");

        m_propsconfig.setProperty("machine.hostname", getLocalHostName());

             
    }
}
