#include "CGSkybox.h"
#include <GL/glew.h>
#include <FreeImage.h>
#include "CGFigure.h"

CGSkybox::CGSkybox()
{
	InitCubemap();
	InitCube();
}

CGSkybox::~CGSkybox()
{
	// Delete buffers
	glDeleteBuffers(4, VBO);
	glDeleteVertexArrays(1, &VAO);
	glDeleteTextures(1, &cubemap);
}


void CGSkybox::InitCube()
{
	GLfloat vertices[12] = {
		-1.0f, -1.0f, -1.0f,
		1.0f, -1.0f, -1.0f,
		1.0f, 1.0f, -1.0f,
		-1.0f, 1.0f, -1.0f
	};
	GLfloat normals[12] = {
		0.0f, 0.0f, 1.0f,
		0.0f, 0.0f, 1.0f,
		0.0f, 0.0f, 1.0f,
		0.0f, 0.0f, 1.0f
	};
	GLfloat textures[8] = {
		-1.0f, -1.0f,
		1.0f, -1.0f,
		1.0f, 1.0f,
		-1.0f, 1.0f,
	};
	GLushort indexes[6] = {
		0,1,2,
		0,2,3
	};

	// Create the Vertex Array Object
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);

	// Create the Vertex Buffer Objects
	glGenBuffers(4, VBO);

	// Copy data to video memory
	// Vertex data
	glBindBuffer(GL_ARRAY_BUFFER, VBO[VERTEX_DATA]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * 12, vertices, GL_STATIC_DRAW);

	// Normal data
	glBindBuffer(GL_ARRAY_BUFFER, VBO[NORMAL_DATA]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * 12, normals, GL_STATIC_DRAW);

	// Texture coordinates
	glBindBuffer(GL_ARRAY_BUFFER, VBO[TEXTURE_DATA]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * 8, textures, GL_STATIC_DRAW);

	// Indexes
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VBO[INDEX_DATA]);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLushort) * 6, indexes, GL_STATIC_DRAW);

	glEnableVertexAttribArray(0); // Vertex position
	glBindBuffer(GL_ARRAY_BUFFER, VBO[VERTEX_DATA]);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);

	glEnableVertexAttribArray(1); // Vertex normals
	glBindBuffer(GL_ARRAY_BUFFER, VBO[NORMAL_DATA]);
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, 0);

	glEnableVertexAttribArray(2); // Vertex textures
	glBindBuffer(GL_ARRAY_BUFFER, VBO[TEXTURE_DATA]);
	glVertexAttribPointer(2, 3, GL_FLOAT, GL_FALSE, 0, 0);
}

void CGSkybox::InitCubemap()
{
	glActiveTexture(GL_TEXTURE1);

	glGenTextures(1, &cubemap);
	glBindTexture(GL_TEXTURE_CUBE_MAP, cubemap);
	InitTexture(GL_TEXTURE_CUBE_MAP_POSITIVE_X, "textures/posx.jpg");
	InitTexture(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, "textures/negx.jpg");
	InitTexture(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, "textures/posy.jpg");
	InitTexture(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, "textures/negy.jpg");
	InitTexture(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, "textures/posz.jpg");
	InitTexture(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, "textures/negz.jpg");

	// Typical cube map settings
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
}

void CGSkybox::InitTexture(GLuint target, const char* filename)
{
	FREE_IMAGE_FORMAT format = FreeImage_GetFileType(filename, 0);
	FIBITMAP* bitmap = FreeImage_Load(format, filename);
	FIBITMAP* pImage = FreeImage_ConvertTo32Bits(bitmap);
	int nWidth = FreeImage_GetWidth(pImage);
	int nHeight = FreeImage_GetHeight(pImage);

	glTexImage2D(target, 0, GL_RGBA8, nWidth, nHeight,
		0, GL_BGRA, GL_UNSIGNED_BYTE, (void*)FreeImage_GetBits(pImage));

	FreeImage_Unload(pImage);
}

void CGSkybox::Draw(CGShaderProgram* program, glm::mat4 projection, glm::mat4 view)
{
	glm::mat3 rot3 = glm::mat3(view); // Parte rotacional de la matriz View
	glm::mat4 rot4 = glm::mat4(rot3);
	glm::mat4 mvp = projection * rot4; // Transformación del Skybox a coordenadas Clip
	glm::mat4 inv = glm::inverse(mvp); // Transformación de coordenadas Clip a coordenadas de modelo del Skybox

	program->SetUniformMatrix4("MVP", inv);
	program->SetUniformMatrix4("ViewMatrix", view);
	program->SetUniformMatrix4("ModelViewMatrix", view);
	program->SetUniformI("CubemapTex", 1);
	program->SetUniformI("DrawSkybox", 1);

	glDepthMask(GL_FALSE);
	glActiveTexture(GL_TEXTURE1);
	glBindTexture(GL_TEXTURE_CUBE_MAP, cubemap);

	glBindVertexArray(VAO);
	glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, NULL);
	program->SetUniformI("DrawSkybox", 0);
	glDepthMask(GL_TRUE);
}