/*
 * Course:     CS 1011 - 71
 * Fall 2019
 * File header contains class Circle
 * Name:       fassg
 * Created:    12/3/2019
 */
package msoe.nowling.shapes;

/**
 * Course: CS 1011 - 71
 * Fall 2019
 * Circle purpose: Circle class for in class exercise
 *
 * @author fassg
 * @version created on 12/3/2019 at 9:20 AM
 */
public class Circle implements Shape {
    private double radius;
    private double centerX;
    private double centerY;
    private String color;
    public Circle(String color, double radius, double centerX, double centerY){
        this.color = color;
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
    }
    public double area(){
        return Math.PI * radius * radius;
    }
    public double perimeter(){
        return Math.PI * diameter();
    }
    public double diameter(){
        return radius * 2;
    }
    public String getColor(){
        return color;
    }
    public void move(double x, double y){
        centerY += y;
        centerX += x;
    }
}
