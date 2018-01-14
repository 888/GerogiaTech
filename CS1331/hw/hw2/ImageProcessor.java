public class ImageProcessor {

    private Pic pic;

    public static void main(String[] args) {
        Pic pic = new Pic(args[0]);
        ImageProcessor imageProc = new ImageProcessor(pic);
        imageProc.greyscale().show();
        imageProc.invert().show();
        imageProc.noRed().show();
        imageProc.blackAndWhite().show();
        imageProc.maximize().show();
        if (args.length > 1) {
            Pic otherPic = new Pic(args[1]);
            imageProc.overlay(otherPic).show();
        }
    }


    public ImageProcessor(Pic picParam) {
        pic = picParam;
    }

    private int[] getRGB(Pixel pixel) {
        int[] rgb = new int[3];
        rgb[0] = pixel.getRed();
        rgb[1] = pixel.getGreen();
        rgb[2] = pixel.getBlue();
        return rgb;
    }

    public void setRGB(Pixel pixel, int[] rgb) {
        pixel.setRed(rgb[0]);
        pixel.setGreen(rgb[1]);
        pixel.setBlue(rgb[2]);
    }

    public Pic greyscale() {
        Pic tempPic = pic.deepCopy();
        Pixel[][] pixels = tempPic.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                int[] rgb = getRGB(pixels[i][j]);
                int average = (rgb[0] + rgb[1] + rgb[2]) / 3;
                int[] newRgb = {average, average, average};
                setRGB(pixels[i][j], newRgb);
            }
        }
        return tempPic;
    }

    public Pic invert() {
        Pic tempPic = pic.deepCopy();
        Pixel[][] pixels = tempPic.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                int[] rgb = getRGB(pixels[i][j]);
                rgb[0] = 255 - rgb[0];
                rgb[1] = 255 - rgb[1];
                rgb[2] = 255 - rgb[2];
                setRGB(pixels[i][j], rgb);
            }
        }
        return tempPic;
    }

    public Pic noRed() {
        Pic tempPic = pic.deepCopy();
        Pixel[][] pixels = tempPic.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                pixels[i][j].setRed(0);
            }
        }
        return tempPic;
    }

    public Pic blackAndWhite() {
        Pic tempPic = pic.deepCopy();
        Pixel[][] pixels = tempPic.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                int[] rgb = getRGB(pixels[i][j]);
                int average = (rgb[0] + rgb[1] + rgb[2]) / 3;
                if (average > 127) {
                    average = 255;
                } else {
                    average = 0;
                }
                int[] newRgb = {average, average, average};
                setRGB(pixels[i][j], newRgb);
            }
        }
        return tempPic;
    }

    public Pic maximize() {
        Pic tempPic = pic.deepCopy();
        Pixel[][] pixels = tempPic.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                int[] rgb = getRGB(pixels[i][j]);
                int max = Math.max(rgb[2], Math.max(rgb[0], rgb[1]));
                if (!(rgb[0] == rgb[1] && rgb[0] == rgb[2])) {
                    for (int k = 0; k < 3; k++) {
                        rgb[k] = rgb[k] == max ? max : 0;
                    }
                    setRGB(pixels[i][j], rgb);
                }
            }
        }
        return tempPic;
    }

    public Pic overlay(Pic other) {
        Pic tempPic = pic.deepCopy();
        Pixel[][] pixels = tempPic.getPixels();
        Pixel[][] otherPix = other.getPixels();
        int pixelsLength = pixels.length > otherPix.length ? otherPix.length
                : pixels.length;
        int pixelsWidth = pixels[0].length > otherPix[0].length
                ? otherPix[0].length : pixels[0].length;
        for (int i = 0; i < pixelsLength; i++) {
            for (int j = 0; j < pixelsWidth; j++) {
                int[] rgb0 = getRGB(pixels[i][j]);
                int[] rgb1 = getRGB(otherPix[i][j]);
                int r = (rgb0[0] + rgb1[0]) / 2;
                int g = (rgb0[1] + rgb1[1]) / 2;
                int b = (rgb0[2] + rgb1[2]) / 2;
                int[] newRGB = {r, g, b};
                setRGB(pixels[i][j], newRGB);
            }
        }

        return tempPic;
    }
}