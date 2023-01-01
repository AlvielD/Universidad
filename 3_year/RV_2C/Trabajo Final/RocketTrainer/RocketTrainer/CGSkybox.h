#pragma once

#include <GL/glew.h>
#include <glm/glm.hpp>
#include "CGShaderProgram.h"

class CGSkybox {
public:
	CGSkybox();
	~CGSkybox();
	void Draw(CGShaderProgram* program, glm::mat4 projection, glm::mat4 view);

private:
	GLuint cubemap;
	GLuint VBO[4];
	GLuint VAO;

	void InitCube();
	void InitCubemap();
	void InitTexture(GLuint target, const char* filename);
};

