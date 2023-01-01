#pragma once

#include <GL/glew.h>
#include <glm/glm.hpp>
#include "CGShaderProgram.h"

class CGFog {

private:
	GLfloat maxDist;
	GLfloat minDist;
	glm::vec3 color;

public:
	CGFog();
	void SetMaxDistance(GLfloat d);
	void SetMinDistance(GLfloat d);
	void SetFogColor(glm::vec3 c);
	void SetUniforms(CGShaderProgram* program);
};

