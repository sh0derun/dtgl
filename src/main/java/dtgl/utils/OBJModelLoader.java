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

import dtgl.math.Vec2;
import dtgl.math.Vec3;

class FaceGroup{
	int positionIndex;
	int textureIndex;
	int normalIndex;

	FaceGroup(int pid, int tid, int nid){
		positionIndex = pid;
		textureIndex = tid;
		normalIndex = nid;
	}
}

class Face{
	FaceGroup[] faceGroups = new FaceGroup[3];
}

public class OBJModelLoader {

	public static void LoadModelDataFromOBJ(String OBJFileName) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(OBJFileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = null;
		List<Vec3> positions = new ArrayList<>();
		List<Vec3> normals = new ArrayList<>();
		List<Vec2> textures = new ArrayList<>();
		List<Face> faces = new ArrayList<>();
		try {
			Function<String, Float> stringToFloat = Float::parseFloat;
			Function<List<Float>, Vec3> floatsToVec3 = floats -> new Vec3(floats.get(0), floats.get(1), floats.get(2));
			Function<String[], Vec3> convertLineToVec3 = str -> floatsToVec3.apply(Arrays.stream(str).map(stringToFloat).collect(Collectors.toList()));
			
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
						textures.add(vec2);
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
							face.faceGroups[i] = new FaceGroup(pid, tid, nid);
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
		
		System.out.println("positions count = " + positions.size());
		System.out.println("normals count = " + normals.size());
		System.out.println("textures coords count = " + textures.size());
		System.out.println("faces count = " + faces.size());
	}
	
}
