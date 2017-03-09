import java.io.*;
import java.util.ArrayList;

/**
 * Cette classe permet, à partir d'un fichier .map, de créer toutes les figures d'un niveau
 *
 * @author RGM
 * @version 03/03/2014
 *
 * @inv WIDTH == Canvas.WIDTH
 */
class Map {

  /** Le nombre de case de la map */
  private int nbCases;
  /** La taille de chacune des cases */
  private int tailleCase;
  /** La position en X de pacman */
  private int pacmanX;
  /** La position en Y de pacman */
  private int pacmanY;
  /** Taille de la fenêtre */
  private final int WIDTH = Canvas.WIDTH;
  /** Tableau à deux dimension de figure contenant toute les figures de la map case par case */
  private Figure[][] theMap;
  /** La couleur des mur de la map */
  private String couleurMur;
  /** Le nom du fichier .map */
  private String mapFile;
  /** Le nombre de gomme présent sur la map */
  private int nbrGomme;
  /** La position sur la map de chaque fantôme en début de niveau : Un liste de couple (x,y) */
  private ArrayList<Integer[]> ghosts;

  /**
   * Constructeur de la classe Map, il creer un niveau du jeu a partir d'un fichier
   *
   * @param mapName le numéro de la map a charger
   * @pre mapNumber > 0
   */
  public Map(int mapNumber) {
    assert mapNumber > 0 : "Precondition non respectée : numéro de la map négatif";
    this.mapFile = "./doc/map"+ mapNumber +".map";
    this.nbrGomme = 0;
    this.ghosts = new ArrayList<Integer[]>();
    this.createMap();
    this.invariant();
  }

  /*******************************************************************
  Un fichier map.txt définira un niveau de jeu
  La première ligne contient les parametre nbCase, couleur du mur ...
                      # = un mur
                      . = une gomme
                      * = une super-gomme
                      O = un chemin vide
                      P = PacMan
                      F = fantome
  *******************************************************************/

  /**
   * Cette fonction est appellée par le constructeur afin de lire le fichier .map et d'initialiser tout les parametres
   *
   * @post nbrGomme > 0
   * @post pacmanX > 0
   * @post pacmanY > 0
   * @post couleurMur == "blue" || couleurMur == "green" || couleurMur == "pink"
   */
  private void createMap(){
		try{
      // Ouverture du fichier pour la lecture
			InputStream ips=new FileInputStream(this.mapFile);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);

      boolean firstLine = true;        // La premiere ligne contient des parametres spéciaux
      String ligne;                    // La ligne suivante à lire
      int i = 0;                       // La ligne de la map

      // On lit toute les lignes du fichier
			while ((ligne=br.readLine())!=null){
        //Traitement spéciale pour la première ligne
        if(firstLine) {
          firstLine = false;
          String[] param = ligne.split(";");
          this.nbCases = Integer.parseInt(param[0]);
          this.tailleCase = this.WIDTH / this.nbCases;
          this.couleurMur = param[1];
          this.theMap = new Figure[this.nbCases][this.nbCases];
        }
        else {
          int j = 0;                   // La colonne de la map
          int tmpx = 0;                // Variable utilisée pour stocké la position en x d'une figure
          int tmpy = 0;                // Variable utilisée pour stocké la position en y d'une figure
          Integer[] posGhost = null;

          String[] param = ligne.split("");
          for (String str : param) {
            tmpx = j*this.tailleCase;  // Calcule de la position de la figure
            tmpy = i*this.tailleCase;  // Calcule de la postion de la figure
            // Voir description des symboles ci-dessus
            switch (str) {
              case "#" :
                this.theMap[i][j] = new Wall(this.tailleCase, tmpx, tmpy, this.couleurMur);
                break;
              case "." :
                this.theMap[i][j] = new Gomme(this.tailleCase, tmpx, tmpy, false);
                this.nbrGomme += 1;
                break;
              case "*" :
                this.theMap[i][j] = new Gomme(this.tailleCase, tmpx, tmpy, true);
                this.nbrGomme += 1;
                break;
              case "O" :
                this.theMap[i][j] = new Gomme(this.tailleCase, tmpx, tmpy);
                break;
              case "P" :
                this.theMap[i][j] = new Gomme(this.tailleCase, tmpx, tmpy);
                this.pacmanX = tmpx;
                this.pacmanY = tmpy;
                break;
              case "F" :
                this.theMap[i][j] = new Gomme(this.tailleCase, tmpx, tmpy);
                posGhost = new Integer[2];
                posGhost[0] = new Integer(tmpx);
                posGhost[1] = new Integer(tmpy);
                this.ghosts.add(posGhost);
                break;
            }
            j++;
          }
          i++;
        }
			}
			br.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
    assert nbrGomme > 0 : "Post condition non respectée : nombre de gomme nul";
    assert pacmanX > 0 : "Post condition non respectée : pacman non initialisé";
    assert pacmanY > 0 : "Post condition non respectée : pacman non initialisé";
    assert couleurMur == "blue" || couleurMur == "green" || couleurMur == "pink" : "Post condition non respectée : Mauvaise couleur de mur";

    this.invariant();
  }

  /**
   * Decremente le nombre de gomme
   */
  public void pickGom () {
    this.nbrGomme -= 1;
  }

  /**
   * Getter pour le nombre courrant de gomme
   *
   * @return Le nombre de gomme
   */
  public int getNbGom () {
    return this.nbrGomme;
  }

  /**
   * Getter pour le tableau des figure de la map
   *
   * @return le tableau des figures
   */
  public Figure[][] getMap(){
    return this.theMap;
  }

  /**
   * Getter pour le nombre de cases de la map
   *
   * @return Le nombre de case
   */
  public int getNbCases() {
    return this.nbCases;
  }

  /**
   * Getter pour la taille des cases de la map
   *
   * @return La taille des cases
   */
  public int getTailleCase() {
    return this.tailleCase;
  }

  /**
   * Getter pour la position en X de pacman sur la map
   *
   * @return La position en X de pacman
   */
  public int getPMX() {
    return this.pacmanX;
  }

  /**
   * Getter pour la position en Y de pacman sur la map
   *
   * @return La position en Y de pacman
   */
  public int getPMY() {
    return this.pacmanY;
  }

  /**
   * Getter pour la liste des positions des fantomes sur la map
   *
   * @return La liste des postion des fantomes
   */
  public ArrayList<Integer[]> getPGhost() {
    return this.ghosts;
  }

  protected void invariant() {
    assert this.WIDTH == Canvas.WIDTH : "Invariant violé : WIDTH a changé";
  }

}
