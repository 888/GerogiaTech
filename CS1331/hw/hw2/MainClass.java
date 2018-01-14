public class MainClass {
    public static void main(String[] args) {
        Pic pic = new Pic(args[0]);
        ImageProcessor imageProc = new ImageProcessor(pic);
        imageProc.greyscale().show();
        imageProc.invert().show();
        imageProc.noRed().show();
        imageProc.blackAndWhite().show();
        imageProc.maximize().show();
        if (args.length == 2) {
            Pic otherPic = new Pic(args[1]);
            imageProc.overlay(otherPic).show();
        }
    }
}