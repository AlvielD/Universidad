#pragma once

#include <GL/glew.h>
#include "CGFigure.h"

//
// CLASE: CGGround
//
// DESCRIPCI�N: Representa un cuadrado dibujado en el suelo
// 
class CGGround : public CGFigure {
public:
    CGGround(GLfloat l1, GLfloat l2);
};
