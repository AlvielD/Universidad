------------------------------------------------------
			--Sesión 2: Funciones PL/SQL--
------------------------------------------------------

----------------
--EJERCICIO 3--
----------------
CREATE OR REPLACE
FUNCTION ganador (nomR RALLY.nombre%TYPE) RETURN PILOTO.nombreP%TYPE IS
	ganador PILOTO.nombreP%TYPE;
	codR RALLY.nombre%TYPE;
	codGanador PILOTO.codPiloto%TYPE;
	num_tramos INTEGER;
	t_minimo INTEGER;
	contador INTEGER;
	CURSOR C IS
		SELECT codPiloto, SUM(tiempo) AS tiempoR
		FROM CORRE
    WHERE codRally = codR
		GROUP BY codPiloto
		HAVING COUNT(numeroTramo) = num_tramos;
BEGIN
	
	--Saca el código del rally pasado por parámetro
	-------------------------
	SELECT codRally INTO codR
	FROM RALLY
	WHERE nombre = nomR;
	-------------------------
  
	--Saca las tuplas del Rally pasado por parámetro
	-----------------------------------------
	SELECT COUNT(numeroTramo) INTO num_tramos
	FROM TRAMO
	WHERE codRally = codR;
	-----------------------------------------
	
	contador := 0;
	
	FOR i_cursor IN C LOOP
		IF contador = 0 THEN
			t_minimo := i_cursor.tiempoR;
			codGanador := i_cursor.codPiloto;
			contador := 1;
		END IF;
		
		IF i_cursor.tiempoR <= t_minimo THEN
			t_minimo := i_cursor.tiempoR;
			codGanador := i_cursor.codPiloto;
		END IF;
	END LOOP;
  
	SELECT nombreP INTO ganador
	FROM PILOTO
	WHERE codPiloto = codGanador;

	RETURN ganador;	
END;

--------------------------
--RESUELTO CON CONSULTAS--
--------------------------

SELECT codRally INTO v_codRally
FROM RALLY
WHERE nombre = p_rally;

SELECT COUNT(*) INTO num_tramos
FROM TRAMO
WHERE codRally = v_codRally;

SELECT nombreP INTO v_nombreP
FROM CORRE INNER JOIN PILOTO USING(codPiloto)
WHERE codRally = v_codRally
GROUP BY nombreP
HAVING COUNT(*) = num_tramos AND SUM(tiempo)
	<= ALL (SELECT SUM(tiempo)
			FROM CORRE
			WHERE codRally = v_codRally
			GROUP BY codPiloto
			HAVING COUNT(*) = num_tramos);
RETURN v_nombreP;

-----------------------------------
--RESUELTO CON CONSULTAS (POR MI)--
-----------------------------------

CREATE OR REPLACE
FUNCTION ganadorConsultas (nomR RALLY.nombre%TYPE) RETURN PILOTO.nombreP%TYPE IS
	cod_rally RALLY.codRally%TYPE;
	cod_ganador PILOTO.codPiloto%TYPE;
	total_tramos INTEGER;
	ganador PILOTO.nombreP%TYPE;
BEGIN

	--Seleccionamos el código del rally pasado por parámetro
	------------------------------
	SELECT codRally INTO cod_rally
	FROM RALLY
	WHERE nombre = nomR;
	------------------------------
	
	--Seleccionamos el número total de tramos del rally pasado por parámetro
	-------------------------------------------
	SELECT COUNT(numeroTramo) INTO total_tramos
	FROM CORRE
	WHERE codRally = cod_rally
  GROUP BY codRally;
	-------------------------------------------
	
	
	SELECT codPiloto INTO cod_ganador
	FROM CORRE
	WHERE codRally = cod_rally
	GROUP BY codPiloto
	HAVING COUNT(*) = total_tramos AND SUM(tiempo) <= ALL (SELECT SUM(tiempo)
                                                      FROM CORRE
                                                      WHERE codRally = cod_rally
                                                      GROUP BY codPiloto
                                                      HAVING COUNT(*) = total_tramos);
													  
	SELECT nombreP INTO ganador
	FROM PILOTO
	WHERE codPiloto = cod_ganador;
	
	RETURN ganador;
	
END;

---------------
--EJERCICIO 4--
---------------
CREATE OR REPLACE
FUNCTION rallyCompletado (p_rally RALLY.nombre%type, p_piloto PILOTO.nombreP%type) RETURN INTEGER IS
	c_rally RALLY.codRally%TYPE;
	c_piloto PILOTO.codPiloto%TYPE;
	t_piloto INTEGER;
	t_rally INTEGER;
	EPiloto INTEGER;
	ERally INTEGER;
BEGIN
	
	SELECT COUNT(*) INTO EPiloto
	FROM PILOTO
	WHERE nombreP = p_piloto;
	
	SELECT COUNT(*) INTO ERally
	FROM RALLY
	WHERE nombre = p_rally;
	
	IF EPiloto = 0 OR ERally = 0 THEN
		RETURN 2;
	END IF;
	
	--Carga el codigo del rally
	-----------------------------
	SELECT codRally INTO c_rally
	FROM RALLY
	WHERE nombre = p_rally;
	-----------------------------
	
	--Carga el codigo del piloto
	------------------------------
	SELECT codPiloto INTO c_piloto
	FROM PILOTO
	WHERE nombreP = p_piloto;
	------------------------------

	--Carga el numero de tramos del rally
	---------------------------------------
	SELECT COUNT(numeroTramo) INTO t_rally
	FROM TRAMO
	WHERE codRally = c_rally;
	---------------------------------------
	
	--Carga el numero de tramos del piloto de ese rally
	----------------------------------------------------
	SELECT COUNT(numeroTramo) INTO t_piloto
	FROM CORRE
	WHERE codPiloto = c_piloto AND codRally = c_rally;
	----------------------------------------------------
	
	IF t_piloto = t_rally THEN
		RETURN 1;
	ELSE
		RETURN 0;
	END IF;
END;

---------------
--EJERCICIO 5--
---------------

CREATE OR REPLACE
TRIGGER mantenerTiempoRally BEFORE INSERT OR UPDATE OR DELETE OF tiempo ON CORRE FOR EACH ROW
DECLARE
	--
	--
	--
BEGIN
	--
	--
	--
EXCEPTION
	--
	--
	--
END;