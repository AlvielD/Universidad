#include "CGScene.h"
#include <GL/glew.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include "CGShaderProgram.h"
#include "CGFigure.h"
#include "CGLight.h"
#include "CGMaterial.h"
#include "CGFog.h"
#include "CGSkybox.h"
#include "CGSphere.h"
#include "CGGround.h"
#include "Porteria.h"
#include "Pared.h"
#include "ParedFrontal.h"
#include "CGSlotCar.h"
#include <iostream>

//
// FUNCIÓN: CGScene::CGScene()
//
// PROPÓSITO: Construye el objeto que representa la escena
//
CGScene::CGScene()
{
// INICIALIZACIÓN DE LA SKYBOX //

    skybox = new CGSkybox();

// INICIALIZACIÓN DE LA NIEBLA //

    fog = new CGFog();
    fog->SetMaxDistance(500.0f);
    fog->SetMinDistance(50.0f);
    fog->SetFogColor(glm::vec3(0.8f, 0.8f, 0.8f));

// INICIALIZACIÓN DE LA LUZ

    glm::vec3 Ldir = glm::vec3(1.0f, -0.8f, -1.0f);
    Ldir = glm::normalize(Ldir);
    light = new CGLight();
    light->SetLightDirection(Ldir);
    light->SetAmbientLight(glm::vec3(0.2f, 0.2f, 0.2f));
    light->SetDifusseLight(glm::vec3(0.8f, 0.8f, 0.8f));
    light->SetSpecularLight(glm::vec3(1.0f, 1.0f, 1.0f));

// MATERIAL PARA EL SUELO //

    matg = new CGMaterial();
    matg->SetAmbientReflect(0.8f, 0.8f, 0.8f);
    matg->SetDifusseReflect(1.0f, 1.0f, 1.0f);
    matg->SetSpecularReflect(0.8f, 0.8f, 0.8f);
    matg->SetShininess(16.0f);
    matg->InitTexture("textures/footballField.jpg");

// INICIALIZACIÓN DEL SUELO //

    ground = new CGGround(50.0f, 75.0f);
    ground->SetMaterial(matg);

// MATERIAL PARA LA PELOTA //

    matb = new CGMaterial();
    matb->SetAmbientReflect(0.6f, 0.6f, 0.6f);
    matb->SetDifusseReflect(0.6f, 0.6f, 0.6f);
    matb->SetSpecularReflect(0.8f, 0.8f, 0.8f);
    matb->SetShininess(16.0f);
    matb->InitTexture("textures/stone.jpg");

    base = new CGGround(5000.0f, 5000.0f);
    base->SetMaterial(matb);
    base->Translate(glm::vec3(0.0f, -0.05f, 0.0f));

    matPelota = new CGMaterial();
    matPelota->SetAmbientReflect(0.8f, 0.8f, 0.8f);
    matPelota->SetDifusseReflect(0.8f, 0.8f, 0.8f);
    matPelota->SetSpecularReflect(0.8f, 0.8f, 0.8f);
    matPelota->SetShininess(16.0f);
    matPelota->InitTexture("textures/ball4.png");

    pelota = new CGSphere(10, 20, 1.5f);
    pelota->SetMaterial(matPelota);
    pelota->Translate(glm::vec3(0.0f, 15.0f, 0.0f));

// INICIALIZACIÓN DE LA PORTERÍA

    matPorteria = new CGMaterial();
    matPorteria->SetAmbientReflect(1.0f, 1.0f, 1.0f);
    matPorteria->SetDifusseReflect(1.0f, 1.0f, 1.0f);
    matPorteria->SetSpecularReflect(1.0f, 1.0f, 1.0f);
    matPorteria->SetShininess(16.0f);
    matPorteria->InitTexture("textures/goal.jpg");

    porteria = new Porteria(35, 17.5f, 2.5f);
    porteria->SetMaterial(matPorteria);
    porteria->Translate(glm::vec3(-17.5f, 0, -75));

// SUELO DE LA PORTERÍA

    matSueloPort = new CGMaterial();
    matSueloPort->SetAmbientReflect(1.0f, 1.0f, 1.0f);
    matSueloPort->SetDifusseReflect(1.0f, 1.0f, 1.0f);
    matSueloPort->SetSpecularReflect(1.0f, 1.0f, 1.0f);
    matSueloPort->SetShininess(16.0f);
    matSueloPort->InitTexture("textures/field.jpg");

    sueloPorteria = new CGGround(17.5f, 20.0f);
    sueloPorteria->SetMaterial(matSueloPort);
    sueloPorteria->Translate(glm::vec3(0.0f, 0.0f, -95.0f));

// PAREDES DE LA PORTERÍA

    matRed = new CGMaterial();
    matRed->SetAmbientReflect(1.0f, 1.0f, 1.0f);
    matRed->SetDifusseReflect(1.0f, 1.0f, 1.0f);
    matRed->SetSpecularReflect(1.0f, 1.0f, 1.0f);
    matRed->SetShininess(16.0f);
    matRed->InitTexture("textures/web.jpg");

    redPorteria_01 = new Pared(35, 20.0f);
    redPorteria_01->SetMaterial(matRed);
    redPorteria_01->Translate(glm::vec3(-17.5f, 0.0f, -110.0f));

    redPorteria_02 = new Pared(35, 20.0f);
    redPorteria_02->SetMaterial(matRed);
    redPorteria_02->Translate(glm::vec3(-17.5f, 0.0f, -75.0f));
    redPorteria_02->Rotate(90, glm::vec3(0.0f, 1.0f, 0.0f));

    redPorteria_03 = new Pared(35, 20.0f);
    redPorteria_03->SetMaterial(matRed);
    redPorteria_03->Translate(glm::vec3(17.5f, 0.0f, -110.0f));
    redPorteria_03->Rotate(270, glm::vec3(0.0f, 1.0f, 0.0f));

    redPorteria_04 = new CGGround(35.0f, 20.0f);
    redPorteria_04->SetMaterial(matRed);
    redPorteria_04->Translate(glm::vec3(0.0f, 20.0f, -95.0f));
    redPorteria_04->Rotate(180, glm::vec3(1.0f, 0.0f, 0.0f));

// INICIALIZACIÓN DEL COCHE

    matCoche = new CGMaterial();
    matCoche->SetAmbientReflect(0.5f, 0.5f, 0.5f);
    matCoche->SetDifusseReflect(0.0f, 0.0f, 0.0f);
    matCoche->SetSpecularReflect(0.5f, 0.5f, 0.5f);
    matCoche->SetShininess(16.0f);
    matCoche->InitTexture("textures/SlotCarRed.jpg");

    coche = new CGSlotCar();
    coche->Rotate(-90, glm::vec3(1.0f, 0.0f, 0.0f));
    coche->Translate(glm::vec3(0, -40, 0));

// PAREDES DEL CAMPO DE JUEGO

    GLuint texId;
    matPared = new CGMaterial;
    matPared->SetAmbientReflect(0.5f, 0.5f, 0.5f);
    matPared->SetDifusseReflect(0.5f, 0.5f, 0.5f);
    matPared->SetSpecularReflect(0.5f, 0.5f, 0.5f);
    matPared->SetShininess(16.0f);
    matPared->InitTexture("textures/cement.jpg");
    texId = matPared->GetTexture();

    pared_01 = new ParedFrontal(100.0f, 25.0f, 35.0f, 17.5f);
    pared_01->SetMaterial(matPared);
    pared_01->Translate(glm::vec3(-50, 0, -75));

    pared_02 = new Pared(150.0f, 25.0f);
    pared_02->SetMaterial(matPared);
    pared_02->Translate(glm::vec3(50, 0, -75));
    pared_02->Rotate(270, glm::vec3(0.0f, 1.0f, 0.0f));

    pared_03 = new Pared(150.0f, 25.0f);
    pared_03->SetMaterial(matPared);
    pared_03->Translate(glm::vec3(-50, 0, 75));
    pared_03->Rotate(90, glm::vec3(0.0f, 1.0f, 0.0f));

    pared_04 = new Pared(100.0f, 25.0f);
    pared_04->SetMaterial(matPared);
    pared_04->Translate(glm::vec3(50, 0, 75));
    pared_04->Rotate(180, glm::vec3(0.0f, 1.0f, 0.0f));

}

