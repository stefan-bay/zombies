import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Tree extends GameObject {
    BufferedImage treeImage;

   Tree(double x, double y) {
       super(x, y, 0, 0);

       try {
           treeImage = ImageIO.read(new File("res/tree 2.png"));
       } catch (IOException e) {
           e.printStackTrace();
       }

       this.setWidth(treeImage.getWidth());
       this.setHeight(treeImage.getHeight());
   }


       @Override
    Image getImage() { return treeImage; }
}
