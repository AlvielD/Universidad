#include <GL/glew.h>
#include <SDKDDKVer.h>
#include <Windows.h>
#include "CGShaderProgram.h"
#include "resource.h"

//
// FUNCI�N: CGShaderProgram::CGShaderProgram()
//
// PROP�SITO: Crea un programa gr�fico cargando y compilando los shaders que lo forman
//
CGShaderProgram::CGShaderProgram()
{
	GLint status;
	logInfo = NULL;

	// Crea y compila el vertex shader
	vertexShader = glCreateShader(GL_VERTEX_SHADER);
	glShaderSource(vertexShader, 1, GetShaderCodeFromResource(IDR_HTML1), NULL);
	glCompileShader(vertexShader);
	glGetShaderiv(vertexShader, GL_COMPILE_STATUS, &status);
	if (status == GL_FALSE)
	{
		linked = GL_FALSE;
		GLint logLength;
		glGetShaderiv(vertexShader, GL_INFO_LOG_LENGTH, &logLength);
		logInfo = (char*)malloc(sizeof(char) * logLength);
		GLsizei written;
		glGetShaderInfoLog(vertexShader, logLength, &written, logInfo);
		return;
	}

	// Crea y compila el fragment shader
	fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
	glShaderSource(fragmentShader, 1, GetShaderCodeFromResource(IDR_HTML2), NULL);
	glCompileShader(fragmentShader);
	glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &status);
	if (status == GL_FALSE)
	{
		linked = GL_FALSE;
		GLint logLength;
		glGetShaderiv(fragmentShader, GL_INFO_LOG_LENGTH, &logLength);
		logInfo = (char*)malloc(sizeof(char) * logLength);
		GLsizei written;
		glGetShaderInfoLog(fragmentShader, logLength, &written, logInfo);
		return;
	}

	//Crea y enlaza el programa
	program = glCreateProgram();
	glAttachShader(program, vertexShader);
	glAttachShader(program, fragmentShader);
	glLinkProgram(program);
	glGetProgramiv(program, GL_LINK_STATUS, &status);
	if (status == GL_FALSE)
	{
		linked = GL_FALSE;
		GLint logLength;
		glGetProgramiv(program, GL_INFO_LOG_LENGTH, &logLength);
		logInfo = (char*)malloc(sizeof(char) * logLength);
		GLsizei written;
		glGetProgramInfoLog(program, logLength, &written, logInfo);
		return;
	}

	linked = GL_TRUE;
}

//
// FUNCI�N: CGShaderProgram::GetShaderCodeFromResource(int idr)
//
// PROP�SITO: Obtiene el contenido de un fichero de texto almacenado como un recurso
//
char** CGShaderProgram::GetShaderCodeFromResource(int idr)
{
	HRSRC shaderHandle = FindResource(NULL, MAKEINTRESOURCE(idr), RT_HTML);
	HGLOBAL shaderGlobal = LoadResource(NULL, shaderHandle);
	LPCTSTR shaderPtr = static_cast<LPCTSTR>(LockResource(shaderGlobal));
	DWORD shaderSize = SizeofResource(NULL, shaderHandle);
	char* shaderCodeLine = (char*)malloc((shaderSize + 1) * sizeof(char));
	memcpy(shaderCodeLine, shaderPtr, shaderSize);
	shaderCodeLine[shaderSize] = '\0';
	char** shaderCode = (char**)malloc(sizeof(char*));
	shaderCode[0] = shaderCodeLine;
	FreeResource(shaderGlobal);
	return shaderCode;
}

//
// FUNCI�N: CGShaderProgram::~CGShaderProgram()
//
// PROP�SITO: Destruye el programa gr�fico
//
CGShaderProgram::~CGShaderProgram()
{
	glDeleteShader(vertexShader);
	glDeleteShader(fragmentShader);
	glDeleteProgram(program);
}

//
// FUNCI�N: CGShaderProgram::IsLinked()
//
// PROP�SITO: Verifica si el programa se ha compilado y linkado de forma correcta
//
GLboolean CGShaderProgram::IsLinked()
{
	return linked;
}

//
// FUNCI�N: CGShaderProgram::GetLog()
//
// PROP�SITO: Obtiene el mensaje de error
//
char* CGShaderProgram::GetLog()
{
	return logInfo;
}

//
// FUNCI�N: CGShaderProgram::Use()
//
// PROP�SITO: Activa el funcionamiento del programa en la tarjeta gr�fica
//
GLvoid CGShaderProgram::Use()
{
	glUseProgram(program);
}

//
// FUNCI�N: CGShaderProgram::SetUniformF(const char* name, GLfloat f)
//
// PROP�SITO: Asigna el valor de una variable uniforme de tipo float
//
void CGShaderProgram::SetUniformF(const char* name, GLfloat f)
{
	GLuint location = glGetUniformLocation(program, name);
	if (location >= 0) glUniform1f(location, f);
}

//
// FUNCI�N: CGShaderProgram::SetUniformMatrix4(const char* name, glm::mat4 m)
//
// PROP�SITO: Asigna el valor de una variable uniforme de tipo mat4 (matriz 4x4)
//
GLvoid CGShaderProgram::SetUniformMatrix4(const char* name, glm::mat4 m)
{
	GLuint location = glGetUniformLocation(program, name);
	if (location >= 0) glUniformMatrix4fv(location, 1, GL_FALSE, &m[0][0]);
}

//
// FUNCI�N: CGShaderProgram::SetUniformVec4(const char* name, glm::vec4 v)
//
// PROP�SITO: Asigna el valor de una variable uniforme de tipo vec4 (vector de 4 float)
//
void CGShaderProgram::SetUniformVec4(const char* name, glm::vec4 v)
{
	GLuint location = glGetUniformLocation(program, name);
	if (location >= 0) glUniform4fv(location, 1, &v[0]);
}

//
// FUNCI�N: CGShaderProgram::SetUniformVec3(const char* name, glm::vec3 v)
//
// PROP�SITO: Asigna el valor de una variable uniforme de tipo vec3 (vector de 3 float)
//
void CGShaderProgram::SetUniformVec3(const char* name, glm::vec3 v)
{
	GLuint location = glGetUniformLocation(program, name);
	if (location >= 0) glUniform3fv(location, 1, &v[0]);
}

//
// FUNCI�N: CGShaderProgram::SetUniformI(const char* name, GLint i)
//
// PROP�SITO: Asigna el valor de una variable uniforme de tipo entero
//
void CGShaderProgram::SetUniformI(const char* name, GLint i)
{
	GLuint location = glGetUniformLocation(program, name);
	if (location >= 0) glUniform1i(location, i);
}

//
// FUNCI�N: CGShaderProgram::SetVertexShaderUniformSubroutine(const char * name)
//
// PROP�SITO: Asigna una subrutina en el VertexShader
//
GLvoid CGShaderProgram::SetVertexShaderUniformSubroutine(const char* name)
{
	GLuint location = glGetSubroutineIndex(program, GL_VERTEX_SHADER, name);
	if (location >= 0) glUniformSubroutinesuiv(GL_VERTEX_SHADER, 1, &location);
}

//
// FUNCI�N: CGShaderProgram::SetFragmentShaderUniformSubroutine(const char * name)
//
// PROP�SITO: Asigna una subrutina en el FragmentShader
//
GLvoid CGShaderProgram::SetFragmentShaderUniformSubroutine(const char* name)
{
	GLuint location = glGetSubroutineIndex(program, GL_FRAGMENT_SHADER, name);
	if (location >= 0) glUniformSubroutinesuiv(GL_FRAGMENT_SHADER, 1, &location);
}