//
// FUNCIÓN: CGScene3:~CGScene()
//
// PROPÓSITO: Destruye el objeto que representa la escena
//
CGScene::~CGScene()
{
    delete base;
    delete ground;
    delete pelota;
    delete coche;
    delete porteria;
    delete sueloPorteria;
    delete redPorteria_01;
    delete redPorteria_02;
    delete redPorteria_03;
    delete redPorteria_04;
    delete pared_01;
    delete pared_02;
    delete pared_03;
    delete pared_04;
    delete matCoche;
    delete matPorteria;
    delete matSueloPort;
    delete matb;
    delete matg;
    delete matPelota;
    delete light;
    delete fog;
    delete skybox;
}

//
// FUNCIÓN: CGScene::Draw()
//
// PROPÓSITO: Dibuja la escena
//
void CGScene::Draw(CGShaderProgram* program, glm::mat4 proj, glm::mat4 view, glm::mat4 shadowViewMatrix)
{
    light->SetUniforms(program);
    fog->SetUniforms(program);
    skybox->Draw(program, proj, view);
    base->Draw(program, proj, view, shadowViewMatrix);
    ground->Draw(program, proj, view, shadowViewMatrix);
    pelota->Draw(program, proj, view, shadowViewMatrix);
    coche->Draw(program, proj, view, shadowViewMatrix);

    porteria->Draw(program, proj, view, shadowViewMatrix);
    sueloPorteria->Draw(program, proj, view, shadowViewMatrix);
    redPorteria_01->Draw(program, proj, view, shadowViewMatrix);
    redPorteria_02->Draw(program, proj, view, shadowViewMatrix);
    redPorteria_03->Draw(program, proj, view, shadowViewMatrix);
    redPorteria_04->Draw(program, proj, view, shadowViewMatrix);

    pared_01->Draw(program, proj, view, shadowViewMatrix);
    pared_02->Draw(program, proj, view, shadowViewMatrix);
    pared_03->Draw(program, proj, view, shadowViewMatrix);
    pared_04->Draw(program, proj, view, shadowViewMatrix);

}

