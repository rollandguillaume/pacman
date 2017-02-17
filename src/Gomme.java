
class Gomme extends Figure {

  private Figure[] figures;
  private boolean supra;
  private static final String COLOR_WALK = "black";
  private static final String COLOR_GOM = "white";

  public Gomme (int size, int x, int y) {
    super(size, size, x, y, "black");
    this.figures = new Figure[2];
    this.figures[0] = new Square(size, x, y, this.COLOR_WALK);
  }

  public Gomme (int size, int x, int y, boolean supra) {
    this(size, x, y);

    this.supra = supra;
    int sg = size;
    if (this.supra) {
      sg = size/2;
    } else {
      sg = size/5;
    }
    int xg = x+(size/2)-(sg/2);
    int yg = y+(size/2)-(sg/2);
    this.figures[1] = new Circle(sg, xg, yg, this.COLOR_GOM);
  }

  public void setGomme (Circle c) {
    this.figures[1] = c;
  }

  public Figure getGomme () {
    return this.figures[1];
  }

  public void draw () {
    for (Figure f : this.figures) {
      if (f!=null) {
        f.draw();
      }
    }
  }

}
