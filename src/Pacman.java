
/**
 * Write a description of class Pacman here.
 *
 * @author launay
 * @version 2017.01.01
 */
public class Pacman extends Figure {

	private Figure[] figures;

	/**
     * Create a new Figure_Pacman.
     *
     * @pre size >= 0
     */
	public Pacman(int size, int x, int y, String color) {
		super(size, size, x, y, color);
		this.figures = new Figure[2];
		this.figures[0] = new Circle(size,x,y,color); // corps

	}

	/**
   * Draw the figure with current specifications on screen.
   */
  protected void draw() {
    for (Figure figure : this.figures) {
			if (figure != null) {
				figure.draw();
			}
    }
  }

	public void move () {
		this.move(10, 10);
		System.out.println("\tmove");
	}


}
