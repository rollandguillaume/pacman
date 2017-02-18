import java.awt.*;
/**
 * Write a description of class ghost here.
 *
 * @author maxime
 * @version 2017.02.14
 */
public class Ghost extends Entite {

	private Figure[] figures;

	/**
     * Create a new ghost.
     *
     * @pre size >= 0
     * @pre color different of ("white")
     */
	public Ghost(int size, int x, int y, String color) {
		//super(size, size, x, y, color);

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
	*	choisir une direction aleatoire
	*/
	public void move () {
		//POINT D ENTREE DU DEPLACEMENT DU FANTOME

		double ran = Math.random()*4;
		if (ran >= 0 && ran < 1) {
			this.move(PacManLauncher.UP);
		} else if (ran >= 1 && ran < 2) {
			this.move(PacManLauncher.DOWN);
		}  else if (ran >= 2 && ran < 3) {
			this.move(PacManLauncher.RIGHT);
		}  else if (ran >= 3 && ran < 4) {
			this.move(PacManLauncher.LEFT);
		}
	}

	/**
	*	se déplacer dans la direction demandee
	*/
	public void move (String toward) {
		int dx = 0;
		int dy = 0;

		int[] crossMap = this.crossMap(toward);
		dx = crossMap[0];
		dy = crossMap[1];

		crossMap = this.checkColision(toward, dx, dy);
		dx = crossMap[0];
		dy = crossMap[1];

		this.move(dx, dy);//move the pacman
	}

	public void move (int dx, int dy) {
		for (Figure figure : figures) {
        figure.move(dx, dy);
    }
	}

	public int getX () {
		return this.figures[0].getX();
	}

	public int getY () {
		return this.figures[0].getY();
	}

	public int getWidth () {
		return this.figures[0].getWidth();
	}

	/**
	*	les fantomes agissent sur les murs
	* mais pas les gommes
	*/
	public boolean typeCaseToCheck (Figure f) {
		return (f instanceof Wall);
	}

	protected void actionWithGom (Figure[][] map, int i, int j) {
		//no interaction
	}

	/**
   * Draw the figure with current specifications on screen.
   */
  public void draw() {
    for (Figure figure : figures) {
        figure.draw();
    }
  }

}
