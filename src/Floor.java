import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Makes a tile of the floor. A tile is a GameObject. Made into a grid of tiles
 * within Game.
 */
public class Floor extends GameObject {
    static BufferedImage floor;

    /**
     * Static block to run at compile time.
     */
    static {
        try {
            floor = readFile(new File("floor/quad.png"));
            floor = tesselateFloor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor. Creates a new png, which is quad.png tesselated in a 2x2 grid reflected horizontally, vertically,
     * and horizontally and vertically at the same time.
     * @throws IOException
     */
    public Floor(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    /**
     * @param fileName
     * @return
     * @throws IOException Takes in a fileName and reads that file
     */
    private static BufferedImage readFile(File fileName) throws IOException {
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




//    public BufferedImage[][] makeFloorAroundPlayer() {
//        BufferedImage[][] floorGrid = new BufferedImage[4][4];
//        BufferedImage currentFloor = new BufferedImage(1040, 1040, BufferedImage.TYPE_INT_ARGB);
//
//
//        Graphics2D g2d = currentFloor.createGraphics();
//
//        for (int r = 0; r < 4; r++) { //filling 2D array with tiles
//            for (int c = 0; c < 4; c++) {
//                floorGrid[r][c] = tesselateFloor();
//            }
//        }
//        //AffineTransform at = new AffineTransform();
//        int multiplier = 0;
//        for (BufferedImage[] bi : floorGrid) { //drawing each tile on BufferedImage
//            for (BufferedImage b : bi) {
//                multiplier++;
//                if (multiplier <= 4) {
//                    g2d.drawImage(b, b.getWidth() * multiplier, 0, null);
//                    multiplier = 0;
//                }
//            }
//        }
//        return floorGrid;
//    }

    /**
     * Gets the original dirt image (0,0 in the grid). Flips it horizontally (1,0 in the grid).
     * Flips that product vertically (1,-1 in the grid). Then flips that product horizontally (-1,-1 in the grid) using
     * affine transforms.
     * @return tess (the tesselated dirt png)
     */
    public static BufferedImage tesselateFloor() {
        AffineTransformOp op;

        BufferedImage flippedHorizontally;
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-floor.getWidth(null), 0);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        flippedHorizontally = op.filter(floor, null);

        BufferedImage flippedVertically;
        // Flip the image vertically
        AffineTransform ty = AffineTransform.getScaleInstance(1, -1);
        ty.translate(0, -flippedHorizontally.getHeight(null));
        op = new AffineTransformOp(ty, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
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

    @Override
    Image getImage() {
       return floor;
    }
}
