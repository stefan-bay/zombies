import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Flashlight shades the screen by the ambient light and draws a translucent
 * flashlight image that allows the player to see infront of them,
 */
public class Flashlight extends GameObject {
    // The image of this flashlight
    BufferedImage flashLightCone;
    // The radius of the flashlight, how far it shines.
    int flashlightRadius = 300;
    // The width of the flashlight beam
    double flashLightTheta = Math.toRadians(60);
    // The ambient light in the room.
    int ambientLight = 250;
    // The maximum darkness (minimum light) that can be displayed.
    static int maxAmbientLight = 250;
    // Whether or not it is daytime.
    boolean dayTime = false;

    /**
     * Constructs a new flashlight based on the players position and direciton.
     * @param playerX the x coordinate of the player
     * @param playerY the y coordinate of the player
     * @param width the width of the screen
     * @param height the height of the screen
     * @param mouseLoc the location of the mouse, and the direction the player is facing.
     */
    Flashlight(double playerX, double playerY, double width, double height, Point2D.Double mouseLoc) {
        super(0,0,width,height);
        createFlashLight(playerX,playerY, width, height, mouseLoc);
        // Don't let other objects collide with teh flashlight since it is an overlay.
        setColliding(false);
    }

    /**
     * Sets the amount of ambient light and whether or not the flashlight should be shown.
     * @param ambientLight the ambient light to shade the room by
     * @param isDayTime true if the flashlight should be displayed.
     */
    void setAmbientLight(int ambientLight, boolean isDayTime) {
        this.ambientLight = ambientLight;
        this.dayTime = isDayTime;
    }

    /**
     * Create a flashlight image from the constructor parameters.
     */
    void createFlashLight(double playerX, double playerY, double width, double height, Point2D.Double mouseLoc) {
        // The point of the player on the screen
        Point2D.Double playerPoint = new Point2D.Double(playerX, playerY);
        // The position to the right of the players position +x
        Point2D.Double posX = new Point2D.Double(playerX+1, 0 );
        flashLightCone = new BufferedImage((int)width,(int)height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = flashLightCone.getGraphics();
        // Shade the screen based on the ambient light
        Color ambientLightColor = new Color(0,0,0,ambientLight);
        g.setColor(ambientLightColor);
        g.fillRect(0,0,(int)width,(int)height);
        // Draw the flashlight beam if it is not daytime.
         if(!dayTime) {
             // The angle between the +x from the player and the mouse location.
             // The angle of the center of the beam relative to positive x.
            double mouseTheta = VectorUtils.getThetaBetweenVectors(posX, mouseLoc, playerPoint);
            double thetaInc = Math.toRadians(1);
            // Shade the flashlight beam using polar coordinates to determine the intensity of the beam at different
             // distances from the player.
            for (double r = 0; r < flashlightRadius; r++) {
                for (double theta = mouseTheta - (flashLightTheta / 2); theta < mouseTheta + (flashLightTheta / 2); theta += thetaInc) {
                    int x = (int) (width / 2 + r * Math.cos(theta) + playerX);
                    int y = (int) (height / 2 + r * Math.sin(theta) + playerY);
                    double radiusRatio = r / flashlightRadius;
                    int alpha = (int) ((radiusRatio * ambientLight));
                    int redGreenToYellow = (int) (ambientLight - alpha);
                    // The color of this flashlight beam to make the area infront of the layer visible.
                    Color flashlightColor = new Color(redGreenToYellow, redGreenToYellow, 0, alpha);
                    // Shade the beam in chunks of arraysize so that it appears smooth.
                    // If we don't draw in chunks we either get multiple beams as we get farther from the player,
                    // or we have to make theta small and do more iteration. This saves compute power.
                    int arraySize = 5;
                    int[] colorArray = new int[(int)Math.pow(arraySize,2)];
                    Arrays.fill(colorArray, flashlightColor.getRGB());
                    // Make sure the coordiantes are within the screen so we don't try to draw a beam out of the image.
                    if(x+arraySize < flashLightCone.getWidth() && x - arraySize > 0
                        && y+arraySize < flashLightCone.getHeight() && y-arraySize > 0) {
                        // Start drawing this color chunk at arraysize before the x coordinate so that it fills in up to
                        // the desired coordinate
                        flashLightCone.setRGB((int) (x) - arraySize, (int) (y) - arraySize, arraySize,
                                arraySize, colorArray, 0, 0);
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
