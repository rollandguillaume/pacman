import java.awt.*;
/**
 * Write a description of class ghost here.
 * 
 * @author maxime
 * @version 2017.02.14
 */
public class Ghost extends Figure {
	private Figure[] figures;
	
	/**
     * Create a new ghost.
     * 
     * @pre size >= 0
     * @pre color different of ("white")
     */
	public Ghost(int size,int x,int y,String color) {
		super(size, size, x, y,color);
		
		int diametrehead=(int)(size);
		int heightbody=(int)(size/2.6);
		int sizeleg=size/5;
		int eyesize=(int)(size/3.5);
		int insideeyesize=eyesize/2;
		
		figures = new Figure[9];
		
		figures[0] = new ArcCircle(size,x,y,color,0,180); // head
		figures[1] = new Rect(diametrehead,heightbody,x,y+diametrehead/2,color); // body
		
		figures[2] = new ArcCircle(sizeleg,x,y+diametrehead/2+heightbody-sizeleg/2,color,180,180); // leg
		figures[3] = new ArcCircle(sizeleg,x+4*sizeleg,y+diametrehead/2+heightbody-sizeleg/2,color,180,180); // leg
		figures[4] = new ArcCircle(sizeleg,x+2*sizeleg,y+diametrehead/2+heightbody-sizeleg/2,color,180,180);//leg
		
		figures[5] = new Circle(eyesize,x+eyesize/2,y+diametrehead/2-eyesize,"white");//eye
		figures[6] = new Circle(eyesize,(int)(x+size-1.5*eyesize),y+diametrehead/2-eyesize,"white");//eye
		figures[7] = new Circle(insideeyesize,x+eyesize/2+insideeyesize/2,y+diametrehead/2-eyesize+insideeyesize/2,"black");//eye
		figures[8] = new Circle(insideeyesize,(int)(x+size-1.5*eyesize+insideeyesize/2),y+diametrehead/2-eyesize+insideeyesize/2,"black");//eye
	}
	
	/**
     * Draw the figure with current specifications on screen.
     */
    protected void draw() {
        for (Figure figure : figures) {
            figure.draw();
        }
    }

    /**
     * Erase the square on screen.
     */
    protected void erase() {
        for (Figure figure : figures) {
            figure.erase();
        }
    }

}
