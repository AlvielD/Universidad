MODULE TriplePen
    ! Module for TriplePen: KS, 23 feb 2014 - des 2018 
    ! Since the course is now taught in English, comments in Norwegian has been removed.

    PERS wobjdata wobjTriplePen; 
    PERS tooldata tTriplePen; 
    PERS tooldata tTriplePenNull := [TRUE,[[0,0,120],[1,0,0,0]],[0.1,[0,0,0.1],[1,0,0,0],0,0,0]];
    PERS tooldata tTriplePenRed := [TRUE,[[-19.4,-3,113],[0.085813067,0.130203676,-0.016811518,-0.98762366]],[0.1,[0,0,0.1],[1,0,0,0],0,0,0]];
    PERS tooldata tTriplePenBlack := [TRUE,[[7.1,18.3,113],[0.812400645,-0.050542636,0.12116545,0.568128126]],[0.1,[0,0,0.1],[1,0,0,0],0,0,0]];
    PERS tooldata tTriplePenBlue := [TRUE,[[12.3,-15.3,113],[0.898213712,0.07966104,0.104353932,-0.419495534]],[0.1,[0,0,0.1],[1,0,0,0],0,0,0]];
    PERS tooldata tTriplePenCam := [TRUE,[[252,0,265],[0.923879533,0,0.382683432,0]],[0.1,[0,0,0.1],[1,0,0,0],0,0,0]];

    LOCAL VAR speeddata vTriplePen := v100;
    LOCAL VAR zonedata zTriplePen := z0;
    LOCAL VAR robtarget pTriplePenOrigo := [[0,0,0],[0,1,0,0],[0,0,-2,0],[9E9,9E9,9E9,9E9,9E9,9E9]];  
    LOCAL VAR robtarget pTriplePenLast := [[0,0,0],[0,1,0,0],[0,0,-2,0],[9E9,9E9,9E9,9E9,9E9,9E9]];       
    CONST num nTriplePenUp := 18;
    CONST num nTriplePenDown := -2;
    LOCAL VAR num nTriplePenZPos := 0;        ! as above (-2 or 18), or 0 for camera
    LOCAL VAR num nTriplePenSelected := 0;    ! 0 up, 1-3 for Red, Black, Blue, or 4 for camera
    LOCAL VAR num nTriplePenAngle := 0;       ! direction of "turtle", 0: x-axis, 90: y-axis

    ! -- INITIALIIZING --
    PROC TriplePenInit()
        wobjTriplePen := wobjBord;   
        tTriplePen := tTriplePenNull;
        nTriplePenSelected := 0;
        nTriplePenAngle := 0;
        nTriplePenZPos := nTriplePenUp;
        MoveJ Offs(pTriplePenOrigo,0,0,nTriplePenZPos), vTriplePen, zTriplePen, tTriplePen \WObj:=wobjTriplePen;
        pTriplePenLast := pTriplePenOrigo;
    ENDPROC

    PROC TriplePenUp()
        IF nTriplePenSelected = 4 THEN
            IF nTriplePenZPos <> 0 THEN       
                MoveJ pTriplePenLast, vTriplePen, z1, tTriplePen \WObj:=wobjTriplePen;
                nTriplePenZPos := 0;
            ENDIF
        ELSE
            IF nTriplePenZPos <> nTriplePenUp THEN        
                IF nTriplePenZPos < 0 THEN
                    MoveL pTriplePenLast, vTriplePen, z1, tTriplePen \WObj:=wobjTriplePen;
                ENDIF
                MoveJ Offs(pTriplePenLast,0,0,nTriplePenUp), vTriplePen, z10, tTriplePen \WObj:=wobjTriplePen;
                nTriplePenZPos := nTriplePenUp;
            ENDIF
        ENDIF
    ENDPROC
    
    PROC TriplePenDown()
        IF nTriplePenSelected = 4 THEN
            IF nTriplePenZPos <> 0 THEN       
                MoveJ pTriplePenLast, vTriplePen, z1, tTriplePen \WObj:=wobjTriplePen;
                nTriplePenZPos := 0;
            ENDIF
        ELSE    
            IF nTriplePenZPos <> nTriplePenDown THEN       
                IF nTriplePenZPos > 0 THEN  
                    MoveJ pTriplePenLast, vTriplePen, z1, tTriplePen \WObj:=wobjTriplePen;
                ENDIF
                MoveL Offs(pTriplePenLast,0,0,nTriplePenDown), vTriplePen, zTriplePen, tTriplePen \WObj:=wobjTriplePen;
                nTriplePenZPos := nTriplePenDown;
            ENDIF
        ENDIF
    ENDPROC

    PROC TriplePenSelectNull()
        TriplePenSelect 0;
    ENDPROC

    PROC TriplePenSelectRed()
        TriplePenSelect 1;
    ENDPROC
    
    PROC TriplePenSelectBlack()
        TriplePenSelect 2;
    ENDPROC

    PROC TriplePenSelectBlue()
        TriplePenSelect 3;
    ENDPROC

    PROC TriplePenSelectCam()
        IF nTriplePenSelected <> 4 THEN
            tTriplePen := tTriplePenCam;
            nTriplePenSelected := 4;
            MoveJ pTriplePenLast, vTriplePen, zTriplePen, tTriplePen \WObj:=wobjTriplePen;
            nTriplePenZPos := 0;
        ENDIF
    ENDPROC
    
    PROC TriplePenMoveL(robtarget p1)
        MoveL Offs(p1,0,0,nTriplePenZPos), vTriplePen, zTriplePen, tTriplePen \WObj:=wobjTriplePen;
        pTriplePenLast := p1;
    ENDPROC

    PROC TriplePenMoveRel(num dx, num dy)
        pTriplePenLast := Offs(pTriplePenLast, dx, dy, 0);
        MoveL Offs(pTriplePenLast,0,0,nTriplePenZPos), vTriplePen, zTriplePen, tTriplePen \WObj:=wobjTriplePen;
   ENDPROC
    
    ! "turtle"-graphics
    PROC TriplePenMoveForward(num dist)
        TriplePenMoveRel dist*Cos(nTriplePenAngle), dist*Sin(nTriplePenAngle);
    ENDPROC
      
    PROC TriplePenTurn(num dAngle)
        nTriplePenAngle := nTriplePenAngle + dAngle;
        IF nTriplePenAngle > 180 THEN
            nTriplePenAngle := nTriplePenAngle - 360;
        ENDIF
        IF nTriplePenAngle < -180 THEN
            nTriplePenAngle := nTriplePenAngle + 360;
        ENDIF
    ENDPROC
     
    PROC TriplePenTurnLeft()
        TriplePenTurn 90;
    ENDPROC
    
    PROC TriplePenTurnRight()
        TriplePenTurn -90;
    ENDPROC

    PROC TriplePenTest()
        TriplePenUp;
        TriplePenSelectBlack;
        TriplePenMoveL pArkM;
        TriplePenDown;
        TriplePenMoveRel 10, 0;
        TriplePenSelectBlue;
        TriplePenMoveRel 10, 0;
        TriplePenSelectRed;
        TriplePenMoveRel 10, 0;
        TriplePenSelectBlack;
        TriplePenMoveRel 10, 0;
        TriplePenSelectBlue;
        TriplePenMoveRel 10, 0;
        TriplePenSelectRed;
        TriplePenMoveRel 10, 0;
        TriplePenSelectBlack;
        TriplePenMoveRel 10, 0;
        TriplePenUp;
        WaitTime 2;
    ENDPROC

ENDMODULE