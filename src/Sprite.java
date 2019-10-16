import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Class that draws sprites on a BufferedImage using Graphics2D.
 */
public class Sprite {
    BufferedImage spriteImage;

    Shape[] spriteShapes;
    Color[] spriteColors;

    int spriteWidth, spriteHeight;

    enum SpriteType {
        PLAYER,
        ENEMY_RANGED,
        ENEMY_MELEE
    }

    private static final Color brown = new Color(222, 184, 135);
    private static final Color hair = new Color(10,10,10);
    private static final Color gray = new Color(100,100,100);
    private static final Color black = new Color(0,0,0);

    private static final Color maroon = new Color(128,0,0);
    private static final Color darkred = new Color(139,0,0);
    private static final Color red = new Color(165,42,42);
    private static final Color black2 = new Color(0,0,0);
    private static final Color crimson = new Color(220,20,60);

    private static final Color maroon2 = new Color(50,0,0);
    private static final Color darkred2 = new Color(70,0,0);
    private static final Color red2 = new Color(125,42,42);
    private static final Color firebrick2= new Color(138,34,34);
    private static final Color crimson2 = new Color(120,20,60);

    private static final Color[] playerColors = new Color[] {brown, brown, hair, gray, black};
    private static final Color[] rangedEnemyColors = new Color[] {maroon, darkred, red, black2, crimson};
    private static final Color[] meleeEnemyColors = new Color[] {maroon2, darkred2, red2, firebrick2, crimson2};

    private static final int playerWidth = 34;
    private static final int playerHeight = 33;

    private static final int meleeWidth = 40;
    private static final int meleeHeight = 38;

    private static final int rangedWidth = 32;
    private static final int rangedHeight = 26;
    // player       = 34 x 33
    // melee enemy  = 40 x 38
    // ranged enemy = 32 x 26

    double oldTheta  = 0;

    /**
     * Constructor for a sprite using enum. Based off of which Sprite trying to create,
     * creates sprite. i.e. SpriteType sprite = PLAYER, makes player.
     * @param sprite
     */
    public Sprite(SpriteType sprite) {

        switch (sprite) {
            case PLAYER:
                this.spriteShapes = playerShapes();
                translatePlayerShapes(this.spriteShapes);

                this.spriteColors = playerColors;

                this.spriteWidth = playerWidth;
                this.spriteHeight = playerHeight;

                break;
            case ENEMY_MELEE:
                this.spriteShapes = meleeEnemyShapes();
                translateMeleeEnemyShapes(this.spriteShapes);

                this.spriteColors = meleeEnemyColors;

                this.spriteWidth = meleeWidth;
                this.spriteHeight = meleeHeight;

                break;
            case ENEMY_RANGED:
                this.spriteShapes = rangedEnemyShapes();
                translateRangedEnemyShapes(this.spriteShapes);

                this.spriteColors = rangedEnemyColors;

                this.spriteWidth = rangedWidth;
                this.spriteHeight = rangedHeight;

                break;
            default:
                System.out.println("No sprite");
        }
    }

    /**
     * Method to rotate a sprite.
     * @param theta
     */
    void rotate(double theta) {
        AffineTransform back = new AffineTransform();
        back.rotate(-oldTheta, spriteWidth/2, spriteHeight/2);

        AffineTransform to = new AffineTransform();
        to.rotate(theta, spriteWidth/2, spriteHeight/2);

        for (int i = 0; i < spriteShapes.length; i++) {
            spriteShapes[i] = back.createTransformedShape(spriteShapes[i]);
            spriteShapes[i] = to.createTransformedShape(spriteShapes[i]);
        }
        oldTheta = theta;
    }

    /**
     * Needed to translate each sprite to (0,0) separately because they each had different dimensions.
     * @param shapes
     */
    void translateRangedEnemyShapes(Shape [] shapes) {
        AffineTransform at = new AffineTransform();
        at.translate(-100, -95);

        for (int i = 0; i < shapes.length; i++)
            shapes[i] = at.createTransformedShape(shapes[i]);
    }
    void translateMeleeEnemyShapes(Shape [] shapes) {
        AffineTransform at = new AffineTransform();
        at.translate(0, 10);

        for (int i = 0; i < shapes.length; i++)
            shapes[i] = at.createTransformedShape(shapes[i]);
    }
    void translatePlayerShapes(Shape [] shapes) {
        AffineTransform at = new AffineTransform();
        at.translate(29, 13 +10);

        for (int i = 0; i < shapes.length; i++)
            shapes[i] = at.createTransformedShape(shapes[i]);
    }

