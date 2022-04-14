package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca2 {
	
	Model dizionario = new Model();
	
	public List<Pos> trovaParola(String parola, Board board) {
		for(Pos p : board.getPositions()) {

			// lettera in pos =?= prima lettera di parola
			if(board.getCellValueProperty(p).get().charAt(0) == parola.charAt(0)) {

				// può partire la ricorsione
				List<Pos> parziale = new ArrayList<>();
				parziale.add(p);
				if(cerca(parola, parziale, 2, board, ""+parola.charAt(0)))
					return parziale;
			}
		}
		return null;
	}
	
	public boolean cerca(String parola, List<Pos> percorso, int livello, Board board, String parolaFinora) {
		// caso terminale
		if(livello == parola.length()) {
			return true;
		}
		if(livello > 1 && !this.dizionario.cercaSubParola(parolaFinora.toLowerCase()))
			return false;
		
		// caso normale
		Pos ultima = percorso.get(percorso.size()-1);
		List<Pos> adiacenti = board.getAdjacencies(ultima);
		
		for(Pos a : adiacenti) {
			// controllo di non avere già presa la lettera in posizione 'a' e che la lettera successiva sia presente tra quelle adiacenti
			if(!percorso.contains(a) && board.getCellValueProperty(a).get().charAt(0) == parola.charAt(livello)) {
				percorso.add(a);
				parolaFinora += board.getCellValueProperty(a).get().charAt(0);
				System.out.println("--");
				// uscita rapida dalla ricorsione -- se trovo la parola è inutile procedere togliendo l'ultima lettera e cercare altri percorsi perché già l'ho trovata
				if(cerca(parola, percorso, livello+1, board, parolaFinora)) {
					return true;
				}
				// backtracking
				percorso.remove(percorso.size()-1);
				parolaFinora.substring(0, parolaFinora.length());
			}
		}
		return false;
	}

}
