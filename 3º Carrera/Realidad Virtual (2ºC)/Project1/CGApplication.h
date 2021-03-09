#pragma once

#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include "CGModel.h"

const int WIDTH = 800;
const int HEIGHT = 600;

/*
*	Clase principal de la aplicaci�n que ser� la encargada de crear y gestionar la ventana sobre
*	la que generaremos los gr�ficos. 
*/
class CGApplication
{
public:
	/*
	*	M�TODO ENCARGADO DE EJECUTAR LA APLICACI�N COMPLETA
	* 
	*	Inicializa la aplicaci�n y permanece en un bucle de gesti�n de eventos de la ventana
	*	hasta recibir la orden de cierre de la apliaci�n
	*/
	void run();

private:
	GLFWwindow* window;	// Referencia a la ventana
	CGModel model;		// Referencia al modelo gr�fico

	double limitFPS;	// Frecuencia de muestreo utilizada (frames per second)
	double lastTime;	// Instante en el que se est� ejecutando el modelo
	double deltaTime;	// Tiempo desde la �ltima vez que el modelo fue actualizado

	// M�todos principales
	void initWindow();	// Encargado de crear y configurar la ventana mediante funciones de GLFW
	void initOpenGL();	// Inicializa la biblioteca GLEW para poder comenzar a utilizar las funciones de OpenGL
	void initModel();	// Inicializa los campos de control de muestreo y crea el modelo gr�fico
	void mainLoop();	// Bucle principal de la aplicaci�n encargado de recibir y gestionar los eventos de la ventana
	void timing();		// Encargado de la temporizaci�n del modelo gr�fico
	void cleanup();		// Se ejecuta al cierre de la aplicaci�n y se encarga de liberar el modelo y la ventan

	// Respuesta a eventos
	static void keyCallback(GLFWwindow* window, int key, int scan, int act, int mods);
	static void mouseButtonCallback(GLFWwindow* window, int bt, int action, int mods);
	static void cursorPositionCallback(GLFWwindow* window, double xpos, double ypos);
	static void framebufferResizeCallback(GLFWwindow* window, int width, int height);
};