    /**
     * Takes in a list of Shapes and a list of Colors and draws each
     * sprite in the list of shapes onto a BufferedImage.
     * @param spriteList
     * @param colorList
     */
    public BufferedImage createSpriteImage(Shape[] spriteList, Color[] colorList) {
        BufferedImage image = new BufferedImage(spriteWidth, spriteHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d  = image.createGraphics();
        for (int i = 0; i < spriteList.length; i++) {
                g2d.setColor(Color.black);
                g2d.draw(spriteList[i]);
                g2d.setColor(colorList[i]);
                g2d.fill(spriteList[i]);
        }
        g2d.dispose();

        return image;
    }


    /**
     * Assembling shapes to make an ENEMY_RANGED
     * @return rangedEnemyShapes
     */
    public Shape[] rangedEnemyShapes() {
        Shape[] rangedEnemyShapes = new Shape[4];
        Ellipse2D.Double head = new Ellipse2D.Double( 100, 100, 13*1.2,13*1.2);

        Path2D.Double leftArm = new Path2D.Double();

        leftArm.moveTo(105, 100);
        leftArm.lineTo(107, 95); //end shoulder
        leftArm.lineTo(109, 95);
        leftArm.lineTo(114, 97); //elbow
        leftArm.lineTo(125, 105);
        leftArm.lineTo(121, 105);
        leftArm.lineTo(114, 100);
        leftArm.closePath();

        Path2D.Double rightArm = new Path2D.Double();
        rightArm.moveTo(105, 115);
        rightArm.lineTo(107, 120);
        rightArm.lineTo(109, 120);
        rightArm.lineTo(114, 117);
        rightArm.lineTo(125, 109);
        rightArm.lineTo(121, 109);
        rightArm.lineTo(114, 113);
        rightArm.closePath();

        Rectangle2D.Double enemyGun = new Rectangle2D.Double(121, 105,10, 4);

        rangedEnemyShapes[0] = leftArm;
        rangedEnemyShapes[1] = rightArm;
        rangedEnemyShapes[2] = head;
        rangedEnemyShapes[3] = enemyGun;


        return rangedEnemyShapes;

    }

    /**
     * Assembling shapes to make an ENEMY_MELEE
     * @return meleeEnemyShapes
     */
    public Shape[] meleeEnemyShapes() {
        Shape[] meleeEnemyShapes = new Shape[2];

        Ellipse2D head = new Ellipse2D.Double(0,0,15.6, 15.6);
        Path2D.Double arms = new Path2D.Double();
        arms.moveTo(6, 0);
        arms.lineTo(7, -5);

        arms.lineTo(8, -5);
        arms.lineTo(10, -7); //m8

        arms.lineTo(30, -7);
        arms.lineTo(32, -9); //m7

        arms.lineTo(33, -10); //index base finger
        arms.lineTo(40, -10); //index finger outside //m6

        arms.lineTo(40, -8); //index finger inside
        arms.lineTo(34, -8); //m5

        arms.lineTo(33, -6);
        arms.lineTo(35, -6);//m4

        arms.lineTo(35, -6);
        arms.lineTo(35, -5); //m3

        arms.lineTo(32, -5);
        arms.lineTo(30, -5); //m2

        arms.lineTo(27, -3); //m1
        arms.lineTo(20, -3);
        arms.lineTo(16, -1);

        arms.lineTo(17, 2); //left pectoral
        arms.lineTo(17, 5);
        arms.lineTo(16, 8);
        /////////////////////////
        arms.lineTo(17, 11); //right pectoral
        arms.lineTo(17, 14);
        arms.lineTo(16, 17);

        arms.lineTo(20, 20);
        arms.lineTo(27, 20);
        arms.lineTo(27,20); //m1

        arms.lineTo(30, 22);//m2
        arms.lineTo(32, 22);

        arms.lineTo(35, 22);//m3
        arms.lineTo(35, 23);

        arms.lineTo(35,23);//m4
        arms.lineTo(33,23);

        arms.lineTo(34, 25);//m5
        arms.lineTo(40, 25);

        arms.lineTo(40, 27); //m6
        arms.lineTo(33, 27);

        arms.lineTo(32, 26);//m7
        arms.lineTo(30, 24);

        arms.lineTo(10, 24); //m8
        arms.lineTo(8, 22);

        arms.lineTo(7, 22);
        arms.lineTo(6, 17);

        arms.closePath();

        meleeEnemyShapes[0] = arms;
        meleeEnemyShapes[1] = head;

        return meleeEnemyShapes;
    }

    /**
     * Assembling shapes to make a PLAYER
     * @return
     */
    public Shape[] playerShapes() {
        double halfHead = 6.5;
        int halfhitbox = 15;

        Shape[] playerShapes = new Shape[5];

        Path2D.Double leftArm = new Path2D.Double();
        //-29, -30
        leftArm.moveTo(-24, -13);
        leftArm.lineTo(-22, -18); //end shoulder
        leftArm.lineTo(-15, -23); //elbow
        leftArm.lineTo(0, -7); //hand
        leftArm.lineTo(-4, -8); //inner hand
        leftArm.lineTo(-15, -18); //inner elbow
        leftArm.lineTo(-15, -19);
        leftArm.lineTo(-15,-18);
        leftArm.lineTo(-18, -13);
        leftArm.closePath();

        Path2D.Double rightArm = new Path2D.Double();
        rightArm.moveTo(-24, 2);
        rightArm.lineTo(-22, 7);
        rightArm.lineTo(-20, 9); //outer shoulder
        rightArm.lineTo(-8, 2); //outer elbow
        rightArm.lineTo(0, -7); //outer hand
        rightArm.lineTo(-4, -6); //inner hand
        rightArm.lineTo(-12, 2);
        rightArm.lineTo(-15, 3);
        rightArm.lineTo(-17, 1);
        rightArm.closePath();

        Rectangle2D.Double gun = new Rectangle2D.Double(-4, -6, 10, 2);
        Rectangle2D.Double headLamp = new Rectangle2D.Double(-halfHead*2, -8, 2, 5);

        Ellipse2D.Double head = new Ellipse2D.Double(-halfhitbox*2 + 1, -halfHead*2/*-6.5*/, 13*1.2,13*1.2);

        //adding bodyparts
        playerShapes[0] = leftArm;
        playerShapes[1] = rightArm;
        playerShapes[2] = head;
        playerShapes[3] = headLamp;
        playerShapes[4] = gun;

        return playerShapes;
    }

    public BufferedImage getImage() {
        return createSpriteImage(this.spriteShapes, this.spriteColors);
    }
}