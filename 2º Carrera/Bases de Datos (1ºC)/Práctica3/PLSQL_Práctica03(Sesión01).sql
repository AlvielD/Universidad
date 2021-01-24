---------------------------------------------------------------------------------------------------
									--PRÁCTICA 1: PL/SQL--
---------------------------------------------------------------------------------------------------

--Ejercicio 1--	
--Bloque PL/SQL
SET serveroutput ON;

CREATE OR REPLACE
PROCEDURE penalizar (nomPiloto PILOTO.nombreP%TYPE, nomRally RALLY.nombre%TYPE, t_penalizacion PARTICIPA.penalizacion%TYPE) IS
	piloto_no_encontrado EXCEPTION;
	rally_no_encontrado EXCEPTION;

	Existe INTEGER; --Variable que almacena cuantas existencias hay de pilotos o rallies que cumplan las consultas dadas
  codigoR RALLY.codRally%TYPE;  --Almacena el codigo del Rally pasado por parametro
  codigoP PILOTO.codPiloto%TYPE;  --Almacena el codigo del Piloto pasado por parametro
  --Estos últimos dos son para poder hacer el update con la tabla participa
  
	CURSOR C_PARTICIPA IS
		SELECT nombreP, penalizacion
		FROM (PILOTO INNER JOIN PARTICIPA USING(codPiloto)) INNER JOIN RALLY USING(codRally)
		WHERE nombre = nomRally;
    
BEGIN
	SELECT COUNT(*) INTO Existe
	FROM PILOTO WHERE nombreP = nomPiloto;
  
	IF Existe = 0 THEN
		RAISE piloto_no_encontrado;
	END IF;
	
	SELECT COUNT(*) INTO Existe
	FROM RALLY WHERE nombre = nomRally;
		
	IF Existe = 0 THEN
		RAISE rally_no_encontrado;
	END IF;

	DBMS_OUTPUT.PUT_LINE(nomRally || '  (antes de la actualización)');
	DBMS_OUTPUT.PUT_LINE('==============================================');  
	  
	FOR r_participa IN C_participa LOOP	
		dbms_output.put_line(r_participa.nombreP || '	' || r_participa.penalizacion);
	END LOOP;
  
  ----Obtenemos los códigos----
  -----------------------------
  SELECT codRally INTO codigoR
  FROM RALLY
  WHERE nombre = nomRally;
  
  SELECT codPiloto INTO codigoP
  FROM PILOTO
  WHERE nombreP = nomPiloto;
  -----------------------------
	
	UPDATE PARTICIPA
	SET penalizacion=penalizacion+t_penalizacion
	WHERE codRally = codigoR AND codPiloto = codigoP;
		
	--Repetir el bucle después de actualizar--
      
	DBMS_OUTPUT.PUT_LINE(nomRally || '  (después de la actualización)');
	DBMS_OUTPUT.PUT_LINE('==============================================');  
	  
	FOR r_participa IN C_participa LOOP	
		dbms_output.put_line(r_participa.nombreP || '	' || r_participa.penalizacion);
	END LOOP;
	
EXCEPTION
	WHEN piloto_no_encontrado THEN
		dbms_output.put_line('No existe el piloto con el nombre '||nomPiloto);
	
	WHEN rally_no_encontrado THEN
		dbms_output.put_line('No existe el piloto con el nombre '||nomRally);
	
end;

--Ejercicio 2--

--Bloque PL/SQL
SET serveroutput ON

CREATE OR REPLACE
PROCEDURE mejor_piloto_tramo(nomPiloto PILOTO.nombreP%TYPE) IS
	piloto_not_found EXCEPTION;
	codP PILOTO.codPiloto%TYPE;
	EPiloto INTEGER;
	--
	--
	CURSOR c_rally IS
		SELECT nombre, codRally
		FROM RALLY INNER JOIN PARTICIPA USING(codRally)
		WHERE codPiloto = codP;
	CURSOR c_tiempo(codR RALLY.codRally%TYPE) IS
		SELECT numeroTramo, MIN(tiempo) AS tiempo
		FROM CORRE
		WHERE codRally = codR AND codPiloto = codP
		GROUP BY numeroTramo
		ORDER BY tiempo;
BEGIN

	--Comprobamos que existe el piloto
	----------------------------
	SELECT COUNT(*) INTO EPiloto
	FROM PILOTO
	WHERE nombreP = nomPiloto;
	
	IF EPiloto = 0 THEN
		RAISE piloto_not_found;
	END IF;
	----------------------------
	
	--Inicializamos codP
	--------------------------
	SELECT codPiloto INTO codP
	FROM PILOTO 
	WHERE nombreP = nomPiloto;
	--------------------------
	
	FOR i_cursor IN c_rally LOOP
		dbms_output.put_line('-----------------------------');
		dbms_output.put_line(i_cursor.nombre);
		dbms_output.put_line('-----------------------------');
		FOR j_cursor IN c_tiempo(i_cursor.codRally) LOOP
			dbms_output.put_line(j_cursor.numeroTramo || ' - ' || j_cursor.tiempo);
		END LOOP;
	END LOOP;
	
EXCEPTION
	WHEN piloto_not_found THEN
		dbms_output.put_line('ERROR: No existe el piloto de nombre ' || nomPiloto);
END;