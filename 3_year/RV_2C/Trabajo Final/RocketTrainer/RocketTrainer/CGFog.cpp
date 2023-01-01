#include "CGFog.h"
#include <glm/glm.hpp>

//
// FUNCIÓN: CGFog::CGFog()
//
// PROPÓSITO: Construye un descriptor de niebla con los valores por defecto
//
CGFog::CGFog()
{
	maxDist = 1000.0f;
	minDist = 100.0f;
	color = glm::vec3(1.0f, 1.0f, 1.0f);
}

//
// FUNCIÓN: CGFog::SetMaxDistance(GLfloat d)
//
// PROPÓSITO: Asigna la distancia a la que se satura la niebla
//
void CGFog::SetMaxDistance(GLfloat d)
{
	maxDist = d;
}

//
// FUNCIÓN: CGFog::SetMinDistance(GLfloat d)
//
// PROPÓSITO: Asigna la distancia a la que comienza el efecto de la niebla
//
void CGFog::SetMinDistance(GLfloat d)
{
	minDist = d;
}

//
// FUNCIÓN: CGFog::SetFogColor(glm::vec3 c)
//
// PROPÓSITO: Asigna el color de la niebla
//
void CGFog::SetFogColor(glm::vec3 c)
{
	color = c;
}

//
// FUNCIÓN: CGFog::SetUniforms(CGShaderProgram* program)
//
// PROPÓSITO: Configura el efecto de la niebla en el programa
//
void CGFog::SetUniforms(CGShaderProgram* program)
{
	program->SetUniformVec3("Fog.color", color);
	program->SetUniformF("Fog.maxDist", maxDist);
	program->SetUniformF("Fog.minDist", minDist);
}
