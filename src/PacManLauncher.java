
class PacManLauncher {

  private Figure[][] maps;
  private Pacman pacman;
  public static final String UP = "UP";
  public static final String DOWN = "DOWN";
  public static final String LEFT = "LEFT";
  public static final String RIGHT = "RIGHT";
  public static final int SPEED = 10;

  public PacManLauncher () {
    this.maps = new Figure[3][3];
    this.pacman = new Pacman(50, 250, 250);
  }

  public static void main(String[] args) {
    Canvas c = Canvas.getCanvas();

    PacManLauncher pml = new PacManLauncher();
    pml.draw();
    pml.animate();

    System.out.println("~~~END~~~");
  }

  public void draw () {
    for (Figure[] fl : this.maps) {
      for (Figure f : fl) {
        if (f!=null) {
          f.draw();
        }
      }
    }
    this.pacman.draw();
  }


  public void animate () {
    int cpt = 0;
    Canvas c = Canvas.getCanvas();
    while (cpt < 1000) {
      //swich the key press, move the pacman
      if (c.isUpPressed()) {
    	this.pacman.DeplaceOuverture(this.UP);
        this.pacman.move(this.UP);
      } else if (c.isDownPressed()) {
    	 this.pacman.DeplaceOuverture(this.DOWN);
    	 this.pacman.move(this.DOWN);
      } else if (c.isLeftPressed()) {
    	 this.pacman.DeplaceOuverture(this.LEFT);
    	 this.pacman.move(this.LEFT);
      } else if (c.isRightPressed()) {
    	this.pacman.DeplaceOuverture(this.RIGHT);
    	this.pacman.move(this.RIGHT);
      }

      this.pacman.animate();
      Canvas.getCanvas().redraw();
      cpt++;
    }
  }

}
