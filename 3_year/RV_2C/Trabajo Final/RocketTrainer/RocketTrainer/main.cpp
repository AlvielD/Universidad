#include "CGApplication.h"
#include <iostream>
#include <stdexcept>

//
//// PROYECTO: Project7
//// DESCRIPCI�N: Aplicaci�n gr�fica que introduce la textura de fondo (Skybox) y el efecto niebla
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