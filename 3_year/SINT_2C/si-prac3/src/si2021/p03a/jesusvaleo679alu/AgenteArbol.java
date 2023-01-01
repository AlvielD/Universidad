package si2021.p03a.jesusvaleo679alu;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import si2021.p03a.jesusvaleo679alu.comportamiento1.acciones.Apuntar;
import si2021.p03a.jesusvaleo679alu.comportamiento1.acciones.BuscarEnemigos;
import si2021.p03a.jesusvaleo679alu.comportamiento1.acciones.Disparar;
import si2021.p03a.jesusvaleo679alu.comportamiento1.acciones.MoverArriba;
import si2021.p03a.jesusvaleo679alu.comportamiento1.acciones.MoverDerecha;
import si2021.p03a.jesusvaleo679alu.comportamiento1.acciones.MoverIzquierda;
import si2021.p03a.jesusvaleo679alu.comportamiento1.acciones.Nada;
import si2021.p03a.jesusvaleo679alu.comportamiento1.acciones.Recargar;
import si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones.Apuntando;
import si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones.Enemigos;
import si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones.Municion;
import si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones.Peligro;
import si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones.PeligroCentro;
import si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones.PeligroDerecha;
import si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones.PeligroIzquierda;
import si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones.PosiblePeligro;
import si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones.TieneBlanco;
import si2021.p03a.jesusvaleo679alu.motorarbol.Nodo;
import tools.ElapsedCpuTimer;

public class AgenteArbol extends AbstractPlayer {

	private Nodo raiz;
	
	public AgenteArbol(StateObservation percepcion, ElapsedCpuTimer et) {
		// NACE
		TieneBlanco apuntandoSI = new TieneBlanco(new Disparar(), new BuscarEnemigos());
		Apuntando enemigosSI = new Apuntando(apuntandoSI, new Apuntar());
		Enemigos municionSI = new Enemigos(enemigosSI, new MoverArriba());
		Municion posiblePeligroNO = new Municion(municionSI, new Recargar());
		PosiblePeligro peligroNO = new PosiblePeligro(new Nada(), posiblePeligroNO);
		PeligroIzquierda peligroDSI = new PeligroIzquierda(new MoverArriba(), new MoverIzquierda());
		PeligroDerecha peligroCSI = new PeligroDerecha(peligroDSI, new MoverDerecha());
		PeligroDerecha peligroINO = new PeligroDerecha(new MoverArriba(), new Nada());
		PeligroIzquierda peligroCNO = new PeligroIzquierda(new MoverArriba(), peligroINO);
		PeligroCentro peligroSI = new PeligroCentro(peligroCSI, peligroCNO);
		raiz = new Peligro(peligroSI, peligroNO);
	}

	@Override
	public ACTIONS act(StateObservation percepcion, ElapsedCpuTimer elapsedTimer) {
		// PENSAR

		ACTIONS accion = raiz.decidir(percepcion).getAccion(percepcion);
		
		return accion; // ACTUAR
	}

}
