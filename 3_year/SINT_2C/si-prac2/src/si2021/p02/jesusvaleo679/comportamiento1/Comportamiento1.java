package si2021.p02.jesusvaleo679.comportamiento1;

import si2021.p02.jesusvaleo679.comportamiento1.acciones.Apuntar;
import si2021.p02.jesusvaleo679.comportamiento1.acciones.Disparar;
import si2021.p02.jesusvaleo679.comportamiento1.acciones.Esquivar_A;
import si2021.p02.jesusvaleo679.comportamiento1.acciones.Esquivar_D;
import si2021.p02.jesusvaleo679.comportamiento1.acciones.Esquivar_I;
import si2021.p02.jesusvaleo679.comportamiento1.acciones.Nada;
import si2021.p02.jesusvaleo679.comportamiento1.acciones.PorDefecto;
import si2021.p02.jesusvaleo679.comportamiento1.acciones.Recargar;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.Apunta;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.Enemigos;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.NoApunta;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.Peligro_IoD;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.Peligro_I_C_D;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.Peligro_NI_C_D;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.Peligro_NI_C_ND;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.PosiblePeligro;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.Predeterminada;
import si2021.p02.jesusvaleo679.comportamiento1.condiciones.SinMunicion;
import si2021.p02.jesusvaleo679.motor.Motor;
import si2021.p02.jesusvaleo679.motor.Regla;

public class Comportamiento1 extends Motor {
	
	public static final int FACTOR = 4;

	public Comportamiento1() {
		// *** Reglas ***
		// Agregar las reglas debajo
		
		/* Peligros - Proyectil acercándose, esquivar */
		// Todas las direcciones - Esquivar hacia arriba
		Regla peligroICD = new Regla();
		peligroICD.setAccion(new Esquivar_A());
		peligroICD.agregarCondicion(new Peligro_I_C_D());
		agregarRegla(peligroICD);
		
		// Centro y derecha - Esquivar hacia la izquierda
		Regla peligroNICD = new Regla();
		peligroNICD.setAccion(new Esquivar_I());
		peligroNICD.agregarCondicion(new Peligro_NI_C_D());
		agregarRegla(peligroNICD);
		
		// Centro - Esquivar derecha
		Regla peligroNICND = new Regla();
		peligroNICND.setAccion(new Esquivar_D());
		peligroNICND.agregarCondicion(new Peligro_NI_C_ND());
		agregarRegla(peligroNICND);
		
		// Izquierda o Derecha - Quedarse quieto
		Regla peligroIoD = new Regla();
		peligroIoD.setAccion(new Esquivar_A());
		peligroIoD.agregarCondicion(new Peligro_IoD());
		agregarRegla(peligroIoD);
		
		// Posible peligro - Proyectil en multiples casillas
		Regla posiblePeligro = new Regla();
		posiblePeligro.setAccion(new Nada());
		posiblePeligro.agregarCondicion(new PosiblePeligro());
		agregarRegla(posiblePeligro);
		
		// Munición - Si tiene munición; recargar
		Regla municion = new Regla();
		municion.setAccion(new Recargar());
		municion.agregarCondicion(new SinMunicion());
		agregarRegla(municion);
		
		// Enemigos - Si no hay enemigos, no hacer nada
		Regla enemigos = new Regla();
		enemigos.setAccion(new Nada());
		enemigos.agregarCondicion(new Enemigos());
		agregarRegla(enemigos);
		
		// No apuntando - Si no está apuntando, apuntar
		Regla noApuntando = new Regla();
		noApuntando.setAccion(new Apuntar());
		noApuntando.agregarCondicion(new NoApunta());
		agregarRegla(noApuntando);
		
		// Apuntando - Disparar si hay enemigo en linea de tiro
		Regla apuntando = new Regla();
		apuntando.setAccion(new Disparar());
		apuntando.agregarCondicion(new Apunta());
		agregarRegla(apuntando);
		
		// Predeterminada - Ir hacia la izquierda
		Regla irIzquierda = new Regla();
		irIzquierda.setAccion(new PorDefecto());
		irIzquierda.agregarCondicion(new Predeterminada());
		agregarRegla(irIzquierda);
		
	}

}
