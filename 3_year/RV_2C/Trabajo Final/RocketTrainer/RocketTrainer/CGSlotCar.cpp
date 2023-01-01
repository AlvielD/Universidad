#include "CGSlotCar.h"
#include "CGSlotCar_pieces.h"
#include <GL/glew.h>
#include "CGObject.h"
#include "CGFigure.h"

CGSlotCar::CGSlotCar()
{
	// Inicialización de los vectores de la matriz view
	Pos = glm::vec3(0.0f, 5.0f, 60.0f);
	Dir = glm::vec3(0.0f, 0.0f, 1.0f);
	Up = glm::vec3(0.0f, 1.0f, 0.0f);
	Right = glm::vec3(1.0f, 0.0f, 0.0f);

	// Gravedad y radio de colisión
	float gravedad = 9.8f / 60;
	radioColision = 3.0f;

	// La velocidad inicial es 0 y la aceleración es la de la gravedad
	velocidad = glm::vec3(0.0f, 0.0f, 0.0f);
	aceleracion = glm::vec3(0.0f, 0.0f, 0.0f);

	CGMaterial* mtl0 = new CGMaterial();
	mtl0->SetAmbientReflect(0.588f, 0.588f, 0.588f);
	mtl0->SetDifusseReflect(0.588f, 0.588f, 0.588f);
	mtl0->SetSpecularReflect(0.0f, 0.0f, 0.0f);
	mtl0->SetShininess(10.0f);
	mtl0->InitTexture("textures/SlotCarRed.jpg");

	CGMaterial* mtl1 = new CGMaterial();
	mtl1->SetAmbientReflect(0.588f, 0.588f, 0.588f);
	mtl1->SetDifusseReflect(0.588f, 0.588f, 0.588f);
	mtl1->SetSpecularReflect(0.0f, 0.0f, 0.0f);
	mtl1->SetShininess(10.0f);

	pieces[0] = new CGSlotCar_0(mtl0);
	pieces[1] = new CGSlotCar_1(mtl1);
}

CGSlotCar::~CGSlotCar()
{
	for (int i = 0; i < 2; i++) delete pieces[i];
}

int CGSlotCar::GetNumPieces()
{
	return 2;
}

CGFigure* CGSlotCar::GetPiece(int index)
{
	return pieces[index];
}

