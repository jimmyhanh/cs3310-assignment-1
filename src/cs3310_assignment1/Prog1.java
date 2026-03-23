/**************************************************************/
/* Hung Anh Ho                                                 */
/* Login ID: 018189015                                         */
/* CS 3310, Spring 2026                                        */
/* Programming Assignment 1                                    */
/* Draw graphs and identify any cycle or acycle                  */
/**************************************************************/


package cs3310_assignment1;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prog1 {

	// Constants
	/*
	 * 1((\\d+) first group = one or more digits -+ first vertexmatches the comma. (\\d+) 
	 * second group = one or more digits - second vertex 
	 * match literal parentheses is needed in Java => (1,2)(10,45)
	 */
	private static final Pattern EDGE_PATTERN = Pattern.compile("\\((\\d+),(\\d+)\\)");
	
	//Line separator
	private static String decorator = ("----------------------------------------");

	public static void main(String[] args) throws IOException {

		
		System.out.println("\tCS 3310 - Programming Assignment 1");
		System.out.println("\tGraphParser: Connected Components and Cycle Detection");
		System.out.println("\t-----------------------------------------------------");

		Scanner keyboard = new Scanner(System.in);
		
		do {
			int option = Input.getInteger("\n\tEnter the your option: "
										+ "\n\t 1.Test a file"
										+ "\n\t 2.Exit "
										+ "\n\n\tEnter your option: ");
			switch(option)
			{
			case 1:
			{
				Option1();
				
				//a Pause
				System.out.println("\n\n\t...Enter any key to continue...");		 
				keyboard.nextLine();
				
				//Clear screen
				//clearScreen();
				break;
			}
			case 2:
				System.exit(0);
				
				//close to prevent memory leak
				keyboard.close();
				break;
			}	
		} while(true);
	}
	
	
	/**************************************************************/
	/* Method: clearScreen */
	/* Purpose: clear screen*/
	/* Parameters: */
	/* void */
	/* Returns: Screen is cleared */
	/*public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");
	    System.out.flush();
	} 
	*/
	
	
	/**************************************************************/
	/* Method: Option1 */
	/* Purpose: execute option 1 to input a file name and read through the file*/
	/* to find a particular book */
	/* Parameters: */
	/* void */
	/* Returns: print the result */
	/**
	 * @throws IOException ************************************************************/
	public static void Option1() throws IOException {
		String input = Input.getString("\tEnter a file name (SampleInput.txt): ");
		
		//Open file
		File readFile = new File(input);
		
		//Check file's existence 
		while (!readFile.exists()) //Page 245
		{
			 System.out.println("\tThe file " + readFile + " is not found.");		 
			 input = Input.getString("\tEnter the correct file name: ");
			 
			 readFile = new File(input);	 //Check file
		}
		
		//read file and return graphs
		try (Scanner text = new Scanner(readFile)) {
			
			int graphNumber = 0;
			
			//Open and read data
			while (text.hasNextLine()) {
				String line = text.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				graphNumber++;
				try {
					Graph graph = parseGraphLine(line);
					printGraphReport(graph, graphNumber);
					
				} catch (IllegalArgumentException ex) {
					System.out.println(decorator);
					System.out.println("Graph" + graphNumber + ":");
					System.out.println("Invalid graph definition. " + ex.getMessage());
				}
			}
			
			//Close read file
			text.close();
			
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to read file: " + ex.getMessage());
			return;
		}
	}
	
	
	/* Class Graph */
	/* Purpose: Graph structure (1-based vertex indexing) */
	/* Parameters: */
	private static class Graph {
		int vertices;
		ArrayList<TreeSet<Integer>> adjacency;

		Graph(int vertices) {
			this.vertices = vertices;
			this.adjacency = new ArrayList<>(vertices + 1);
			for (int i = 0; i <= vertices; i++) {
				this.adjacency.add(new TreeSet<>());
			}
		}
	}
	
	/**************************************************************/
	/* Method: parseGraphLine */
	/* Purpose: Parse one graph line in format:
	 * n (u1,v1) (u2,v2) ...*/
	/* to find a particular book */
	/* Parameters: */
	/* Graph object target: String line*/
	/* Returns: the formated result graph */
	/**************************************************************/
	private static Graph parseGraphLine(String line) {
		String[] info = line.split("\\s+");
		if (info.length == 0) {
			throw new IllegalArgumentException("\tEmpty graph definition.");
		}

		int vertices;
		try {
			vertices = Integer.parseInt(info[0]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("\tInvalid vertex count: " + info[0]);
		}

		if (vertices <= 0) {
			throw new IllegalArgumentException("\tVertex count must be positive.");
		}

		Graph graph = new Graph(vertices);
		Matcher matcher = EDGE_PATTERN.matcher(line);
		while (matcher.find()) {
			int u = Integer.parseInt(matcher.group(1));
			int v = Integer.parseInt(matcher.group(2));
			if (u < 1 || u > vertices || v < 1 || v > vertices) {
				continue;
			}
			graph.adjacency.get(u).add(v);
			graph.adjacency.get(v).add(u);
		}

		return graph;
	}
	
	
	/* Method: printGraphReport */
	/* Purpose: Print full report for one graph. */
	/* Parameters: */
	/* Graph object target: graph object */
	/* Returns: print Graph Report */
	private static void printGraphReport(Graph graph, int graphNumber) {
		System.out.println("\t" + decorator);
		System.out.println("\tGraph" + graphNumber + ":");

		List<List<Integer>> components = findConnectedComponents(graph);
		printConnectedComponents(components);

		List<Integer> cycle = findOneCycle(graph);
		if (cycle.isEmpty()) {
			System.out.println("\tThe graph is acyclic.");
		} else {
			System.out.println("\tCycle detected: " + formatCycle(cycle));
		}
	}
	
	
	/* Method: findConnectedComponents */
	/* Purpose: Find connected components using DFS. */
	/* Parameters: */
	/* Graph object target: graph object */
	/* Returns: List(integer): components of the graph */
	private static List<List<Integer>> findConnectedComponents(Graph graph) {
		boolean[] visited = new boolean[graph.vertices + 1];
		List<List<Integer>> components = new ArrayList<>();

		for (int start = 1; start <= graph.vertices; start++) {
			if (visited[start]) {
				continue;
			}

			List<Integer> component = new ArrayList<>();
			ArrayDeque<Integer> stack = new ArrayDeque<>();
			stack.push(start);
			visited[start] = true;
			
			//Check Stack
			while (!stack.isEmpty()) {
				int node = stack.pop();
				component.add(node);
				
				for (int neighbor : graph.adjacency.get(node).descendingSet()) {
					if (!visited[neighbor]) {
						visited[neighbor] = true;
						stack.push(neighbor);
					}
				}
			}

			Collections.sort(component);
			components.add(component);
		}

		return components;
	}
	
	
	/* Method: buildCyclePath */
	/* Purpose: Print connected component count and vertex sets. */
	/* Parameters: */
	/* Integer target: two-dimensional Lists */
	/* Returns: String: Print connected component count and vertex sets. */
	private static void printConnectedComponents(List<List<Integer>> components) {
		int count = components.size();
		String label = count == 1 ? "connected component" : "connected components";
		System.out.print("\t" + numberToWord(count) + " " + label + ": ");

		for (List<Integer> component : components) {
			System.out.print("{" + joinNumbers(component, " ") + "} ");
		}
		System.out.println();
	}
	
	
	/* Method: buildCyclePath */
	/* Purpose: Build cycle path from DFS parent links. */
	/* Parameters: */
	/* Integer target: int current, int ancestor (the node of before previous node), int[] parent(previous node) */
	/* Returns: String: return the path in array list */
	private static List<Integer> findOneCycle(Graph graph) {
		int[] state = new int[graph.vertices + 1];
		int[] parent = new int[graph.vertices + 1];
		Arrays.fill(parent, -1);
		
		List<Integer> cycle = new ArrayList<>();

		for (int node = 1; node <= graph.vertices; node++) {
			if (state[node] == 0 && dfsFindCycle(node, -1, graph, state, parent, cycle)) {
				return cycle;
			}
		}
		return cycle;
	}

	/**
	 * Method dfsFindCycle
	 * DFS helper to detect and build one cycle path.
	 * int state: 0 = unvisted, 1 = visted, 2 = fully processed
	 * 
	 * During DFS, if you see a neighbor with
		state[neighbor] == 1
		the parent), that indicates a back-edge
		and therefore a cycle
		So prevent visiting blindly and is the key signal for "cycle found" vs "acyclic." 
	* Parameters: 
	* Integer target: int current, int parentNode, Graph graph, int[] state, int[] parent, List of integers in the cycle
	* Returns: Boolean: true if cycle	
	*/
	private static boolean dfsFindCycle(int current, int parentNode, Graph graph, int[] state, int[] parent,
			List<Integer> cycle) {
		state[current] = 1;

		for (int neighbor : graph.adjacency.get(current)) {
			if (neighbor == parentNode) {
				continue;
			}

			if (state[neighbor] == 0) {
				parent[neighbor] = current;
				if (dfsFindCycle(neighbor, current, graph, state, parent, cycle)) {
					return true;
				}
			} else if (state[neighbor] == 1) {
				cycle.addAll(buildCyclePath(current, neighbor, parent));
				return true;
			}
		}

		state[current] = 2;
		return false;
	}
	
	/* Method: buildCyclePath */
	/* Purpose: Build cycle path from DFS parent links. */
	/* Parameters: */
	/* Integer target: int current, int ancestor (the node of before previous node), int[] parent(previous node) */
	/* Returns: String: return the path in array list */
	private static List<Integer> buildCyclePath(int current, int ancestor, int[] parent) {
		List<Integer> path = new ArrayList<>();
		path.add(current);
		
		while (path.get(path.size() - 1) != ancestor) {
			int next = parent[path.get(path.size() - 1)];
			if (next == -1) {
				return new ArrayList<>();
			}
			path.add(next);
		}

		Collections.reverse(path);
		path.add(ancestor);
		return path;
	}

	/* Method: formatCycle */
	/* Purpose: Format cycle as a-b-c-a display. */
	/* Parameters: List<Integer> cycle*/
	/* Integer target: List of integers from the cycle list */
	/* Returns: String: string of joined integers */
	private static String formatCycle(List<Integer> cycle) {
		return joinNumbers(cycle, " - ");
	}

	/* Method: joinNumbers */
	/* Purpose: Join integer list into one string. */
	/* Parameters: List<Integer> values, String separator */
	/* Integer target: List of integers, separator symbol to connect */
	/* Returns: String: string of joined integers */
	private static String joinNumbers(List<Integer> values, String separator) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < values.size(); i++) {
			if (i > 0) {
				builder.append(separator);
			}
			builder.append(values.get(i));
		}
		return builder.toString();
	}

	/* Method: numberToWord */
	/* Purpose: Convert small integers to words for component label. */
	/* Parameters: */
	/* Integer target: integer to convert */
	/* Returns: String: string from converted number */
	/**************************************************************/
	private static String numberToWord(int value) {
		switch (value) {
			case 1:
				return "One";
			case 2:
				return "Two";
			case 3:
				return "Three";
			case 4:
				return "Four";
			case 5:
				return "Five";
			case 6:
				return "Six";
			case 7:
				return "Seven";
			case 8:
				return "Eight";
			case 9:
				return "Nine";
			case 10:
				return "Ten";
			default:
				return Integer.toString(value);
		}
	}
}