/**
 * Write a description of class Pacman here.
 *
 * @author maxime
 * @version 2017.01.01
 */
public class Pacman extends Entite {

	private static final String PACMAN_COLOR = "yellow"; // the Pacman default color
	public static final int OUVERTURE_MIN = 10;
	public static final int OUVERTURE_MAX = 40;
	private static final int SCORE_GOMME = 10;

	private ArcCircle pac;
	private int ouverture;
	private boolean mouthIsOpen;
	private String dernierePosition;
	private int score;

	/**
     * Create a new Figure_Pacman.
     *
     * @pre size >= 0
     */
	public Pacman(int size, int x, int y) {
		this.pac = new ArcCircle(size, x, y, PACMAN_COLOR, 0, 360);
		//initialize the direction of pacman
		this.dernierePosition = PacManLauncher.LEFT;
		this.ouverture = this.OUVERTURE_MIN;
		this.deplaceOuverture(PacManLauncher.LEFT);
	}

	public void setLocation (int x, int y) {
		int tmpx = x-this.pac.getX();
		int tmpy = y-this.pac.getY();

		this.pac.move(tmpx, tmpy);
	}

	public void upScore () {
		this.score += this.SCORE_GOMME;
	}

	public int getScore () {
		return this.score;
	}

	public void draw () {
		this.pac.draw();
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
		this.pac.move(dx, dy);//move the pacman
	}

	public int getX () {
		return this.pac.getX();
	}

	public int getY () {
		return this.pac.getY();
	}

	public int getWidth () {
		return this.pac.getWidth();
	}

	private void deplaceOuverture(String direction) {
		int as = 0;
		int ae = 0;

		if (direction.equals(PacManLauncher.UP)) {
			as = (90-ouverture);
			ae = (-360+2*ouverture);
		} else if (direction.equals(PacManLauncher.LEFT)) {
			as = (180-ouverture);
			ae = (-360+2*ouverture);
		} else if (direction.equals(PacManLauncher.DOWN)) {
			as = (270-ouverture);
			ae = (-360+2*ouverture);
		} else if (direction.equals(PacManLauncher.RIGHT)) {
			as = (-ouverture);
			ae = (-360+2*ouverture);
		}

		this.pac.setAngleStart(as);
		this.pac.setAngleExtent(ae);
		this.dernierePosition = direction;
	}


	protected void actionWithGom (Figure[][] map, int i, int j) {
		Figure f = map[i][j];
		Gomme tmp = (Gomme)f;
		if (tmp.getGomme() != null) {
			tmp.setGomme(null);//plus de gomme
			tmp.draw();
			map[i][j] = tmp;
			this.map.pickGom();
			this.upScore();
		} else {
			//deja pas de gomme donc rien a faire
		}
	}


	/**
	*	pacman peut agir sur les mur et les gommes
	*/
	public boolean typeCaseToCheck (Figure f) {
		return (f instanceof Wall) || (f instanceof Gomme);
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
