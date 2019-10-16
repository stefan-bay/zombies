import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Helper method to convert an int to an image using our selected font
 */
public class NumberHelper {
    /**
     * Holds widths of each number png
     */
    private static int [] numberwidth = {
            15, 14, 19, 21, 19, 22, 19, 20, 17, 17
    };

    /**
     * Creates image of to display number in our font
     * @param number number to display
     */
    public static BufferedImage imageForNumber(int number) {
        ArrayList<BufferedImage> numberList = Animation.getListForPath("res/numbers/", 0, 9);
        String numString = Integer.toString(number);
        int number_of_digits = numString.length();
        int [] numbers_to_display = new int[number_of_digits];
        int img_width = 0; // total width of image (depends on which numbers are shown)

        // fill numbers to display with correlating digits
        for (int i = 0; i < number_of_digits; i++) {
            int digit = Character.getNumericValue(numString.charAt(i));
            numbers_to_display[i] = digit;
            img_width += numberwidth[digit];
        }

        //
        BufferedImage image = new BufferedImage(img_width, 32, BufferedImage.TYPE_INT_ARGB);
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

    /**
     * Finds potential width if image with number were to be created
     */
    public static int imageWidthForNumber(int num) {
        String numString = Integer.toString(num);
        int number_of_digits = numString.length();
        int [] numbers_to_display = new int[number_of_digits];
        int img_width = 0; // total width of image (depends on which numbers are shown)

        // fill numbers to display with correlating digits
        for (int i = 0; i < number_of_digits; i++) {
            int digit = Character.getNumericValue(numString.charAt(i));
            img_width += numberwidth[digit];
        }

        return img_width;
    }

}
