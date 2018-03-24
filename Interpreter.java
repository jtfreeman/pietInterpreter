import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class Interpreter {

	private static int totRow;		// total number of rows
	private static int totCol;		// total number of columns

	private static String dp = "right";
	private static String cc = "left";
	
	private static Stack<Integer> stack = new Stack<Integer>();
	
	private static int lastRotate = 0;

	// Takes in a .ppm file as an argument, and runs it as a Piet program
	// The Piet program is described at http://www.dangermouse.net/esoteric/piet.html
	public static void main(String[] args) throws IOException
	{
				
		File oldfile = new File("nhello.ppm");
		String firstBit = oldfile.getName().split("\\.")[0];
		File newfile = new File(firstBit + ".txt");
		oldfile.renameTo(newfile);
//		System.out.println(newfile);

		convertFile(newfile.getName());
		
		String[][] board = readFile("tmp.txt");

//		System.out.println(board[0][0]);
		
		int[] f = {0, 0};
		int[] g = {0, 1};
//		Codel cod1 = new Codel(f, board);
//		Codel cod2 = new Codel(g, board);

		readBoard(board);
//		System.out.println();
		
//		newfile.renameTo(oldfile);
	}
	
	public static void convertFile(String s) throws IOException, FileNotFoundException
	{
		File in = new File(s);
		PrintWriter writer = new PrintWriter("tmp.txt", "UTF-8");
		Scanner sc = new Scanner(in);
		int numCol, numRow;
		
		sc.nextLine(); sc.nextLine(); 
		String tmp = sc.nextLine();
//		System.out.println(tmp);
		String[] colrow = tmp.split("\\s");
//		System.out.println(colrow[0]);
		
		numCol = Integer.parseInt(colrow[0]); 
		numRow = Integer.parseInt(colrow[1]);
		sc.nextLine();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(in, true));
//		System.out.println(in.getAbsolutePath());
		String appendThis = "";
		for(int i = 0; i < numCol*numRow; i++)
		{
//			System.out.println("Getting number " + i);
			if(i % numRow == 0 && i != 0)
			{
//				System.out.println("new line");
				appendThis += "\n";
//				System.out.println("So far:\n" + appendThis);
			}
			int r, g, b;
			r = sc.nextInt();
			g = sc.nextInt();
			b = sc.nextInt();
//			System.out.print(r + " "+ g + " " + b + "\t\t");
			
			if(r == 0x00)
			{
				if(g == 0x00)
				{
					if(b == 0x00)
					{
						appendThis += "00 ";
//						System.out.println("Black");
					}
					else if(b == 0xC0)
					{
						appendThis += "52 ";
//						System.out.println("Dark Blue");
					}
					else if(b == 0xFF)
					{
						appendThis += "51 ";
//						System.out.println("Blue");
					}
				}
				else if(g == 0xC0)
				{
					if(b == 0x00)
					{
						appendThis += "32 ";
//						System.out.println("Dark Green");
					}
					else if(b == 0xC0)
					{
						appendThis += "32 ";
//						System.out.println("Dark Green");
					}
				}
				else if(g == 0xFF)
				{
					if(b == 0x00)
					{
						appendThis += "31 ";
//						System.out.println("Green");
					}
					else if(b == 0xFF)
					{
						appendThis += "41 ";
//						System.out.println("Cyan");
					}
				}
			}
			else if(r == 0xC0)
			{
				if(g == 0x00)
				{
					if(b == 0x00)
					{
						appendThis += "12 ";
//						System.out.println("Dark Red");
					}
					else if(b == 0xC0)
					{
						appendThis += "62 ";
//						System.out.println("Dark Magenta");
					}
				}
				else if(g == 0xC0)
				{
					if(b == 0x00)
					{
						appendThis += "22 ";
//						System.out.println("Dark Yellow");
					}
					else if(b == 0xFF)
					{
						appendThis += "50 ";
//						System.out.println("Light Blue");
					}
				}
				else if(g == 0xFF)
				{
					if(b == 0xC0)
					{
						appendThis += "30 ";
//						System.out.println("Light Green");
					}
					else if(b == 0xFF)
					{
						appendThis += "40 ";
//						System.out.println("Light Cyan");
					}
				}
			}
			else if(r == 0xFF)
			{
				if(g == 0x00)
				{
					if(b == 0x00)
					{
						appendThis += "11 ";
//						System.out.println("Red");
					}
					else if(b == 0xFF)
					{
						appendThis += "61 ";
//						System.out.println("Magenta");
					}
				}
				else if(g == 0xC0)
				{
					if(b == 0xC0)
					{
						appendThis += "10 ";
//						System.out.println("Light Red");
					}
					else if(b == 0xFF)
					{
						appendThis += "60 ";
//						System.out.println("Light Magenta");
					}
				}
				else if(g == 0xFF)
				{
					if(b == 0x00)
					{
						appendThis += "21 ";
//						System.out.println("Yellow");
					}
					else if(b == 0xC0)
					{
						appendThis += "20 ";
//						System.out.println("Light Yellow");
					}
					else if(b == 0xFF)
					{
						appendThis += "01 ";
//						System.out.println("White");
					}
				}
			}			
		}
		
//		System.out.println("here");
//		System.out.println(appendThis);
//		bw.write(appendThis);
		writer.print(appendThis);
		writer.close();
		
	}
	
	// reading in the file and returning it as a 2d string array
	public static String[][] readFile(String s) throws FileNotFoundException
	{
		
		File infile = new File(s);
		Scanner infileS = new Scanner(infile);
//		System.out.println(infileS.nextLine());

		Scanner s1 = new Scanner(infile);
		Scanner s2 = new Scanner(infile);

		String readLines;

		totRow = 0;
		totCol = 0;
		while(s1.hasNextLine())
		{
			readLines = s1.nextLine();
			if(readLines.trim().isEmpty())
				break;

			totRow++;
		}

		String[] wholeLine = new String[totRow];
		for(int i = 0; i < totRow; i++)
		{
			wholeLine[i] = s2.nextLine();
			String[] brokenLine = wholeLine[i].split(" ");
			totCol = (totCol > brokenLine.length) ? totCol : brokenLine.length;
		}


		String[][] board = new String[totRow][totCol];
		for(int i = 0; i < totRow; i++)
		{
			for(int j = 0; j < totCol; j++)
			{
				String[] brokenLine = wholeLine[i].split(" ");
				board[i][j] = brokenLine[j];
			}
		}

		s1.close();
		s2.close();

		return board;
	}
	
	// by now we've read in the file and pass it in as board
	public static void readBoard(String[][] board)
	{

		boolean[][] visited = new boolean[totRow][totCol];
		for(int i = 0; i < totRow; i++)
			for(int j = 0; j < totCol; j++)
				visited[i][j] = false;

		int nextRow = 0;
		int nextCol = 0;
		
		Codel[] codels = new Codel[2];
	
		// initiate the very first Codel
		int[] f = {nextRow, nextCol};
		codels[0] = new Codel(f, board);
		int initSize = 1 + findSizeCodel(board, visited, codels[0], f[0], f[1]);
		codels[0].size = initSize;
		
		codels[0].printCodel();

		// the program will end on it's own (hypothetically)
		boolean end = false;
		while(!end)
		{
			// get the coords of the next Codel
			int[] nextBlock = getNextBlock(board, codels[0], 0);
			nextRow = nextBlock[0];
			nextCol = nextBlock[1];
			
			int[] g = {nextRow, nextCol};
			
			// initiate the newest Codel
			codels[1] = new Codel(g, board);
			initSize = 1+findSizeCodel(board, visited, codels[1], g[0], g[1]);
			codels[1].size = initSize;
			codels[1].printCodel();
			
			// perform the command given by the two Codel's
			getCommand(codels[0], codels[1]);

			// shift the new Codel to the old one's spot
			codels[0] = codels[1];
			
			// if I want to print the stack for debuggin
			System.out.println(Arrays.toString(stack.toArray()));
			
		}
	}

	// get the size of the codel being input
	public static int findSizeCodel(String[][] board, boolean[][] visited, Codel c, int a, int b)
	{
		// this codel is uninitiated
		if(c.rightTop[0] == -1)
		{
			setCorners(c, a, b);
		}
		
		visited[a][b] = true;

		int[][] moves = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };
		int newA = 0, newB = 0;
		int count = 0;
		
		// moving in 4 cardinal directions
		for(int m = 0; m < 4; m++)
		{
			newA = a + moves[m][0];
			newB = b + moves[m][1];

			// skip this direction if out of bounds
			if(!inBounds(newA, newB))
				continue;

			// skip this direction if already visited
			if(visited[newA][newB])
				continue;
			
			visited[newA][newB] = true;
			
			String s1 = c.colorVal;
			String s2 = board[newA][newB];
			
			if(!s1.equals(s2))
				continue;
			
			setCorners(c, newA, newB);
			
			count++;
			int num = findSizeCodel(board, visited, c, newA, newB);
			count += num;
		}
		
