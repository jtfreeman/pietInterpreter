package com.util;
import com.entity.Coordinate;

public class Codel {
	private Integer size;
	private Color color;
	private String colorVal;
	private String colorName;

	// eight types of corner
	private Coordinate rightTop = new Coordinate();	
	private Coordinate rightBottom = new Coordinate();
	private Coordinate bottomRight = new Coordinate();	
	private Coordinate bottomLeft = new Coordinate();
	private Coordinate leftBottom = new Coordinate();	
	private Coordinate leftTop = new Coordinate();
	private Coordinate topLeft = new Coordinate();	
	private Coordinate topRight = new Coordinate();

	public Codel() {
	}
	
	public Codel(Color color) {
		this.color = color;
	}
	
	public Codel(String[][] board) {
		this.color = codelIntoString(colorVal);
	}

	public Codel(Coordinate coordinate) {
		this.rightTop = coordinate;
		this.rightBottom = coordinate;
		this.bottomRight = coordinate;
		this.bottomLeft = coordinate;
		this.leftBottom = coordinate;
		this.leftTop = coordinate;
		this.topLeft = coordinate;
		this.topRight = coordinate;
	}

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getColorName() {
		return this.color.getName();
	}
	
	public Integer getColorValue() {
		return this.color.getValue();
	}

	public Coordinate getRightTop() {
		return this.rightTop;
	}

	public void setRightTop(Coordinate coordinate) {
		this.rightTop = coordinate;
	}

	public Coordinate getRightBottom() {
		return this.rightBottom;
	}

	public void setRightBottom(Coordinate coordinate) {
		this.rightBottom = coordinate;
	}

	public Coordinate getBottomRight() {
		return this.bottomRight;
	}

	public void setBottomRight(Coordinate coordinate) {
		this.bottomRight = coordinate;
	}

	public Coordinate getBottomLeft() {
		return this.bottomLeft;
	}

	public void setBottomLeft(Coordinate coordinate) {
		this.bottomLeft = coordinate;
	}

	public Coordinate getLeftBottom() {
		return this.leftBottom;
	}

	public void setLeftBottom(Coordinate coordinate) {
		this.leftBottom = coordinate;
	}

	public Coordinate getLeftTop() {
		return this.leftTop;
	}

	public void setLeftTop(Coordinate coordinate) {
		this.leftTop = coordinate;
	}

	public Coordinate getTopLeft() {
		return this.topLeft;
	}

	public void setTopLeft(Coordinate coordinate) {
		this.topLeft = coordinate;
	}

	public Coordinate getTopRight() {
		return this.topRight;
	}

	public void setTopRight(Coordinate coordinate) {
		this.topRight = coordinate;
	}

	public void printCodel(int printLevel) {
		if(printLevel > 3) {
			System.out.println("\tCodel size: " + this.size);
			System.out.println("\tCodel color value: " + this.colorVal);
			System.out.println("\tCodel color: " + this.colorName);
		}
	}

	public void debugCodel() {
		System.out.println("\tCodel's right-most & upper-most block is at " + this.rightTop.getX() + ", " + this.rightTop.getY());
		System.out.println("\tCodel's right-most & lower-most block is at " + this.rightBottom.getX() + ", " + this.rightBottom.getY());
		System.out.println("\tCodel's bottom-most & right-most block is at " + this.bottomRight.getX() + ", " + this.bottomRight.getY());
		System.out.println("\tCodel's bottom-most & left-most block is at " + this.bottomLeft.getX() + ", " + this.bottomLeft.getY());
		System.out.println("\tCodel's left-most & bottom-most block is at " + this.leftBottom.getX() + ", " + this.leftBottom.getY());
		System.out.println("\tCodel's left-most & top-most block is at " + this.leftTop.getX() + ", " + this.leftTop.getY());
		System.out.println("\tCodel's upper-most & left-most block is at " + this.topLeft.getX() + ", " + this.topLeft.getY());
		System.out.println("\tCodel's upper-most & right-most block is at " + this.topRight.getX()+ ", " + this.topRight.getY());
		System.out.println();
	}

	public Color codelIntoString(String val) {
		
		return Enum.valueOf(Color.class, val);
		// switch(val)
		// {
		// 	case "0": return "black";			
		// 	case "1": return "white";
		// 	case "10": return "light red";		
		// 	case "11": return "red";
		// 	case "12": return "dark red";
		// 	case "20": return "light yellow";	
		// 	case "21": return "yellow"; 		
		// 	case "22": return "dark yellow";
		// 	case "30": return "light green";		
		// 	case "31": return "green"; 		
		// 	case "32": return "dark green";
		// 	case "40": return "light cyan";		
		// 	case "41": return "cyan";		
		// 	case "42": return "dark cyan";
		// 	case "50": return "light blue"; 		
		// 	case "51": return "blue";		
		// 	case "52": return "dark blue";
		// 	case "60": return "light magenta";
		// 	case "61": return "magenta";		
		// 	case "62": return "dark magenta";
		// }

		// return "white";

		// red -> yellow -> green -> cyan -> blue -> magenta
		//  11		  21       31      41      51         61

		// black / white
		//	     0       1

	}
}