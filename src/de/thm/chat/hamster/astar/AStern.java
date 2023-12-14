package de.thm.chat.hamster.astar;

import java.util.ArrayList;

public class AStern {

//________________________________________Initialisierung ______________________________________

   //Territorium Map erstellen
   int x= Territorium.getAnzahlSpalten();
   int y= Territorium.getAnzahlReihen();
   int map[][] = new int[x][y]; 

   //initalisieren der 3 ArrayLists 
   ArrayList<Node> alleFelder = new ArrayList<Node>();
   ArrayList<Node> opened = new ArrayList<Node>();
   ArrayList<Node> closed = new ArrayList<Node>();
   
   //Kornposition
   int[] kornPosition = new int[2];
   int[] startPosition = new int[2];
   
   //korn gefunden? kein Weg gefunden? Hamster ist bereits auf Kornfeld?
   boolean kornGefunden = false;
   boolean keinWeg = false;
   boolean startAufKorn = false;
   
   //weg zum ziel
   int[][] wegZumZiel;
   
   //Blickrichtungen
   public final static int NORD = 0;
   public final static int OST = 1;
   public final static int SUED = 2;
   public final static int WEST = 3;
   
   //jedes Feld untersuchen und in ArrayList und map abspeichern
   //durch das speichern in der map wird die zuordnung der Nodes in der ArrayList erleichtert
   public AStern(Hamster standard){
   		for (int i = 0; i < this.x; i++){
   			for (int j = 0; j < this.y; j++){
   				if((standard.getSpalte() == i) && (standard.getReihe() == j) && ( Territorium.getAnzahlKoerner(j, i) >0)){
   					this.startAufKorn = true;
   				}else if((standard.getSpalte() == i) && (standard.getReihe() == j)){
   					alleFelder.add(new Node (i,j,3));
   					this.startPosition[0] = i;
   					this.startPosition[1] = j;
   					map[i][j] = alleFelder.size() - 1;
   				}else if ( Territorium.getAnzahlKoerner(j, i) >0){
   					alleFelder.add(new Node (i,j,4));
   					this.kornPosition[0] = i;
   					this.kornPosition[1] = j;
   					map[i][j] = alleFelder.size() - 1;
   				} else if (Territorium.mauerDa(j,i)){
   					alleFelder.add(new Node (i,j,-1));
   					map[i][j] = alleFelder.size() - 1;
   				}else{
   					alleFelder.add(new Node (i,j,0));
   					map[i][j] = alleFelder.size() - 1;
   				}		
   			}
   		}
   }
   
//______________________________________Vollst�ndiger Ablauf______________________________________
   
   public void hamsterNutztAStern(Hamster standard){
   		if(this.startAufKorn){
   			System.out.println("Auf dem Startfeld ist bereits das Korn");
   			//standard.nimm();
   		} else{
   			findeWeg();
   			if (this.keinWeg){
   				System.out.println("Es gibt keinen Weg zum Korn");
   			} else{
   				hamsterZuKorn(standard);
   			}
   		}
   }
    
   
   
//______________________________________Finde den Weg zum Korn mit A*______________________________________


   public void findeWeg(){
   		//f�ge Startknoten in die open List hinzu
   		
   			this.closed.add(alleFelder.get(map[this.startPosition[0]][this.startPosition[1]]));
   		 		
   			while(!this.kornGefunden){
   				addNeighborsToOpen(closed.get(closed.size() - 1));
   				if (kornGefunden){
   					break;
   				}
   				addNodeToClosed();
   				if (this.keinWeg){
   					break;
   				}		
   			}
   			opened.clear();
   			alleFelder.clear();
  
   }
   
   //check ob die Startposition die Kornposition ist
   public boolean checkStartIsKorn(){
   		if ((this.startPosition[0] == this.kornPosition[0]) && (this.startPosition[1] == this.kornPosition[1])){
   			return true;
   		}
   		return false;
   }
   
