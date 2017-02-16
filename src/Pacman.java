/**
 * Write a description of class Pacman here.
 *
 * @author maxime
 * @version 2017.01.01
 */
public class Pacman extends ArcCircle {

	private static final String PACMAN_COLOR = "yellow"; // the Pacman default color
	public static final int OUVERTURE_MAX = 40;
	public static final int OUVERTURE_MIN = 10;
	private int ouverture;
	private boolean mouthIsOpen;
	private String dernierePosition;

	/**
     * Create a new Figure_Pacman.
     *
     * @pre size >= 0
     */
	public Pacman(int size, int x, int y) {
		super(size, x, y, PACMAN_COLOR, 0, 360);
		//initialize the direction of pacman
		this.dernierePosition = "LEFT";
		this.ouverture = this.OUVERTURE_MIN;
		this.deplaceOuverture(PacManLauncher.LEFT);
	}


	/**
	* move the pacman and all figures which blend him
	*/
	public void move (String toward) {
		int[] crossMap = this.crossMap(toward);
		this.deplaceOuverture(toward);
		int dx = crossMap[0];
		int dy = crossMap[1];

		this.animateMouth();

		this.move(dx, dy);//move the pacman

	}

	/**
	* check if pacman go out the map
	* return the race of move for pacman
	*
	* @return ret {dx, dy}
	*/
	private int[] crossMap (String toward) {
		int[] ret = new int[2];
		int dx = 0;
		int dy = 0;
		int x = this.getX();
		int y = this.getY();

		int speed = PacManLauncher.SPEED;
		int width = this.getWidth()/4;
		int heightMap = Canvas.HEIGHT;
		int widthMap = Canvas.WIDTH;
		if (toward.equals(PacManLauncher.UP)) {
			//pacman is out the map of a part of his body
			if (y-speed < -width) {
				//so he spawn at the bottom with a part of his body visible
				dy = heightMap;
			} else {
				dy = -speed;
			}
		} else if (toward.equals(PacManLauncher.DOWN)) {
			if (y+speed > heightMap-width) {
				dy = -heightMap;
			} else {
				dy = speed;
			}
		} else if (toward.equals(PacManLauncher.LEFT)) {
			if (x-speed < -width) {
				dx = widthMap;
			} else {
				dx = -speed;
			}
		} else if (toward.equals(PacManLauncher.RIGHT)) {
			if (x+speed > widthMap-width) {
				dx = -widthMap;
			} else {
				dx = speed;
			}
		}

		ret[0] = dx;
		ret[1] = dy;
		return ret;
	}

	public void deplaceOuverture(String direction) {
		if (direction.equals(PacManLauncher.UP)) {
			this.setAngleStart(90-ouverture);
			this.setAngleExtent(-360+2*ouverture);
		} else if (direction.equals(PacManLauncher.LEFT)) {
			this.setAngleStart(180-ouverture);
			this.setAngleExtent(-360+2*ouverture);
		} else if (direction.equals(PacManLauncher.DOWN)) {
			this.setAngleStart(270-ouverture);
			this.setAngleExtent(-360+2*ouverture);
		} else if (direction.equals(PacManLauncher.RIGHT)) {
			this.setAngleStart(-ouverture);
			this.setAngleExtent(-360+2*ouverture);
		}
		this.dernierePosition = direction;
	}

	/**
	*	animation of the mouth of pacman
	*/
	public void animateMouth()
	{
		if(mouthIsOpen) {
			//fermeture
			this.ouverture = this.OUVERTURE_MIN;
		} else {
			//ouverture
			this.ouverture = this.OUVERTURE_MAX;
		}
		this.deplaceOuverture(this.dernierePosition);
		this.mouthIsOpen = !this.mouthIsOpen;
	}

}
