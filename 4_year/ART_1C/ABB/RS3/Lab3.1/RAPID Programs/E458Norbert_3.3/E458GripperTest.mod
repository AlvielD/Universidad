MODULE E458GripperTest
    ! Module for testing gripper, UiS KS Nov. 29, 2018

    ! wobj in the middle of the table, x-axis along short side of table, y-axis along long axis
	TASK PERS wobjdata wobjTableN:=[FALSE,TRUE,"",[[150,-500,8],[0.707106781,0,0,-0.707106781]],[[0,0,0],[1,0,0,0]]];
    
    VAR robtarget targets {7} := [
        [[0,-200,0],[0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[200 , -200 ,0] ,[0 ,1 ,0 ,0] ,[0 ,0 ,0 ,0] ,[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[0,0,0], [0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[200 ,0 ,0] ,[0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]], 
        [[ -200 ,200 ,0] ,[0 ,1 ,0 ,0] ,[0 ,0 ,0 ,0] ,[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[0 ,200 ,0] ,[0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]],
        [[200 ,200 ,0] , [0,1,0,0],[0,0,0,0],[9E9 ,9E9 ,9E9 ,9E9 ,9E9 ,9E9]]
    ];
        
    VAR num currentPos := 1;
    
    ! robtargets
	CONST robtarget initialPos:=[[0,0,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
	!CONST robtarget target_K1:=[[0,-200,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
	!CONST robtarget target_K2:=[[-200,200,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    
	PROC main()
	    !MoveL Offs(initialPos, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
	    movePuck;
	ENDPROC    
    
    PROC movePuck()
	    MoveJ Offs(initialPos, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
        WaitTime 1.0;   
        FOR i FROM 1 TO 5 DO
            getPuck(Offs(targets{1}, 0, 0, 30*(5-i)));
            putPuck(Offs(targets{4}, 0, 0, 30*(i-1)));
        ENDFOR
	    MoveJ Offs(initialPos, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
        WaitTime 1.0;   
        FOR i FROM 1 TO 5 DO
            getPuck(Offs(targets{4}, 0, 0, 30*(5-i)));
            putPuck(Offs(targets{1}, 0, 0, 30*(i-1)));
        ENDFOR
	    MoveJ Offs(initialPos, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
        WaitTime 1.0;   
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