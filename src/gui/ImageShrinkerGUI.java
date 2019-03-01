package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import javax.swing.*;
import shrinker.ImageShrinker;

// Referenced classes of package gui:
//            JFolderChooser

public class ImageShrinkerGUI extends JFrame
{
    class ButtonListener
        implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            if(ae.getSource() == sourceBrowse)
            {
                sourceChooser = new JFolderChooser();
                int returnVal = JFolderChooser.showFolderChooser();
                if(returnVal == 0)
                {
                    File sourceFile = JFolderChooser.getFile();
                    sourceFolder = sourceFile.getAbsolutePath();
                    System.out.println("inside source button click");
                    System.out.println((new StringBuilder("source:")).append(sourceFolder).toString());
                    sourceText.setText(sourceFolder);
                }
            }
            if(ae.getSource() == destBrowse)
            {
                destChooser = new JFolderChooser();
                int returnVal = JFolderChooser.showFolderChooser();
                if(returnVal == 0)
                {
                    File destFile = JFolderChooser.getFile();
                    destFolder = destFile.getAbsolutePath();
                    System.out.println("inside dest button click");
                    System.out.println((new StringBuilder("dest:")).append(destFolder).toString());
                    destText.setText(destFolder);
                }
            }
            if(ae.getSource() == sourcePicBrowse)
            {
                ImageShrinkerGUI.this.sourceFile = new JFileChooser();
                int returnVal = ImageShrinkerGUI.this.sourceFile.showOpenDialog(null);
                if(returnVal == 0)
                {
                    File source = ImageShrinkerGUI.this.sourceFile.getSelectedFile();
                    sourceFolder = source.getAbsolutePath();
                    sourcePicText.setText(sourceFolder);
                }
            }
            if(ae.getSource() == destPicBrowse)
            {
                ImageShrinkerGUI.this.destFile = new JFileChooser();
                int returnVal = ImageShrinkerGUI.this.destFile.showOpenDialog(null);
                if(returnVal == 0)
                {
                    File dest = ImageShrinkerGUI.this.destFile.getSelectedFile();
                    destFolder = dest.getAbsolutePath();
                    destPicText.setText(destFolder);
                }
            }
            if(ae.getSource() == start)
            {
                start.setEnabled(false);
                if(folder.isSelected())
                {
                    sourceFolder = sourceText.getText();
                    destFolder = destText.getText();
                    (new ImageShrinker()).shrinkImageFolder(sourceFolder, destFolder, selectedReduction);
                } else
                if(single.isSelected())
                {
                    sourceFolder = sourcePicText.getText();
                    destFolder = destPicText.getText();
                    (new ImageShrinker()).singleImage(sourceFolder, destFolder, selectedReduction);
                }
                start.setEnabled(true);
            }
        }

       
    }

    class ListListener
        implements ActionListener
    {
    	public void actionPerformed(ActionEvent ae)
        {
            if(ae.getSource() == reductionPercent)
            {
                JComboBox reduc = (JComboBox)ae.getSource();
                String percent = (String)reduc.getSelectedItem();
                if(percent.equals("80%"))
                {
                    selectedReduction = 0.6F;
                } else
                if(percent.equals("75%"))
                {
                    selectedReduction = 0.52F;
                } else
                if(percent.equals("50%"))
                {
                    selectedReduction = 0.445F;
                } else
                if(percent.equals("25%"))
                {
                    selectedReduction = 0.13F;
                }
            }
        }
    }

    class RadioListener
        implements ActionListener
    {
    	public void actionPerformed(ActionEvent ae)
        {
            String selection = ae.getActionCommand();
            if(selection.equals("single"))
            {
                sourceFolderLabel.setEnabled(false);
                sourceText.setEnabled(false);
                sourceBrowse.setEnabled(false);
                destFiolderLabel.setEnabled(false);
                destText.setEnabled(false);
                destBrowse.setEnabled(false);
                sourcePicLabel.setEnabled(true);
                sourcePicText.setEnabled(true);
                sourcePicBrowse.setEnabled(true);
                destPicLabel.setEnabled(true);
                destPicText.setEnabled(true);
                destPicBrowse.setEnabled(true);
            } else
            if(selection.equals("folder"))
            {
                sourcePicLabel.setEnabled(false);
                sourcePicText.setEnabled(false);
                sourcePicBrowse.setEnabled(false);
                destPicLabel.setEnabled(false);
                destPicText.setEnabled(false);
                destPicBrowse.setEnabled(false);
                sourceFolderLabel.setEnabled(true);
                sourceText.setEnabled(true);
                sourceBrowse.setEnabled(true);
                destFiolderLabel.setEnabled(true);
                destText.setEnabled(true);
                destBrowse.setEnabled(true);
            }
        }

    }


    final Toolkit tool = getToolkit();
    JRadioButton single;
    JRadioButton folder;
    ButtonGroup radio;
    JTextField sourceText;
    JTextField destText;
    JTextField sourcePicText;
    JTextField destPicText;
    JFolderChooser sourceChooser;
    JFolderChooser destChooser;
    JFileChooser sourceFile;
    JFileChooser destFile;
    JButton sourceBrowse;
    JButton destBrowse;
    JButton sourcePicBrowse;
    JButton destPicBrowse;
    JButton start;
    JLabel sourceFolderLabel;
    JLabel destFiolderLabel;
    JLabel sourcePicLabel;
    JLabel destPicLabel;
    JLabel reduction;
    Container contentPane;
    JComboBox reductionPercent;
    JProgressBar progressBar;
    String sourceFolder;
    String destFolder;
    float selectedReduction;

    public ImageShrinkerGUI()
    {
    	super("PictureDiluter");
        sourceFolder = null;
        destFolder = null;
        selectedReduction = 0.0F;
        setSize(420, 480);
        setVisible(true);
        setDefaultCloseOperation(3);
        Dimension d = tool.getScreenSize();
        setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);
        setLayout(null);
        contentPane = getContentPane();
        folder = new JRadioButton("Image Folder");
        folder.setBounds(10, 30, 150, 20);
        folder.setActionCommand("folder");
        folder.setSelected(true);
        folder.addActionListener(new RadioListener());
        single = new JRadioButton("Single Image");
        single.setBounds(10, 60, 150, 20);
        single.setActionCommand("single");
        single.setSelected(true);
        single.addActionListener(new RadioListener());
        radio = new ButtonGroup();
        radio.add(folder);
        radio.add(single);
        contentPane.add(folder);
        contentPane.add(single);
        sourceFolderLabel = new JLabel("Source Picture Folder");
        destFiolderLabel = new JLabel("Destination Picture Folder");
        reduction = new JLabel("Reduction Percentage");
        sourceFolderLabel.setBounds(10, 100, 150, 30);
        destFiolderLabel.setBounds(10, 150, 150, 30);
        reduction.setBounds(10, 300, 170, 30);
        contentPane.add(sourceFolderLabel);
        contentPane.add(destFiolderLabel);
        contentPane.add(reduction);
        sourceText = new JTextField();
        destText = new JTextField();
        sourceText.setBounds(165, 100, 150, 25);
        destText.setBounds(165, 150, 150, 25);
        contentPane.add(sourceText);
        contentPane.add(destText);
        sourceBrowse = new JButton("Browse");
        destBrowse = new JButton("Browse");
        sourceBrowse.setBounds(320, 100, 80, 25);
        sourceBrowse.addActionListener(new ButtonListener());
        destBrowse.setBounds(320, 150, 80, 25);
        destBrowse.addActionListener(new ButtonListener());
        contentPane.add(sourceBrowse);
        contentPane.add(destBrowse);
        sourcePicLabel = new JLabel("Source Picture");
        destPicLabel = new JLabel("Destination Picture");
        sourcePicLabel.setBounds(10, 200, 150, 30);
        destPicLabel.setBounds(10, 250, 150, 30);
        contentPane.add(sourcePicLabel);
        contentPane.add(destPicLabel);
        sourcePicText = new JTextField();
        destPicText = new JTextField();
        sourcePicText.setBounds(165, 200, 150, 25);
        destPicText.setBounds(165, 250, 150, 25);
        contentPane.add(sourcePicText);
        contentPane.add(destPicText);
        sourcePicBrowse = new JButton("Browse");
        destPicBrowse = new JButton("Browse");
        sourcePicBrowse.setBounds(320, 200, 80, 25);
        sourcePicBrowse.addActionListener(new ButtonListener());
        destPicBrowse.setBounds(320, 250, 80, 25);
        destPicBrowse.addActionListener(new ButtonListener());
        contentPane.add(sourcePicBrowse);
        contentPane.add(destPicBrowse);
        sourcePicLabel.setEnabled(false);
        sourcePicText.setEnabled(false);
        sourcePicBrowse.setEnabled(false);
        destPicLabel.setEnabled(false);
        destPicText.setEnabled(false);
        destPicBrowse.setEnabled(false);
        String reductionList[] = {
            "", "80%", "75%", "50%", "25%"
        };
        reductionPercent = new JComboBox(reductionList);
        reductionPercent.setBounds(165, 300, 80, 25);
        reductionPercent.addActionListener(new ListListener());
        contentPane.add(reductionPercent);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        setSize(421, 481);
        start = new JButton("Start");
        start.setBounds(160, 360, 80, 25);
        start.addActionListener(new ButtonListener());
        contentPane.add(start);
    }

    public static void main(String args[])
    {
        new ImageShrinkerGUI();
    }
}
