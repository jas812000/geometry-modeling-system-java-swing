/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * This "Circle" class is a subclass of the "TwoDimensional" class. It introduces a
 * private instance variable "radius" to store the radius of the circle and
 * provides a constructor to initialize it. The "Circle" class also implements
 * the abstract method "getArea()" to calculate and return the area of the
 * circle.
 *
 */

//package CMSC_335_Project2;

// declares a public class that extends its parent class
public class Circle extends TwoDimensional {

    private final double radius;

    // declares a public constructor with one parameter
    public Circle(double radius) {

        this.radius = radius;

    } // end constructor

    // declares a public method; retrieves and returns the value stored
    // in the "radius"
    public double getRadius(){

        return radius;

    }// end getRadius method

    // declares a public method which calculates and returns a value
    // overrides inherited method
    @Override
    public double getArea(){

        return Math.PI * Math.pow(radius, 2);

    }// end getArea method

} // end Circle class
