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
    
	PROC main()
	    !MoveL Offs(initialPos, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
        movePuck 0, 8;
        movePuck 1, 5;
        movePuck 1, 4;
        movePuck 4, 2;
        movePuck 5, 1;
        movePuck 1, 2;
        movePuck 1, 7;
	ENDPROC    
    
    PROC movePuck(num fromPosNo , num toPosNo)
	    
        IF fromPosNo >= 1 AND fromPosNo <= nPos AND toPosNo >= 1 AND toPosNo <= nPos THEN
            
            IF fromPosNo <> lastPosNo THEN
                MoveJ Offs(initialPos, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
            ENDIF
                
            WaitTime 1.0;
            
            ! Takes the puck
            getPuck(Offs(targets{fromPosNo}, 0, 0, 30*(nOnPos{fromPosNo} - 1)));
            ! Update the pucks position
            nOnPos{fromPosNo} := nOnPos{fromPosNo} - 1;
            
            ! Puts the puck
            putPuck(Offs(targets{toPosNo}, 0, 0, 30*(nOnPos{toPosNo})));
            ! Update the pucks position
            nOnPos{toPosNo} := nOnPos{toPosNo} + 1;
            lastPosNo := toPosNo;
        ELSE
            TPWrite "Incorrect arguments given";
        ENDIF
        
    ENDPROC
    
    PROC getPuck(robtarget pos)
        MoveJ Offs(pos, 0, 0, 200),v500,z50,tGripper\WObj:=wobjTableN;
        MoveJ Offs(pos, 0, 0, 50),v200,z10,tGripper\WObj:=wobjTableN;
        MoveL Offs(pos, 0, 0, 10),v50,fine,tGripper\WObj:=wobjTableN;  
        closeGripper(TRUE);        
	    MoveL Offs(pos, 0, 0, 50),v50,z10,tGripper\WObj:=wobjTableN;
        MoveJ Offs(pos, 0, 0, 200),v200,z50,tGripper\WObj:=wobjTableN;
    ENDPROC

    PROC putPuck(robtarget pos)
        MoveJ Offs(pos, 0, 0, 200),v500,z50,tGripper\WObj:=wobjTableN;
        MoveJ Offs(pos, 0, 0, 50),v200,z10,tGripper\WObj:=wobjTableN;
        MoveL Offs(pos, 0, 0, 10),v50,fine,tGripper\WObj:=wobjTableN;
        closeGripper(FALSE);        
	    MoveL Offs(pos, 0, 0, 50),v50,z10,tGripper\WObj:=wobjTableN;
        MoveJ Offs(pos, 0, 0, 200),v200,z50,tGripper\WObj:=wobjTableN;
    ENDPROC
    
ENDMODULE