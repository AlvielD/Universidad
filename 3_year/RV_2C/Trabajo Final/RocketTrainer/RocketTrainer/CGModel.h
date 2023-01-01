#pragma once

#include <GL/glew.h>
#include "CGShaderProgram.h"
#include "CGScene.h"

class CGModel
{
public:
	bool DetectaGolaso();
	void initialize(int w, int h);
	void finalize();
	void render();
	void update();
	void key_pressed(int key);
	void key_released(int key);
	void mouse_button(int button, int action);
	void mouse_move(double xpos, double ypos);
	void resize(int w, int h);

private:
	CGShaderProgram* program;
	CGScene* scene;
	glm::mat4 projection;
	GLsizei wndWidth;
	GLsizei wndHeight;
	GLuint shadowFBO;
	GLuint depthTexId;

	// Restricciones
	void PelotaConstraints();
	void CocheConstraints();

	// Actualización de la escena
	void ColisionPelotaCoche();
	void ActualizaVel();
	void ActualizaAcc();

	bool InitShadowMap();
};
