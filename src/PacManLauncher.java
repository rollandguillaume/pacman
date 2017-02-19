import java.util.*;

class PacManLauncher {

  //private Figure[][] maps;
  private Map maps;
  private Pacman pacman;
  private Ghost[] ghost;
  public static final String UP = "UP";
  public static final String DOWN = "DOWN";
  public static final String LEFT = "LEFT";
  public static final String RIGHT = "RIGHT";
  public static final int SPEED = 10;//doit etre un multiple de taille de case
  private static final String ColorWall = "blue";
  private static final int NBR_LVL = 2;

  public PacManLauncher () {
    this.maps = new Map(1);

    this.fillGhost();

    this.pacman = new Pacman(this.maps.getTailleCase(), this.maps.getPMX(), this.maps.getPMY());
    this.pacman.setMap(this.maps);
  }

  public static void main(String[] args) {
    Canvas c = Canvas.getCanvas();

    PacManLauncher pml = new PacManLauncher();
    pml.draw();
    pml.animate();//lvl1
    for (int i=1; i<PacManLauncher.NBR_LVL; i++) {
      pml.upLvl(i+1);
      pml.draw();
      pml.animate();
    }

    System.out.println(pml.getPacman().getScore());
    System.out.println("~~~END~~~");
  }

  public void upLvl (int lvl) {
    this.maps = new Map(lvl);
    this.fillGhost();
    this.pacman.setLocation(this.maps.getPMX(), this.maps.getPMY());
    this.pacman.setMap(this.maps);
  }

  public void fillGhost () {
    ArrayList<Integer[]> gs = this.maps.getPGhost();//tab des positions fantome
    this.ghost = new Ghost[gs.size()];

    int cpt = 0;
    for (Integer[] t : gs) {
      this.ghost[cpt] = new Ghost(this.maps.getTailleCase(), t[0], t[1], "red");
      this.ghost[cpt].setMap(this.maps);

      cpt++;
    }
  }

  public void draw () {
    for (Figure[] fl : this.maps.getMap()) {
      for (Figure f : fl) {
        if (f!=null) {
          f.draw();
        }
      }
    }
    this.pacman.draw();
    for (Ghost g : this.ghost) {
      if (g != null) {
        g.draw();
      }
    }
  }

  public Pacman getPacman () {
    return this.pacman;
  }


  public void animate () {
    Canvas c = Canvas.getCanvas();
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

      for (Ghost g : this.ghost) {
        g.move();
      }
      Canvas.getCanvas().redraw();
    }
  }

}
