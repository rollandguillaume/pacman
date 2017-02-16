class PacManLauncher {

  private Figure[][] maps;
  private Pacman pacman;
  public static final String UP = "UP";
  public static final String DOWN = "DOWN";
  public static final String LEFT = "LEFT";
  public static final String RIGHT = "RIGHT";
  public static final int SPEED = 10;

  public PacManLauncher () {
    //pour la maps, je pense qu'un objet pour recuperer directement le tableau serait cool :)
    this.maps = new Figure[3][3];
    this.maps[0][0] = new Wall(50, 0, 0, "blue");
    this.maps[1][1] = new Wall(50, 50, 50, "blue");
    this.maps[2][2] = new Wall(50, 100, 100, "blue");

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
