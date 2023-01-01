#pragma once

#include <GL/glew.h>
#include "CGFigure.h"

//
// CLASE: CGSphere
//
// DESCRIPCI�N: Representa una esfera de radio 'r', dividida en 'p' 
//              capas (paralelos) y 'm' l�neas (meridianos).
//
class CGSphere : public CGFigure {
public:
	CGSphere(GLint p, GLint m, GLfloat r);
};
