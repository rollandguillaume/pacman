import java.io.*;

class Map {

  private int nbCases, tailleCase;
  private final int WIDTH = Canvas.WIDTH;
  private Figure[][] theMap;
  private String couleurMur, mapFile;

  /**
   * Constructeur de la classe Map, il creer un niveau du jeu a partir d'un fichier
   *
   * @param mapName le numéro de la map a charger
   */
  public Map(int mapNumber) {
    this.mapFile = "./doc/map"+ mapNumber +".txt";
    this.createMap();
    this.tailleCase = this.WIDTH / this.nbCases;
  }

  /*
  Un fichier map.txt définira un niveau de jeu
  La première ligne contient les parametre nbCase, couleur du mur ...
  # = un mur
  . = une gomme
  * = une super-gomme
  O = un chemin vide
  P = PacMan
  F = fantome
  */

  private void createMap(){
		try{

      // File reader
			InputStream ips=new FileInputStream(this.mapFile);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);

			String ligne;                                          // La ligne suivante
      boolean firstLine = true;                              // La premiere ligne contient des parametres spéciaux
      int i = 0;                                             // La ligne de la map

      // On lit toute les lignes du fichier
			while ((ligne=br.readLine())!=null){
        System.out.println("\n" + ligne);
        if(firstLine) {
          firstLine = false;
          String[] param = ligne.split(";");
          this.nbCases = Integer.parseInt(param[0]);
          this.couleurMur = param[1];
          this.theMap = new Figure[this.nbCases][this.nbCases];
        }
        else {
          int j = 0;    // La colonne de la map
          String[] param = ligne.split("");
          for (String str : param) {
            switch (str) {
              case "#" :  //this.theMap[i][j] = new Mur("this.couleurMur");
                          System.out.print(" Mur ");
                          break;
              case "." :  //this.theMap[i][j] = new Chemin(0);
                          System.out.print(" Gomme ");
                          break;
              case "*" :  //this.theMap[i][j] = new Chemin(1);
                          System.out.print(" SuperGomme ");
                          break;
              case "O" :  //this.theMap[i][j] = new Chemin(2);
                          System.out.print(" Fantome ");
                          break;
              case "P" :  //this.theMap[i][j] = new PacMan();
                          System.out.print(" PacMan ");
                          break;
              case "F" :  //this.theMap[i][j] = new Fantome();
                          System.out.print(" Fantome ");
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
  }
}
