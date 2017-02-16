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
	private Figure[][] map;

	/**
     * Create a new Figure_Pacman.
     *
     * @pre size >= 0
     */
	public Pacman(int size, int x, int y) {
		super(size, x, y, PACMAN_COLOR, 0, 360);
		//initialize the direction of pacman
		this.dernierePosition = PacManLauncher.LEFT;
		this.ouverture = this.OUVERTURE_MIN;
		this.deplaceOuverture(PacManLauncher.LEFT);

	}

	public void setMap (Figure[][] map) {
		this.map = map;
	}


	/**
	* move the pacman and all figures which blend him
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

		this.deplaceOuverture(toward);
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

	private void deplaceOuverture(String direction) {
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
	*	Check if pacman go in the wall
	*/
	private int[] checkColision (String toward, int dx, int dy) {
		int[] ret = new int[2];

		/*
		//FAIRE optimisation en fonction de la direction choisi !!
		//FAIRE pour eviter de checker toute la map
		//FAIRE connaitre ligne et colonne de pacman et
		//FAIRE en fonction de direction
		//FAIRE les 3 figures devant pacman

		int colonne = this.getX()/PacManLauncher.SIZE_WALL;
		int ligne = this.getY()/PacManLauncher.SIZE_WALL;
		System.out.println(colonne+","+ligne);
		*/

		for (Figure[] l : this.map) {
			for (Figure f : l) {
				if (this.checkOneColision(f, dx, dy)) {
					if (f instanceof Wall) {
						if (toward.equals(PacManLauncher.UP)) {
							//dy<0
							dy = this.getY()-(f.getY()+f.getHeight());
						} else if (toward.equals(PacManLauncher.DOWN)) {
							//dy>0
							dy = (this.getY()+this.getSize())-f.getY();
						} else if (toward.equals(PacManLauncher.LEFT)) {
							//dx<0
							dx = this.getX()-(f.getX()+f.getWidth());
						} else if (toward.equals(PacManLauncher.RIGHT)) {
							//dy>0
							dx = (this.getX()+this.getSize())-f.getX();
						}
					} else if (f instanceof Gomme) {
						//System.out.println("it's a gomme");
						//FAIRE disparaitre la gomme
					}
				}
			}
		}

		ret[0] = dx;
		ret[1] = dy;
		return ret;
	}

	/**
	*	check if is one colision with Figure
	* avec le déplacement qui va etre effectuer
	*
	* @param f the figure
	* @param dx le deplacement x a faire
	* @param dy le deplacement y a faire
	*
	* @pre f is not null and is an instance of Wall
	*/
	private boolean checkOneColision (Figure f, int dx, int dy) {
		boolean ret = false;

		if (f != null) {
			if (f instanceof Wall || f instanceof Gomme) {
				int xf = f.getX();//x de f
				int yf = f.getY();//y de f
				int wf = f.getWidth();//largeur f
				int hf = f.getHeight();//hauteur f

				int xt = this.getX()+dx;//x
				int yt = this.getY()+dy;//y
				int st = this.getSize();//size pacman

				boolean posMinX = (xt < (xf+wf)) || ((xt+st) < (xf+wf));//inferieur bord droit
				boolean posMaxX = (xt > xf) || (xt+st > xf);//superieur bord gauche
				boolean posMinY = (yt < (yf+hf)) || (yt+st < (yf+hf));//inferieur bord bas
				boolean posMaxY = (yt > yf) || (yt+st > yf);//superieur bord haut

				if (posMinX && posMaxX && posMinY && posMaxY) {
					ret = true;
				}
			}
		}

		return ret;
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
