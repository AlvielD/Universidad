#pragma once

#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include "CGModel.h"

const int WIDTH = 800;
const int HEIGHT = 600;

/*
*	Clase principal de la aplicación que será la encargada de crear y gestionar la ventana sobre
*	la que generaremos los gráficos. 
*/
class CGApplication
{
public:
	/*
	*	MÉTODO ENCARGADO DE EJECUTAR LA APLICACIÓN COMPLETA
	* 
	*	Inicializa la aplicación y permanece en un bucle de gestión de eventos de la ventana
	*	hasta recibir la orden de cierre de la apliación
	*/
	void run();

private:
	GLFWwindow* window;	// Referencia a la ventana
	CGModel model;		// Referencia al modelo gráfico

	double limitFPS;	// Frecuencia de muestreo utilizada (frames per second)
	double lastTime;	// Instante en el que se está ejecutando el modelo
	double deltaTime;	// Tiempo desde la última vez que el modelo fue actualizado

	// Métodos principales
	void initWindow();	// Encargado de crear y configurar la ventana mediante funciones de GLFW
	void initOpenGL();	// Inicializa la biblioteca GLEW para poder comenzar a utilizar las funciones de OpenGL
	void initModel();	// Inicializa los campos de control de muestreo y crea el modelo gráfico
	void mainLoop();	// Bucle principal de la aplicación encargado de recibir y gestionar los eventos de la ventana
	void timing();		// Encargado de la temporización del modelo gráfico
	void cleanup();		// Se ejecuta al cierre de la aplicación y se encarga de liberar el modelo y la ventan

	// Respuesta a eventos
	static void keyCallback(GLFWwindow* window, int key, int scan, int act, int mods);
	static void mouseButtonCallback(GLFWwindow* window, int bt, int action, int mods);
	static void cursorPositionCallback(GLFWwindow* window, double xpos, double ypos);
	static void framebufferResizeCallback(GLFWwindow* window, int width, int height);
};