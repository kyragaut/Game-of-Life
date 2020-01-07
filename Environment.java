/**
 *
 * @author Kyra GauthierDickey
 * Project 1
 * January 12, 2018
 * This part of the program is responsible for running the simulation
 * 
 */

import java.util.Scanner;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import edu.princeton.cs.introcs.StdDraw;


public class Environment {
	
	private final int CANVAS_SIZE = 500;
	private int cellHeight;
	private int cellWidth;
	private int numRow;
	private int numCol;
	private Cell[][] cells;
	
	//methods
	//constructor:
	public Environment(String fileName)
	{
		Scanner myScanner = null;
		//open file
		try
		{
			myScanner = new Scanner (new FileInputStream(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
			System.exit(0);
		}
		//reading in first two numbers, rows and columns 
		numRow = myScanner.nextInt();
		numCol = myScanner.nextInt();
		
		cells = new Cell[numRow][numCol];
		//reading in the rest of the information
		for(int row = 0; row < numRow; ++row)
		{
			for(int col = 0; col < numCol; ++col)
			{
				if(myScanner.nextInt() == 1)
				{
					cells[row][col] = new Cell(true);
				}
				else
				{
					cells[row][col] = new Cell(false);
				}
			}
		}
		System.out.println("Environment is: \n" + this);
		
		//drawing the grid
		StdDraw.setCanvasSize(CANVAS_SIZE, CANVAS_SIZE);
		StdDraw.setScale(0, CANVAS_SIZE);
		//calculating size of my cell rectangles based on the canvas size
		cellWidth = CANVAS_SIZE / numRow;
		cellHeight = CANVAS_SIZE / numCol;
		StdDraw.clear(Color.WHITE);
		StdDraw.enableDoubleBuffering();
		
		
	}
	
	//drawBoard method
	private void drawBoard()
	{
		//creating variables that allow me to place my rectangles properly down my canvas based on the cellWidth and cellHeight
		double width =  cellWidth/2;
		double height = CANVAS_SIZE - cellHeight/2;
		
		//going through my array cells[][] 
		for(int row = 0; row < cells.length; ++row)
		{
			for(int col = 0; col < cells[row].length; ++col)
			{
				//I use red for the occupied cells
				if(cells[row][col].getOccupied() == true)
				{
					StdDraw.setPenColor(Color.RED);
				}
				
				//I use white for my non-occupied cells
				else
				{
					StdDraw.setPenColor(Color.WHITE);
				}
				//drawing the rectangle
				StdDraw.filledRectangle(width, height, cellWidth/2, cellHeight/2);
				//incrementing cellWidth to place the next rectangle properly
				width += cellWidth;
			}
			//setting width equal to 0 for the next row so it'll start on the very left again
			width = cellWidth/2;
			//decrementing height so I move the rectangles down the canvas
			height -= cellHeight;
			
		}
		
	}
	
	//getCell method
	public Cell getCell(int row, int col)
	{
		return cells[row][col];
	}
	
	//getTopNeighbor method
	public int getTopNeighbor(int row, int col)
	{
		if(row != 0 && getCell(row-1, col).getOccupied() == true)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//getBottomNeighbor method
	public int getBottomNeighbor(int row, int col)
	{
		if(row != cells.length-1 && getCell(row+1, col).getOccupied() == true)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//get Top right diagonal Neighbor method
	public int getTopRDiagonalNeighbor(int row, int col)
	{
		if(row != 0 && col != cells[row].length-1 && getCell(row-1, col+1).getOccupied() == true)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//get Top left diagonal Neighbor method
	public int getTopLDiagonalNeighbor(int row, int col)
	{
		if(row != 0 && col != 0 && getCell(row-1, col-1).getOccupied() == true)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//get Bottom right diagonal Neighbor method
	public int getBottomRDiagonalNeighbor(int row, int col)
	{
		if(row != cells.length-1 && col != cells[row].length-1 && getCell(row+1, col+1).getOccupied() == true)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//get Bottom left diagonal Neighbor method
	public int getBottomLDiagonalNeighbor(int row, int col)
	{
		if(row != cells.length-1 && col != 0 && getCell(row+1, col-1).getOccupied() == true)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//get left neighbor method
	public int getLeftNeighbor(int row, int col)
	{
		if(col != 0 && getCell(row, col-1).getOccupied() == true)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//get right neighbor method
	public int getRightNeighbor(int row, int col)
	{
		if(col != cells[row].length-1 && getCell(row, col+1).getOccupied() == true)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//method to count neighbors of a given cell
	//I use all my getNeighbor methods to calculate how many of the cell's neighbors are alive
	public int getNeighborCount(int row, int col)
	{
		int sum = 0;
		sum += getTopNeighbor(row, col);
		sum += getBottomNeighbor(row, col);
		sum += getTopRDiagonalNeighbor(row, col);
		sum += getTopLDiagonalNeighbor(row, col);
		sum += getBottomRDiagonalNeighbor(row, col);
		sum += getBottomLDiagonalNeighbor(row, col);
		sum += getLeftNeighbor(row, col);
		sum += getRightNeighbor(row, col);
		return sum;
	}
	
	//calculating the next generation
	public Cell[][] nextGeneration()
	{
		Cell[][] nextGen = new Cell[numRow][numCol];
		for(int row = 0; row < nextGen.length; ++row)
		{
			for(int col = 0; col < nextGen[row].length; ++col)
			{
				//Any living creature (occupied cell) with fewer than two live neighbors dies of loneliness
				if(getNeighborCount(row, col) < 2)
				{
					nextGen[row][col] = new Cell(false);
				}
				//Any living creature with two or three live neighbors lives on to the next generation.
				else if(getCell(row, col).getOccupied() == true && (getNeighborCount(row, col) == 2 || getNeighborCount(row, col) == 3))
				{
					nextGen[row][col] = new Cell(true);
				}
				//Any living creature with more than three live neighbors dies of overcrowding.
				else if(getCell(row, col).getOccupied() == true && (getNeighborCount(row, col) > 3))
				{
					nextGen[row][col] = new Cell(false);
				}
				//Any empty cell with exactly three live neighbors becomes an occupied cell. This represents a birth.
				else if(getCell(row, col).getOccupied() == false && (getNeighborCount(row, col) == 3))
				{
					nextGen[row][col] = new Cell(true);
				}
				else
				{
					nextGen[row][col] = new Cell(false);
				}
			}
		}
		return nextGen;
	}
	
	
	//runSimulation method
	public void runSimulation()
	{
		drawBoard();
		StdDraw.show();
		while(true)
		{
			cells = nextGeneration();
			drawBoard();
			StdDraw.show();
			StdDraw.pause(300);
		}
	}
	
	//printing out my array to check it
	public String toString()
	{
		String cellString = "";
		for(int row = 0; row < cells.length; ++row)
		{
			for(int col = 0; col < cells[row].length; ++col)
			{
				cellString += cells[row][col] + " ";
				
			}
			cellString += "\n";
		}
		return cellString;
	}
	
	//
	
	

}
