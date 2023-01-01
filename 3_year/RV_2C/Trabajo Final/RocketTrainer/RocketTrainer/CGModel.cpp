#include "CGModel.h"
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <GLFW/glfw3.h>
#include <iostream>
#include "CGScene.h"

//
// FUNCIÓN: CGModel::initialize(int, int)
//
// PROPÓSITO: Initializa el modelo 3D
//
void CGModel::initialize(int w, int h)
{
    // Crea el programa
    program = new CGShaderProgram();
    if (program->IsLinked() == GL_TRUE) program->Use();
    else std::cout << program->GetLog() << std::endl;

    // Crea la escena
    scene = new CGScene();

    // Crea el Framebuffer de la sombra
    bool frameBufferStatus = InitShadowMap();
    if (!frameBufferStatus) std::cout << "FrameBuffer incompleto" << std::endl;

    // Asigna el viewport y el clipping volume
    resize(w, h);

    // Opciones de dibujo
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glFrontFace(GL_CCW);
    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
}

//
// FUNCIÓN: CGModel::finalize()
//
// PROPÓSITO: Libera los recursos del modelo 3D
//
void CGModel::finalize()
{
    delete scene;
    delete program;
}

//
// FUNCIÓN: CGModel::resize(int w, int h)
//
// PROPÓSITO: Asigna el viewport y el clipping volume
//
void CGModel::resize(int w, int h)
{
    double fov = glm::radians(15.0);
    double sin_fov = sin(fov);
    double cos_fov = cos(fov);
    if (h == 0) h = 1;
    GLfloat aspectRatio = (GLfloat)w / (GLfloat)h;
    GLfloat wHeight = (GLfloat)(sin_fov * 0.2 / cos_fov);
    GLfloat wWidth = wHeight * aspectRatio;

    wndWidth = w;
    wndHeight = h;

    glViewport(0, 0, w, h);
    projection = glm::frustum(-wWidth, wWidth, -wHeight, wHeight, 0.2f, 400.0f);
}

//
// FUNCIÓN: CGModel::render()
//
// PROPÓSITO: Genera la imagen
//
void CGModel::render()
{
    //*********************************************************//
    //                  Genera el ShadowMap                    //
    //*********************************************************//

    // Asigna las matrices Viewport, View y Projection de la luz.
    glm::mat4 lightViewMatrix = scene->GetLightViewMatrix();
    glm::mat4 lightPerspective = glm::ortho(-150.0f, 150.0f, -150.0f, 150.0f, 0.0f, 400.0f);
    glm::mat4 lightMVP = lightPerspective * lightViewMatrix;

    // Activa el framebuffer de la sombra
    glBindFramebuffer(GL_FRAMEBUFFER, shadowFBO);

    // Limpia la información de profundidad
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Selecciona la subrutina recordDepth
    program->SetFragmentShaderUniformSubroutine("recordDepth");

    // Activamos polygonOffset para evitar shadowAcne
    glEnable(GL_POLYGON_OFFSET_FILL);
    glPolygonOffset(1.0f, 1.0f);

    // Activa front-face culling
    glCullFace(GL_FRONT);

    //Asigna el viewport
    glViewport(0, 0, 1024, 1024);

    // Dibuja la escena
    scene->Draw(program, lightPerspective, lightViewMatrix, lightMVP);

    //*********************************************************//
    //                  Dibuja la escena                       //
    //*********************************************************//

    // Activa el framebuffer de la imagen
    glBindFramebuffer(GL_FRAMEBUFFER, 0);

    // Limpia el framebuffer
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Activa back-face culling
    glCullFace(GL_BACK);

    // Selecciona la subrutina shadeWithShadow
    program->SetFragmentShaderUniformSubroutine("shadeWithShadow");
    program->SetUniformI("ShadowMap", 2);

    // Asigna el viewport
    glViewport(0, 0, wndWidth, wndHeight);

    // Dibuja la escena
    glm::mat4 viewMatrix = scene->GetCoche()->ViewMatrix();
    scene->Draw(program, projection, viewMatrix, lightMVP);
}

//
// FUNCIÓN: CGModel::update()
//
// PROPÓSITO: Anima la escena
//
void CGModel::update()
{
    PelotaConstraints();        // Detecta las colisiones de la pelota con el campo
    CocheConstraints();         // Detecta las colisiones del coche con las paredes del campo
    ColisionPelotaCoche();      // Detecta las colisiones entre el coche y la pelota

    ActualizaAcc();             // Actualiza las aceleraciones
    ActualizaVel();             // Actualiza las velocidades

    // Desarrollamos las físicas de la pelota
    scene->GetPelota()->MuevePelota();
    
    // Desarrollamos las físicas del coche y de la cámara
    scene->GetCoche()->MueveCoche();

    // Detecta continuamente si se ha marcado gol
    DetectaGolaso();
}

