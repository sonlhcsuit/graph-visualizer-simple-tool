package uit.tool.app.graph;

import java.util.ArrayList;

public class Utilities {

	public static void printVertexNames(ArrayList<String> vertexNames){
		System.out.print("Vertex:");
		for(int i = 0; i < vertexNames.size(); i++){
			System.out.print(" " + vertexNames.get(i));
		}
		System.out.print("\n");
	}

	public static void printEdge(double edges[][], int size){
		for(int i = 0; i<size; i++){
			for(int j = 0; j<size; j++){
				System.out.print(" " + edges[i][j]);
			}
			System.out.print("\n");
		}
	}

	public static void printPath(int path[], int size){
		System.out.print("Path:");
		for(int i = 0; i<size; i++){
			System.out.print(" " + path[i]);
		}
		System.out.println();
	}

}
