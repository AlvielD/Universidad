#include "Porteria.h"
#include <GL/glew.h>
#include <math.h>
#include "CGFigure.h"

Porteria::Porteria(GLfloat w, GLfloat h, GLfloat d)
{
    numFaces = 2 * m * (p - 1); // Number of faces
    numVertices = (m + 1) * (p + 1); // Number of vertices
    normals = new GLfloat[numVertices * 3];
    textures = new GLfloat[numVertices * 2];
    vertices = new GLfloat[numVertices * 3];
    indexes = new GLushort[numFaces * 3];

    GLfloat p_normals[20][3] = {

    };

    GLfloat p_textures[20][2] = { // Array of texture coordinates

    };

    GLfloat p_vertices[20][3] = {
      { 0, 0, d }, // 0
      { d, 0, d }, // 1
      { 0, h, d }, // 2
      { d, h, d }, // 3
      { d, h-d, d }, // 4
      { w-d, h, d }, // 5
      { w-d, h-d, d }, // 6
      { w-d, 0, d }, // 7
      { w, h, d }, // 8
      { w, 0, d }, // 9
      { w, 0, 0 }, // 10
      { w, h, 0 }, // 11
      { w-d, 0, 0 }, // 12
      { w-d, h, 0 }, // 13
      { w-d, h-d, 0 }, // 14
      { d, h-d, 0 }, // 15
      { d, h, 0 }, // 16
      { d, 0, 0 }, // 17
      { 0, 0, 0 }, // 18
      { 0, h, 0 }  // 19
    };

    GLushort p_indexes[24][3]{
        {0, 1, 2},
        {2, 1, 3},
        {3, 4, 5},
        {5, 4, 6},
        {5, 7, 8},
        {8, 7, 9},
        {9, 10, 8},
        {8, 10, 11},
        {11, 10, 12},
        {12, 13, 11},
        {13, 14, 15},
        {13, 15, 16},
        {16, 17, 18},
        {16, 18, 19},
        {19, 18, 0},
        {19, 0, 2},
        {2, 8, 19},
        {19, 8, 11},
        {4, 15, 14},
        {14, 16, 4}
    };

    normals = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) normals[3 * i + j] = p_normals[i][j];
    vertices = new GLfloat[numVertices * 3];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 3; j++) vertices[3 * i + j] = p_vertices[i][j];
    indexes = new GLushort[numFaces * 3];
    for (int i = 0; i < numFaces; i++)
        for (int j = 0; j < 3; j++) indexes[3 * i + j] = p_indexes[i][j];
    textures = new GLfloat[numVertices * 2];
    for (int i = 0; i < numVertices; i++)
        for (int j = 0; j < 2; j++) textures[2 * i + j] = p_textures[i][j];

    InitBuffers();
}
