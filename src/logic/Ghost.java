package logic;
import java.awt.*;
import java.util.ArrayList;
import data.*;
import view.*;


/**
 * Cette classe représente l'entité fantome et toute ses caractéristique
 *
 * @author RGM
 * @version 03/03/2017
 */
public class Ghost extends Entite {

	/** Tableau des figure composants le fantome */
	private GhostSkin figures;
	/** La couleur du fantome */
	private String couleur;
	/** Le mouvement que vient d'effectuer le fantome */
	private String previousMove;
	/** Compteur qui va aléatoirement faire faire demi-tour au fantome */
	private int compteurInversionMove;
	/** Compteur du temps de peur des fantomes */
	private int compteurPeur;

	public static final int SPEED_GHOST = 10;//doit etre un multiple de taille de case
	public static final int SCORE_FANTOME = 100;


	/**
   * Create a new ghost.
   *
   * @pre size >= 0
   * @pre color different of ("white")
   */
	public Ghost(int size, int x, int y, String color) {
		this.previousMove = PacManLauncher.UP;
		this.initCompteur();

		this.compteurPeur = 0;
		this.couleur = color;

		this.figures = new GhostSkin(size, x, y, color);
	}

	/**
	*	Choisir une direction aleatoire
	*/
	public void move () {
		if (this.compteurPeur == 0) {
			this.setEtatNormal();
		}
		if (this.compteurPeur % 2 == 1 || this.compteurPeur == 0){
			if (this.compteurPeur > 0) {
				this.compteurPeur--;
			}
			this.compteurInversionMove--;
			if (this.compteurInversionMove == 0) {
				switch (this.previousMove) {
					case PacManLauncher.UP :
						this.move(PacManLauncher.DOWN);
						break;
					case PacManLauncher.DOWN :
						this.move(PacManLauncher.UP);
						break;
					case PacManLauncher.LEFT :
						this.move(PacManLauncher.RIGHT);
						break;
					case PacManLauncher.RIGHT :
						this.move(PacManLauncher.LEFT);
						break;
				}
				this.initCompteur();
			} else {
				checkCroisement(this.previousMove);
			}
		}
		else {
			this.compteurPeur--;
		}
	}

	/**
	 * initialize le compteur de changement de direction d'un fantome
	 * avec un temps aleatoire compris entre un nombre de raffraichissement de la fenetre
	 */
	public void initCompteur () {
		this.compteurInversionMove = (int) (Math.random()*30) + 20;
	}

	/**
   * deplace l'entite dans la direction demander
   *
   * @param String toward direction demande
   * @pre (toward.equals("UP") || toward.equals("DOWN") || toward.equals("LEFT") || toward.equals("RIGHT"))
   */
	public void move (String toward) {
		this.previousMove = toward;
		int dx = 0;
		int dy = 0;

		int[] crossMap = this.crossMap(toward);
		dx = crossMap[0];
		dy = crossMap[1];

		crossMap = this.checkColision(toward, dx, dy);
		dx = crossMap[0];
		dy = crossMap[1];

		this.move(dx, dy);//move the ghost
	}

	/**
	 * deplace l'entite d'une variation de (dx,dy)
	 * @param int dx le decalage x
	 * @param int dy le decalage y
	 */
	public void move (int dx, int dy) {
		for (Figure figure : this.getSkin()) {
        figure.move(dx, dy);
    }
	}

	private Figure[] getSkin () {
		return this.figures.getFigures();
	}

	/**
	 * Met le fantome en etat de peur
	 */
	public void setEtatPeur() {
		this.compteurPeur = 60;
		Figure[] figures = this.getSkin();
		for (int i = 0; i < 5; i++) {
			figures[i].setColor("blue");
		}
	}

	public void setEtatNormal() {
		this.compteurPeur = 0;
		Figure[] figures = this.getSkin();
		for (int i = 0; i < 5; i++) {
			figures[i].setColor(this.couleur);
		}
	}

	/**
	 * retourne la position x de l'entite
	 * @return la position x de l'entite
	 */
	public int getX () {
		return this.figures.getX();
	}

	/**
	 * retourne la position y de l'entite
	 * @return la position y de l'entite
	 */
	public int getY () {
		return this.figures.getY();
	}

	/**
	 * retourne la taille de l'Entite fantome
	 * @return la taille de Ghost
	 */
	public int getWidth () {
		return this.figures.getWidth();
	}

