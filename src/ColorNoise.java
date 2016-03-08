import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class ColorNoise {
	private static final File SAVE_FOLDER = new File("C:\\noise");
	private static final String IMAGE_FORMAT = "png";
	private static final long RANDOMNESS_SEED = 123L;
	private static final Random RANDOM = new Random(RANDOMNESS_SEED);

	public static void main(String[] args) {
		if (!SAVE_FOLDER.exists() || !SAVE_FOLDER.isDirectory()) {
			if (!SAVE_FOLDER.mkdirs()) {
				System.err.println(SAVE_FOLDER + " is not a valid directory");
				return;
			}
		}

		generateNoiseBitmap(1280,  720, 1, "1Pixel@720p");
		generateNoiseBitmap(1920, 1080, 1, "1Pixel@1080p");
		generateNoiseBitmap(3840, 2160, 1, "1Pixel@4K");

		System.out.println("All done.");
	}
	
	private static int getRandomColor() {
		return RANDOM.nextInt(0xFFFFFF);
	}

	private static void generateNoiseBitmap(int width, int height, int pixelPerBlock, String fileName) {
		System.out.println("Generating " + fileName + " …");
		int realSizeWidth = width / pixelPerBlock;
		int realSizeHeight = height / pixelPerBlock;
		BufferedImage img = new BufferedImage(realSizeWidth, realSizeHeight, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < realSizeWidth; x++) {
			for (int y = 0; y < realSizeHeight; y++) {
				img.setRGB(x, y, getRandomColor());
			}
		}

		Image scaledInstance = img.getScaledInstance(width, height, BufferedImage.SCALE_DEFAULT);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		img.getGraphics().drawImage(scaledInstance, 0, 0, null);

		save(img, fileName);
	}

	private static void save(BufferedImage img, String fileName) {
		File file = new File(SAVE_FOLDER, fileName + '.' + IMAGE_FORMAT);

		boolean success = false;
		try {
			success = ImageIO.write(img, IMAGE_FORMAT, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (success) {
			System.out.println(fileName + " saved.");
		} else {
			System.err.println(fileName + " could not be saved.");
		}
	}
}
