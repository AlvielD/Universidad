#include "CGModel.h"
#include <GLFW\glfw3.h>
#include <iostream>

//
// FUNCI�N: CGModel::initialize(int, int)
//
// PROP�SITO: Inicializa el modelo 3D
//
void CGModel::initialize(int w, int h)
{
    // RGBA --> Red, Green, Blue, Alpha(Opacidad)
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    resize(w, h);
}

//
// FUNCI�N: CGModel::finalize()
//
// PROP�SITO: Libera los recursos del modelo 3D
//
void CGModel::finalize()
{
}

//
// FUNCI�N: CGModel::resize(int w, int h)
//
// PROP�SITO: Asigna el viewport y el clipping volume
//
void CGModel::resize(int w, int h)
{
    glViewport(0, 0, w, h);
}

//
// FUNCI�N: CGModel::render()
//
// PROP�SITO: Genera la imagen
//
void CGModel::render()
{
    glClear(GL_COLOR_BUFFER_BIT);
}

//
// FUNCI�N: CGModel::update()
//
// PROP�SITO: Anima la escena
//
void CGModel::update()
{
}

//
// FUNCI�N: CGModel::key_pressed(int)
//
// PROP�SITO: Respuesta a acciones de teclado
//
void CGModel::key_pressed(int key)
{
    switch (key)
    {
    case GLFW_KEY_R:
        glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        break;
    case GLFW_KEY_G:
        glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
        break;
    case GLFW_KEY_B:
        glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        break;
    case GLFW_KEY_W:
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        break;
    }
}

//
//  FUNCI�N: CGModel:::mouse_button(int button, int action)
//
//  PROP�SITO: Respuesta del modelo a un click del rat�n.
//
void CGModel::mouse_button(int button, int action)
{
}

//
//  FUNCI�N: CGModel::mouse_move(double xpos, double ypos)
//
//  PROP�SITO: Respuesta del modelo a un movimiento del rat�n.
//
void CGModel::mouse_move(double xpos, double ypos)
{
}