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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileUtils {

	/*key = included_file, value = include_content*/
	private static final Map<String, String> includeLookup;

	static {
		includeLookup = new HashMap<>();
	}

	private FileUtils(){}
	
	public static String loadShaderFile(String path) {
		String fileContent = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			fileContent = reader.lines().reduce((acc, curr) -> {
				if(curr.startsWith("#include")){
					try {
						curr = includeProcessor(curr);
					} catch (FileNotFoundException e) {
						System.out.println(e.getMessage());
					}
				}
				return acc+"\n"+curr;
			}).orElse(null);
			reader.close();
		} catch (IOException e) {
			System.out.println("cannot load file "+path+" !");
			System.out.println(e.getMessage());
		}
		return fileContent;
	}

	private static String includeProcessor(String includeStatement) throws FileNotFoundException{
		String res = "";
		String include = includeStatement.trim();
		int s = include.indexOf('<') + 1;
		int e = include.indexOf('>');
		include = include.substring(s, e);
		URL resource = getPath(include);
		if(resource == null){
			throw new FileNotFoundException(include + " : no such  file or directory");
		}
		if(!includeLookup.containsKey(include)){
			try (BufferedReader resourceReader = new BufferedReader(new FileReader(resource.getPath()))) {
				res = resourceReader.lines().collect(Collectors.joining("\n"));
				includeLookup.put(include, res);
			} catch (IOException ioException) {
				System.out.println("cannot load file "+include+" !");
				System.out.println(ioException.getMessage());
			}
		}
		else{
			res = includeLookup.get(include);
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

	public static URL getPath(String resourcePath) {
		return Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(resourcePath));
	}

}
