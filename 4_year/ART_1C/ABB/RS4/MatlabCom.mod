MODULE MatlabCom
    ! Module MatlabCom: Karl S, March 2014 --> March 2015 --> Jan 2016  --> Feb 2019
    !
    ! Read and understand (most of) this code before it is used. 
    
    ! Note that wobj and tool need to be PERS (or?)
    PERS wobjdata wobjMatlabCom; 
    PERS tooldata tMatlabCom; 
    VAR speeddata vMatlabCom := v100;
    VAR zonedata zMatlabCom := z1;
    VAR jointtarget jMatlabCom := [[0,0,0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];

    ! the most important variables
    VAR num nMatlabComWRD := 0;   ! What Robot Does
    VAR num nMatlabComWMW := 0;   ! What Matlab Wants robot to do next
    
    ! more variables
    CONST num nMatlabComMAXLEN := 50;
    VAR num nMatlabComArrayX1{nMatlabComMAXLEN};
    VAR num nMatlabComArrayY1{nMatlabComMAXLEN};
    VAR num nMatlabComArrayZ1{nMatlabComMAXLEN};
    VAR num nMatlabComLen1 := 0;    ! number of elements in array
    VAR num nMatlabComArrayX2{nMatlabComMAXLEN};
    VAR num nMatlabComArrayY2{nMatlabComMAXLEN};
    VAR num nMatlabComArrayZ2{nMatlabComMAXLEN};
    VAR num nMatlabComLen2 := 0;    ! number of elements in array
    VAR robtarget pMatlabCom0 :=  [[0,0,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    VAR robtarget pMatlabCom1 :=  [[0,0,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    VAR robtarget pMatlabCom2 :=  [[0,0,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    ! current (or last) position
    VAR robtarget pMatlabComCur :=  [[0,0,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    VAR num nMatlabComNum1 := -1;  
    
    PROC MatlabComInit()    
        wobjMatlabCom := wobj0;
        tMatlabCom := tool0;
    ENDPROC
            
    PROC MatlabComMain()
        TPWrite "MatlabComMain starts.";
        WHILE TRUE DO  
            nMatlabComWRD := 0;              ! robot waits
            IF (nMatlabComWMW = 0) THEN
                TPWrite "Robot waits until Matlab set WMW.";
            ENDIF
            WaitUntil (nMatlabComWMW <> 0);  ! Matlab wants robot to do something
            nMatlabComWRD := nMatlabComWMW;  ! and robot does this
            nMatlabComWMW := 0;              ! and is ready for receiving next wish
            TEST nMatlabComWRD     
            CASE -1 : TPWrite "MatlabComMain quit."; RETURN;
            CASE  0 : TPWrite "MatlabComWRD = 0 is strange here."; WaitTime 0.1;
            CASE  1 : MatlabCom01;    ! path 1
            CASE  2 : MatlabCom02;    ! path 2
            CASE  3 : MatlabCom03;    ! moves to jMatlabCom  
            CASE  4 : MatlabCom04;    ! changes speeddata vMatlabCom
            CASE  5 : MatlabCom05;    ! changes zonedata zMatlabCom
            CASE  6 : MatlabCom06;    ! moves to pMatlabCom0
            CASE  7 : MatlabCom07;    ! moves to pMatlabCom1
            CASE  8 : MatlabCom08;    ! moves to pMatlabCom2
            CASE  9 : MatlabCom09;    ! store current (position and) angles
            CASE 10 : MatlabCom10;    ! changes (TriplePen) tool
            ENDTEST
        ENDWHILE
    ENDPROC

    PROC MatlabCom01()
        TPWrite "Robot moves along path 1."; 
        FOR i FROM 1 TO nMatlabComLen1 DO
            MoveL Offs(pMatlabCom0, nMatlabComArrayX1{i}, nMatlabComArrayY1{i}, nMatlabComArrayZ1{i}),
                  vMatlabCom, zMatlabCom, tMatlabCom \WObj:=wobjMatlabCom;  
        ENDFOR
        pMatlabComCur := Offs(pMatlabCom0, nMatlabComArrayX1{nMatlabComLen1}, 
                                           nMatlabComArrayY1{nMatlabComLen1}, 
                                           nMatlabComArrayZ1{nMatlabComLen1} );
    ENDPROC
    
    PROC MatlabCom02()
        TPWrite "Robot moves along path 2."; 
        FOR i FROM 1 TO nMatlabComLen2 DO
            MoveL Offs(pMatlabCom0, nMatlabComArrayX2{i}, nMatlabComArrayY2{i}, nMatlabComArrayZ2{i}),
                  vMatlabCom, zMatlabCom, tMatlabCom \WObj:=wobjMatlabCom;  
        ENDFOR
        pMatlabComCur := Offs(pMatlabCom0, nMatlabComArrayX2{nMatlabComLen2}, 
                                           nMatlabComArrayY2{nMatlabComLen2}, 
                                           nMatlabComArrayZ2{nMatlabComLen2} );
    ENDPROC

    PROC MatlabCom03()
        TPWrite "Robot moves to jMatlabCom."; 
        MoveAbsJ jMatlabCom, vMatlabCom, fine \Inpos := inpos50, tool0;  
        pMatlabComCur := CRobT(\Tool:=tMatlabCom \WObj:=wobjMatlabCom);
    ENDPROC
    
    PROC MatlabCom04()
        TPWrite "Robot changes vMatlabCom (max speed), argument in nMatlabComNum1."; 
        TEST nMatlabComNum1   
        CASE 5 : vMatlabCom := v5;
        CASE 10 : vMatlabCom := v10;
        CASE 20 : vMatlabCom := v20;
        CASE 30 : vMatlabCom := v30;
        CASE 40 : vMatlabCom := v40;
        CASE 50 : vMatlabCom := v50;
        CASE 60 : vMatlabCom := v60;
        CASE 80 : vMatlabCom := v80;
        CASE 100 : vMatlabCom := v100;
        CASE 150 : vMatlabCom := v150;
        CASE 200 : vMatlabCom := v200;
        CASE 300 : vMatlabCom := v300;
        CASE 400 : vMatlabCom := v400;
        CASE 500 : vMatlabCom := v500;
        CASE 600 : vMatlabCom := v600;
        CASE 800 : vMatlabCom := v800;
        CASE 1000 : vMatlabCom := v1000;
        CASE 1500 : vMatlabCom := v1500;
        CASE 2000 : vMatlabCom := v2000;
        CASE 2500 : vMatlabCom := v2500;
        CASE 3000 : vMatlabCom := v3000;
        DEFAULT : TPWrite "Illegal argument in nMatlabComNum1, " + NumToStr(nMatlabComNum1, 0);        
        ENDTEST
    ENDPROC
    
    PROC MatlabCom05()
        TPWrite "Robot changes zMatlabCom (zone), argument in nMatlabComNum1."; 
        TEST nMatlabComNum1   
        CASE 0 : zMatlabCom := z0;
        CASE 1 : zMatlabCom := z1;
        CASE 5 : zMatlabCom := z5;
        CASE 10 : zMatlabCom := z10;
        CASE 15 : zMatlabCom := z15;
        CASE 20 : zMatlabCom := z20;
        CASE 30 : zMatlabCom := z30;
        CASE 40 : zMatlabCom := z40;
        CASE 50 : zMatlabCom := z50;
        CASE 60 : zMatlabCom := z60;
        CASE 80 : zMatlabCom := z80;
        CASE 100 : zMatlabCom := z100;
        CASE 150 : zMatlabCom := z150;
        CASE 200 : zMatlabCom := z200;
        DEFAULT : TPWrite "Illegal argument in nMatlabComNum1, " + NumToStr(nMatlabComNum1, 0);        
        ENDTEST
    ENDPROC

    PROC MatlabCom06()
        MoveL pMatlabCom0, vMatlabCom, zMatlabCom, tMatlabCom \WObj:=wobjMatlabCom;  
        pMatlabComCur := pMatlabCom0;
    ENDPROC
    
    PROC MatlabCom07()
        MoveL pMatlabCom1, vMatlabCom, zMatlabCom, tMatlabCom \WObj:=wobjMatlabCom;  
        pMatlabComCur := pMatlabCom1;
    ENDPROC
    
    PROC MatlabCom08()
        MoveL pMatlabCom2, vMatlabCom, zMatlabCom, tMatlabCom \WObj:=wobjMatlabCom;  
        pMatlabComCur := pMatlabCom2;
    ENDPROC
    
    PROC MatlabCom09()
        TPWrite "Robot read angles."; 
        jMatlabCom := CJointT();
    ENDPROC

! only to be used when TriplePen.mod is included in program
    PROC MatlabCom10()
        TPWrite "Robot changes (TriplePen) tool, number in nMatlabComNum1."; 
        ! TEST nMatlabComNum1   
        ! CASE 0 : tMatlabCom := tTriplePenNull;
        ! CASE 1 : tMatlabCom := tTriplePenRed;
        ! CASE 2 : tMatlabCom := tTriplePenBlack;
        ! CASE 3 : tMatlabCom := tTriplePenBlue;
        ! CASE 4 : tMatlabCom := tTriplePenCam;
        ! DEFAULT : TPWrite "Illegal argument in nMatlabComNum1, " + NumToStr(nMatlabComNum1, 0);        
        ! ENDTEST
    ENDPROC
    
ENDMODULE
