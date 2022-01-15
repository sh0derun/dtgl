package dtgl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.event.ListSelectionEvent;

import dtgl.math.Vec;
import dtgl.math.Vec2;
import dtgl.math.Vec3;

class ModelData{
	public float[] vertexPositions;
	public float[] normals;
	public float[] textureCoords;
	public int[] indices;
}

public class OBJModelLoader {

	public static ModelData LoadModelDataFromOBJ(String OBJFileName) {
		ModelData modelData = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(OBJFileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = null;
		List<Vec3> vertexPositions = new ArrayList<Vec3>();
		List<Vec3> normals = new ArrayList<Vec3>();
		List<Vec2> textureCoords = new ArrayList<Vec2>();
		List<Integer> indices = new ArrayList<Integer>();
		try {
			Function<String, Float> stringToFloat = Float::parseFloat;
			Function<List<Float>, Vec3> floatsToVec3 = floats -> new Vec3(floats.get(0), floats.get(1), floats.get(2));
			Function<String[], Vec3> convertLineToVec3 = str -> floatsToVec3.apply(Arrays.stream(str).map(stringToFloat).collect(Collectors.toList()));
			while((line = reader.readLine()) != null) {
				switch(line.substring(0, 2)) {
					case "v ":{
						String[] cds = line.substring(2).split(" ");
						vertexPositions.add(convertLineToVec3.apply(cds));
					}
					break;
					case "vn":{
						String[] cds = line.substring(3).split(" ");
						normals.add(convertLineToVec3.apply(cds));
					}
					break;
					case "vt":{
						String[] cds = line.substring(3).split(" ");
						Vec2 vec2 = new Vec2(Float.parseFloat(cds[0]), Float.parseFloat(cds[1]));
						textureCoords.add(vec2);
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(vertexPositions);
		System.out.println(normals);
		System.out.println(textureCoords);
		
		return modelData;
	}
	
}
