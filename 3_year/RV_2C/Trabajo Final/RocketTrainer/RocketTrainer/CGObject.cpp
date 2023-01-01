#include "CGObject.h"
#include <GL/glew.h>
#include <FreeImage.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <iostream>

glm::mat4 CGObject::ViewMatrix()
{
    // Creates a rotation matrix using vectors Dir, Up and Right
    glm::mat4 matrix(1.0f);
    matrix[0][0] = Right.x;
    matrix[1][0] = Right.y;
    matrix[2][0] = Right.z;
    matrix[3][0] = 0.0f;
    matrix[0][1] = Up.x;
    matrix[1][1] = Up.y;
    matrix[2][1] = Up.z;
    matrix[3][1] = 0.0f;
    matrix[0][2] = Dir.x;
    matrix[1][2] = Dir.y;
    matrix[2][2] = Dir.z;
    matrix[3][2] = 0.0f;
    matrix[0][3] = 0.0f;
    matrix[1][3] = 0.0f;
    matrix[2][3] = 0.0f;
    matrix[3][3] = 1.0f;

    return glm::translate(matrix, -Pos);
}

glm::vec3 CGObject::GetPosicion()
{
    GLfloat xPos = model[3][0];
    GLfloat yPos = model[3][1];
    GLfloat zPos = model[3][2];

    return glm::vec3(xPos, yPos, zPos);
}

glm::vec3 CGObject::GetVelocidad()
{
    return velocidad;
}

glm::vec3 CGObject::GetAceleracion()
{
    return aceleracion;
}

float CGObject::GetRadioColision()
{
    return radioColision;
}

void CGObject::SetPosicion(glm::vec3 pos)
{
    model[3][0] = pos.x;
    model[3][1] = pos.y;
    model[3][2] = pos.z;
}

void CGObject::SetVelocidad(glm::vec3 vel)
{
    velocidad = vel;
}

void CGObject::SetAceleracion(glm::vec3 acc)
{
    aceleracion = acc;
}

//
// FUNCIÓN: CGObject::ResetLocation()
//
// PROPÓSITO: Asigna la posición inicial del objecto 
//
void CGObject::ResetLocation()
{
    model = glm::mat4(1.0f);
}

//
// FUNCIÓN: CGObject::SetLocation(glm::mat4 loc)
//
// PROPÓSITO: Asigna la posición del objecto 
//
void CGObject::SetLocation(glm::mat4 loc)
{
    model = loc;
}

//
// FUNCIÓN: CGObject::GetLocation()
//
// PROPÓSITO: Obtiene la posición del objeto 
//
glm::mat4 CGObject::GetLocation()
{
    return model;
}

//
// FUNCIÓN: CGObject::Translate(glm::vec3 t)
//
// PROPÓSITO: Añade un desplazamiento a la matriz de posición del objeto 
//
void CGObject::Translate(glm::vec3 t)
{
    // Calculo de la posicion del coche
    model = glm::translate(model, t);
}

void CGObject::MueveCoche()
{
    // Calculo de la posicion del coche
    model = glm::translate(model, velocidad);

    // Recalculo de la posición de la cámara
    glm::vec3 posCoche = GetPosicion();

    Pos.x = posCoche.x;
    Pos.z = posCoche.z + 20.0f;
}

void CGObject::PrintData() {
    std::cout << "Posicion de la cámara: " << Pos.x << " " << Pos.y << " " << Pos.z << std::endl;
    std::cout << "Direccion de la cámara: " << Dir.x << " " << Dir.y << " " << Dir.z << std::endl;
    std::cout << "Posicion del coche: " << GetPosicion().x << " " << GetPosicion().y << " " << GetPosicion().z << std::endl;
    std::cout << "Velocidad del coche: " << velocidad.x << " " << velocidad.y << " " << velocidad.z << std::endl;
    std::cout << std::endl;
}

//
// FUNCIÓN: CGObject::Rotate(GLfloat angle, glm::vec3 axis)
//
// PROPÓSITO: Añade una rotación a la matriz de posición del objeto 
//
void CGObject::Rotate(GLfloat angle, glm::vec3 axis)
{
    model = glm::rotate(model, glm::radians(angle), axis);
    }

//
// FUNCIÓN: CGObject::Draw(CGShaderProgram * program, 
//                         glm::mat4 projection, glm::mat4 view)
//
// PROPÓSITO: Dibuja el objeto
//
void CGObject::Draw(CGShaderProgram* program, glm::mat4 projection, glm::mat4 view, glm::mat4 shadowViewMatrix)
{
    int num = GetNumPieces();
    for (int i = 0; i < num; i++)
    {
        GetPiece(i)->Draw(program, projection, view, model, shadowViewMatrix);
    }
}