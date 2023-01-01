MODULE E458PenTest
    ! Module for ELE610 assignment 2 section 4, UiS KS Nov. 30, 2018

    ! wobj in the middle of the table
    TASK PERS wobjdata wobjTableR :=[ FALSE ,TRUE ,"" ,[[150 ,500 ,8] ,[1 ,0 ,0 ,0]] ,[[0 ,0 ,0] ,[1 ,0 ,0 ,0]]];
    TASK PERS wobjdata wobjA4 :=[ FALSE ,TRUE ,"" ,[[150 ,500 ,8.1] ,[1 ,0 ,0 ,0]] ,[[0 ,0 ,0] ,[1 ,0 ,0 ,0]]];
    PERS tooldata tSimplePen :=[TRUE ,[[0 ,0 ,110] ,[1 ,0 ,0 ,0]] ,[1 ,[0 ,0 ,1] ,[1 ,0 ,0 ,0] ,0 ,0 ,0]];
    
    CONST jointtarget jCalibPos := [[60 ,30 ,0 ,0 ,0 ,0] ,[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]];
    CONST robtarget TableCenter := [[0 ,0 ,0] ,[0 ,0 ,1 ,0] ,[0 ,0 ,0 ,0] ,[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget StandByPoint := [[0 ,0 ,30] ,[0 ,0 ,1 ,0] ,[0 ,0 ,0 ,0] ,[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget SheetCorner :=[[-148.5 ,-105 ,0] ,[0 ,0 ,1 ,0] ,[0 ,0 ,0 ,0] ,[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget rectCorner1 :=[[-35 ,-25 ,0.2] ,[0 ,0 ,1 ,0] ,[0 ,0 ,0 ,0] ,[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget rectCorner2 :=[[-40 ,-30 ,0.2] ,[0 ,0 ,1 ,0] ,[0 ,0 ,0 ,0] ,[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget rectCorner3 :=[[-45 ,-35 ,0.2] ,[0 ,0 ,1 ,0] ,[0 ,0 ,0 ,0] ,[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST num triangle_side := 100;

    PROC main ()
        ! MoveAbsJ jCalibPos , v1000 , z10 , tSimplePen;
        
        !drawRectangle rectCorner1, 70, 50;
        !drawRectangle rectCorner2, 80, 60;
        !drawRectangle rectCorner3, 90, 70;
        
        !drawTriangle TableCenter, triangle_side;
        
        !drawCircle TableCenter, 2/3*triangle_side;
        
        drawShapes TableCenter, 5;
    ENDPROC
    
    PROC drawRectangle (robtarget p10, num width, num heigth)
        ! Draw a rectangle given the corner point, width and heigth
        VAR robtarget pInitial;
        VAR robtarget p20;
        VAR robtarget p30;
        VAR robtarget p40;
        
        ! Assuming we start from the bottom left corner
        pInitial := Offs(p10, 0, 0, 10);
        p20 := Offs(p10, width, 0, 0);
        p30 := Offs(p10, width, heigth, 0);
        p40 := Offs(p10, 0, heigth, 0);
        
        ! Instruction for the algorithm
        MoveL pInitial, v10, z10, tSimplePen\WObj := wobjA4;
        MoveL p10, v10, z10, tSimplePen\WObj := wobjA4;
        MoveL p20, v200, z10, tSimplePen\WObj := wobjA4;
        MoveL p30, v200, z10, tSimplePen\WObj := wobjA4;
        MoveL p40, v200, z10, tSimplePen\WObj := wobjA4;
        MoveL p10, v200, z10, tSimplePen\WObj := wobjA4;
        MoveL pInitial, v10, z10, tSimplePen\WObj := wobjA4;  
    ENDPROC
    
    PROC drawTriangle(robtarget center, num side_length)
        ! Variable declaration
        VAR robtarget initialP;
        VAR robtarget p1;
        VAR robtarget p2;
        VAR robtarget p3;
        
        ! Variable initialization
        p1 := Offs(center, -1/2*side_length, -1/3*side_length, 0);
        initialP := Offs(p1, 0, 0, 10);
        p2 := Offs(p1, side_length, 0, 0);
        p3 := Offs(center, 0, 2/3*side_length, 0);
        
        ! Instruction section
        MoveL initialP, v100, z5, tSimplePen\WObj := wobjA4;
        MoveL p1, v10, z5, tSimplePen\WObj := wobjA4;
        MoveL p2, v100, z5, tSimplePen\WObj := wobjA4;
        MoveL p3, v100, z5, tSimplePen\WObj := wobjA4;
        MoveL p1, v100, z5, tSimplePen\WObj := wobjA4;
        MoveL initialP, v10, z5, tSimplePen\WObj := wobjA4;
    ENDPROC
    
    PROC drawCircle(robtarget center, num radius)
        ! Variable declaration
        VAR robtarget pInitial;
        VAR robtarget p1;
        VAR robtarget p2;
        VAR robtarget p3;
        VAR robtarget p4;
        
        ! Variable initialization
        p1 := Offs(center, radius, 0, 0);
        pInitial := Offs(p1, 0, 0, 10);
        p2 := Offs(center, 0, -radius, 0);
        p3 := Offs(center, -radius, 0, 0);
        p4 := Offs(center, 0, radius, 0);
        
        ! Instructions section
        MoveL pInitial, v100, z0, tSimplePen\WObj := wobjA4;
        MoveL p1, v10, fine, tSimplePen\WObj := wobjA4;
        MoveC p2, p3, v100, fine, tSimplePen\WObj := wobjA4;
        MoveC p4, p1, v100, fine, tSimplePen\WObj := wobjA4;
        MoveL pInitial, v10, z0, tSimplePen\WObj := wobjA4;
    ENDPROC
    
    PROC drawShapes(robtarget center, num n_shapes)
        ! Draws a square of 120mm side
        VAR num figSize := 120;
        VAR bool sqr := TRUE;
        
        FOR i FROM 1 TO n_shapes DO
            IF sqr = TRUE THEN
                drawRectangle Offs(center, -1/2*figSize, -1/2*figSize, 0), figSize, figSize;
                figSize := 1/2*figSize;
                sqr := FALSE;
            ELSE
                drawCircle center, figSize;
                figSize := 2*figSize/Sqrt(2);
                sqr := TRUE;
            ENDIF
        ENDFOR
    ENDPROC
    
ENDMODULE