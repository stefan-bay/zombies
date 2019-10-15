import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Flashlight extends GameObject {
    Image flashLightCone;
    int flashlightLength = 350;
    int flashlightWidth = 300;
    Flashlight(double playerX, double playerY, double width, double height, Point2D.Double mouseLoc) {
        super(0,0,width,height);
        Path2D.Double flashlightConePath = new Path2D.Double();
        flashlightConePath.moveTo(playerX, playerY);
        flashlightConePath.lineTo(playerX + flashlightLength, playerY + flashlightLength);
        createFlashLight(playerX,playerY, width, height, mouseLoc);
        setColliding(false);
    }

    void createFlashLight(double playerX, double playerY, double width, double height, Point2D.Double mouseLoc) {
        Point2D.Double playerPoint = new Point2D.Double(playerX, playerY);
        Point2D.Double horizontalFromPlayerPoint = new Point2D.Double(mouseLoc.getX(), playerY);
        double theta = VectorUtils.getThetaBetweenVectors(horizontalFromPlayerPoint, mouseLoc, playerPoint);
        Point2D.Double firstFlashPoint = new Point2D.Double(playerX + flashlightLength, playerY - flashlightWidth/2);
        Point2D.Double secondFlashPoint = new Point2D.Double(playerX + flashlightLength, playerY + flashlightWidth/2);
        firstFlashPoint = VectorUtils.rotate(firstFlashPoint, playerPoint, theta);
        secondFlashPoint = VectorUtils.rotate(secondFlashPoint, playerPoint, theta);
        Path2D.Double flashlightConePath = new Path2D.Double();
        flashlightConePath.moveTo(playerX, playerY);
        flashlightConePath.lineTo(firstFlashPoint.getX(), firstFlashPoint.getY());
        flashlightConePath.lineTo(secondFlashPoint.getX(), secondFlashPoint.getY());
        flashlightConePath.closePath();
        flashLightCone = new BufferedImage((int) 800,(int) 800, BufferedImage.TYPE_INT_ARGB);
        Graphics g = flashLightCone.getGraphics();
        g.setColor(Color.BLUE);
        g.translate((int)width/2,(int)height/2);
        Path2D.Double totalPath = new Path2D.Double();
        totalPath.moveTo(-width/2,-height/2);
        totalPath.lineTo(-width/2,height/2);
        totalPath.lineTo(width/2,height/2);
        totalPath.lineTo(width/2,-height/2);
        totalPath.closePath();
        Area totalArea = new Area(totalPath);
        Area flashLightArea = new Area(flashlightConePath);
        totalArea.subtract(flashLightArea);
        Color flashlight=new Color(255,255,0,100 );
        Color ambientLight = new Color(0,0,0,230);
        g.setColor(ambientLight);
        ((Graphics2D) g).fill(totalArea);
        g.setColor(flashlight);
        ((Graphics2D) g).fill(flashLightArea);



    }

    @Override
    Image getImage() {
        return flashLightCone;
    }

    @Override
    void move(double xDelta, double yDelta, ArrayList<GameObject> objects) {
    }

    @Override
    void setX(double x) {

    }

    @Override
    void setY(double y) {}
}
