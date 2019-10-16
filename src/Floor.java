import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Floor {
    BufferedImage floor;
    AffineTransform tx;
    AffineTransformOp op;

    public Floor() throws IOException {
        floor = readFile(new File("floor/dirtfloor.png"));
//        tesselateFloor();
//        writeFile("floor/tesselated.png");
    }

    public static void main(String[] args) {
        try {
            Floor test = new Floor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName
     * @return
     * @throws IOException Takes in a fileName and reads that file
     */
    private BufferedImage readFile(File fileName) throws IOException {
        floor = ImageIO.read(fileName);
        return floor;
    }
    /**
     * Takes in a name and creates a new file with that name
     * @param name
     */
    private void writeFile(String name) {
//        tesselatedFloor = new File("floor/tesselatedDirtFloor.jpg");
        try {
            ImageIO.write(tesselateFloor(), "png", new File(name));
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public BufferedImage tesselateFloor() {
        BufferedImage flippedHorizontally;
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-floor.getWidth(null), 0);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        flippedHorizontally = op.filter(floor, null);

        BufferedImage flippedVertically;
        // Flip the image vertically
        AffineTransform ty = AffineTransform.getScaleInstance(1, -1);
        ty.translate(0, -flippedHorizontally.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(ty, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        flippedVertically = op.filter(flippedHorizontally, null);

        BufferedImage lastOne;
        AffineTransform lo = AffineTransform.getScaleInstance(-1, 1);
        lo.translate(-flippedVertically.getWidth(null), 0);
        op = new AffineTransformOp(lo, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        lastOne = op.filter(flippedVertically, null);

        BufferedImage tess = new BufferedImage(flippedHorizontally.getWidth() * 2, flippedHorizontally.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tess.createGraphics();
        g2d.drawImage(floor, 0,0, null);
        g2d.drawImage(flippedHorizontally, floor.getWidth(), 0, null);
        g2d.drawImage(flippedVertically, floor.getWidth(), floor.getHeight(), null);
        g2d.drawImage(lastOne, 0, floor.getHeight(), null);
        return tess;
    }

}
