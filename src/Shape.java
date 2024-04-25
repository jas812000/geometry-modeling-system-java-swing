/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * This the parent class that serves as as the base class, providing the
 * basic framework for representing shapes in a two- and three- dimensional
 * space. It has a private instance variable "numberOfDimensions" to store
 * the number of dimensions of the shape. It provides a constructor to
 * initialize the "numberOfDimensions" variable.
 *
 */

//package CMSC_335_Project2;

// declares a public class
public class Shape {

    private final int numberOfDimensions;

    // declares a public constructor
    public Shape(int numberOfDimensions){

        this.numberOfDimensions = numberOfDimensions;

    } // end constructor

    // declares a public method
    public int getNumberOfDimensions(){

        return numberOfDimensions;

    }// end getNumberOfDimensions method

} // end Shape class
