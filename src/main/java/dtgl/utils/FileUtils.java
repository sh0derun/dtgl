package dtgl.utils;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class FileUtils {
	
	public static String loadFile(String path) {
		String fileContent = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			fileContent = reader.lines().reduce((acc, curr)->acc+"\n"+curr).orElse(null);
			reader.close();
		} catch (IOException e) {
			System.out.println("cannot load file "+path+" !");
			System.out.println(e.getMessage());
		}
		return fileContent;
	}

	public static void getImageMetadata(String imagePath) {
		System.out.println("***************"+imagePath+"***************");
		try {
			File image = new File(imagePath);
			ImageInputStream iis = ImageIO.createImageInputStream(image);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
			if(readers.hasNext()){
				ImageReader reader = readers.next();
				reader.setInput(iis, true);
				IIOMetadata metadata = reader.getImageMetadata(0);
				IIOMetadataNode metaTree = (IIOMetadataNode) metadata.getAsTree("javax_imageio_1.0");
				displayMetadata(metaTree);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public static void displayMetadata(IIOMetadataNode tree) {
		if(tree == null)
			return;
		else if(tree.getChildNodes() == null) {
			Element elementInfo = (Element) tree.getElementsByTagName(tree.getNodeName()).item(0);
			System.out.println(elementInfo.getNodeName() + " : " + elementInfo.getAttribute("value"));
		}
		else{
			NodeList nodeList = tree.getChildNodes();
			for(int i = 0; i < nodeList.getLength(); i++){
				Element item = (Element) nodeList.item(i);
				Element elementInfo = (Element) item.getElementsByTagName(item.getNodeName()).item(0);
				System.out.println(elementInfo.getNodeName() + " : " + elementInfo.getAttribute("value"));
				displayMetadata((IIOMetadataNode) item);
			}
		}
	}

}
