#include "CGApplication.h"

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

//
// FUNCI�N: CGApplication::initWindow()
//
// PROP�SITO: Inicializa la ventana
//
void CGApplication::initWindow()
{
    glfwInit(); // Inicializa la biblioteca encargada del manejo de la ventana

    window = glfwCreateWindow(WIDTH, HEIGHT, "Computer Graphics", nullptr, nullptr); // Crea la ventana
    glfwSetWindowUserPointer(window, this); // Obtenemos una referencia para la ventana

    // Indicamos los controladores para los eventos
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
//  Tener dos buffers nos permite tener uno en el que generamos la im�gen y otro en el que la
//  mostramos, de esa forma, solo mostraremos la im�gen una vez est� generada
//
//  SwapBuffers nos permite intercambiar los buffers y actualizar as� la im�gen
//
void CGApplication::mainLoop()
{
    while (!glfwWindowShouldClose(window))
    {
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
    // Liberar la memoria de la tarjeta gr�fica
    model.finalize();   // Liberar los recursos del modelo 
    glfwDestroyWindow(window);
    glfwTerminate();    // Liberar los recursos de la librer�a GLFW
}

//
// FUNCI�N: CGApplication::keyCallback(GLFWwindow* window, int key, int scancode, 
//                                                         int action, int mods)
//
// PROP�SITO: Respuesta a un evento de teclado sobre la aplicaci�n
//
void CGApplication::keyCallback(GLFWwindow* window, int key, int scancode,
    int action, int mods)
{
    auto app = reinterpret_cast<CGApplication*>(glfwGetWindowUserPointer(window));
    if (action == GLFW_PRESS || action == GLFW_REPEAT) app->model.key_pressed(key);
}

//
// FUNCI�N: CAApplication::mouseButtonCallback(GLFWwindow* window, int button, 
//                                                         int action, int mods)
//
// PROP�SITO: Respuesta a un evento de rat�n sobre la aplicaci�n
//
void CGApplication::mouseButtonCallback(GLFWwindow* window, int button,
    int action, int mods)
{
    auto app = reinterpret_cast<CGApplication*>(glfwGetWindowUserPointer(window));
    app->model.mouse_button(button, action);
}

//
// FUNCI�N: CGApplication::cursorPositionCallback(GLFWwindow* window, double xpos, 
//                                                                    double ypos)
//
// PROP�SITO: Respuesta a un evento de movimiento del cursor sobre la aplicaci�n
//
void CGApplication::cursorPositionCallback(GLFWwindow* window, double xpos,
    double ypos)
{
    auto app = reinterpret_cast<CGApplication*>(glfwGetWindowUserPointer(window));
    app->model.mouse_move(xpos, ypos);
}

//
// FUNCI�N: CGApplication::framebufferResizeCallback(GLFWwindow* window, int width, 
//                                                                       int height)
//
// PROP�SITO: Respuesta a un evento de redimensionamiento de la ventana principal
//
void CGApplication::framebufferResizeCallback(GLFWwindow* window, int width,
    int height)
{
    // 'auto' nos permite que la variable obtenga el tipo al que es igualado
    // 'reiterpret_cast' castea el tipo
    auto app = reinterpret_cast<CGApplication*>(glfwGetWindowUserPointer(window));
    if (height != 0) app->model.resize(width, height);
}