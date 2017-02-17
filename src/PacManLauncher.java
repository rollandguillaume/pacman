class PacManLauncher {

  //private Figure[][] maps;
  private Map maps;
  private Pacman pacman;
  public static final String UP = "UP";
  public static final String DOWN = "DOWN";
  public static final String LEFT = "LEFT";
  public static final String RIGHT = "RIGHT";
  public static final int SPEED = 10;
  private static final String ColorWall = "blue";

  public PacManLauncher () {
    this.maps = new Map(1);

    this.pacman = new Pacman(this.maps.getTailleCase(), this.maps.getPMX(), this.maps.getPMY());
    this.pacman.setMap(this.maps);
  }

  public static void main(String[] args) {
    Canvas c = Canvas.getCanvas();

    PacManLauncher pml = new PacManLauncher();
    pml.draw();
    pml.animate();

    System.out.println("~~~END~~~");
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
  }

  public void toStringMap () {
    for (Figure[] l : this.maps.getMap()) {
			for (Figure f : l) {
				System.out.print(f+"\t");
			}
			System.out.println("\n");
		}
  }


  public void animate () {
    int cpt = 0;
    Canvas c = Canvas.getCanvas();
    while (this.maps.getNbGom() > 0) {
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

      Canvas.getCanvas().redraw();
      cpt++;
    }
  }

}
