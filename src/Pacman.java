/**
 * UN arc de cercle jaune avec une ouverture pour la bouche qui représente le pacman
 *
 * @author maxime,guillaume,remi
 * @version 2017.02.14
 * @inv getColor().equals("yellow")
 */
public class Pacman extends Entite {

	private static final String PACMAN_COLOR = "yellow"; // the Pacman default color
	public static final int OUVERTURE_MIN = 10;//ouverture minimal de la bouche de pacman
	public static final int OUVERTURE_MAX = 40;//ouverture maximal de la bouche de pacman
	private static final int SCORE_GOMME = 10;
	private static final int LIFE_START = 3;//nombre de vie de pacman

	private ArcCircle pac;
	private int ouverture;// ouverture de la bouche de pacman
	private boolean mouthIsOpen;// ouverture de la bouche de pacman
	private String dernierePosition;
	private String toGo;// La direction que pacman veut prendre
	private String previousMove;//Le dernier mouvement de pacman
	private int life;// the pacman life
	private int score;// the pacman score

	/**
     * Create a new Figure_Pacman.
     *
     * @pre size >= 0
     * @pre life >= 0
     */
	public Pacman(int size, int x, int y) {
		this.pac = new ArcCircle(size, x, y, PACMAN_COLOR, 0, 360);
		//initialize the direction of pacman
		this.dernierePosition = PacManLauncher.LEFT;
		this.ouverture = this.OUVERTURE_MIN;
		this.deplaceOuverture(PacManLauncher.LEFT);
		this.life = this.LIFE_START;
	}

	/**
	* remove one life of pacman
	*
	* @return if one life carry off
	*@pre this.life > 0
	*/
	public boolean carryOff () {
		boolean ret = false;
		if (this.life > 0) {
			this.life -= 1;
			ret = true;
		}
		return ret;
	}
	/**
     * Give the pacman life
     *
     * @return the pacman life
     */
	public int getLife () {
		return this.life;
	}
	/**
	 * up the score to this.SCORE_Gomme.
	 *
	 */
	public void upScore () {
		this.score += Gomme.SCORE_GOMME;
	}
	/**
     * Give the pacman score
     *
     * @return the pacman score
     */
	public int getScore () {
		return this.score;
	}
	/**
     * Give the pacman speed
     *
     * @return the pacman speed
     */
	public int getSpeed () {
		return PacManLauncher.SPEED_PACMAN;
	}
	/**
     * Give the pacman x location in pixels
     *
     * @return the pacman x location in pixels
     */
	public int getX () {
		return this.pac.getX();
	}
	/**
     * Give the figure y location in pixels
     *
     * @return the figure y location in pixels
     */
	public int getY () {
		return this.pac.getY();
	}
	/**
     * Give the pacman width in pixels
     *
     * @return the pacman width in pixels
     */
	public int getWidth () {
		return this.pac.getWidth();
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

		if (this.testMove(toward)) {

			int[] crossMap = this.crossMap(toward);
			dx = crossMap[0];
			dy = crossMap[1];

			crossMap = this.checkColision(toward, dx, dy);
			dx = crossMap[0];
			dy = crossMap[1];

			this.deplaceOuverture(toward);
			this.animateMouth();
			this.move(dx, dy);//move the pacman
			this.previousMove = toward;
		} else {
			int[] crossMap = this.crossMap(this.previousMove);
			dx = crossMap[0];
			dy = crossMap[1];

			crossMap = this.checkColision(this.previousMove, dx, dy);
			dx = crossMap[0];
			dy = crossMap[1];

			this.deplaceOuverture(this.previousMove);
			this.animateMouth();
			this.move(dx, dy);//move the pacman
		}
		this.invariant();
	}

	private boolean testMove(String toward) {

		boolean haveMoved = false;

		Figure[][] map = this.map.getMap();

		if(this.getX() % this.map.getTailleCase() == 0 && this.getY() % this.map.getTailleCase() == 0) {

			int colonne = this.getX()/this.map.getTailleCase();
			int ligne = this.getY()/this.map.getTailleCase();
	    if (colonne <= 0) {//gestion bord de map droite/gauche
	      colonne = 1;
	    } else if (colonne >= map.length-1) {
	      colonne = map.length-2;
	    }
	    if (ligne <= 0) {//gestion bord de map bas/haut
	      ligne = 1;
	    } else if (ligne >= map.length-1) {
	      ligne = map.length-2;
	    }

			Figure fup = map[ligne-1][colonne];
			Figure fdown = map[ligne+1][colonne];
			Figure fleft = map[ligne][colonne-1];
			Figure fright = map[ligne][colonne+1];

			switch (toward) {
				case PacManLauncher.UP :
					if (fup.getClass().getName().compareTo("Wall") != 0) {
						haveMoved = true;
					} else {
					}
					break;
				case PacManLauncher.DOWN :
					if (fdown.getClass().getName().compareTo("Wall") != 0) {
						haveMoved = true;
					} else {
					}
					break;
				case PacManLauncher.LEFT :
					if (fleft.getClass().getName().compareTo("Wall") != 0) {
						haveMoved = true;
					} else {
					}
					break;
				case PacManLauncher.RIGHT :
					if (fright.getClass().getName().compareTo("Wall") != 0) {
						haveMoved = true;
					} else {
					}
					break;
			}
		}

		return haveMoved;
	}

	public void move (int dx, int dy) {
		this.pac.move(dx, dy);
	}

/**
 * Change mouth pacman direction to the new mouth pacman direction .
 * @param direction the new mouth pacman direction
 * @pre direction.equals("UP") || direction.equals("LEFT") || direction.equals("DOWN")|| direction.equals("RIGHT")
 */
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


	/**
   * definie les actions que l'entite va devoir realiser avec un objet de type gomme
   * qui est en position (i,j) sur la Map
   * @param Figure[][] map la carte ayant les objets de type gomme
   * @param int        i   position colonne pour la Map
   * @param int        j   position ligne dans la Map
   * @pre (map[i][j] instanceof Gomme)
   */
	protected void actionWithGom (Figure[][] map, int i, int j) {
		Figure f = map[i][j];
		if (f instanceof Gomme) {
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

	public boolean colisionGhost (Ghost f) {
		boolean ret = false;

		int xf = f.getX();//x de fpac
		int yf = f.getY();//y de f
		int sf = f.getWidth();//size f

		int xt = this.getX();//x
		int yt = this.getY();//y
		int st = this.getWidth();//size

		boolean posMinX = (xt < (xf+sf)) || ((xt+st) < (xf+sf));//inferieur bord droit
		boolean posMaxX = (xt > xf) || (xt+st > xf);//superieur bord gauche
		boolean posMinY = (yt < (yf+sf)) || (yt+st < (yf+sf));//inferieur bord bas
		boolean posMaxY = (yt > yf) || (yt+st > yf);//superieur bord haut

		if (posMinX && posMaxX && posMinY && posMaxY) {
			ret = true;
		}

		return ret;
	}
	 /**
     * Check the class invariant
     */
    protected void invariant() {
    	this.pac.invariant();
        assert this.pac.getColor().equals("yellow") : "Invariant violated: wrong dimensions";
    }

}
