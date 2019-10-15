import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Flashlight extends GameObject {
    BufferedImage flashLightCone;
    int flashlightRadius = 300;
    double flashLightTheta = Math.toRadians(60);
    int ambientLight = 250;
    int ambientDim = 50;
    Flashlight(double playerX, double playerY, double width, double height, Point2D.Double mouseLoc) {
        super(0,0,width,height);
        createFlashLight(playerX,playerY, width, height, mouseLoc);
        setColliding(false);
    }

    void createFlashLight(double playerX, double playerY, double width, double height, Point2D.Double mouseLoc) {
        Point2D.Double playerPoint = new Point2D.Double(playerX, playerY);
        Point2D.Double posX = new Point2D.Double(playerX + flashlightRadius, playerY);
        flashLightCone = new BufferedImage((int)width,(int)height, BufferedImage.TYPE_INT_ARGB);

        // Draw the dimness of the screen
        Graphics g = flashLightCone.getGraphics();
        Color ambientLightColor = new Color(0,0,0,ambientLight);
        g.setColor(ambientLightColor);
        g.fillRect(0,0,(int)width,(int)height);
        // for r in range(radius)
        // for theta in range(mousetheta-mouseTheta/2 : mouseTheta+mouseTheta/2)
        double mouseTheta = VectorUtils.getThetaBetweenVectors(posX, mouseLoc, playerPoint);
        double thetaInc = Math.toRadians(1);
        for(double r = 0; r < flashlightRadius; r++) {
            for(double theta = mouseTheta - flashLightTheta/2; theta < mouseTheta + flashLightTheta/2; theta += thetaInc){
                int x = (int) (width/2 + r * Math.cos(theta));
                int y = (int) (height/2 + r * Math.sin(theta));
                double radiusRatio = r/flashlightRadius;
                int alpha = (int)((radiusRatio * ambientLight));
                int redGreenToYellow = (int)(ambientLight - alpha);
                Color flashlightColor = new Color(redGreenToYellow, redGreenToYellow, 0, alpha);
                int[] colorArray = new int[25];
                Arrays.fill(colorArray, flashlightColor.getRGB());
                flashLightCone.setRGB((int)(x)-2,(int)(y)-2, 5,5,colorArray, 0,0);

            }
        }
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
