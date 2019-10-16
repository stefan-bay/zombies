import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NumberHelper {
    /**
     * Holds widths of each number png
     */
    private static int [] numberwidth = {
            15, 14, 19, 21, 19, 22, 19, 20, 17, 17
    };

    public static BufferedImage imageForNumber(int number) {
        ArrayList<BufferedImage> numberList = Animation.getListForPath("res/numbers/", 0, 9);
        String killString = Integer.toString(number);
        int number_of_digits = killString.length();
        int [] numbers_to_display = new int[number_of_digits];
        int img_width = 0; // total width of image (depends on which numbers are shown)

        // fill numbers to display with correlating digits
        for (int i = 0; i < number_of_digits; i++) {
            int digit = Character.getNumericValue(killString.charAt(i));
            System.out.println(digit);
            numbers_to_display[i] = digit;
            img_width += numberwidth[digit];
        }

        //
        BufferedImage image = new BufferedImage(img_width, 22, BufferedImage.TYPE_INT_ARGB);
        Graphics2D numbers_g = (Graphics2D) image.createGraphics();

        // draw
        int loc = 0; // location in image
        for (int i = 0; i < number_of_digits; i++) {
            int digit = numbers_to_display[i];
            numbers_g.drawImage(numberList.get(digit), null, loc, 0);
            loc += numberwidth[digit]; // update location in image
        }

        numbers_g.dispose();

        return image;
    }
}
