tasm /zi /l RelojP1.asm
tlink /t RelojP1.obj

tasm /zi /l main.asm
tlink /v main.obj

tasm /zi /l result.asm
tlink /v result.obj
