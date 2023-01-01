#include "CGGround.h"
#include <GL/glew.h>
#include "CGFigure.h"

///
/// FUNCION: CGGround::CGGround(GLfloat l1, GLfloat l2)
///
/// PROPÓSITO: Construye un rectángulo de lados 2*l1 y 2*l2.
///
CGGround::CGGround(GLfloat l1, GLfloat l2)
{
    numFaces = 2; // Number of faces
    numVertices = 4; // Number of vertices

    GLfloat p_normals[4][3] = {
      { 0.0f, 1.0f, 0.0f },
      { 0.0f, 1.0f, 0.0f },
      { 0.0f, 1.0f, 0.0f },
      { 0.0f, 1.0f, 0.0f }
    };

    GLfloat p_textures[4][2] = {
      { 1.0f, 0.5f },
      { 1.0f, 1.0f },
      { 0.0f, 1.0f },
      { 0.0f, 0.5f }
    };

    GLfloat p_vertices[4][3] = {
      { l1, 0.0f, l2 },
      { l1, 0.0f, -l2 },
      { -l1, 0.0f, -l2 },
      { -l1, 0.0f, l2 }
    };

    GLushort p_indexes[2][3] = {
      { 0, 1, 2 },
      { 0, 2, 3 }
    };

    normals = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) normals[3 * i + j] = p_normals[i][j];
    vertices = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) vertices[3 * i + j] = p_vertices[i][j];
    textures = new GLfloat[numVertices * 2];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 2; j++) textures[2 * i + j] = p_textures[i][j];
    indexes = new GLushort[numFaces * 3];
    for (int i = 0; i < numFaces; i++)
        for (int j = 0; j < 3; j++) indexes[3 * i + j] = p_indexes[i][j];

    InitBuffers();
}