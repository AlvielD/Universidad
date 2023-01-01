#include "CGFog.h"
#include <glm/glm.hpp>

//
// FUNCI�N: CGFog::CGFog()
//
// PROP�SITO: Construye un descriptor de niebla con los valores por defecto
//
CGFog::CGFog()
{
	maxDist = 1000.0f;
	minDist = 100.0f;
	color = glm::vec3(1.0f, 1.0f, 1.0f);
}

//
// FUNCI�N: CGFog::SetMaxDistance(GLfloat d)
//
// PROP�SITO: Asigna la distancia a la que se satura la niebla
//
void CGFog::SetMaxDistance(GLfloat d)
{
	maxDist = d;
}

//
// FUNCI�N: CGFog::SetMinDistance(GLfloat d)
//
// PROP�SITO: Asigna la distancia a la que comienza el efecto de la niebla
//
void CGFog::SetMinDistance(GLfloat d)
{
	minDist = d;
}

//
// FUNCI�N: CGFog::SetFogColor(glm::vec3 c)
//
// PROP�SITO: Asigna el color de la niebla
//
void CGFog::SetFogColor(glm::vec3 c)
{
	color = c;
}

//
// FUNCI�N: CGFog::SetUniforms(CGShaderProgram* program)
//
// PROP�SITO: Configura el efecto de la niebla en el programa
//
void CGFog::SetUniforms(CGShaderProgram* program)
{
	program->SetUniformVec3("Fog.color", color);
	program->SetUniformF("Fog.maxDist", maxDist);
	program->SetUniformF("Fog.minDist", minDist);
}
