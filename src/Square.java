/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * The "Square" class is a subclass of the "TwoDimensional" class. It
 * represents a square in a two-dimensional space. The class includes a
 * private instance variable called "side" which stores the length of the
 * square's side. The program provides a constructor that takes the side length
 * as a parameter and assigns it to the "side" instance variable. The "Square"
 * class includes a method called "getSide()" which returns the value of the
 * "side" instance variable. The class overrides the abstract method
 * "getArea()" inherited from the "TwoDimensional" class. The overridden
 * method calculates and returns the area of the square.
 *
 */

//package CMSC_335_Project2;

// declares a public class that extends its parent class
public class Square extends TwoDimensional {

    private final double side;

    // declares a public constructor with one parameter
    public Square(double side) {

        this.side = side;

    } // end constructor

    // declares a public method; retrieves and returns the value stored
    // in the "side"
    public double getSide(){

        return side;

    }// end getRadius method

    // declares a public method which calculates and returns a value
    // overrides inherited method
    @Override
    public double getArea(){

        return Math.pow(side, 2);

    } // end getArea method

} // end Square class