//
// FUNCIÓN: CGModel::key_pressed(int)
//
// PROPÓSITO: Respuesta a acciones de teclado
//
void CGModel::key_pressed(int key)
{
    glm::vec3 acc = scene->GetCoche()->GetAceleracion();
    switch (key)
    {
    case GLFW_KEY_W:
        scene->GetCoche()->SetAceleracion(glm::vec3(acc.x, acc.y + 0.005f, acc.z));
        break;
    case GLFW_KEY_S:
        scene->GetCoche()->SetAceleracion(glm::vec3(acc.x, acc.y - 0.005f, acc.z));
        break;
    case GLFW_KEY_LEFT:
        scene->GetCoche()->Rotate(3.0f, glm::vec3(0.0f, 0.0f, 1.0f));
        break;
    case GLFW_KEY_RIGHT:
        scene->GetCoche()->Rotate(-3.0f, glm::vec3(0.0f, 0.0f, 1.0f));
        break;
    case GLFW_KEY_M:
        scene->GetCoche()->PrintData();
        break;
    }
}

void CGModel::key_released(int key)
{
    switch (key) {
    case GLFW_KEY_W:
    case GLFW_KEY_S:
        scene->GetCoche()->SetAceleracion(glm::vec3(0.0f, 0.0f, 0.0f));
        break;
    }
}

//
//  FUNCIÓN: CGModel:::mouse_button(int button, int action)
//
//  PROPÓSITO: Respuesta del modelo a un click del ratón.
//
void CGModel::mouse_button(int button, int action)
{
}

//
//  FUNCIÓN: CGModel::mouse_move(double xpos, double ypos)
//
//  PROPÓSITO: Respuesta del modelo a un movimiento del ratón.
//
void CGModel::mouse_move(double xpos, double ypos)
{
}

void CGModel::PelotaConstraints()
{
    glm::vec3 pos = scene->GetPelota()->GetPosicion();
    float radio = scene->GetPelota()->GetRadioColision();
    glm::vec3 vel = scene->GetPelota()->GetVelocidad();

    // Invertimos la velocidad en el eje perpendicular al choque
    int constraint = 0;
    if (pos.y <= radio) { 
        pos.y = radio;
        vel.y = -vel.y;
        constraint = 1;
    }
    if (pos.y >= 100.0f) { 
        pos.y = 100.0f;
        vel.y = -vel.y;
        constraint = 1;
    }
    if (pos.x > 75.0f) { 
        pos.x = 70.0f;
        vel.x = -vel.x;
        constraint = 1;
    }
    if (pos.x < -75.0f) { 
        pos.x = -70.0f;
        vel.x = -vel.x;
        constraint = 1;
    }
    if (pos.z > 50.0f) { 
        pos.z = 45.0f;
        vel.z = -vel.z;
        constraint = 1;
    }
    if (pos.z < -50.0f && (pos.x < -17.5f || pos.x > 17.5f)) {
        pos.z = -45.0f;
        vel.z = -vel.z;
        constraint = 1;
    }

    if (constraint == 1)
    {
        vel = vel * 0.6f;
        scene->GetPelota()->SetPosicion(pos);
        scene->GetPelota()->SetVelocidad(vel);
    }
}

void CGModel::CocheConstraints()
{
    glm::vec3 pos = scene->GetCoche()->GetPosicion();
    float radioColision = scene->GetCoche()->GetRadioColision();
    glm::vec3 vel = scene->GetCoche()->GetVelocidad();

    glm::vec3 posPared1 = scene->GetPared1()->GetPosicion();
    glm::vec3 posPared2 = scene->GetPared2()->GetPosicion();
    glm::vec3 posPared3 = scene->GetPared3()->GetPosicion();
    glm::vec3 posPared4 = scene->GetPared4()->GetPosicion();

    int constraint = 0;

    if (pos.x > posPared2.x - radioColision)
    {
        pos.x = posPared2.x - radioColision;
        constraint = 1;
    }
    if (pos.x < posPared3.x + radioColision) 
    { 
        pos.x = posPared3.x + radioColision;
        constraint = 1;
    }
    if (pos.z < posPared1.z + radioColision) 
    { 
        pos.z = posPared1.z + radioColision;
        constraint = 1;
    }
    if (pos.z > posPared4.z - radioColision)
    { 
        pos.z = posPared4.z - radioColision;
        constraint = 1;
    }

    if (constraint == 1)
    {
        scene->GetCoche()->SetPosicion(glm::vec3(pos.x, pos.y, pos.z));
        scene->GetCoche()->SetVelocidad(-(vel*0.2f));
        scene->GetCoche()->SetAceleracion(glm::vec3(0.0f, 0.0f, 0.0f));
    }
}

