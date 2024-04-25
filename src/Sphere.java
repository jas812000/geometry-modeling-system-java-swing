/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * The "Sphere" class is a subclass of the "ThreeDimensional" class.
 * It represents a sphere in a three-dimensional space. The class includes
 * a private instance variable called "radius" which stores the radius of
 * the sphere. The program provides a constructor that takes the radius as a
 * parameter and assigns it to the "radius" instance variable. The "Sphere"
 * class includes a method called "getRadius()" which returns
 * the value of the "radius" instance variable. The class overrides the
 * abstract method "getVolume()" inherited from the "ThreeDimensional" class.
 * The overridden method calculates and returns the volume of the sphere.
 *
 */

//package CMSC_335_Project2;

// declares a public class that extends its parent class
public class Sphere extends ThreeDimensional {

    private final double radius;

    // declares a public constructor with one parameter
    public Sphere(double radius) {

        this.radius = radius;

    } // end constructor

    // declares a public method; retrieves and returns the value stored
    // in the "radius"
    public double getRadius(){

        return radius;

    }// end getLength method

    // declares a public method which calculates and returns a value
    // overrides inherited method
    @Override
    public double getVolume() {

        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);

    } // end getArea method

} // end Sphere class
