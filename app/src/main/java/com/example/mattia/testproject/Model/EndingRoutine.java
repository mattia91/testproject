package com.example.mattia.testproject.Model;

import com.example.mattia.testproject.Model.Bowl;
import com.example.mattia.testproject.Model.Constant;
import com.example.mattia.testproject.Model.Player;

import java.util.ArrayList;

/**
 * Created by martino on 19/11/14.
 */
public class EndingRoutine {


    public int gameOver(Player last_active, ArrayList<Player> players){
        //controllo se il gioco è concluso
        boolean game_over =  is_over(last_active);
        int winner=-1;
        //nel caso sia concluso procedo con la routine finale
      if (game_over) {
          arrange_remaining_seeds(players);
          //stabilisco il vincitore
          winner = winner(players);
      }

        //TODO aggiorna le statistiche

        //valori di ritorno :
        //-1 gioco non concluso
        //-2 gioco concluso in pareggio
        //+n indice del giocatore nell'array che ha vinto
        return winner;
    }

    private void arrange_remaining_seeds(ArrayList<Player> players){
            //aggiorno le varie tray mettendo tutti i semi restanti
            for(Player player : players){
                int point = 0;
                for(Bowl bowl : player.getBowls()){
                    point += bowl.getNum_seeds();
                    bowl.setNum_seeds(0);
                }
                player.increment_tray(point);
            }
    }

    private boolean is_over(Player last_active) {
        boolean game_over = true;
        for( Bowl bowl : last_active.getBowls() ){
            if( bowl.getNum_seeds() > 0 ){
                game_over = false;
            }
        }
        return game_over;
    }

    private int winner(ArrayList<Player> players){

        int winning = players.get(0).getTray().getSeeds();
        int return_value = -2;
        boolean tie = true;  // <- ho aggiunto questa variabile

        //controlliamo se per caso è avvenuto un pareggio fra i giocatori
        for( int e = 0; e < Constant.number_player ; e++ ){
            //se la condizione sotto si verifica non è avvenuto pareggio
            if(players.get(e).getTray().getSeeds() != winning){
                return_value = 0;
                tie = false;  // <- al posto di return = -1;  uso la nuova variabile
            }
        }

        //stabiliamo il vincitore
        // 0 se vince il primo
        // 1 se vince il secondo
        if( !tie ) {  // se non ce stato pareggio entro nel ciclo e stabilisco vincitore
            for (int e = 0; e < Constant.number_player; e++) {
                if (players.get(e).getTray().getSeeds() > winning) {
                    winning = players.get(e).getTray().getSeeds();
                    return_value = e;
                }
            }
        }

        return return_value;

    }
}
