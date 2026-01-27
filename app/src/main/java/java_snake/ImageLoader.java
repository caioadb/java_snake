package java_snake;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for loading and caching images from the classpath.
 * 
 * Images are loaded from the resources folder and cached to avoid
 * repeated disk access. Supports loading at original size or scaled
 * to a specific dimension.
 * 
 * Usage:
 * <pre>
 *     ImageLoader loader = new ImageLoader();
 *     BufferedImage sprite = loader.load("/images/snake_head.png");
 *     BufferedImage scaled = loader.loadScaled("/images/snake_head.png", 32, 32);
 * </pre>
 */
public class ImageLoader {
    
    private final Map<String, BufferedImage> imageCache = new HashMap<>();
    private final Map<String, BufferedImage> scaledCache = new HashMap<>();
    
    /**
     * Loads an image from the classpath.
     * 
     * @param path The resource path (e.g., "/images/snake_head.png")
     * @return The loaded BufferedImage
     * @throws IllegalArgumentException if path is null or empty
     * @throws ImageLoadException if the image cannot be loaded
     */
    public BufferedImage load(String path) {
        validatePath(path);
        
        return imageCache.computeIfAbsent(path, this::loadFromClasspath);
    }
    
    /**
     * Loads an image and scales it to the specified dimensions.
     * Scaled versions are cached separately from originals.
     * 
     * @param path The resource path
     * @param width Target width in pixels
     * @param height Target height in pixels
     * @return The scaled BufferedImage
     * @throws IllegalArgumentException if path is null or empty
     * @throws ImageLoadException if the image cannot be loaded
     */
    public BufferedImage loadScaled(String path, int width, int height) {
        validatePath(path);
        
        String cacheKey = path + "@" + width + "x" + height;
        
        return scaledCache.computeIfAbsent(cacheKey, key -> {
            BufferedImage original = load(path);
            return scaleImage(original, width, height);
        });
    }
    
    /**
     * Clears all cached images to free memory.
     */
    public void clearCache() {
        imageCache.clear();
        scaledCache.clear();
    }
    
    private void validatePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Image path cannot be null or empty");
        }
    }
    
    private BufferedImage loadFromClasspath(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new ImageLoadException("Image not found: " + path);
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new ImageLoadException("Failed to load image: " + path, e);
        }
    }
    
    private BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaled.createGraphics();
        
        // Use high-quality scaling
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                             RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                             RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(original, 0, 0, width, height, null);
        g2d.dispose();
        
        return scaled;
    }
}
