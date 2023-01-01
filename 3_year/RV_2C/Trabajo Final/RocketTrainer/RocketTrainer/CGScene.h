#pragma once

#include <GL/glew.h>
#include <glm/glm.hpp>
#include "CGShaderProgram.h"
#include "CGLight.h"
#include "CGMaterial.h"
#include "CGFigure.h"
#include "CGFog.h"
#include "CGSkybox.h"
#include "CGObject.h"

class CGScene {
public:
    CGScene();
    ~CGScene();

    // Getters
    glm::mat4 GetLightViewMatrix();
    CGFigure* GetPelota();
    CGObject* GetCoche();
    CGFigure* GetPorteria();
    CGFigure* GetPared1();
    CGFigure* GetPared2();
    CGFigure* GetPared3();
    CGFigure* GetPared4();

    void Draw(CGShaderProgram* program, glm::mat4 proj, glm::mat4 view, glm::mat4 shadowViewMatrix);

private:
    CGFigure* base;
    CGFigure* ground;
    CGFigure* pelota;
    CGObject* coche;

    // Portería
    CGFigure* porteria;
    CGFigure* sueloPorteria;
    CGMaterial* matRed;
    CGFigure* redPorteria_01;
    CGFigure* redPorteria_02;
    CGFigure* redPorteria_03;
    CGFigure* redPorteria_04;

    // Paredes
    CGFigure* pared_01;
    CGFigure* pared_02;
    CGFigure* pared_03;
    CGFigure* pared_04;

    // Materiales
    CGMaterial* matPared;
    CGMaterial* matb;
    CGMaterial* matg;
    CGMaterial* matPelota;
    CGMaterial* matCoche;
    CGMaterial* matPorteria;
    CGMaterial* matSueloPort;

    // Entorno
    CGLight* light;
    CGFog* fog;
    CGSkybox* skybox;
};