   //suche die Nachbarn des Knoten und f�ge sie der openedList hinzu 
   public void addNeighborsToOpen(Node parent){
   		if ((parent.getX() - 1) >= 0){
   			toOpened(parent, alleFelder.get(map[parent.getX()-1][parent.getY()]));
   		}
   		if ((parent.getX() + 1) < this.x){
   			toOpened(parent, alleFelder.get(map[parent.getX()+1][parent.getY()]));
   		}
   		if ((parent.getY() - 1) >= 0){
   			toOpened(parent, alleFelder.get(map[parent.getX()][parent.getY() - 1]));
   		}
   		if ((parent.getY() + 1) < this.y){
   			toOpened(parent, alleFelder.get(map[parent.getX()][parent.getY() + 1]));
   		}
   		
   }
   
   //fuege Knoten opened hinzu wenn noch in Status 0 (also bisher ungenutzes Feld) 
   //wenn Knoten bereits in Status 1 (in liste opened) pr�fe ob aktueller Pfad (G) k�rzer -> wenn ja, �ndere parent und g
   //gib 
   public void toOpened(Node parent, Node aktuell){
   		if(aktuell.getState() == 4){
   			aktuell.setParent(parent);
   			aktuell.setG(parent.getG() + 1);
   			closed.add(aktuell);
   			this.kornGefunden = true;
   		}
   		
   		else if (aktuell.getState() == 0){
   			aktuell.setState(1);
   			aktuell.setParent(parent);
   			aktuell.setG(parent.getG() + 1);
   			aktuell.calcH(this.kornPosition);
   			opened.add(aktuell);
   			
   		} else if (aktuell.getState() == 1){
   			if (aktuell.getG() > (parent.getG()+1)){
   				aktuell.setParent(parent);
   				aktuell.setG(parent.getG() + 1);
   				aktuell.calcH(this.kornPosition);
   			} 
   		}
   		
   }
   //w�hle Node aus der opened List als n�chsten Schritt im Path und lege den Node in die closed liste
   public void addNodeToClosed(){
   		if (opened.isEmpty()){
   			this.keinWeg = true;   			
   		} else{
   			Node smallestF = opened.get(opened.size()-1);
   			int indexOpened = opened.size()-1;
   			for (int i = (opened.size() - 2); i >= 0; i--){
   				if (smallestF.getF() > (opened.get(i)).getF()){
   					smallestF = opened.get(i);
   					indexOpened = i;
   				}
   			}
   			smallestF.setState(2);
   			closed.add(smallestF);
   			opened.remove(indexOpened);		
   		}
   }
   
//______________________________________Bewege den Hamster zum Korn______________________________________
   
   public void hamsterZuKorn(Hamster standard){
   		gehZuKorn();
   		gehZumKorn(standard);
   }
   
   	// schreibe in 2d Array wegZumZiel die Koordinaten von Start zum Ziel
	public void gehZuKorn(){
		Node aktuell = closed.get(closed.size()-1);
		this.wegZumZiel = new int[aktuell.getG()+1][2];
		int laufVari = aktuell.getG();
		while(laufVari >= 0){
			this.wegZumZiel[laufVari][0] = aktuell.getX();
			this.wegZumZiel[laufVari][1] = aktuell.getY();
			laufVari--;
			if (aktuell.getParent() != null){
				aktuell = aktuell.getParent();
			}				 
		}		
	}
	
	public void gehZumKorn(Hamster standard){
			for (int i = 1; i < this.wegZumZiel.length; i++){
				if (wegZumZiel[i-1][0] > wegZumZiel[i][0]){
					drehDich(standard, WEST);
					standard.vor();
				}else if (wegZumZiel[i-1][0] < wegZumZiel[i][0]){
					drehDich(standard, OST);
					standard.vor();
				}else if (wegZumZiel[i-1][1] > wegZumZiel[i][1]){
					drehDich(standard, NORD);
					standard.vor();
				}else if (wegZumZiel[i-1][1] < wegZumZiel[i][1]){
					drehDich(standard, SUED);
					standard.vor();
				}
				
			}
			//standard.nimm();
	}
	
	//Hamster dreht sich bis die Blickrichtung stimmt
	public void drehDich(Hamster standard, int Blickrichtung){
		while(standard.getBlickrichtung() != Blickrichtung){
			standard.linksUm();
		}
	}
   
   
}

 

   