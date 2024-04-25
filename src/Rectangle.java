/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * The "Rectangle" class is a subclass of the "TwoDimensional" class. It
 * represents a rectangle in a two-dimensional space. The class includes
 * two private instance variables: "length" and "width," which store the
 * dimensions of the rectangle. Additionally, the "Rectangle" class includes
 * methods to retrieve the length and width of the rectangle: "getLength()"
 * and "getWidth()". These methods return the values of the length and width
 * instance variables. The class overrides the abstract method "getArea()"
 * inherited from the "TwoDimensional" class. The overridden method calculates
 * and returns the area of the rectangle.
 *
 */

//package CMSC_335_Project2;

// declares a public class that extends its parent class
public class Rectangle extends TwoDimensional {

    private final double length;
    private final double width;

    // declares a public constructor with two parameters
    public Rectangle(double length, double width) {

        this.length = length;
        this.width = width;

    } // end constructor

    // declares a public method; retrieves and returns the value stored
    // in the "length"
    public double getLength(){

        return length;

    }// end getLength method

    // declares a public method; retrieves and returns the value stored
    // in the "width"
    public double getWidth(){

        return width;

    }// end getWidth method

    // declares a public method which calculates and returns a value
    // overrides inherited method
    @Override
    public double getArea() {

        return length * width;

    } // end getArea method

} // end Rectangle class