	/**
	 * retourne la vitesse de deplacement d'un fantome
	 * @return la vitesse de Ghost
	 */
	public int getSpeed () {
		return Ghost.SPEED_GHOST;
	}

	/**
	 * retourne la vitesse de deplacement d'un fantome
	 * @return la vitesse de Ghost
	 */
	public int getPeur () {
		return this.compteurPeur;
	}

	/**
	 * [checkCroisement description]
	 * @param String toward [description]
	 */
	public void checkCroisement (String toward) {
		boolean haveMoved = false;
		Figure[][] map = this.map.getMap();

		if(this.getX() % this.map.getTailleCase() == 0 && this.getY() % this.map.getTailleCase() == 0) {
			int[] colLign = this.getColLign();
	    int colonne = colLign[0];
	    int ligne = colLign[1];

			Figure fup = map[ligne-1][colonne];
			Figure fdown = map[ligne+1][colonne];
			Figure fleft = map[ligne][colonne-1];
			Figure fright = map[ligne][colonne+1];

			ArrayList<Figure> caseAround =  new ArrayList<Figure>();
			caseAround.add(fup);
			caseAround.add(fdown);
			caseAround.add(fleft);
			caseAround.add(fright);

			switch (toward) {
				case PacManLauncher.UP :
					if (fleft.getClass().getName().compareTo("Wall") != 0 || fright.getClass().getName().compareTo("Wall") != 0) {
						caseAround.remove(fdown);
						this.chooseMove(toward, caseAround, fup, fdown, fleft, fright);
						haveMoved = true;
					} else if (fup.getClass().getName().compareTo("Wall") == 0) {
						this.chooseMove(toward, caseAround, fup, fdown, fleft, fright);
						haveMoved = true;
					}
					break;
				case PacManLauncher.DOWN :
					if (fleft.getClass().getName().compareTo("Wall") != 0 || fright.getClass().getName().compareTo("Wall") != 0) {
						caseAround.remove(fup);
						this.chooseMove(toward, caseAround, fup, fdown, fleft, fright);
						haveMoved = true;
					} else if (fdown.getClass().getName().compareTo("Wall") == 0) {
						this.chooseMove(toward, caseAround, fup, fdown, fleft, fright);
						haveMoved = true;
					}
					break;
				case PacManLauncher.LEFT :
					if (fup.getClass().getName().compareTo("Wall") != 0 || fdown.getClass().getName().compareTo("Wall") != 0) {
						caseAround.remove(fright);
						this.chooseMove(toward, caseAround, fup, fdown, fleft, fright);
						haveMoved = true;
					} else if (fleft.getClass().getName().compareTo("Wall") == 0) {
						this.chooseMove(toward, caseAround, fup, fdown, fleft, fright);
						haveMoved = true;
					}
					break;
				case PacManLauncher.RIGHT :
					if (fup.getClass().getName().compareTo("Wall") != 0 || fdown.getClass().getName().compareTo("Wall") != 0) {
						caseAround.remove(fleft);
						this.chooseMove(toward, caseAround, fup, fdown, fleft, fright);
						haveMoved = true;
					} else if (fright.getClass().getName().compareTo("Wall") == 0) {
						this.chooseMove(toward, caseAround, fup, fdown, fleft, fright);
						haveMoved = true;
					}
					break;
			}
		}

		if (!haveMoved) {
			this.move(this.previousMove);
		}
	}

	public void chooseMove(String toward, ArrayList<Figure> listF, Figure fup, Figure fdown, Figure fleft, Figure fright) {
		boolean ok = false;
		ArrayList<Figure> toGo =  new ArrayList<Figure>();

		for (Figure f : listF) {
			if (f.getClass().getName().compareTo("Wall") != 0) {
				toGo.add(f);
			}
		}

		Figure nextMove = null;
		double ran = Math.random()*toGo.size();
		for (int i = 0; i < toGo.size(); i++) {
			if (ran >= i && ran < i+1) {
				nextMove = toGo.get(i);
			}
		}

		if (nextMove == null) {
			this.move(toward);
		} else if (nextMove == fup) {
			this.move(PacManLauncher.UP);
		} else if (nextMove == fdown) {
			this.move(PacManLauncher.DOWN);
		} else if (nextMove == fleft) {
			this.move(PacManLauncher.LEFT);
		} else if (nextMove == fright) {
			this.move(PacManLauncher.RIGHT);
		}
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
		this.figures.draw();
  }

}
