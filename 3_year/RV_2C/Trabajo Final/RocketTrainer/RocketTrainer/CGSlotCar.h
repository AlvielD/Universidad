#pragma once

#include <GL/glew.h>
#include "CGObject.h"
#include "CGFigure.h"

class CGSlotCar : public CGObject {
private:
	CGFigure* pieces[2];

public:
	CGSlotCar();
	~CGSlotCar();
	virtual int GetNumPieces();
	virtual CGFigure* GetPiece(int i);
};

