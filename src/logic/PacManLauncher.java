package logic;
import java.util.*;
import data.*;
import view.*;

public class PacManLauncher {

  private data.Map maps;
  private Pacman pacman;
  private Ghost[] ghost;
  public static final String UP = "UP";
  public static final String DOWN = "DOWN";
  public static final String LEFT = "LEFT";
  public static final String RIGHT = "RIGHT";
  private static final int NBR_LVL = 3; // TODO : compter le nbr de fichier .map ??

  /**
   * initialize au lancement le jeu pacman
   * en creant la map de niveau 1
   * le pacman de toute la partie
   * les fantomes du niveau
   */
  public PacManLauncher () {
    this.maps = new data.Map(1);
    this.fillGhost();
    this.pacman = new Pacman(this.maps.getTailleCase(), this.maps.getPMX(), this.maps.getPMY());
    this.pacman.setMap(this.maps);
  }

  public static void main (String[] args) {
    Canvas c = Canvas.getCanvas();
    PacManLauncher pml = new PacManLauncher();
    pml.draw();
    pml.animate(); // Le lvl 1

    int i = 2;
    while ((pml.getPacman().getLife() > 0)) {
      pml.upLvl(i);
      pml.draw();
      pml.animate();
      i++;
      if (i > PacManLauncher.NBR_LVL) {
        i=1;
      }
    }

    if (Integer.valueOf(Score.getScore()) < pml.getPacman().getScore()) {
      Score.setScore(pml.getPacman().getScore()+"");
    }
    System.out.println("~~~END~~~");
  }

  /**
   * change la map en prenant le niveau passe en parametre
   * @param int lvl le niveau souhaité
   */
  public void upLvl (int lvl) {
    this.maps = new data.Map(lvl);
    this.fillGhost();
    this.pacman.setLocation(this.maps.getPMX(), this.maps.getPMY());
    this.pacman.setMap(this.maps);
  }

  /**
   * creer tous les fantomes necessaires
   * en fonction de l'objet this.map
   */
  public void fillGhost () {
    ArrayList<Integer[]> gs = this.maps.getPGhost();//tab des positions fantome
    this.ghost = new Ghost[gs.size()];

    String[] color = {"redG", "blueG", "orangeG", "pinkG"};
    int cpt = 0;
    int alea = 0;
    int cptGhost = 0;
    for (Integer[] t : gs) {
    	alea = (int)(Math.random()*color.length);
      this.ghost[cpt] = new Ghost(this.maps.getTailleCase(), t[0], t[1], color[cptGhost]);
      this.ghost[cpt].setMap(this.maps);

      cpt++;
      cptGhost++;
      if (cptGhost >= color.length) {
        cptGhost = 0;
      }
    }
  }

  /**
   * dessine la map
   * et pacman
   * et tous les fantomes
   * d'un niveau
   */
  public void draw () {
    this.maps.draw();
    this.pacman.draw();
    for (Ghost g : this.ghost) {
      if (g != null) {
        g.draw();
      }
    }
  }

  /**
   * retourne le pacman de la partie
   * @return le pacman de la partie
   */
  public Pacman getPacman () {
    return this.pacman;
  }

  /**
   * lance le deroulement du jeu
   * en regardant la touche utiliser par l'utilisateur pour deplacer pacman
   * puis deplace les fantomes
   * et verifie les colisions eventuelles entre pacman et les fantomes
   * (sans redessiner toute la map)
   */
  public void animate () {
    Canvas c = Canvas.getCanvas();
    c.resetMove();
    while ((this.maps.getNbGom() > 0) && (this.pacman.getLife() > 0)) {
      //swich the key press, move the pacman
      if (c.isUpPressed()) {
        this.pacman.move(this.UP);
      } else if (c.isDownPressed()) {
        this.pacman.move(this.DOWN);
      } else if (c.isLeftPressed()) {
        this.pacman.move(this.LEFT);
      } else if (c.isRightPressed()) {
        this.pacman.move(this.RIGHT);
      }

      if (this.pacman.getPMSupra()) {
        for (Ghost g : this.ghost) {
          g.setEtatPeur();
        }
        this.pacman.resetSupra();
      }

      this.collisionGhost();

      for (Ghost g : this.ghost) {
        g.move();
      }
      this.collisionGhost();
      Canvas.getCanvas().redraw(this.pacman.getScore(), this.pacman.getLife(), Score.getScore());
    }
  }

  /**
   * verifie s'il existe une colision entre pacman et l'un des fantome
   * si oui alors pacman perd une vie
   * et toutes les Entites sont repositionner à leur point de départ pour le niveau en cours
   * @return true si une colision existe
   */
  private boolean collisionGhost () {
	  boolean ret = false;
	  int i = 0;
	  while (!ret && (i<this.ghost.length)) {
    	  //si pacman colision avec un fantome
		  //perdre une vie a pacman && repositionner les entites
		  if(this.pacman.colisionGhost(this.ghost[i]) && this.ghost[i].getPeur() == 0) {

			  this.pacman.carryOff();
			  this.pacman.setLocation(this.maps.getPMX(), this.maps.getPMY());

			  ret = true;
		  }
      else if(this.pacman.colisionGhost(this.ghost[i]) && this.ghost[i].getPeur() > 0) {

        ArrayList<Integer[]> gs = this.maps.getPGhost();

        int cpt = 0;
		    for (Integer[] t : gs) {
          if (cpt == i) {
            this.ghost[cpt].setLocation(t[0], t[1]);
            this.ghost[cpt].setEtatNormal();
            this.pacman.upScoreFantomme();
          }
		      cpt++;
		    }
		  }
		  i++;
    }

	  if (ret) {//si une colision
		  ArrayList<Integer[]> gs = this.maps.getPGhost();//tab des positions fantome

		    int cpt = 0;
		    for (Integer[] t : gs) {
		      this.ghost[cpt].setLocation(t[0], t[1]);

		      cpt++;
		    }
	  }
	  return ret;
  }

}
