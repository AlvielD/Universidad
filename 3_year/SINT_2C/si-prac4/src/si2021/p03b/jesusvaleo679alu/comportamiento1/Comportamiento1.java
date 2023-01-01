package si2021.p03b.jesusvaleo679alu.comportamiento1;

import java.util.ArrayList;

import si2021.p03b.jesusvaleo679alu.comportamiento1.acciones.Apuntar;
import si2021.p03b.jesusvaleo679alu.comportamiento1.acciones.BuscarEnemigos;
import si2021.p03b.jesusvaleo679alu.comportamiento1.acciones.Disparar;
import si2021.p03b.jesusvaleo679alu.comportamiento1.acciones.MoverArriba;
import si2021.p03b.jesusvaleo679alu.comportamiento1.acciones.MoverDerecha;
import si2021.p03b.jesusvaleo679alu.comportamiento1.acciones.MoverIzquierda;
import si2021.p03b.jesusvaleo679alu.comportamiento1.acciones.Recargar;
import si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones.Municion;
import si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones.NoPeligroCentro;
import si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones.NoPeligroDerecha;
import si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones.NoPeligroIzquierda;
import si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones.NoTieneBlanco;
import si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones.PosiblePeligro;
import si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones.SinMunicion;
import si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones.SinPeligro;
import si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones.TieneBlanco;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Accion;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Condicion;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Estado;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.HSM;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.SSM;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Transicion;

public class Comportamiento1 extends HSM {

	public static final int FACTOR = 4;
	
