package linktic.lookfeel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


@Component
public class ImageToBase64Converter {
	
	private final ResourceLoader resourceLoader;

    public ImageToBase64Converter(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

//	public String convertImageToBase64(String imagePath) throws IOException {
//		Resource resource = resourceLoader.getResource("classpath:" + imagePath);
//		try (InputStream inputStream = resource.getInputStream()) {
//			byte[] imageBytes = inputStream.readAllBytes();
//			return Base64.getEncoder().encodeToString(imageBytes);
//		}
//	}
    
    public String convertImageToBase64(String imagePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + imagePath);
        try (InputStream inputStream = resource.getInputStream()) {
            // Leer los bytes en un arreglo de bytes
            byte[] buffer = new byte[1024];
            int bytesRead;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            
            // Convertir el arreglo de bytes a Base64
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }

}
