MODULE E458GripperTest
    ! Module for testing gripper, UiS KS Nov. 29, 2018

    ! wobj in the middle of the table, x-axis along short side of table, y-axis along long axis
	TASK PERS wobjdata wobjTableN:=[FALSE,TRUE,"",[[150,-500,8],[0.707106781,0,0,-0.707106781]],[[0,0,0],[1,0,0,0]]];
    
    CONST speeddata vSlow := v100;
    CONST speeddata vFast := v1000;
    CONST num puckHeight := 30;
    CONST num safeHeight := 240;
    CONST num nPos := 7;
    
    ! number of different positions on the table
    VAR num lastPosNo := 0;     ! Position where the last puck was moved to
    VAR num nOnPos{nPos} := [ 5, 0, 0, 0, 0, 0, 0];     ! Array with the position of each puck
    
    CONST robtarget initialPos:=[[0,0,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    
    VAR robtarget targets {nPos} := [
        [[0,-200,0],[0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[200 , -200 ,0] ,[0 ,1 ,0 ,0] ,[0 ,0 ,0 ,0] ,[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[0,0,0], [0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[200 ,0 ,0] ,[0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]], 
        [[ -200 ,200 ,0] ,[0 ,1 ,0 ,0] ,[0 ,0 ,0 ,0] ,[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[0 ,200 ,0] ,[0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[200 ,200 ,0] , [0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]]
    ];
        
    VAR num currentPos := 1;
	
    !***********************************************************
    !
    ! Module:  E458GripperTest
    !
    ! Description:
    !   Applied Robot Technology: Assignment RS3
    !
    ! Author: Álvaro Esteban Muñoz and Nourane Bouzad
    !
    ! Version: 1.0
    !
    !***********************************************************
	
	PROC main()
        !Open gripper and move to position 1
        closeGripper(FALSE);
	    MoveL Offs(targets{1}, 0, 0, 250),v200,z10,tGripper\WObj:=wobjTableN;
        
        ! Move pucks
        movePuck 1, 4;
        movePuck 4, 1;
        
        ! Move stack
        moveStack 1, 3;
        
        ! Flip stack
        flipStack 3;
        
        ! Collect all pucks to stack 1
        moveStack 3, 1;
	ENDPROC    
    
    PROC movePuck(num fromPosNo , num toPosNo)
	    
        IF fromPosNo >= 1 AND fromPosNo <= nPos AND toPosNo >= 1 AND toPosNo <= nPos THEN
            
            IF fromPosNo <> lastPosNo THEN
                MoveJ Offs(initialPos, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
            ENDIF
                
            WaitTime 1.0;
            
            ! Takes the puck
            getPuck(Offs(targets{fromPosNo}, 0, 0, 5+30*(nOnPos{fromPosNo} - 1)));
            ! Update the pucks position
            nOnPos{fromPosNo} := nOnPos{fromPosNo} - 1;
            
            ! Puts the puck
            putPuck(Offs(targets{toPosNo}, 0, 0, 5+30*(nOnPos{toPosNo})));
            ! Update the pucks position
            nOnPos{toPosNo} := nOnPos{toPosNo} + 1;
            lastPosNo := toPosNo;
        ELSE
            TPWrite "Incorrect arguments given";
        ENDIF
        
    ENDPROC
    
    PROC moveStack(num fromPosNo , num toPosNo)
	    
        IF fromPosNo >= 1 AND fromPosNo <= nPos AND toPosNo >= 1 AND toPosNo <= nPos THEN
            
            IF fromPosNo <> lastPosNo THEN
                MoveJ Offs(initialPos, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
            ENDIF
                
            WaitTime 1.0;
            
            FOR i FROM 1 TO nOnPos{fromPosNo} DO
                ! Takes the puck
                getPuck(Offs(targets{fromPosNo}, 0, 0, 30*(nOnPos{fromPosNo} - 1)));
                ! Update the pucks position
                nOnPos{fromPosNo} := nOnPos{fromPosNo} - 1;
                
                ! Puts the puck
                putPuck(Offs(targets{toPosNo}, 0, 0, 30*(nOnPos{toPosNo})));
                ! Update the pucks position
                nOnPos{toPosNo} := nOnPos{toPosNo} + 1;
                lastPosNo := toPosNo;
            ENDFOR
        ELSE
            TPWrite "Incorrect arguments given";
        ENDIF
        
    ENDPROC
    
    PROC flipStack(num fromPosNo)
	    
        VAR num emptyPos1 := -1;
        VAR num emptyPos2 := -1;
        
        ! Find an empty position
        VAR num count := 1;
        WHILE emptyPos1 = -1 DO
            IF nOnPos{count} = 0 THEN
                emptyPos1 := count;
            ENDIF
            count := count + 1;
        ENDWHILE

        ! Move the stack from the initial position to the empty position        
        FOR i FROM 1 TO nOnPos{fromPosNo} DO
            movePuck fromPosNo, emptyPos1;
        ENDFOR
        
        ! Find another empty position
        count := 1;
        WHILE emptyPos2 = -1 DO
            IF nOnPos{count} = 0 AND count <> fromPosNo THEN
                emptyPos2 := count;
            ENDIF
            count := count + 1;
        ENDWHILE
        
        ! Move the stack from the first empty position to the second empty position
        FOR i FROM 1 TO nOnPos{emptyPos1} DO
            movePuck emptyPos1, emptyPos2;
        ENDFOR
        
        ! Move the stack from the second empty position to the initial position
        FOR i FROM 1 TO nOnPos{emptyPos2} DO
            movePuck emptyPos2, fromPosNo;
        ENDFOR
        
    ENDPROC
    
    PROC getPuck(robtarget pos)
        MoveJ Offs(pos, 0, 0, 200),v500,z50,tGripper\WObj:=wobjTableN;
        MoveJ Offs(pos, 0, 0, 50),v100,z10,tGripper\WObj:=wobjTableN;
        MoveL Offs(pos, 0, 0, 10),v10,fine,tGripper\WObj:=wobjTableN;  
        closeGripper(TRUE);        
	    MoveL Offs(pos, 0, 0, 50),v10,z10,tGripper\WObj:=wobjTableN;
        MoveJ Offs(pos, 0, 0, 200),v100,z50,tGripper\WObj:=wobjTableN;
    ENDPROC

    PROC putPuck(robtarget pos)
        MoveJ Offs(pos, 0, 0, 200),v500,z50,tGripper\WObj:=wobjTableN;
        MoveJ Offs(pos, 0, 0, 50),v100,z10,tGripper\WObj:=wobjTableN;
        MoveL Offs(pos, 0, 0, 10),v10,fine,tGripper\WObj:=wobjTableN;
        closeGripper(FALSE);        
	    MoveL Offs(pos, 0, 0, 50),v10,z10,tGripper\WObj:=wobjTableN;
        MoveJ Offs(pos, 0, 0, 200),v100,z50,tGripper\WObj:=wobjTableN;
    ENDPROC
    
ENDMODULE