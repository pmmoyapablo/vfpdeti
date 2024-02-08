/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motorvfp;

import java.io.*;
import javax.swing.filechooser.FileFilter;

public class ImgFileFilter extends FileFilter{
public boolean accept(File f) {
if (f.getPath().endsWith(".xml") ||
f.isDirectory()) return true;
else return false;
}
public String getDescription() {
return "Firmware Files XML"; } }