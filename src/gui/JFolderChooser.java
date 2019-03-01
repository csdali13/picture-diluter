package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

// Referenced classes of package gui:
//            Filefilter, WindowUtil

public class JFolderChooser extends JDialog
{

    static File rev;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel tm;
    private File froots[];
    private Filefilter ff;
    static int opr = 0;
    boolean win;
    private JButton cancel;
    private JTree fsview;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JButton ok;

    public JFolderChooser()
    {
        win = true;
        initComponents();
        ff = new Filefilter("%DIR%", "");
        init();
        tm = new DefaultTreeModel(root);
        fsview.setModel(tm);
        WindowUtil.centerWindow(this);
        setSize(280, 340);
        DefaultTreeCellRenderer tmp = new DefaultTreeCellRenderer();
        tmp.setLeafIcon(tmp.getDefaultClosedIcon());
        fsview.setCellRenderer(tmp);
    }

    private void init()
    {
        froots = File.listRoots();
        if(froots.length == 1)
        {
            win = false;
            root = new DefaultMutableTreeNode(froots[0].getName());
            froots = froots[0].listFiles(ff);
            for(int i = 0; i < froots.length; i++)
            {
                DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(froots[i].getPath());
                root.add(tmp);
                File f[] = froots[i].listFiles(ff);
                if(f != null && f.length != 0)
                {
                    if(f.length == 1 && f[0].isDirectory())
                    {
                        tmp.add(new DefaultMutableTreeNode(f[0].getPath()));
                    } else
                    {
                        tmp.add(new DefaultMutableTreeNode("%MORE%"));
                    }
                }
            }

        } else
        {
            win = true;
            root = new DefaultMutableTreeNode("My Computer");
            for(int i = 0; i < froots.length; i++)
            {
                DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(froots[i].getPath());
                root.add(tmp);
                File f[] = froots[i].listFiles(ff);
                if(f != null && f.length != 0)
                {
                    if(f.length == 1 && f[0].isDirectory())
                    {
                        tmp.add(new DefaultMutableTreeNode(f[0].getName()));
                    } else
                    {
                        tmp.add(new DefaultMutableTreeNode("%MORE%"));
                    }
                }
            }

        }
    }

    private void closefile(DefaultMutableTreeNode node, File cf)
    {
        File f[] = cf.listFiles(ff);
        node.removeAllChildren();
        if(f.length == 0)
        {
            return;
        }
        if(f.length == 1 && f[0].isDirectory())
        {
            node.add(new DefaultMutableTreeNode(f[0].getName()));
        } else
        {
            node.add(new DefaultMutableTreeNode("%MORE%"));
        }
    }

    private void openfile(DefaultMutableTreeNode node, File cf)
    {
        if(node.getChildAt(0).toString().equals("%MORE%"))
        {
            node.removeAllChildren();
            File f[] = cf.listFiles(ff);
            for(int i = 0; i < f.length; i++)
            {
                DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(f[i].getName());
                node.add(tmp);
                closefile(tmp, f[i]);
            }

        } else
        {
            File f[] = cf.listFiles(ff);
            if(f[0].isDirectory())
            {
                closefile((DefaultMutableTreeNode)node.getChildAt(0), f[0]);
            }
        }
    }

    private void initComponents()
    {
        jScrollPane1 = new JScrollPane();
        fsview = new JTree();
        jLabel1 = new JLabel();
        jPanel1 = new JPanel();
        ok = new JButton();
        cancel = new JButton();
        setDefaultCloseOperation(2);
        setModal(true);
        addWindowListener(new WindowAdapter() {

            public void windowClosed(WindowEvent evt)
            {
                formWindowClosed(evt);
            }
            });
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        fsview.addTreeWillExpandListener(new TreeWillExpandListener() {
        	
        	public void treeWillCollapse(TreeExpansionEvent evt)
                throws ExpandVetoException
            {
                fsviewTreeWillCollapse(evt);
            }

            public void treeWillExpand(TreeExpansionEvent evt)
                throws ExpandVetoException
            {
                fsviewTreeWillExpand(evt);
            }
        });
        fsview.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent evt)
            {
                fsviewValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(fsview);
        getContentPane().add(jScrollPane1, "Center");
        jLabel1.setText("Choose a folder");
        getContentPane().add(jLabel1, "North");
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setMinimumSize(new Dimension(181, 30));
        jPanel1.setPreferredSize(new Dimension(100, 40));
        ok.setText("OK");
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                okActionPerformed(evt);
            }
        });
        jPanel1.add(ok);
        cancel.setText("Cancel");
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                cancelActionPerformed(evt);
            }

        });
        jPanel1.add(cancel);
        getContentPane().add(jPanel1, "South");
        pack();
    }

    private void fsviewValueChanged(TreeSelectionEvent evt)
    {
        TreePath tp = evt.getPath();
        ok.setEnabled(!tp.getLastPathComponent().toString().equals("My Computer"));
    }

    private void cancelActionPerformed(ActionEvent evt)
    {
        opr = 2;
        dispose();
    }

    private void okActionPerformed(ActionEvent evt)
    {
        opr = 0;
        TreePath tp = fsview.getSelectionPath();
        StringBuilder path = new StringBuilder();
        int i;
        if(win)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        for(; i < tp.getPathCount(); i++)
        {
            path.append(tp.getPathComponent(i)).append(File.separator);
        }

        rev = new File(path.toString());
        dispose();
    }

    private void fsviewTreeWillExpand(TreeExpansionEvent evt)
        throws ExpandVetoException
    {
        TreePath tp = evt.getPath();
        if(tp.getLastPathComponent().toString().equals("My Computer"))
        {
            return;
        }
        StringBuilder path = new StringBuilder();
        int i;
        if(win)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        for(; i < tp.getPathCount(); i++)
        {
            path.append(tp.getPathComponent(i)).append(File.separator);
        }

        openfile((DefaultMutableTreeNode)tp.getLastPathComponent(), new File(path.toString()));
    }

    private void fsviewTreeWillCollapse(TreeExpansionEvent evt)
        throws ExpandVetoException
    {
        TreePath tp = evt.getPath();
        if(tp.getLastPathComponent().toString().equals("My Computer"))
        {
            return;
        }
        StringBuilder path = new StringBuilder();
        int i;
        if(win)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        for(; i < tp.getPathCount(); i++)
        {
            path.append(tp.getPathComponent(i)).append(File.separator);
        }

        closefile((DefaultMutableTreeNode)tp.getLastPathComponent(), new File(path.toString()));
    }

    private void formWindowClosed(WindowEvent evt)
    {
        opr = -1;
    }

    public static int showFolderChooser()
    {
        (new JFolderChooser()).setVisible(true);
        return opr;
    }

    public static File getFile()
    {
        return rev;
    }







}
