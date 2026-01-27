package java_snake;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

class ImageLoaderTest {
    
    private ImageLoader imageLoader;
    
    @BeforeEach
    void setUp() {
        imageLoader = new ImageLoader();
    }
    
    @Test
    void loadImage_withValidPath_returnsImage() {
        // test_image.png is a 16x16 test image in test resources
        BufferedImage image = imageLoader.load("/images/test_image.png");
        
        assertNotNull(image, "Image should be loaded");
        assertEquals(16, image.getWidth(), "Image width should be 16");
        assertEquals(16, image.getHeight(), "Image height should be 16");
    }
    
    @Test
    void loadImage_withInvalidPath_throwsException() {
        assertThrows(ImageLoadException.class, () -> {
            imageLoader.load("/images/nonexistent.png");
        });
    }
    
    @Test
    void loadImage_cachesImages() {
        BufferedImage first = imageLoader.load("/images/test_image.png");
        BufferedImage second = imageLoader.load("/images/test_image.png");
        
        assertSame(first, second, "Same image instance should be returned from cache");
    }
    
    @Test
    void loadImage_withNullPath_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            imageLoader.load(null);
        });
    }
    
    @Test
    void loadImage_withEmptyPath_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            imageLoader.load("");
        });
    }
    
    @Test
    void getScaledImage_returnsCorrectSize() {
        BufferedImage scaled = imageLoader.loadScaled("/images/test_image.png", 32, 32);
        
        assertNotNull(scaled, "Scaled image should be returned");
        assertEquals(32, scaled.getWidth(), "Scaled width should be 32");
        assertEquals(32, scaled.getHeight(), "Scaled height should be 32");
    }
    
    @Test
    void getScaledImage_cachesScaledVersions() {
        BufferedImage first = imageLoader.loadScaled("/images/test_image.png", 32, 32);
        BufferedImage second = imageLoader.loadScaled("/images/test_image.png", 32, 32);
        
        assertSame(first, second, "Same scaled instance should be returned from cache");
    }
    
    @Test
    void getScaledImage_differentSizesAreCachedSeparately() {
        BufferedImage small = imageLoader.loadScaled("/images/test_image.png", 32, 32);
        BufferedImage large = imageLoader.loadScaled("/images/test_image.png", 64, 64);
        
        assertNotSame(small, large, "Different sizes should be different instances");
        assertEquals(32, small.getWidth());
        assertEquals(64, large.getWidth());
    }
    
    @Test
    void clearCache_removesAllCachedImages() {
        BufferedImage first = imageLoader.load("/images/test_image.png");
        imageLoader.clearCache();
        BufferedImage second = imageLoader.load("/images/test_image.png");
        
        assertNotSame(first, second, "After cache clear, new instance should be loaded");
    }
}
