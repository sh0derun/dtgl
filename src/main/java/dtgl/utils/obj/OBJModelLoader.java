package dtgl.utils.obj;

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

import dtgl.math.Vec2;
import dtgl.math.Vec3;

class Face{
	FaceGroup[] faceGroups = new FaceGroup[3];

	static class FaceGroup{
		int positionIndex;
		int textureIndex;
		int normalIndex;

		FaceGroup(int pid, int tid, int nid){
			positionIndex = pid;
			textureIndex = tid;
			normalIndex = nid;
		}
	}
}

public class OBJModelLoader {

	public static OBJModel LoadModelDataFromOBJ(String OBJFileName) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(OBJFileName));
		} catch (FileNotFoundException e) {
			System.out.println(OBJFileName + " : No such obj file");
			System.out.println(e.getMessage());
		}

		List<Vec3> positions = new ArrayList<>();
		List<Vec3> normals = new ArrayList<>();
		List<Vec2> textureCoords = new ArrayList<>();
		List<Face> faces = new ArrayList<>();
		try {
			Function<String, Float> stringToFloat = Float::parseFloat;
			Function<List<Float>, Vec3> floatsToVec3 = floats -> new Vec3(floats.get(0), floats.get(1), floats.get(2));
			Function<String[], Vec3> convertLineToVec3 = str -> floatsToVec3.apply(Arrays.stream(str).map(stringToFloat).collect(Collectors.toList()));

			String line = null;
			while((line = reader.readLine()) != null) {
				switch(line.substring(0, 2)) {
					case "v ":{
						String[] cds = line.substring(2).split(" ");
						Vec3 vertex = convertLineToVec3.apply(cds);
						positions.add(vertex);
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
					case "f ":{
						String[] groups = line.substring(2).split(" ");
						Face face = new Face();
						for(int i = 0; i < groups.length; i++){
							String group = groups[i];
							String[] groupIndices = group.split("/");
							int pid = Integer.parseInt(groupIndices[0]) - 1;
							//Both texture and normal ids can be optional in OBJ file
							int tid = -1;
							int nid = -1;
							if(groupIndices.length > 1){
								tid = groupIndices[1].length() == 0 ? -1 : Integer.parseInt(groupIndices[1]) - 1;
								if(groupIndices.length > 2){
									nid = groupIndices[2].length() == 0 ? -1 : Integer.parseInt(groupIndices[2]) - 1;
								}
							}
							face.faceGroups[i] = new Face.FaceGroup(pid, tid, nid);
						}
						faces.add(face);
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

		//Reordering positions, texture coords and normals and constructing indices array
		
		System.out.println("positions count = " + positions.size());
		System.out.println("normals count = " + normals.size());
		System.out.println("textures coords count = " + textureCoords.size());
		System.out.println("faces count = " + faces.size());

		float[] positionsArray = new float[positions.size() * 3];
		float[] textureCoordsArray = new float[positions.size() * 2];
		float[] normalsArray = new float[positions.size() * 3];

		List<Integer> indices = new ArrayList<>();

		for(int i = 0; i < positions.size(); i++){
			Vec3 position = positions.get(i);
			positionsArray[i * 3 + 0] = position.getCoords()[0];
			positionsArray[i * 3 + 1] = position.getCoords()[1];
			positionsArray[i * 3 + 2] = position.getCoords()[2];
		}

		int[] indicesArray = new int[faces.size() * 3];
		int i = 0;

		for(Face face : faces){
			for(Face.FaceGroup faceGroup : face.faceGroups){
				int positionIndex = faceGroup.positionIndex;
				indicesArray[i++] = positionIndex;

				if(faceGroup.textureIndex >= 0){
					Vec2 textureCoord = textureCoords.get(faceGroup.textureIndex);
					textureCoordsArray[positionIndex * 2 + 0] = textureCoord.getCoords()[0];
					/*opengl starts from top-left and blender bottom-left so we should flip V component*/
					textureCoordsArray[positionIndex * 2 + 1] = 1 - textureCoord.getCoords()[1];
				}

				if(faceGroup.normalIndex >= 0){
					Vec3 normal = normals.get(faceGroup.normalIndex);
					normalsArray[positionIndex * 3 + 0] = normal.getCoords()[0];
					normalsArray[positionIndex * 3 + 1] = normal.getCoords()[1];
					normalsArray[positionIndex * 3 + 2] = normal.getCoords()[2];
				}
			}
		}

		return new OBJModel(positionsArray, textureCoordsArray, normalsArray, indicesArray);
	}
	
}
