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
        double theta =  Math.acos(((p1.getX()) * (p2.getX()) + (p1.getY())
                * (p2.getY())) / (p1.distance(origin) * p2.distance(origin)));
        if(p2.getY() < origin.getY() && p2.getX() < origin.getX()) {
            theta +=Math.PI;
        } else if (p2.getY() < origin.getY() && p2.getX() > origin.getX()){
            theta = Math.PI/2 - theta;
            theta +=  Math.PI*3/2;
        } else if (p2.getY() > origin.getY() && p2.getX() < origin.getX()) {
            theta = Math.PI/2 - theta;
            theta += Math.PI/2;
        }
        return theta;
    }
}