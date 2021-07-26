package mooc.vandy.java4android.shapes.ui;

import mooc.vandy.java4android.shapes.logic.Shapes;

/**
 * Define the methods that the User Interface [MainActivity] will
 * implement.  You can ignore this interface for now since interfaces
 * will be covered later in this course.
 */
public interface OutputInterface {
    /**
     * Get the shape specified by the user.
     */
    Shapes getShape();

    /**
     * Get the length.
     */
    double getLength();

    /**
     * Get the width.
     */
    double getWidth();

    /**
     * Get the height.
     */
    double getHeight();

    /**
     * Get the radius.
     */
    double getRadius();

    /**
     * Reset the on-screen output (EditText box).
     */
    void resetText();

    /**
     * Prints the string representation of the passed Java Object or primitive
     * type followed by a new line.
     *
     * @param obj a String, int, double, float, boolean or any Java Object.
     */
    default void println(Object obj) {
        print(obj);
        println();
    }

    /**
     * Print a newline.
     */
    default void println() {
        print('\n');
    }

    /**
     * Prints the string representation of the passed Java Object or primitive type.
     *
     * @param obj a String, int, double, float, boolean or any Java Object.
     */
    void print(Object obj);
}
