#include "Pared.h"

Pared::Pared(GLfloat w, GLfloat h) {

	numFaces = 2;
	numVertices = 4;
	normals = new GLfloat[numVertices * 3];
	textures = new GLfloat[numVertices * 2];
	vertices = new GLfloat[numVertices * 3];
	indexes = new GLushort[numFaces * 3];

    GLfloat p_normals[4][3] = {
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f }
    };

    GLfloat p_textures[4][2] = { // Array of texture coordinates
        { 0.0f, 0.0f },
        { 1.0f, 0.0f },
        { 0.0f, 1.0f },
        { 1.0f, 1.0f }
    };

    GLfloat p_vertices[4][3] = {
        { 0, 0, 0 }, // 0
        { w, 0, 0 }, // 1
        { w, h, 0 }, // 2
        { 0, h, 0 }  // 3
    };

    GLushort p_indexes[2][3]{
        {0, 1, 2},
        {0, 2, 3}
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
