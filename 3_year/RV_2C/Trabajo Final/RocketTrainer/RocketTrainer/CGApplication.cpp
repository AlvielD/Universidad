#include "CGApplication.h"
#include <string>

//
// FUNCI�N: CGApplication::run()
//
// PROP�SITO: Ejecuta la aplicaci�n
//
void CGApplication::run()
{
    initWindow();
    initOpenGL();
    initModel();
    mainLoop();
    cleanup();
}

bool CGApplication::juegoTerminado()
{
    return model.DetectaGolaso();
}

//
// FUNCI�N: CGApplication::initWindow()
//
// PROP�SITO: Inicializa la ventana
//
void CGApplication::initWindow()
{
    glfwInit();

    window = glfwCreateWindow(WIDTH, HEIGHT, "Computer Graphics", nullptr, nullptr);
    glfwSetWindowUserPointer(window, this);
    glfwSetFramebufferSizeCallback(window, framebufferResizeCallback);
    glfwSetKeyCallback(window, keyCallback);
    glfwSetCursorPosCallback(window, cursorPositionCallback);
    glfwSetMouseButtonCallback(window, mouseButtonCallback);
    glfwMakeContextCurrent(window);
}

//
// FUNCI�N: CGApplication::initOpenGL()
//
// PROP�SITO: Inicializa el entorno gr�fico
//
void CGApplication::initOpenGL()
{
    glewInit();
}

//
// FUNCI�N: CGApplication::initModel()
//
// PROP�SITO: Inicializa el modelo
//
void CGApplication::initModel()
{
    limitFPS = 1.0 / 60.0;
    lastTime = glfwGetTime();
    deltaTime = 0;

    int width, height;
    glfwGetFramebufferSize(window, &width, &height);
    model.initialize(width, height);
}

//
// FUNCI�N: CGApplication::mainLoop()
//
// PROP�SITO: Bucle principal que procesa los eventos de la aplicaci�n
//
void CGApplication::mainLoop()
{
    terminaJuego = false;
    while (!glfwWindowShouldClose(window) && !terminaJuego)
    {
        terminaJuego = juegoTerminado();
        glfwPollEvents();
        timing();
        glfwSwapBuffers(window);
    }
}

//
// FUNCI�N: CGApplication::timing()
//
// PROP�SITO: Renderizado
//
void CGApplication::timing()
{
    double nowTime = glfwGetTime();
    deltaTime += (nowTime - lastTime) / limitFPS;
    lastTime = nowTime;

    while (deltaTime >= 1.0)
    {
        model.update();
        deltaTime--;
    }
    model.render();
}

//
// FUNCI�N: CGApplication::cleanup()
//
// PROP�SITO: Libera los recursos y finaliza la aplicaci�n
//
void CGApplication::cleanup()
{
    model.finalize();
    glfwDestroyWindow(window);
    glfwTerminate();
}

//
// FUNCI�N: CGApplication::keyCallback(GLFWwindow* window, int key, int scancode, int action, int mods)
//
// PROP�SITO: Respuesta a un evento de teclado sobre la aplicaci�n
//
void CGApplication::keyCallback(GLFWwindow* window, int key, int scancode, int action, int mods)
{
    auto app = reinterpret_cast<CGApplication*>(glfwGetWindowUserPointer(window));
    if (action == GLFW_PRESS || action == GLFW_REPEAT) app->model.key_pressed(key);

    if (action == GLFW_RELEASE) app->model.key_released(key);
}

//
// FUNCI�N: CAApplication::mouseButtonCallback(GLFWwindow* window, int button, int action, int mods)
//
// PROP�SITO: Respuesta a un evento de rat�n sobre la aplicaci�n
//
void CGApplication::mouseButtonCallback(GLFWwindow* window, int button, int action, int mods)
{
    auto app = reinterpret_cast<CGApplication*>(glfwGetWindowUserPointer(window));
    app->model.mouse_button(button, action);
}

//
// FUNCI�N: CGApplication::cursorPositionCallback(GLFWwindow* window, double xpos, double ypos)
//
// PROP�SITO: Respuesta a un evento de movimiento del cursor sobre la aplicaci�n
//
void CGApplication::cursorPositionCallback(GLFWwindow* window, double xpos, double ypos)
{
    auto app = reinterpret_cast<CGApplication*>(glfwGetWindowUserPointer(window));
    app->model.mouse_move(xpos, ypos);
}

//
// FUNCI�N: CGApplication::framebufferResizeCallback(GLFWwindow* window, int width, int height)
//
// PROP�SITO: Respuesta a un evento de redimensionamiento de la ventana principal
//
void CGApplication::framebufferResizeCallback(GLFWwindow* window, int width, int height)
{
    auto app = reinterpret_cast<CGApplication*>(glfwGetWindowUserPointer(window));
    if (height != 0) app->model.resize(width, height);
}
