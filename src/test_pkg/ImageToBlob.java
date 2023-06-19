package test_pkg;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class ImageToBlob {
	
	
	public byte[] imageToByteArray(String filePath) throws Exception {
		
		byte[] imageValue = null;
		
		ByteArrayOutputStream baos = null;
		FileInputStream fis = null;
		
		try {
			baos = new ByteArrayOutputStream();
			fis = new FileInputStream(filePath);
			
			byte[] buf = new byte[1024];
			int read = 0;
			
			while((read = fis.read(buf, 0 , buf.length))!= -1 ) {
				baos.write(buf, 0, read);
			}
			
			imageValue = baos.toByteArray();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (baos != null) {
				baos.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		
		return imageValue;	
		
	}
}
