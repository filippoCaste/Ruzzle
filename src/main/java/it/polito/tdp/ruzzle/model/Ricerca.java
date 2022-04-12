package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {
										//parolaDaTrovare
	public List<Pos> trovaParola(String parola, Board board) {
		for(Pos p : board.getPositions()) {
			
			// lettera in pos =?= prima lettera di parola
			if(board.getCellValueProperty(p).get().charAt(0) == parola.charAt(0)) {

				// può partire la ricorsione
				List<Pos> parziale = new ArrayList<>();
				parziale.add(p);
				if(cerca(parola, parziale, 1, board))
					return parziale;
			}
		}
		return null;
	}
										//parziale: è un percorso
	public boolean cerca(String parola, List<Pos> percorso, int livello, Board board) {
		// caso terminale
		if(livello == parola.length()) {
			return true;
		}
		Pos ultima = percorso.get(percorso.size()-1);
		List<Pos> adiacenti = board.getAdjacencies(ultima);
		
		for(Pos a : adiacenti) {
			// controllo di non avere già presa la lettera in posizione 'a' e che la lettera successiva sia presente tra quelle adiacenti
			if(!percorso.contains(a) && board.getCellValueProperty(a).get().charAt(0) == parola.charAt(livello)) {
				percorso.add(a);
				
				// uscita rapida dalla ricorsione -- se trovo la parola è inutile procedere togliendo l'ultima lettera e cercare altri percorsi perché già l'ho trovata
				if(cerca(parola, percorso, livello+1, board))
					return true;
				// backtracking
				percorso.remove(percorso.size()-1);
			}
		}
		
		return false;
	}
}
