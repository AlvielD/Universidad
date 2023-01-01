#include "CGLight.h"
#include <glm/glm.hpp>

//
// FUNCI�N: CGLight::CGLight()
//
// PROP�SITO: Construye una luz con los valores por defecto
//
CGLight::CGLight()
{
	Ldir = glm::vec3(0.0f, 0.0f, -1.0f);
	La = glm::vec3(1.0f, 1.0f, 1.0f);
	Ld = glm::vec3(1.0f, 1.0f, 1.0f);
	Ls = glm::vec3(0.0f, 0.0f, 0.0f);
}

//
// FUNCI�N: CGLight::GetLightDirection()
//
// PROP�SITO: Obtiene la direcci�n de la luz (expresada en coordenadas de modelo)
//
glm::vec3 CGLight::GetLightDirection()
{
	return Ldir;
}

//
// FUNCI�N: CGLight::SetLightDirection(glm::vec3 d)
//
// PROP�SITO: Asigna la direcci�n de la luz (expresada en coordenadas de modelo)
//
void CGLight::SetLightDirection(glm::vec3 d)
{
	Ldir = d;
}

//
// FUNCI�N: CGLight::SetAmbientLight(glm::vec3 a)
//
// PROP�SITO: Asigna el color de la componente ambiental
//
void CGLight::SetAmbientLight(glm::vec3 a)
{
	La = a;
}

//
// FUNCI�N: CGLight::SetDifusseLight(glm::vec3 d)
//
// PROP�SITO: Asigna el color de la componente difusa
//
void CGLight::SetDifusseLight(glm::vec3 d)
{
	Ld = d;
}

//
// FUNCI�N: CGLight::SetSpecularLight(glm::vec3 s)
//
// PROP�SITO: Asigna el color de la componente especular
//
void CGLight::SetSpecularLight(glm::vec3 s)
{
	Ls = s;
}

//
// FUNCI�N: CGLight::SetUniforms(ShaderProgram* program)
//
// PROP�SITO: Configura la luz en el programa gr�fico
//
void CGLight::SetUniforms(CGShaderProgram* program)
{
	program->SetUniformVec3("Light.Ldir", Ldir);
	program->SetUniformVec3("Light.La", La);
	program->SetUniformVec3("Light.Ld", Ld);
	program->SetUniformVec3("Light.Ls", Ls);
}