	public Comportamiento1() {
		// Definir todos los estados
		SSM atacando = new SSM("ATACANDO (SSM)");
		Estado recargando = new Estado("RECARGANDO (ESTADO)", new Recargar(), null, null);
		SSM esquivando = new SSM("ESQUIVANDO (SSM)");
		
		/* Atacando (SSM) */
		// Subestados
		Estado buscandoEnemigos = new Estado("BUSCANDO ENEMIGOS (SUBESTADO)", new BuscarEnemigos(), null, null);
		Estado disparando = new Estado("DISPARANDO (SUBESTADO)", new Disparar(), new Apuntar(), null);
		
		// Agregar subestados al SSM
		atacando.agregarEstado(buscandoEnemigos);
		atacando.agregarEstado(disparando);
		
		// Transiciones SSM
		// Hacia Esquivando
		ArrayList<Condicion> condAtacEsq = new ArrayList<>();
		condAtacEsq.add(new PosiblePeligro());
		Transicion tAtacEsq = new Transicion(0, esquivando, condAtacEsq, null);
		buscandoEnemigos.agregarTransicion(tAtacEsq);
		disparando.agregarTransicion(tAtacEsq);
		
		// Hacia Recargando
		ArrayList<Condicion> condAtacReca = new ArrayList<>();
		condAtacReca.add(new SinMunicion());
		Transicion tAtacReca = new Transicion(1, recargando, condAtacReca, null);
		buscandoEnemigos.agregarTransicion(tAtacReca);
		disparando.agregarTransicion(tAtacReca);
		
		// Buscando Enemigos
		ArrayList<Condicion> condBuscarEnemigosAp = new ArrayList<>();
		condBuscarEnemigosAp.add(new TieneBlanco());
		Transicion tBusEnemApunt = new Transicion(1,disparando,condBuscarEnemigosAp, new MoverArriba());
		buscandoEnemigos.agregarTransicion(tBusEnemApunt);
		
		// Disparando
		ArrayList<Condicion> condDisparando = new ArrayList<>();
		condDisparando.add(new NoTieneBlanco());
		Transicion tDispBusEnem = new Transicion(1, buscandoEnemigos, condDisparando, null);
		disparando.agregarTransicion(tDispBusEnem);
		
		// Establecer jerarquia
		buscandoEnemigos.setPadre(atacando);
		disparando.setPadre(atacando);
		
		// Inicializar SSM
		atacando.setEstadoInicial(buscandoEnemigos);
		atacando.setPadre(this);
		/* Fin Atacando */
		
		/* Recargando (Estado) */
		// Transiciones Estado
		// Hacia Atacando
		ArrayList<Condicion> condRecaAtac = new ArrayList<>();
		condRecaAtac.add(new Municion());
		Transicion tRecaAtac = new Transicion(0, atacando, condRecaAtac, null);
		recargando.agregarTransicion(tRecaAtac);
		
		// Hacia Esquivando
		ArrayList<Condicion> condRecaEsq = new ArrayList<>();
		condRecaEsq.add(new PosiblePeligro());
		Transicion tRecaEsq = new Transicion(0, esquivando, condRecaEsq, null);
		recargando.agregarTransicion(tRecaEsq);
		
		// Inicializar Estado
		recargando.setPadre(this);
		/* Fin recargando */
				
		/* Esquivando (SSM) */
		// Subestados
		Estado posible = new Estado("POSIBLE PELIGRO (SUBESTADO)", null, null, null);
		Estado noPCentro = new Estado("NO PELIGRO CENTRO (SUBESTADO)", null, new MoverArriba(), null);
		Estado noPDerecha = new Estado("NO PELIGRO DERECHA (SUBESTADO)", null, new MoverDerecha(), null);
		Estado noPIzquierda = new Estado("NO PELIGRO IZQUIERDA (SUBESTADO)", null, new MoverIzquierda(), null);
		Estado sinPeligro = new Estado("SIN PELIGRO (SUBESTADO)", null, null, null);
		
		// Agregar subestados al SSM
		esquivando.agregarEstado(posible);
		esquivando.agregarEstado(noPCentro);
		esquivando.agregarEstado(noPDerecha);
		esquivando.agregarEstado(noPIzquierda);
		esquivando.agregarEstado(sinPeligro);
		
		// Establecer jerarquia
		posible.setPadre(esquivando);
		noPCentro.setPadre(esquivando);
		noPDerecha.setPadre(esquivando);
		noPIzquierda.setPadre(esquivando);
		sinPeligro.setPadre(esquivando);
		
		// Posible
		ArrayList<Condicion> condPosNoPCentro = new ArrayList<>();
		condPosNoPCentro.add(new NoPeligroCentro());
		Transicion tPosNoPCentro = new Transicion(1, noPCentro, condPosNoPCentro, null);
		posible.agregarTransicion(tPosNoPCentro);
		
		ArrayList<Condicion> condPosNoPDerecha = new ArrayList<>();
		condPosNoPDerecha.add(new NoPeligroDerecha());
		Transicion tPosNoPDerecha = new Transicion(1, noPDerecha, condPosNoPDerecha, null);
		posible.agregarTransicion(tPosNoPDerecha);
		
		ArrayList<Condicion> condPosNoPIzquierda = new ArrayList<>();
		condPosNoPIzquierda.add(new NoPeligroIzquierda());
		Transicion tPosNoPIzquierda = new Transicion(1, noPIzquierda, condPosNoPIzquierda, null);
		posible.agregarTransicion(tPosNoPIzquierda);
		
		// NoPCentro, NoPDerecha, NoPIzquierda
		ArrayList<Condicion> condNoPeligro = new ArrayList<>();
		condNoPeligro.add(new SinPeligro());
		Transicion tNoPeligro = new Transicion(1, sinPeligro, condNoPeligro, null);
		
		ArrayList<Condicion> condPeligro = new ArrayList<>();
		condPeligro.add(new PosiblePeligro());
		Transicion tPeligro = new Transicion(1, posible, condPeligro, null);
		
		noPCentro.agregarTransicion(tNoPeligro);
		noPDerecha.agregarTransicion(tNoPeligro);
		noPIzquierda.agregarTransicion(tNoPeligro);
		
		noPCentro.agregarTransicion(tPeligro);
		noPDerecha.agregarTransicion(tPeligro);
		noPIzquierda.agregarTransicion(tPeligro);
		
		// SinPeligro
		ArrayList<Condicion> condSinPelReca = new ArrayList<>();
		condSinPelReca.add(new SinMunicion());
		Transicion tSinPelReca = new Transicion(1, recargando, condSinPelReca, null);
		sinPeligro.agregarTransicion(tSinPelReca);
		
		ArrayList<Condicion> condSinPelAtac = new ArrayList<>();
		condSinPelAtac.add(new Municion());
		Transicion tSinPelAtac = new Transicion(1, atacando, condSinPelAtac, null);
		sinPeligro.agregarTransicion(tSinPelAtac);
		
		// Inicializar SSM
		esquivando.setEstadoInicial(posible);
		esquivando.setPadre(this);
		/* Fin Esquivando */
		
		// Inicializar HSM
		this.setEstadoInicial(atacando);
		this.setPadre(null);
	}
	
	@Override
	public Accion getAccion() {
		return null;
	}
	
}
