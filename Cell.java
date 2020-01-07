/**
 *
 * @author Kyra GauthierDickey
 * Project 1
 * January 12, 2018
 * This part of the program holds the individual cells, which can be occupied or not
 * 
 */

public class Cell {
	
	private boolean occupied;
	
	//constructor
	public Cell(boolean occu)
	{
		occupied = occu;
	}
	
	//getter
	public boolean getOccupied()
	{
		return occupied;
	}
	
	//setter
	public void setOccupied(boolean occu)
	{
		occupied = occu;
	}
	
	public String toString()
	{
		if(occupied == true)
		{
			return "1";
		}
		else
		{
			return "0";
		}
	}
}
