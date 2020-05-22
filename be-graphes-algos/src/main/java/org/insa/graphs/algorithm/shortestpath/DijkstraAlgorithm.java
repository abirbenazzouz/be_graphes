package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
   
  //Création d'une méthode newLabel qui remplace le "new Label", pour redéfinir cette méthode dans A*
    protected Label newLabel(Node pNoeud, double pCout, Node pPere, Arc pArc) {
    	return new Label(pNoeud,pCout,pPere,pArc);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph= data.getGraph();
        boolean fini = false;
        
        
        // Création d'une liste de Labels
        Label[] tabLabels= new Label[graph.size()];
        
        // Création d'un tas de labels
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        // Ajout du sommet d'origine et de ses attributs
        Label origine = newLabel(data.getOrigin(), 0, null, null);
        tabLabels[origine.getSommet().getId()]=origine;
        // Le cout du point d'origine à lui même est de 0
        tas.insert(origine);
        
        
        
        while(!tas.isEmpty() && !fini) {
        	
        	// On trouve le noeud qui a le cout le plus faible du tas, on le retire et on le garde en mémoire dans la variable labelX
        	Label labelX=tas.deleteMin();

        	if (labelX.isMarque()) {
        		continue;
        	}
        	// Quand on sort un noeud du tas, on le marque, et on prévient les observateurs
        	labelX.setMarque();
        	notifyNodeMarked(labelX.getSommet());
        	
        	if (labelX.getSommet() == data.getDestination()) {
        		fini = true;
        		break;
        	}
        	
        	for (Arc arc : labelX.getSommet().getSuccessors()) {
        		
        		// On vérifie qu'on peut bien prendre cet arc
        		if (!data.isAllowed(arc)) {
                     continue;
                }
        		// On introduit le mode :
        		double getarc;
        		if(data.getMode() == AbstractInputData.Mode.LENGTH) {
        			getarc=arc.getLength();
        		}
        		else {
        			getarc=arc.getMinimumTravelTime();
        		}
        		// On récupère le label dans le tableau
        		Label successeurLabel = tabLabels[arc.getDestination().getId()];
        		// Si la case correspondant à ce noeud est vide, c'est qu'on ne l'a jamais visité
        		if (successeurLabel == null) {
        			// On prévient les obervateurs qu'on a atteint un nouveau noeud
        			notifyNodeReached(arc.getDestination());
        			// On lui crée un label 
        			successeurLabel= newLabel(arc.getDestination(),tabLabels[arc.getOrigin().getId()].getCout()+ getarc, arc.getOrigin(), arc);
        			tabLabels[arc.getDestination().getId()]= successeurLabel;
        			tas.insert(successeurLabel);
        		}
        		else {
        			if (successeurLabel.getCout() > tabLabels[arc.getOrigin().getId()].getCout()+ getarc) {
        				tabLabels[arc.getDestination().getId()]=newLabel(arc.getDestination(),tabLabels[arc.getOrigin().getId()].getCout()+ getarc, arc.getOrigin(), arc);
        			// Si le label est déjà dans le tas on met à jour sa position, sinon on l'y ajoute
        				try {
        					tas.remove(successeurLabel);
        				} catch (Exception e) {}
        				tas.insert(tabLabels[arc.getDestination().getId()]);
        			}
        		}
        	}
        }
        // On genère la solution
        
        // Création d'une liste d'arcs
        
        ArrayList<Arc> listeArcsSolution = new ArrayList<Arc>();
        //Arc [] tabArcsSolution = new Arc [graph.size()];
        
        Label labelX = tabLabels[data.getDestination().getId()];
        
        if (labelX.getPere()==null) {
        	solution= new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	 // The destination has been found, notify the observers.
        	notifyDestinationReached(data.getDestination());
        	
        	//On garde en mémoire le cout de notre dernier arc du chemin à construire pour les vérifications du path généré
        	int coutChemin = (int)labelX.getCout(); //Math.round((float)labelX.getCout()*100.0f)/100.0f;
        	
        	// On remplit la liste des arcs solution
        	while (labelX.getArc() != null) {
        		listeArcsSolution.add(0,labelX.getArc());
        		labelX= tabLabels[labelX.getArc().getOrigin().getId()];
        	}
        	
        	// On crée le Path solution
        	Path pathSolution = new Path(graph, listeArcsSolution);
        	
			
			  // On vérifie que la longueur du chemin est identique au plus court chemin trouvé, si on est en mode distance 
        	if(data.getMode()==AbstractInputData.Mode.LENGTH) { 
        		if((int)pathSolution.getLength()==coutChemin) {
        			System.out.println("Longueur du chemin OK"); } 
        		else {
        			System.out.println("Longueur du chemin différente"); } 
	        	}
        	
        	
        	solution= new ShortestPathSolution(data, Status.OPTIMAL, pathSolution);
        	}
			 
        	
        	
			/*
			 * // On vérifie qu'il est bien valide if (pathSolution.isValid()) { // On crée
			 * la solution solution= new ShortestPathSolution(data, Status.OPTIMAL,
			 * pathSolution); } // Sinon on renvoie une solution nulle et on prévient que le
			 * chemin n'est pas valide else { solution=null;
			 * System.out.println("Le chemin n'est pas valide"); }
			 */
        	
        	
        	
        
        
        
        return solution;
    }

}
