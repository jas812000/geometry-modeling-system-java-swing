/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program demonstrates inheritance hierarchy of a series of related
 * classes with a "Shape" theme, satisfying the is-a and has-a relationships.
 *
 * The "Torus" class is is a subclass of the "ThreeDimensional" class. It
 * represents a torus in a three-dimensional space. The class includes two
 * private instance variables: "major_radius" and "minor_radius," which store
 * the major radius and minor radius of the torus, respectively. The program
 * provides a constructor that takes the major radius and minor radius as
 * parameters and assigns them to the respective instance variables. The
 * "Torus" class includes methods to retrieve the major radius and minor
 * radius of the torus: "getMajorRadius()" and "getMinorRadius()". These
 * methods return the values of the major radius and minor radius instance
 * variables. The class overrides the abstract method "getVolume()" inherited
 * from the "ThreeDimensional" class. The overridden method calculates and
 * returns the volume of the torus.
 *
 */

//package CMSC_335_Project2;

// declares a public class that extends its parent class
public class Torus extends ThreeDimensional {

    private final double major_radius;
    private final double minor_radius;

    // declares a public constructor with two parameters
    public Torus(double major_radius, double minor_radius) {

        this.major_radius = major_radius;
        this.minor_radius = minor_radius;

    }// end constructor

    // declares a public method; retrieves and returns the value stored
    // in the "major_radius"
    public double getMajorRadius(){

        return major_radius;

    }// end getMajorRadius method

    // declares a public method; retrieves and returns the value stored
    // in the "minor_radius"
    public double getMinorRadius(){

        return minor_radius;

    }// end getMinorRadius method

    // declares a public method which calculates and returns a value
    // overrides inherited method
    @Override
    public double getVolume() {

        return (Math.PI * Math.pow(minor_radius, 2)) *
                ( 2 * Math.PI * major_radius);

    } // end calculateVolumeTorus method

} // end Torus class
