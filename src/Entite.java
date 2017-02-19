

abstract class Entite {

  protected Map map;

  public abstract void draw();

  public abstract int getX();

  public abstract int getY();

  public abstract int getWidth();

  public void setMap (Map map) {
		this.map = map;
	}

  public void setLocation (int x, int y) {
		int tmpx = x-this.getX();
		int tmpy = y-this.getY();

		this.move(tmpx, tmpy);
	}

  public abstract void move (String toward);

  public abstract void move (int dx, int dy);

  public abstract boolean typeCaseToCheck (Figure f);

  protected abstract void actionWithGom (Figure[][] map, int i, int j);

  /**
	*	Check if pacman go in the wall
	*/
	protected int[] checkColision (String toward, int dx, int dy) {
		int[] ret = new int[2];
    Figure[][] map = this.map.getMap();

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

    for (int i=colonne-1; i<=colonne+1; i++) {
      for (int j=ligne-1; j<=ligne+1; j++) {
				Figure f = map[j][i];
				if (this.checkOneColision(f, dx, dy)) {
					if (f instanceof Wall) {
						if (toward.equals(PacManLauncher.UP)) {
							//dy<0
							dy = this.getY()-(f.getY()+f.getHeight());
						} else if (toward.equals(PacManLauncher.DOWN)) {
							//dy>0
							dy = (this.getY()+this.getWidth())-f.getY();
						} else if (toward.equals(PacManLauncher.LEFT)) {
							//dx<0
							dx = this.getX()-(f.getX()+f.getWidth());
						} else if (toward.equals(PacManLauncher.RIGHT)) {
							//dy>0
							dx = (this.getX()+this.getWidth())-f.getX();
						}
					} else if (f instanceof Gomme) {
						this.actionWithGom(map, j, i);
					}
				}
			}
		}

		ret[0] = dx;
		ret[1] = dy;
		return ret;
	}


  /**
	* check if pacman go out the map
	* return the race of move for pacman
	*
	* @return ret {dx, dy}
	*/
	protected int[] crossMap (String toward) {
		int[] ret = new int[2];
		int dx = 0;
		int dy = 0;
		int x = this.getX();
		int y = this.getY();

		int speed = PacManLauncher.SPEED;
		int width = this.getWidth()/4;
		int heightMap = Canvas.HEIGHT;
		int widthMap = Canvas.WIDTH;
		if (toward.equals(PacManLauncher.UP)) {
			//pacman is out the map of a part of his body
			if ((y-speed) < (-width)) {
				//so he spawn at the bottom with a part of his body visible
				dy = heightMap-speed;
			} else {
				dy = -speed;
			}
		} else if (toward.equals(PacManLauncher.DOWN)) {
			if ((y+speed) > (heightMap-width)) {
				dy = -heightMap+speed;
			} else {
				dy = speed;
			}
		} else if (toward.equals(PacManLauncher.LEFT)) {
      if ((x-speed) < (-width)) {
				dx = widthMap-speed;
			} else {
				dx = -speed;
			}
		} else if (toward.equals(PacManLauncher.RIGHT)) {
			if ((x+speed) > (widthMap-width)) {
				dx = -widthMap+speed;
			} else {
				dx = speed;
			}
		}

		ret[0] = dx;
		ret[1] = dy;
		return ret;
	}


  /**
	*	check if is one colision with Figure
	* avec le déplacement qui va etre effectuer
	*
	* @param f the figure
	* @param dx le deplacement x a faire
	* @param dy le deplacement y a faire
	*
	* @pre f is not null and is an instance of Wall or Gomme
	*/
	protected boolean checkOneColision (Figure f, int dx, int dy) {
		boolean ret = false;

		if (f != null) {
			if (this.typeCaseToCheck(f)) {
				int xf = f.getX();//x de f
				int yf = f.getY();//y de f
				int wf = f.getWidth();//largeur f
				int hf = f.getHeight();//hauteur f

				int xt = this.getX()+dx;//x
				int yt = this.getY()+dy;//y
				int st = this.getWidth();//size pacman

				boolean posMinX = (xt < (xf+wf)) || ((xt+st) < (xf+wf));//inferieur bord droit
				boolean posMaxX = (xt > xf) || (xt+st > xf);//superieur bord gauche
				boolean posMinY = (yt < (yf+hf)) || (yt+st < (yf+hf));//inferieur bord bas
				boolean posMaxY = (yt > yf) || (yt+st > yf);//superieur bord haut

				if (posMinX && posMaxX && posMinY && posMaxY) {
					ret = true;
				}
			}
		}

		return ret;
	}


}
