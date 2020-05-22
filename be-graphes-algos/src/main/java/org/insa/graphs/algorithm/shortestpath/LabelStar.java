package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

//import org.insa.graphs.algorithm.shortestpath.ShortestPathData;


public class LabelStar extends Label{

	private double estimcout;
	
	public LabelStar(Node pNoeud, double pCout, Node pPere, Arc pArc, ShortestPathData pData) {
		super(pNoeud,pCout,pPere,pArc);

		if (pData.getMode() == AbstractInputData.Mode.LENGTH) {
			this.estimcout = (double)Point.distance(pNoeud.getPoint(),pData.getDestination().getPoint());
		  } 
		else { 
			int vitesse = Math.max(pData.getMaximumSpeed(), pData.getGraph().getGraphInformation().getMaximumSpeed());
			this.estimcout=(double)Point.distance(pNoeud.getPoint(),pData.getDestination().getPoint())/(vitesse*1000.0f/3600.0f); 
		}
		
	}
	
	// Getter
	public double getEstimCout() {
		return estimcout;
		}
		
	//Setter
	public void setEstimCout(double pEstimCout) {
		this.estimcout=pEstimCout;
		}
	
	public double getTotalCost() {
		return this.getCout()+this.estimcout;
	}
}