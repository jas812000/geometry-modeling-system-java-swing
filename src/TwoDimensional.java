/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * This "TwoDimensional" class is a subclass of "Shape" and sets the
 * "numberOfDimensions" to 2 using the constructor of the superclass. It
 * includes an abstract method "getArea()" that must be implemented by its
 * subclasses.
 *
 */

//package CMSC_335_Project2;

// declares a public abstract class that extends the "Shape" class
public abstract class TwoDimensional extends Shape {

    // declares a public constructor
    public TwoDimensional(){

        super(2);

    } // end constructor

    // method intended to be overridden by concrete subclasses
    public abstract double getArea();

}// end TwoDimensional method
