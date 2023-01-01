#pragma once

#include <GL/glew.h>
#include <glm/glm.hpp>
#include "CGShaderProgram.h"
#include "CGFigure.h"

//
// CLASE: CGObject
//
// DESCRIPCIÓN: Clase abstracta que representa un objeto 
//              formado por varias piezas
// 
class CGObject {
protected:

	glm::vec3 Pos;
	glm::vec3 Dir;
	glm::vec3 Up;
	glm::vec3 Right;

	glm::mat4 view;			// Matriz view para sacar la posición del observador
	glm::mat4 model;		// Posicion del objeto
	glm::vec3 velocidad;	// Velocidad del objeto
	glm::vec3 aceleracion;	// Aceleración del objeto
	float radioColision;	// Radio de colisión del coche

public:

	glm::mat4 ViewMatrix();
	glm::vec3 GetPosicion();
	glm::vec3 GetVelocidad();
	glm::vec3 GetAceleracion();

	float GetRadioColision();

	void PrintData();

	void SetPosicion(glm::vec3 pos);
	void SetVelocidad(glm::vec3 vel);
	void SetAceleracion(glm::vec3 acc);

	void ResetLocation();
	void Translate(glm::vec3 t);
	void MueveCoche();
	void Rotate(GLfloat angle, glm::vec3 axis);
	void SetLocation(glm::mat4 loc);
	glm::mat4 GetLocation();
	void Draw(CGShaderProgram* program, glm::mat4 projection, glm::mat4 view, glm::mat4 shadowViewMatrix);

	virtual int GetNumPieces() = 0;
	virtual CGFigure* GetPiece(int i) = 0;
};