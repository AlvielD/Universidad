#pragma once

#include <GL/glew.h>
#include "CGFigure.h"

/*
* Porter�a de 'w' de ancho, 'h' de alto y postes de 'd' de grosor
*/
class Porteria : public CGFigure {
public:
	Porteria(GLfloat w, GLfloat h, GLfloat d);
};

