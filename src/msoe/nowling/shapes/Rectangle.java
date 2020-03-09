/*
 * Course:     CS 1011 - 71
 * Fall 2019
 * File header contains class Rectangle
 * Name:       fassg
 * Created:    12/3/2019
 */
package msoe.nowling.shapes;

/**
 * Course: CS 1011 - 71
 * Fall 2019
 * Rectangle purpose: Rectangle class for in class exercise
 *
 * @author fassg
 * @version created on 12/3/2019 at 9:21 AM
 */
public class Rectangle implements Shape {
    private double height;
    private double width;
    private double upperLeftX;
    private double upperLeftY;
    private String color;
    public Rectangle(String color, double height, double width, double upperLeftX, double upperLeftY){
        this.color = color;
        this.height = height;
        this.width = width;
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
    }
    public double area(){
        return width * height;
    }
    public double perimeter(){
        return 2 * width + 2 * height;
    }
    public boolean isSquare(){
        if(width == height){
            return true;
        }
        return false;
    }
    public String getColor(){
        return color;
    }
    public void move(double x, double y){
        upperLeftY += y;
        upperLeftX += x;
    }
}
