/*
 * Course:     CS 1011 - 71
 * Fall 2019
 * File header contains class Hexagon
 * Name:       fassg
 * Created:    12/3/2019
 */
package msoe.nowling.shapes;

/**
 * Course: CS 1011 - 71
 * Fall 2019
 * Hexagon purpose: Hexagon class for in class demo
 *
 * @author fassg
 * @version created on 12/3/2019 at 9:30 AM
 */
public class Hexagon implements Shape{
    private double sideLength;
    private double centerX;
    private double centerY;
    private String color;
    public Hexagon(double sideLength, double centerX, double centerY, String color){
        this.sideLength = sideLength;
        this.centerX = centerX;
        this.centerY = centerY;
        this.color = color;
    }
    public double area(){
        return 3 * Math.sqrt(3) * sideLength * sideLength / 2;
    }
    public double perimeter(){
        return 6 * sideLength;
    }
    public String getColor(){
        return color;
    }
    public void move(double x, double y){
        centerY += y;
        centerX += x;
    }
}
