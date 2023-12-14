package de.thm.chat.hamster.astar;

import java.lang.*;

class Node{
	int x;
	int y;
	int g;
	int h;
	int f;
	Node parent;
	int state; //-1 -> Mauer,  0-> unbenutztes Feld, 1 -> in open list, 2-> in closed list, 3 -> start, 4 -> korn
	
	public Node(int x, int y, int state){
		this.x = x;
		this.y = y;
		this.state = state;
		this.g = 0;
		this.h = 0;
		this.f = 0;
		this.parent = null;
	}
	public void setState(int state){
		this.state = state;
	}
	
	public int getState(){
		return this.state;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setG(int g){
		this.g = g;
		calcF();
	}
	public int getG(){
		return this.g;
	}
	
	public void calcH(int[] posKorn){
		this.h = Math.abs(this.x - posKorn[0]) + Math.abs(this.y - posKorn[1]);
		calcF();
	}
	
	public int getH(){
		return this.h;
	}
	
	public void calcF(){
		this.f = this.g + this.h;
	}
	public int getF(){
		return this.f;
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}
	public Node getParent(){
		return this.parent;
	}
	

	
}
