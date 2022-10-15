package dtgl.utils;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Collectors;

public class FileUtils {

	private FileUtils(){}
	
	public static String loadShaderFile(String path) {
		String fileContent = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			fileContent = reader.lines().reduce(
				(acc, curr) -> acc+"\n"+(curr.startsWith("#include") ? includeProcessor(curr) : curr)
			).orElse(null);
			reader.close();
		} catch (IOException e) {
			System.out.println("cannot load file "+path+" !");
			System.out.println(e.getMessage());
		}
		return fileContent;
	}

	private static String includeProcessor(String includeStatement) {
		String res = "";
		String include = includeStatement.trim();
		int s = include.indexOf('<') + 1;
		int e = include.indexOf('>');
		include = include.substring(s, e);
		URL resource = ClassLoader.getSystemResource(include);
		try (BufferedReader resourceReader = new BufferedReader(new FileReader(resource.getPath()))) {
			res = resourceReader.lines().collect(Collectors.joining("\n"));
		} catch (FileNotFoundException ex) {
			System.out.println(include + " : no such  file or directory");
		} catch (IOException ioException) {
			System.out.println("cannot load file "+include+" !");
			System.out.println(ioException.getMessage());
		}
		return res;
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
				FileUtils.displayMetadata(metaTree);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	private static void displayMetadata(IIOMetadataNode tree) {
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
