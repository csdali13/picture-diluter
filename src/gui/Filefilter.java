package gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class Filefilter extends FileFilter
    implements java.io.FileFilter
{

    String ext;
    String desc;

    public Filefilter(String ex, String des)
    {
        ext = ex;
        desc = des;
    }

    public Filefilter()
    {
        ext = "*";
    }

    public boolean accept(File pathname)
    {
        if(ext.equals("*") || pathname.isDirectory())
        {
            return true;
        }
        return pathname.getName().endsWith(ext) && pathname.getName().lastIndexOf(".") == pathname.getName().length() - ext.length() - 1;
    }

    public String getDescription()
    {
        return ext.equals("*") ? "All files" : ext.equals("%DIR%") ? "Folders" : desc;
    }
}
