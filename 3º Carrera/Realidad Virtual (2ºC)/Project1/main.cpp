#include "CGApplication.h"
#include <iostream>
#include <stdexcept>

//
//// PROYECTO: Project1
//// DESCRIPCI�N: Creaci�n de una ventana con GLFW y un modelo b�sico 
// con las respuestas a los eventos de la ventana.
//
int main()
{
    CGApplication app;

    try
    {
        app.run();
    }
    catch (const std::exception& e)
    {
        std::cerr << e.what() << std::endl;
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}