void CGModel::ColisionPelotaCoche()
{
    // Posición y velocidad del coche
    glm::vec3 posCoche = scene->GetCoche()->GetPosicion();
    glm::vec3 velCoche = scene->GetCoche()->GetVelocidad();

    // Posición y velocidad de la pelota
    glm::vec3 posPelota = scene->GetPelota()->GetPosicion();
    glm::vec3 velPelota;

    // Radios de colisión
    float radioColision = scene->GetCoche()->GetRadioColision();
    float radioPelota = scene->GetPelota()->GetRadioColision();
    float radios = radioColision + radioPelota;

    glm::vec3 distancia = posPelota - posCoche;
    float moduloDistancia = glm::length(distancia) - radios;

    // La pelota choca con el coche
    if (moduloDistancia <= 0) {
        velPelota.x = (distancia.x + velCoche.x) * 0.6f;
        velPelota.y = (distancia.y + (-velCoche.z)) * 0.6f;
        velPelota.z = (distancia.z + (-velCoche.y)) * 0.6f;
        scene->GetPelota()->SetVelocidad(velPelota);
    }
}

void CGModel::ActualizaVel()
{
    // Actualiza la velocidad de la pelota
    CGFigure* pelota = scene->GetPelota();

    glm::vec3 vel = pelota->GetVelocidad();
    glm::vec3 acc = pelota->GetAceleracion();

    vel = vel + acc;    // Añadimos la acceleración a la velocidad actual

    pelota->SetVelocidad(vel);  // Actualizamos la velocidad

    // Actualiza la velocidad del coche y de la cámara
    CGObject* coche = scene->GetCoche();

    vel = coche->GetVelocidad();
    acc = coche->GetAceleracion();

    if (acc.y != 0) {
        vel = vel + acc;
    }
    else {

        glm::vec3 rozamiento = -(vel * 0.02f);
        vel = vel + rozamiento;
    }

    if (glm::length(vel) > 2.0f) {
        coche->SetAceleracion(glm::vec3(0.0f, 0.0f, 0.0f));
    }

    coche->SetVelocidad(vel);
}

void CGModel::ActualizaAcc()
{
    // Actualiza la aceleracion de la pelota
    float gravedad = 9.8f / 60;
    glm::vec3 pos = scene->GetPelota()->GetPosicion();
    if (pos.y >= 1.5f) {
        scene->GetPelota()->SetAceleracion(glm::vec3(0.0f, -gravedad, 0.0f));
    }
    else {
        scene->GetPelota()->SetAceleracion(glm::vec3(0.0f, 0.0f, 0.0f));
    }
}

bool CGModel::DetectaGolaso()
{
    glm::vec3 posPelota = scene->GetPelota()->GetPosicion();
    float radio = scene->GetPelota()->GetRadioColision();

    glm::vec3 posPorteria = scene->GetPorteria()->GetPosicion();

    int constraint = 0;
    if (posPelota.z <= posPorteria.z)
    { 
        if (posPelota.x > posPorteria.x + 2.5f && posPelota.x < posPorteria.x + (35.0f - 2.5f))
            constraint = 1;
    }

    if (constraint == 1)
    {
        return true;
    }

    return false;
}

bool CGModel::InitShadowMap()
{
    GLfloat border[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    GLsizei shadowMapWidth = 1024;
    GLsizei shadowMapHeight = 1024;

    glGenFramebuffers(1, &shadowFBO);
    glBindFramebuffer(GL_FRAMEBUFFER, shadowFBO);

    glGenTextures(1, &depthTexId);
    glActiveTexture(GL_TEXTURE2);
    glBindTexture(GL_TEXTURE_2D, depthTexId);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT24, shadowMapWidth,
        shadowMapHeight, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
    glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, border);
    glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTexId, 0);

    glDrawBuffer(GL_NONE);

    bool result = true;
    if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
    {
        result = false;
    }

    glBindFramebuffer(GL_FRAMEBUFFER, 0);

    return result;
}
