#include "Porteria.h"
#include <GL/glew.h>
#include <math.h>
#include "CGFigure.h"

Porteria::Porteria(GLfloat w, GLfloat h, GLfloat d)
{
    numFaces = 24;
    numVertices = 44;
    normals = new GLfloat[numVertices * 3];
    textures = new GLfloat[numVertices * 2];
    vertices = new GLfloat[numVertices * 3];
    indexes = new GLushort[numFaces * 3];

    GLfloat p_normals[44][3] = {
        // Cara delantera
        { 0.0f, 0.0f, 1.0f },    // 0
        { 0.0f, 0.0f, 1.0f },    // 1
        { 0.0f, 0.0f, 1.0f },    // 2
        { 0.0f, 0.0f, 1.0f },    // 3
        { 0.0f, 0.0f, 1.0f },    // 4
        { 0.0f, 0.0f, 1.0f },    // 5
        { 0.0f, 0.0f, 1.0f },    // 6
        { 0.0f, 0.0f, 1.0f },    // 7
        { 0.0f, 0.0f, 1.0f },    // 8
        { 0.0f, 0.0f, 1.0f },    // 9
        // Cara trasera
        { 0.0f, 0.0f, -1.0f },   // 10
        { 0.0f, 0.0f, -1.0f },   // 11
        { 0.0f, 0.0f, -1.0f },   // 12
        { 0.0f, 0.0f, -1.0f },   // 13
        { 0.0f, 0.0f, -1.0f },   // 14
        { 0.0f, 0.0f, -1.0f },   // 15
        { 0.0f, 0.0f, -1.0f },   // 16
        { 0.0f, 0.0f, -1.0f },   // 17
        { 0.0f, 0.0f, -1.0f },   // 18
        { 0.0f, 0.0f, -1.0f },   // 19
        // Palo izquierdo - Lateral exterior
        { -1.0f, 0.0f, 0.0f },   // 20
        { -1.0f, 0.0f, 0.0f },   // 21
        { -1.0f, 0.0f, 0.0f },   // 22
        { -1.0f, 0.0f, 0.0f },   // 23
        // Palo izquierdo - Lateral interior
        { 1.0f, 0.0f, 0.0f },    // 24
        { 1.0f, 0.0f, 0.0f },    // 25
        { 1.0f, 0.0f, 0.0f },    // 26
        { 1.0f, 0.0f, 0.0f },    // 27
        // Palo derecho - Lateral interior
        { -1.0f, 0.0f, 0.0f },   // 28
        { -1.0f, 0.0f, 0.0f },   // 29
        { -1.0f, 0.0f, 0.0f },   // 30
        { -1.0f, 0.0f, 0.0f },   // 31
        // Palo derecho - Lateral exterior
        { 1.0f, 0.0f, 0.0f },    // 32
        { 1.0f, 0.0f, 0.0f },    // 33
        { 1.0f, 0.0f, 0.0f },    // 34
        { 1.0f, 0.0f, 0.0f },    // 35
        // Palo superior - Cara superior
        { 0.0f, 1.0f, 0.0f },    // 36
        { 0.0f, 1.0f, 0.0f },    // 37
        { 0.0f, 1.0f, 0.0f },    // 38
        { 0.0f, 1.0f, 0.0f },    // 39
        // Palo superior - Cara inferior
        { 0.0f, -1.0f, 0.0f },   // 40
        { 0.0f, -1.0f, 0.0f },   // 41
        { 0.0f, -1.0f, 0.0f },   // 42
        { 0.0f, -1.0f, 0.0f },   // 43
    };

    GLfloat p_textures[44][2] = { // Array of texture coordinates
        // Cara delantera
        { 0.0f, 0.0f },         // 0
        { 0.0518f, 0.0f },      // 1
        { 0.0f, 1.0f },         // 2
        { 0.0518f, 1.0f },      // 3
        { 0.0518f, 0.9482f },   // 4
        { 0.9482f, 1.0f },      // 5
        { 0.9482f, 0.9482f },   // 6
        { 1.0f, 1.0f },         // 7
        { 0.9482f, 0.0f },      // 8
        { 1.0f, 0.0f },         // 9
        // Cara trasera
        { 0.0f, 0.0f },         // 10
        { 0.0518f, 0.0f },      // 11
        { 0.0f, 1.0f },         // 12
        { 0.0518f, 1.0f },      // 13
        { 0.0518f, 0.9482f },   // 14
        { 0.9482f, 1.0f },      // 15
        { 0.9482f, 0.9482f },   // 16
        { 1.0f, 1.0f },         // 17
        { 0.9482f, 0.0f },      // 18
        { 1.0f, 0.0f },         // 19
        // Palo izquierdo - Lateral exterior
        { 0.0f, 0.0f },         // 20
        { 0.0518f, 0.0f },      // 21
        { 0.0f, 1.0f },         // 22
        { 0.0518f, 1.0f },      // 23
        // Palo izquierdo - Lateral interior
        { 0.0f, 0.0f },         // 24
        { 0.0518f, 0.0f },      // 25
        { 0.0f, 0.9482f },      // 26
        { 0.0518f, 0.9482f },   // 27
        // Palo derecho - Lateral interior
        { 0.0f, 0.0f },         // 28
        { 0.0518f, 0.0f },      // 29
        { 0.0f, 0.9482f },      // 30
        { 0.0518f, 0.9482f },   // 31
        // Palo derecho - Lateral exterior
        { 0.0f, 0.0f },         // 32
        { 0.0518f, 0.0f },      // 33
        { 0.0f, 1.0f },         // 34
        { 0.0518f, 1.0f },      // 35
        // Palo superior - Cara superior
        { 0.0f, 0.9482f },      // 36
        { 0.0f, 1.0f },         // 37
        { 1.0f, 1.0f },         // 38
        { 1.0f, 0.9482f },      // 39
        // Palo superior - Cara inferior
        { 0.0518f, 0.9482f },   // 40
        { 0.0518f, 1.0f },      // 41
        { 0.9482f, 1.0f },      // 42
        { 0.9482f, 0.9482f }    // 43
    };

    GLfloat p_vertices[44][3] = {
      // Cara delantera
      { 0, 0, d },              // 0
      { d, 0, d },              // 1
      { 0, h, d },              // 2
      { d, h, d },              // 3
      { d, h - d, d },          // 4
      { w - d, h, d },          // 5
      { w - d, h - d, d },      // 6
      { w, h, d },              // 7
      { w - d, 0, d },          // 8
      { w, 0, d },              // 9
      // Cara trasera
      { w, 0, 0 },              // 10
      { w - d, 0, 0 },          // 11
      { w, h, 0 },              // 12
      { w - d, h, 0 },          // 13
      { w - d, h - d, 0 },      // 14
      { d, h, 0 },              // 15
      { d, h - d, 0 },          // 16
      { 0, h, 0 },              // 17
      { d, 0, 0 },              // 18
      { 0, 0, 0 },              // 19
      // Palo izquierdo - Lateral exterior
      { 0, 0, 0 },              // 20
      { 0, 0, d },              // 21
      { 0, h, 0 },              // 22
      { 0, h, d },              // 23
      // Palo izquierdo - Lateral interior
      { d, 0, d },              // 24
      { d, 0, 0 },              // 25
      { d, h - d, d },          // 26
      { d, h - d, 0 },          // 27
      // Palo derecho - Lateral interior
      { w - d, 0, 0 },          // 28
      { w - d, 0, d },          // 29
      { w - d, h - d, 0 },      // 30
      { w - d, h - d, d },      // 31
      // Palo derecho - Lateral exterior
      { w, 0, d },              // 32
      { w, 0, 0 },              // 33
      { w, h, d },              // 34
      { w, h, 0 },              // 35
      // Palo superior - Cara superior
      { 0, h, d },              // 36
      { 0, h, 0 },              // 37
      { w, h, 0 },              // 38
      { w, h, d },              // 39
      // Palo superior - Cara inferior
      { d, h - d, 0 },
      { d, h - d, d },
      { w - d, h - d, d },
      { w - d, h - d, 0 }
    };

    GLushort p_indexes[24][3]{
        // Cara delantera
        {0, 1, 2},
        {2, 1, 3},
        {3, 4, 5},
        {5, 4, 6},
        {5, 8, 7},
        {7, 8, 9},
        // Cara trasera
        {10, 11, 12},
        {12, 11, 13},
        {13, 14, 15},
        {15, 14, 16},
        {15, 18, 17},
        {17, 18, 19},
        // Palo izquierdo - Lateral exterior
        {20, 21, 22},
        {22, 21, 23},
        // Palo izquierdo - Lateral interior
        {24, 25, 26},
        {26, 25, 27},
        // Palo derecho - Lateral interior
        {28, 29, 30},
        {30, 29, 31},
        // Palo derecho - Lateral exterior
        {34, 32, 33},
        {34, 33, 35},
        // Palo superior - Cara superior
        {36, 38, 37},
        {38, 36, 39},
        // Palo superior - Cara inferior
        {40, 42, 41},
        {42, 40, 43}
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
