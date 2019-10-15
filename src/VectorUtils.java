import java.awt.geom.Point2D;

/**
 * Class to represent a point in 3d space.
 */
public class VectorUtils {

    public static Point2D.Double rotate(Point2D.Double point, Point2D.Double origin, double theta) {
        double x = point.getX() - origin.getX();
        double y = point.getY() - origin.getY();
        double x2 = x;
        double y2 = y;
        x = x2 * Math.cos(theta) + y2 * -Math.sin(theta);
        y = x2 * Math.sin(theta) + y2 * Math.cos(theta);
        x += origin.getX();
        y += origin.getY();
        return new Point2D.Double(x,y);
    }

    public static double getThetaBetweenVectors(Point2D.Double p1, Point2D.Double p2, Point2D.Double origin) {
        Point2D.Double horizontalLine = new Point2D.Double(p1.getX()-origin.getX(), p1.getY());
        Point2D.Double toVector = p2;
        Point2D.Double zero = new Point2D.Double(0,0);
        double theta =  Math.acos(((horizontalLine.getX()) * (toVector.getX()) + (horizontalLine.getY())
                * (toVector.getY())) / (zero.distance(horizontalLine) * zero.distance(toVector)));
        if(toVector.getY() < 0 && toVector.getX() < 0) {
            theta = 2*Math.PI - theta;
        } else if (toVector.getY() < 0 && toVector.getX() > 0){
            theta = Math.PI/2 - theta;
            theta +=  Math.PI*3/2;
        }
        return theta;
    }
}