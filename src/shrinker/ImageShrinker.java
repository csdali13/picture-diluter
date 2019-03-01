package shrinker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import javax.imageio.*;

public class ImageShrinker
{

    public ImageShrinker()
    {
    }

    public static void main(String args[])
    {
        (new ImageShrinker()).shrinkImageFolder("C:\\Documents and Settings\\dsreedha\\Desktop\\Pics", "C:\\Documents and Settings\\dsreedha\\Desktop\\Pics\\reduced", 0.13F);
    }

    public void singleImage(String sourcePath, String destPath, float compressQuality)
    {
        try
        {
            BufferedImage bufferedImage = new BufferedImage(500, 500, 4);
            File sourceFile = new File(sourcePath);
            File resultFile = new File(destPath);
            resultFile.setWritable(true);
            resultFile.delete();
            bufferedImage = ImageIO.read(sourceFile);
            ImageWriter writer = (ImageWriter)ImageIO.getImageWritersBySuffix("jpeg").next();
            writer.setOutput(ImageIO.createImageOutputStream(resultFile));
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(2);
            param.setCompressionQuality(compressQuality);
            writer.write(null, new IIOImage(bufferedImage, null, null), param);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void shrinkImageFolder(String sourceFolder, String destFolder, float compressQuality)
    {
        try
        {
            BufferedImage bufferedImage = new BufferedImage(500, 500, 4);
            File sourceDir = new File(sourceFolder);
            File resultDir = new File(destFolder);
            String sourceFiles[] = sourceDir.list();
            String destFiles[] = resultDir.list();
            for(int i = 0; i < destFiles.length; i++)
            {
                File toDelete = new File((new StringBuilder(String.valueOf(destFolder))).append("\\").append(destFiles[i]).toString());
                toDelete.delete();
            }

            for(int i = 0; i < sourceFiles.length; i++)
            {
                File sourceFile = new File((new StringBuilder(String.valueOf(sourceFolder))).append("\\").append(sourceFiles[i]).toString());
                File resultFile = new File((new StringBuilder(String.valueOf(destFolder))).append("\\").append(sourceFiles[i]).toString());
                if(sourceFiles[i].contains(".jpg") || sourceFiles[i].contains(".gif") || sourceFiles[i].contains(".bmp") || sourceFiles[i].contains("jpeg")
                		|| sourceFiles[i].contains(".JPG") || sourceFiles[i].contains(".GIF") || sourceFiles[i].contains(".BMP") || sourceFiles[i].contains("JPEG"))
                {
                    bufferedImage = ImageIO.read(sourceFile);
                    int width = bufferedImage.getWidth();
                    int height = bufferedImage.getHeight();
                    ImageWriter writer = (ImageWriter)ImageIO.getImageWritersBySuffix("jpeg").next();
                    writer.setOutput(ImageIO.createImageOutputStream(resultFile));
                    ImageWriteParam param = writer.getDefaultWriteParam();
                    param.setCompressionMode(2);
                    param.setCompressionQuality(compressQuality);
                    writer.write(null, new IIOImage(bufferedImage, null, null), param);
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
