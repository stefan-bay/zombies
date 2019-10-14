import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Flashlight extends GameObject {
    Image flashLightCone;
    int flashlightLength = 100;
    int flashlightWidth = 100;
    Flashlight(double playerX, double playerY, double width, double height) {
        super(0,0,width,height);
        Path2D.Double flashlightConePath = new Path2D.Double();
        flashlightConePath.moveTo(playerX, playerY);
        flashlightConePath.lineTo(playerX + flashlightLength, playerY + flashlightLength);
        createFlashLight(playerX,playerY);
    }

    void createFlashLight(double playerX, double playerY) {
        Path2D.Double flashlightConePath = new Path2D.Double();
        flashlightConePath.moveTo(playerX, playerY);
        flashlightConePath.lineTo(playerX + flashlightLength, playerY - flashlightWidth/2);
        flashlightConePath.lineTo(playerX + flashlightLength, playerY + flashlightWidth/2);
        flashlightConePath.closePath();
        flashLightCone = new BufferedImage((int) 800,(int) 800, BufferedImage.TYPE_INT_RGB);
        Graphics g = flashLightCone.getGraphics();
        g.setColor(Color.BLUE);
        Path2D.Double totalPath = new Path2D.Double();
        totalPath.moveTo(0,0);
        totalPath.lineTo(0,800);
        totalPath.lineTo(800,800);
        totalPath.lineTo(800,0);
        totalPath.closePath();
        Area totalArea = new Area(totalPath);
        Area flashLightArea = new Area(flashlightConePath);
        totalArea.subtract(flashLightArea);
        g.setColor(Color.BLACK);
        ((Graphics2D) g).fill(totalArea);

    }
    @Override
    Image getImage() {
        return flashLightCone;
    }
}
