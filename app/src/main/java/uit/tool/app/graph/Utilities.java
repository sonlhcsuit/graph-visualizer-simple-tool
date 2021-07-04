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

	public static void printPath(int path[], int size, ArrayList<String> vertexNames ){
		System.out.print("Path:");
		for(int i = 0; i<size; i++){
			if(path[i] != -1){
				System.out.print(" " + vertexNames.get(path[i]));
			}
			else{
				System.out.print(" null");
			}
		}
		System.out.println();
	}

	public static void setNegativeValue(int list[], int size ){
		for(int i =0 ; i < size; i++)
		{
			list[i] = -1;
		}
	}

	public static Boolean removeEdgeFromAdj(int origin, int destination, double edges[][]){
		if(edges[origin][destination] > 0){
			edges[origin][destination] = 0;
			return true;
		}else if(edges[destination][origin] > 0){
			edges[destination][origin] = 0;
			return true;
		}
		else{
			return false;
		}
	}

	public static int isEuler(double[][] edges, int size){
		// 0 iis exist Euler cycyle 
		// 2 is exist Euler path 
		// other is not exist Euler path(cycle)
		int oldVertices = 0;
		int count;
		for(int i = 0; i < size; i++){
			count = 0;
			for(int j = 0; j < size; j++){
				if(edges[i][j] > 0){
					count ++;
				}
				if(edges[j][i] > 0){
					count ++;
				}
			}
			if(count % 2 != 0){
				oldVertices ++;
			}
		}
		return oldVertices;
	}

	public static int findStartIndexEuler(double[][] edges, int size){
		int count;
		for(int i = 0; i < size; i++){
			count = 0;
			for(int j = 0; j < size; j++){
				if(edges[i][j] > 0){
					count ++;
				}
				if(edges[j][i] > 0){
					count ++;
				}
			}
			if(count % 2 != 0){
				return i;
			}
		}
		return 0;
	}

	public static int computeDegreeVertex(double edges [][], int v){
		int count = 0;
		for(int i = 0; i < edges.length; i++){
			if(edges[v][i] > 0 ){
				count ++;
			}
			if(edges[i][v] > 0){
				count ++;
			}

		}
		return count;
	}
	public static int dfsCount(Integer v, boolean[] isVisited, double edges [][]){
		isVisited[v] = true;
		int count = 1;
		for(int i=0; i<edges.length; i++){
			if(edges[v][i] > 0 || edges[i][v] > 0){
				if(!isVisited[i]){
					count = count + dfsCount(i, isVisited, edges);
				}
			}
		}
		return count;
	}

	public static double[][] copyAdjacencyMatrix(double edges [][] ){
		int size = edges.length;
		double[][] result = new double[size][size];
		for(int i =0; i< size; i++){
			for(int j =0 ;j < size; j++){
				result[i][j] = 	edges[i][j];
			}
		}
		return result;
	}

}
