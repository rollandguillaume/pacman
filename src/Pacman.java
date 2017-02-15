
/**
 * Write a description of class Pacman here.
 *
 * @author launay
 * @version 2017.01.01
 */
public class Pacman extends ArcCircle {

	private ArcCircle[] figures;
	private static final String PACMAN_COLOR = "yellow"; // the Pacman default color
	private static int Ouverture=40;
	private boolean plusouvert =true;
	private String DernierePosition="";

	/**
     * Create a new Figure_Pacman.
     *
     * @pre size >= 0
     */
	public Pacman(int size, int x, int y) {
		super(size,x,y,PACMAN_COLOR,0,360);
		this.figures = new ArcCircle[2];
		this.figures[0] = new ArcCircle(size,x,y,PACMAN_COLOR,0,360); // corps

	}



	/**
	* move the pacman and all figures which blend him
	*/
	public void move (String toward) {
		int[] crossMap = this.crossMap(toward);
		int dx = crossMap[0];
		int dy = crossMap[1];
		this.move(dx, dy);//move the pacman
		//and move all figure of the pacman
		
    
	}

	/**
	* check if pacman go out the map
	* return the race of move for pacman
	*
	* @return ret {dx, dy}
	*/
	private int[] crossMap (String toward) {
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
			if (y-speed < -width) {
				//so he spawn at the bottom with a part of his body visible
				dy = heightMap;
			} else {
				dy = -speed;
			}
		} else if (toward.equals(PacManLauncher.DOWN)) {
			if (y+speed > heightMap-speed) {
				dy = -heightMap;
			} else {
				dy = speed;
			}
		} else if (toward.equals(PacManLauncher.LEFT)) {
			if (x-speed < -width) {
				dx = widthMap;
			} else {
				dx = -speed;
			}
		} else if (toward.equals(PacManLauncher.RIGHT)) {
			if (x+speed > widthMap-width) {
				dx = -widthMap;
			} else {
				dx = speed;
			}
		}

		ret[0] = dx;
		ret[1] = dy;
		return ret;
	}
	
	public void DeplaceOuverture(String direction){
		if (direction.equals("UP")){
			setAngleStart( 90-Ouverture);
			setAngleExtent(-360+2*Ouverture);
			DernierePosition="UP";
		}
		if (direction.equals("LEFT")){
			setAngleStart( 180-Ouverture);
			setAngleExtent(-360+2*Ouverture);
			DernierePosition="LEFT";
		}
		if (direction.equals("DOWN")){
			setAngleStart( 270-Ouverture);
			setAngleExtent(-360+2*Ouverture);
			DernierePosition="DOWN";
		}
		if (direction.equals("RIGHT")){
			setAngleStart( -Ouverture);
			setAngleExtent(-360+2*Ouverture);
			DernierePosition="RIGHT";
		}
	}
	
	
	public void animate()
	{
		Canvas canvas = Canvas.getCanvas();
		if(plusouvert){
			int temp=Ouverture;
			Ouverture=0;
			this.DeplaceOuverture(DernierePosition);
			Ouverture=temp;
			plusouvert=!plusouvert;
			}
		else{
			this.DeplaceOuverture(DernierePosition);
			plusouvert=!plusouvert;
		}
		draw();
		
	}

}
