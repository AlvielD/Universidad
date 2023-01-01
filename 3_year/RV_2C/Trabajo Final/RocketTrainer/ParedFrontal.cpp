#include "ParedFrontal.h"

ParedFrontal::ParedFrontal(GLfloat w, GLfloat h, GLfloat pw, GLfloat ph) {

    numFaces = 2;
    numVertices = 4;
    normals = new GLfloat[numVertices * 3];
    textures = new GLfloat[numVertices * 2];
    vertices = new GLfloat[numVertices * 3];
    indexes = new GLushort[numFaces * 3];

    GLfloat p_normals[10][3] = {
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f }.
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f },
        { 0.0f, 0.0f, 1.0f }
    };

    GLfloat p_textures[4][2] = { // Array of texture coordinates
        { 0.0f, 0.0f },
        {},
        { 0.0f, 1.0f },
        {},
        {},
        {},
        {},
        {},
        { 1.0f, 0.0f },
        { 1.0f, 1.0f }
    };

    GLfloat p_vertices[10][3] = {
        { 0, 0, 0 },                    // 0
        { (w - pw / 2), 0, 0 },         // 1
        { h, 0, 0 },                    // 2
        { (w - pw / 2), h, 0 },         // 3
        { (w - pw / 2), ph, 0 },        // 4
        { (w - pw / 2) + pw, ph, 0 },   // 5
        { (w - pw / 2) + pw, h, 0 },    // 6
        { (w - pw / 2) + pw, 0, 0 },    // 7
        { w, 0, 0 },                    // 8
        { w, h, 0 }                     // 9
    };

    GLushort p_indexes[6][3]{
        {0, 1, 2},
        {2, 1, 3},
        {3, 4, 5},
        {3, 5, 6},
        {6, 7, 8},
        {6, 8, 9}
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