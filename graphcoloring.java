import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

//@author 88min      Graph Coloring

public class GraphColoring {
	public static void main(String args[]) {

		File file = new File("..\\input.txt");
		ArrayList<int[]> adjMatrix = new ArrayList<int[]>();

                //read input.txt file if existing
		try {
			Scanner sc = new Scanner(file);			
			System.out.println("ADJACENCY MATRIX:");
			int count = 0;
			while (sc.hasNextLine()) {
				String[] r = sc.nextLine().split(" "); //split line with space as delimiter into string array
				String[] row = new String[r.length + 2];
				for (int i = 0; i < row.length; i++) {
					if (i == 0)
						row[i] = ""+count;
					else if (i == 1)
						row[i] = "";
					else
						row[i] = r[i-2];
				}
				
				// print adjacency matrix from input.txt file
				for (int i = 2; i < row.length; i++) {
					System.out.print(String.format("%-" + 4 + "s", row[i]));
				}
				System.out.println();
				
				//convert string to integer
				int[] rowi = new int[row.length];
				for (int i = 0; i < row.length; i++) {
					if (toNumber(row[i]))
						rowi[i] = Integer.parseInt(row[i]);
					else
						rowi[i] = -1;
				}
				
				//get every degree of nodes
				int degree = 0;
				for (int i = 2; i < rowi.length; i++) {
					if (rowi[i] > 0)
						degree += 1;
				}
				rowi[1] = degree;				
				adjMatrix.add(rowi);
				count += 1;
			}			
			sc.close();
		}
    //print if input.txt cannot be found
		catch (FileNotFoundException e) {
			System.out.println("File doesn't exist");
			return;
		}
		
		ArrayList<Integer> checkedNodes = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> colors = new ArrayList<ArrayList<Integer>>();
		
		while (checkedNodes.size() != adjMatrix.size()) {
			ArrayList<Integer> color = new ArrayList<Integer>();
			
			for (int i = 0; i < adjMatrix.size(); i++) {
				int[] row = adjMatrix.get(i);
				
        //avoiding duplicate
				if (!checkedNodes.contains(row[0])) {
					ArrayList<Integer> neighbours = getNeighbour(i, adjMatrix);
					boolean colorable = true;
					for (int neighbour : neighbours) {
						if (color.contains(neighbour)) {
							colorable = false;
							break;
						}
					}
					if (colorable) {
						checkedNodes.add(row[0]);
						color.add(row[0]);
					}
				}
			}
			colors.add(color);
		}
                //printing result
                System.out.println("\nNumber of Colors: " + colors.size() + "\n");
		int count = 1;
		for (ArrayList<Integer> color : colors) {
			System.out.print("Color"+count+": { " );
			for (int j = 0; j < color.size(); j++) {
				if (j == color.size()-1)
					System.out.print(color.get(j) + " ");
				else
					System.out.print(color.get(j) + ", ");
			}
			System.out.println("}");
			count += 1;
		}
	}
        
	private static boolean toNumber(String str) { 
		try {  
			Double.parseDouble(str);  
			return true;
		}
		catch(NumberFormatException e){  
			return false;  
		}  
	}
        
	private static ArrayList<Integer> getNeighbour(int index, ArrayList<int[]> adjMatrix) {
		int[] row = adjMatrix.get(index);
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		for (int i = 2; i < row.length; i++) {
			if (row[i] > 0)
				neighbours.add(i - 2);
		}
		return neighbours;
	}	
}
