package uit.tool.app.graph;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Iterator;
import java.util.Collections;
import java.util.*;



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

	public static double[] calculateHeuristic(int target, ArrayList<Vertex> vertexs){
		double[] heuristicVertexs = new double[vertexs.size()];
		double targetX = vertexs.get(target).getX();
		double targetY = vertexs.get(target).getY();
		for(int i=0; i < vertexs.size(); i++){
			double CurrX = vertexs.get(i).getX();
			double CurrY = vertexs.get(i).getX();

			heuristicVertexs[i] = Math.sqrt((CurrX - targetX )*(CurrX - targetX ) + (CurrY - targetY )*(CurrY - targetY));
		}

		return heuristicVertexs;
	}

	public static int findMinWay(int vertex, double heuristicVertexs [], double edges [][]){
		int minIndex = findStartMin(vertex,edges);
		double minValue = getMinValue(vertex, minIndex, heuristicVertexs, edges);
	
		for(int i=0; i<heuristicVertexs.length; i++){
			if(edges[vertex][i] > 0 || edges[i][vertex] > 0){
				double curDistance = getMinValue(vertex, i, heuristicVertexs, edges);
				if(curDistance < minValue){
					minIndex = i;
					minValue = curDistance;
				}
			}
		}
		return minIndex;
	}

	public static int findStartMin(int vertex, double edges [][]){
		for(int i=0; i<edges.length; i++){
			if(edges[vertex][i] > 0 || edges[i][vertex] > 0){
				return i;
			}
		}
		return -1;
	}

	public static double getMinValue(int vertex,int minIndex, double heuristicVertexs [], double edges [][]){
		double minValue = -1;
		if(edges[vertex][minIndex] > 0){
			minValue = edges[vertex][minIndex] + heuristicVertexs[minIndex];
		}
		if(edges[minIndex][vertex]> 0){
			double minValue2 = edges[minIndex][vertex] + heuristicVertexs[minIndex];
			if(minValue == -1){
				minValue = minValue2;
			}
			else{
				if(minValue2 < minValue){
					minValue = minValue2;
				}
			}
		}
		return minValue;
	}

	public static ArrayList<Integer> getAdjList(int sourceIndex, double heuristicVertexs [], double edges [][] ){
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		ArrayList<Double> valueList = new ArrayList<Double>();
		ArrayList<Double> CopyValueList = new ArrayList<Double>();
		// System.out.println("Copy value: ");
		for(int i = 0; i < edges.length; i++){
			if(edges[sourceIndex][i] > 0 || edges[i][sourceIndex] > 0){
				double curDistance = getMinValue(sourceIndex, i, heuristicVertexs, edges);
				valueList.add(curDistance);
				CopyValueList.add(curDistance);
			}
			else{
				CopyValueList.add(-1.0);
			}
			System.out.println(" " + CopyValueList.get(i));

		}
		System.out.println();
		Collections.sort(valueList);

		for(int i = 0; i < valueList.size(); i++){
			indexList.add(CopyValueList.indexOf(valueList.get(i)));
		}
		//Copy array 
		return indexList;
	}

	public static void printStack(Stack<Integer> stack){
		Iterator value = stack.iterator();
        // Displaying the values
        // after iterating through the stack
        System.out.print("Stack: ");
        while (value.hasNext()) {
            System.out.print(" " + value.next());
        }
		System.out.println();
	}


}
