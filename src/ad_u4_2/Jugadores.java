/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad_u4_2;

import java.util.ArrayList;

/**
 *
 * @author mdfda
 */
public class Jugadores {
    private ArrayList<Jugador> jugadores;

    
    public Jugadores(){
        jugadores = new ArrayList<Jugador>();
    }
    
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void addJugador(Jugador jugador) {
        this.jugadores.add(jugador);
    }
}