//		c.printYesBoard(totRow, totCol);
		if(inBounds(newA, newB))
			return count + findSizeCodel(board, visited, c, newA, newB);

		return count;
	}

	// Here, col1 represents the last color, col2 is the newest color
	public static void getCommand(Codel cod1, Codel cod2)
	{

		String col1 = cod1.colorVal;
		String col2 = cod2.colorVal;

		int hueChange = Character.getNumericValue(col2.charAt(0)) - Character.getNumericValue(col1.charAt(0));
		int lightChange = Character.getNumericValue(col2.charAt(1)) - Character.getNumericValue(col1.charAt(1));

		if(hueChange < 0)
			hueChange += 6;
		if(lightChange < 0)
			lightChange += 3;
		
//		lightChange = correctMod(lightChange, 3);
//		hueChange = correctMod(lightChange, 6);
		
		int val1, val2;
		
		System.out.println("hue: " + hueChange);
		System.out.println("light: " + lightChange);

	try {
		switch(hueChange)
		{
			case 0:
				switch(lightChange)
				{
					case 0:
						return;
					case 1:	// push
//						System.out.println("pushing " + cod1.size);
						stack.push(cod1.size);
//						System.out.println(cod1.size);
						return;
					case 2: // pop
						System.out.println("pop");
						stack.pop();
						
						return;
				}
			case 1:
				switch(lightChange)
				{
					case 0: // add 
//						System.out.println("add");
						val2 = stack.pop();
						val1 = stack.pop();
						stack.push(val1 + val2);
						return;
					case 1: // subtract
//						System.out.println("sub");
						val2 = stack.pop();
						val1 = stack.pop();
						stack.push(val1 - val2);
						return;
					case 2: // multiply
//						System.out.println("multiply");
						val2 = stack.pop();
						val1 = stack.pop();
						stack.push(val1 * val2);
						return;
				}
			case 2:
				switch(lightChange)
				{
					case 0: // divide
						val2 = stack.pop();
						val1 = stack.pop();
						stack.push(val1 / val2);
						return;
					case 1: // mod 
						val2 = stack.pop();
						val1 = stack.pop();
						stack.push(correctMod(val1, val2));
						return;
					case 2: // not
						val2 = stack.pop();
						if(val2 == 0)
							stack.push(1);
						else
							stack.push(0);
						return;
				}
			case 3:
				switch(lightChange)
				{
					case 0: // greater
						val2 = stack.pop();
						val1 = stack.pop();
						if(val1 > val2)
							stack.push(1);
						else
							stack.push(0);
						return;
					case 1: // pointer
						val2 = stack.pop();
						rotateDP(val2);
						return;
					case 2: // switch
						val2 = stack.pop();
						rotateCC(val2);
						return;
				}
			case 4:
				switch(lightChange)
				{
					case 0:	// duplicate
						val2 = stack.pop();
						stack.push(val2);
						stack.push(val2);
						return;
					case 1: // roll
						int size = stack.size();
						val2 = stack.pop();
						val1 = stack.pop();
						while(val2 > 0)
						{
							int rollNum = stack.pop();
							int[] vals = new int[size];
							for(int i = 0; i < val1; i++)
								vals[i] = stack.pop();
							stack.push(rollNum);
							for(int i = val1 - 1; i >= 0; i--)
								stack.push(vals[i]);
							val2--;
						}
						return;
					case 2: // inNum
						System.out.println("in num");
						Scanner s = new Scanner(System.in);
						int i = s.nextInt();
						stack.push(i);
						return;
				}
			case 5:
				switch(lightChange)
				{
					case 0: // inChar
						System.out.println("in char");
						Scanner s = new Scanner(System.in);
						char i = s.next().charAt(0);
						int j = i;
						stack.push(j);
						return;
					case 1: // outNum
						System.out.println("out num");
						int k = stack.pop();
						System.out.print(k);
						return;
					case 2: // outChar
//						System.out.println("out char");
						int l = stack.pop();
						char m = (char) l;
						System.out.println(m);
						return;
				}
		}
	} catch(EmptyStackException e) {	}
	
		return;
	}
	
	// newX = row [0], newY = column [1]
	public static void setCorners(Codel c, int newX, int newY)
	{
//		System.out.println("Testing new corner at " + newX + ", " + newY);
		
		// potential for new right column value
		if(newY >= c.rightTop[1] || c.rightTop[1] == -1)
		{
			// if further right
			if(newY > c.rightTop[1] || c.rightTop[1] == -1) 
			{
//				System.out.println("new right-most column");
				c.rightTop[1] = newY;
				c.rightTop[0] = newX;
			}
			// if further up
			if(newX < c.rightTop[0] || c.rightTop[0] == -1)
			{
//				System.out.println("new right-most row");
				c.rightTop[1] = newY;
				c.rightTop[0] = newX;
			}
			// if further right
			if(newY > c.rightBottom[1] || c.rightBottom[1] == -1)
			{
				c.rightBottom[1] = newY;
				c.rightBottom[0] = newX;
			}
			// if further down
			if(newX > c.rightBottom[0] || c.rightBottom[0] == -1)
			{
				c.rightBottom[1] = newY;
				c.rightBottom[0] = newX;
			}
		}

		// potential for new bottom row value
		if(newX >= c.bottomRight[0] || c.bottomRight[0] == -1)
		{
			// if further down
			if(newX > c.bottomRight[0] || c.bottomRight[0] == -1)
			{
//				System.out.println("new bottom-most row");
				c.bottomRight[0] = newX;
				c.bottomRight[1] = newY;
			}
			// if further right
			if(newY > c.bottomRight[1] || c.bottomRight[1] == -1)
			{
//				System.out.println("new bottom-most column");
				c.bottomRight[1] = newY;
				c.bottomRight[0] = newX;
			}
			// if further down
			if(newX > c.bottomLeft[1] || c.bottomLeft[1] == -1)
			{
				c.bottomLeft[1] = newY;
				c.bottomLeft[0] = newX;
			}
			// if further left
			if(newY < c.bottomLeft[1] || c.bottomLeft[1] == -1 || c.bottomLeft[0] == -1)
			{
				c.bottomLeft[1] = newY;
				c.bottomLeft[0] = newX;
			}
		}
		
		// potential for new left column value
		if(newY <= c.leftBottom[1] || c.leftBottom[1] == -1)
		{
			// if further left
			if(newY < c.leftBottom[1] || c.leftBottom[1] == -1)
			{
//				System.out.println("new left-most column");
				c.leftBottom[1] = newY;
				c.leftBottom[0] = newX;
			}
			// if further down
			if(newX > c.leftBottom[0] || c.leftBottom[0] == -1)
			{
//				System.out.println("new left-most row");
				c.leftBottom[1] = newY;
				c.leftBottom[0] = newX;
			}
			// if further left
			if(newY < c.leftTop[0] || c.leftTop[0] == -1)
			{
				c.leftTop[0] = newX;
				c.leftTop[1] = newY;
			}
			// if further up
			if(newX < c.leftTop[0] || c.leftTop[0] == -1)
			{
				c.leftTop[0] = newX;
				c.leftTop[1] = newY;
			}
		}

		// potential for upper-most row
		if(newX <= c.topLeft[0] || c.topLeft[0] == -1)
		{
			// if further up
			if(newX < c.topLeft[0] || c.topLeft[0] == -1)
			{
//				System.out.println("new top-most row");
				c.topLeft[0] = newX;
				c.topLeft[1] = newY;
			}	
			// if further left
			if(newY < c.topLeft[1] || c.topLeft[1] == -1)
			{
//				System.out.println("new top-most column");
				c.topLeft[1] = newY;
				c.topLeft[0] = newX;
			}
			// if further up
			if(newX < c.topRight[0] || c.topRight[1] == -1)
			{
				c.topRight[0] = newX;
				c.topRight[1] = newY;
			}
			// if further right
			if(newY > c.topRight[1] || c.topRight[1] == -1)
			{
				c.topRight[1] = newY;
				c.topRight[0] = newX;
			}
		}
	}

	// return whether the point will be in bounds
	public static boolean inBounds(int i, int j)
	{
		return ((i >= 0) && (i < totRow) && (j >= 0) && (j < totCol));
	}


	// Java can't do modulo arithmetic correctly, so I made this
	public static int correctMod(int dividend, int divisor)
	{
		while(dividend < 0)
			dividend += divisor;

		return dividend % divisor;
	}
	
	public static void rotateDP(int val)
	{
		lastRotate = 0;
		if(val == 0)
			return;
		
		if(val > 0)
		{
			while(val > 0)
			{
				if(dp.equals("right"))
					dp = "down";
				else if(dp.equals("down"))
					dp = "left";
				else if(dp.equals("left"))
					dp = "up";
				else
					dp = "right";
				val--;
			}
		}
		if(val < 0)
		{
			while(val < 0)
			{
				if(dp.equals("right"))
					dp = "up";
				else if(dp.equals("up"))
					dp = "left";
				else if(dp.equals("left"))
					dp = "down";
				else
					dp = "right";
				val++;
			}
		}
	}
	
	public static void rotateCC(int val)
	{
		lastRotate = 1;
		if(correctMod(val, 2) == 2)
			return;
		else
			if(cc.equals("right"))
				cc = "left";
			else
				cc = "right";
	}
	
	// we're getting the newest codel, from the old one, c
	public static int[] getNextBlock(String[][] board, Codel c, int attempt)
	{
		
		int nextBlock[] = new int[2];
//		c.printCodel();
		
		// we've tried every orientation and can't find a new Codel
		if(attempt > 6)
		{
			System.out.println("DONE");
			System.exit(0);
		}
//		System.out.println("Attempt: " + attempt);
		
//		System.out.println("DP: " + dp + "   CC: " + cc);
		
		int nextRow = 0, nextCol = 0;
		switch(dp)
		{
			// if we're going right
			case "right":
				switch(cc)
				{
					// we want to start from the right-top
					case "left":
						nextRow = c.rightTop[0];
						nextCol = c.rightTop[1];
						break;
					// we want to start from the right-bottom
					case "right":
						nextRow = c.rightBottom[0];
						nextCol = c.rightBottom[1];
						break;
				}
				nextCol++;
				
				// theoretical, do this for all dp directions
				// just moves through white space
//				while(Integer.parseInt(board[nextRow][nextCol]) == 1)
//				{
//					nextCol++;
//				}
				if(!inBounds(nextRow, nextCol) || Integer.parseInt(board[nextRow][nextCol]) == 0)
				{
					if(attempt % 2 == 0)
						rotateCC(1);
					else
						rotateDP(1);
					
					nextCol--;
					return getNextBlock(board, c, attempt + 1);
				}
				break;
			case "down":
				switch(cc)
				{
					case "left":
						nextRow = c.bottomRight[0];
						nextCol = c.bottomRight[1];
						break;
					case "right":
						nextRow = c.bottomLeft[0];
						nextCol = c.bottomLeft[1];
						break;
				}
//				System.out.println("Starting at " + nextRow + ", " + nextCol);
				nextRow++;
				if(!inBounds(nextRow, nextCol) || Integer.parseInt(board[nextRow][nextCol]) == 0)
				{
					if(attempt % 2 == 0)
						rotateCC(1);
					else
						rotateDP(1);
					nextRow--;
					return getNextBlock(board, c, attempt + 1);
					
				}
				break;
			case "left":
				switch(cc)
				{
					case "left":
						nextRow = c.leftBottom[0];
						nextCol = c.leftBottom[1];
						break;
					case "right":
						nextRow = c.leftTop[0];
						nextCol = c.leftTop[1];
						break;
				}
//				System.out.println("Starting at " + nextRow + ", " + nextCol);
				nextCol--;
				if(!inBounds(nextRow, nextCol) || Integer.parseInt(board[nextRow][nextCol]) == 0)
				{
					if(attempt % 2 == 0)
						rotateCC(1);
					else
						rotateDP(1);
					
					nextCol++;
					return getNextBlock(board, c, attempt + 1);
				}
				break;
			case "up":
				switch(cc)
				{
					case "left":
						nextRow = c.topLeft[0];
						nextCol = c.topLeft[1];
						break;
					case "right":
						nextRow = c.topRight[0];
						nextCol = c.topRight[1];
						break;
				}
//				System.out.println("Starting at " + nextRow + ", " + nextCol);
				nextRow--;
				if(!inBounds(nextRow, nextCol) || Integer.parseInt(board[nextRow][nextCol]) == 0)
				{
					if(attempt % 2 == 0)
						rotateCC(1);
					else
						rotateDP(1);
					nextRow++;
					return getNextBlock(board, c, attempt + 1);
				}
				break;
		}

		nextBlock[0] = nextRow;
		nextBlock[1] = nextCol;
		System.out.println("Continuing from " + nextBlock[0] + ", " + nextBlock[1]);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		String curVal = c.colorVal;

		System.out.println("Returning");
		return nextBlock;
	}
}
