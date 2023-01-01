#include "CGLight.h"
#include <glm/glm.hpp>

//
// FUNCIÓN: CGLight::CGLight()
//
// PROPÓSITO: Construye una luz con los valores por defecto
//
CGLight::CGLight()
{
	Ldir = glm::vec3(0.0f, 0.0f, -1.0f);
	La = glm::vec3(1.0f, 1.0f, 1.0f);
	Ld = glm::vec3(1.0f, 1.0f, 1.0f);
	Ls = glm::vec3(0.0f, 0.0f, 0.0f);
}

//
// FUNCIÓN: CGLight::GetLightDirection()
//
// PROPÓSITO: Obtiene la dirección de la luz (expresada en coordenadas de modelo)
//
glm::vec3 CGLight::GetLightDirection()
{
	return Ldir;
}

//
// FUNCIÓN: CGLight::SetLightDirection(glm::vec3 d)
//
// PROPÓSITO: Asigna la dirección de la luz (expresada en coordenadas de modelo)
//
void CGLight::SetLightDirection(glm::vec3 d)
{
	Ldir = d;
}

//
// FUNCIÓN: CGLight::SetAmbientLight(glm::vec3 a)
//
// PROPÓSITO: Asigna el color de la componente ambiental
//
void CGLight::SetAmbientLight(glm::vec3 a)
{
	La = a;
}

//
// FUNCIÓN: CGLight::SetDifusseLight(glm::vec3 d)
//
// PROPÓSITO: Asigna el color de la componente difusa
//
void CGLight::SetDifusseLight(glm::vec3 d)
{
	Ld = d;
}

//
// FUNCIÓN: CGLight::SetSpecularLight(glm::vec3 s)
//
// PROPÓSITO: Asigna el color de la componente especular
//
void CGLight::SetSpecularLight(glm::vec3 s)
{
	Ls = s;
}

//
// FUNCIÓN: CGLight::SetUniforms(ShaderProgram* program)
//
// PROPÓSITO: Configura la luz en el programa gráfico
//
void CGLight::SetUniforms(CGShaderProgram* program)
{
	program->SetUniformVec3("Light.Ldir", Ldir);
	program->SetUniformVec3("Light.La", La);
	program->SetUniformVec3("Light.Ld", Ld);
	program->SetUniformVec3("Light.Ls", Ls);
}
