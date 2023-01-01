#pragma once

#include <glm/glm.hpp>
#include "CGShaderProgram.h"

class CGLight {

private:
	glm::vec3 Ldir; // Light direction
	glm::vec3 La; // Ambient intensity
	glm::vec3 Ld; // Difusse intensity
	glm::vec3 Ls; // Specular intensity

public:
	CGLight();
	glm::vec3 GetLightDirection();
	void SetLightDirection(glm::vec3 d);
	void SetAmbientLight(glm::vec3 a);
	void SetDifusseLight(glm::vec3 d);
	void SetSpecularLight(glm::vec3 s);
	void SetUniforms(CGShaderProgram* program);
};
