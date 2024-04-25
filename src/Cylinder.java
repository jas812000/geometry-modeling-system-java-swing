/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * The "Cylinder" class is a subclass of the "ThreeDimensional" class. It
 * represents a cylinder in a three-dimensional space. The class includes two
 * private instance variables: "radius" and "height," which store the radius
 * of the cylinder's base and the height of the cylinder, respectively. The
 * program provides a constructor that takes the radius and height as
 * parameters and assigns them to the respective instance variables. The
 * "Cylinder" class includes methods to retrieve the radius and height of
 * the cylinder: "getRadius()" and "getHeight()". These methods return the
 * values of the radius and height instance variables. The class
 * overrides the abstract method "getVolume()" inherited from the
 * "ThreeDimensional" class. The overridden method calculates and returns the
 * volume of the cylinder.
 *
 */

//package CMSC_335_Project2;

// declares a public class that extends its parent class
public class Cylinder extends ThreeDimensional {

    private final double radius;
    private final double height;

    // declares a public constructor with two parameters
    public Cylinder(double radius, double height) {

        this.radius = radius;
        this.height = height;

    } // end constructor

    // declares a public method; retrieves and returns the value stored
    // in the "radius"
    public double getRadius(){

        return radius;

    }// end getRadius method

    // declares a public method; retrieves and returns the value stored
    // in the "height"
    public double getHeight(){

        return height;

    }// end getHeight method

    // declares a public method which calculates and returns a value
    // overrides inherited method
    @Override
    public double getVolume() {

        return (Math.PI * Math.pow(radius, 2) * height);

    } // end calculateVolumeCylinder method

} // end Cylinder class
