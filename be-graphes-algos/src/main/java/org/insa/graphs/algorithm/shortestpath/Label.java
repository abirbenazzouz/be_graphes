package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {

	private Node sommet;
	private boolean marque;
	private double cout;
	private Node pere;
	private Arc arc;
	
	// Constructeur 
	public Label(Node pNoeud, double pCout, Node pPere, Arc pArc) {
		this.sommet=pNoeud;
		this.marque=false;
		this.pere=pPere;
		this.cout=pCout;
		this.arc = pArc;
	}
	
	
	
	//Getters
	public double getCout() {
		return cout;
	}

	public Node getPere() {
		return this.pere;
	}

	
	public Node getSommet() {
		return sommet;
	}
	
	public boolean isMarque() {
		return this.marque;
	}
	
	public Arc getArc() {
		return this.arc;
	}
	

	//Définition de la méthode getTotalCost
	public double getTotalCost() {
		return this.getCout();
	}
	
	//Setters
	public void setCout(double cout) {
		this.cout=cout;
	}
	
	public void setMarque() {
		this.marque= true;
	}


	// Ajout du compare to, un label est "supérieur" à un autre si son cout est supérieur
	@Override
	public int compareTo(Label o) {
		if (this.getTotalCost()> o.getTotalCost()) {
			return 1;
		}
		if (this.getTotalCost()<o.getTotalCost()) {
			return -1;
		}
		else {
			return 0;
		}
	}


	
	
}