//
// FUNCIÓN: CGScene::GetLightViewMatrix()
//
// PROPÓSITO: Obtiene la matriz de posicionamiento de la luz
//
glm::mat4 CGScene::GetLightViewMatrix()
{
    glm::vec3 Zdir = -(light->GetLightDirection());
    glm::vec3 Up = glm::vec3(0.0f, 1.0f, 0.0f);
    glm::vec3 Xdir = glm::normalize(glm::cross(Up, Zdir));
    glm::vec3 Ydir = glm::cross(Zdir, Xdir);
    glm::vec3 Zpos = 150.0f * Zdir;
    glm::vec3 Center = glm::vec3(0.0f, 0.0f, 0.0f);

    glm::mat4 view = glm::lookAt(Zpos, Center, Ydir);
    return view;
}

CGFigure* CGScene::GetPelota()
{
    return pelota;
}

CGObject* CGScene::GetCoche()
{
    return coche;
}

CGFigure* CGScene::GetPorteria()
{
    return porteria;
}

CGFigure* CGScene::GetPared1()
{
    return pared_01;
}

CGFigure* CGScene::GetPared2()
{
    return pared_02;
}

CGFigure* CGScene::GetPared3()
{
    return pared_03;
}

CGFigure* CGScene::GetPared4()
{
    return pared_04;
}
