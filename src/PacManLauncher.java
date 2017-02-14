
class PacManLauncher {

  private Figure[][] maps;
  private Pacman pacMan;

  public PacManLauncher () {
    this.maps = new Figure[3][3];
    this.pacMan = new Pacman(50, 250, 250, "yellow");
  }

  public static void main(String[] args) {
    Canvas c = Canvas.getCanvas();

    PacManLauncher pml = new PacManLauncher();
    pml.draw();
    pml.animate();
  }

  public void draw () {
    for (Figure[] fl : this.maps) {
      for (Figure f : fl) {
        if (f!=null) {
          f.draw();
        }
      }
    }
    this.pacMan.draw();
  }


  public void animate () {
    int cpt = 0;
    Canvas c = Canvas.getCanvas();
    while (cpt < 1000) {
      if (c.isUpPressed()) {
        System.out.println("up");
        this.pacMan.move();
      } else if (c.isDownPressed()) {
        System.out.println("down");
      } else if (c.isLeftPressed()) {
        System.out.println("left");
      } else if (c.isRightPressed()) {
        System.out.println("right");
        //this.pacMan.move();
      }

      Canvas.getCanvas().redraw();
      cpt++;
    }
    System.out.println("END");
  }

}
