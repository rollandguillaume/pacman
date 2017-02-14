import java.awt.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author launay
 * @version 2017.01.01
 */

public class Triangle extends Figure
{

    /**
     * Create a new triangle.
     * 
     * @param width the triangle initial width
     * @param height the triangle initial height
     * @param x the triangle initial x location
     * @param y the triangle initial y location
     * @param color the triangle initial color.
     * 
     * @pre width >= 0 && height >= 0 
     * @pre color.equals("white") || color.equals("black") || color.equals("red") || color.equals("blue") || color.equals("yellow") || color.equals("green")
     */
    public Triangle(int width, int height, int x, int y, String color)
    {
        super(width, height, x, y, color);
    }

    /**
     * Draw the triangle with current specifications on screen.
     */
    protected void draw()
    {
        Canvas canvas = Canvas.getCanvas();
        int[] xpoints = { getX(), getX() + (getWidth()/2), getX() - (getWidth()/2) };
        int[] ypoints = { getY(), getY() + getHeight(), getY() + getHeight() };
        canvas.draw(this, getColor(), new Polygon(xpoints, ypoints, 3));
    }
}
