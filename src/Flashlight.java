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
    static int maxAmbientLight = 250;
    boolean dayTime = false;
    int ambientDim = 50;
    Flashlight(double playerX, double playerY, double width, double height, Point2D.Double mouseLoc) {
        super(0,0,width,height);
        createFlashLight(playerX,playerY, width, height, mouseLoc);
        setColliding(false);
    }

    void setAmbientLight(int ambientLight, boolean isDayTime) {
        this.ambientLight = ambientLight;
        this.dayTime = isDayTime;
    }

    void createFlashLight(double playerX, double playerY, double width, double height, Point2D.Double mouseLoc) {
        Point2D.Double playerPoint = new Point2D.Double(playerX, playerY);
        Point2D.Double posX = new Point2D.Double(playerX+1, 0 );
        flashLightCone = new BufferedImage((int)width,(int)height, BufferedImage.TYPE_INT_ARGB);

        // Draw the dimness of the screen
        Graphics g = flashLightCone.getGraphics();
        Color ambientLightColor = new Color(0,0,0,ambientLight);
        g.setColor(ambientLightColor);
        g.fillRect(0,0,(int)width,(int)height);
        // for r in range(radius)
        // for theta in range(mousetheta-mouseTheta/2 : mouseTheta+mouseTheta/2)
        if(!dayTime) {
            double mouseTheta = VectorUtils.getThetaBetweenVectors(posX, mouseLoc, playerPoint);
            double thetaInc = Math.toRadians(1);
            for (double r = 0; r < flashlightRadius; r++) {
                for (double theta = mouseTheta - (flashLightTheta / 2); theta < mouseTheta + (flashLightTheta / 2); theta += thetaInc) {
                    int x = (int) (width / 2 + r * Math.cos(theta) + playerX);
                    int y = (int) (height / 2 + r * Math.sin(theta) + playerY);
                    double radiusRatio = r / flashlightRadius;
                    int alpha = (int) ((radiusRatio * ambientLight));
                    int redGreenToYellow = (int) (ambientLight - alpha);
                    Color flashlightColor = new Color(redGreenToYellow, redGreenToYellow, 0, alpha);
                    int arraySize = 5;
                    int[] colorArray = new int[(int)Math.pow(arraySize,2)];
                    Arrays.fill(colorArray, flashlightColor.getRGB());
                    if(x+arraySize < flashLightCone.getWidth() && x - arraySize > 0
                        && y+arraySize < flashLightCone.getHeight() && y-arraySize > 0) {
                        flashLightCone.setRGB((int) (x) - 5, (int) (y) - 5, 5, 5, colorArray, 0, 0);
                    }
                }
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
