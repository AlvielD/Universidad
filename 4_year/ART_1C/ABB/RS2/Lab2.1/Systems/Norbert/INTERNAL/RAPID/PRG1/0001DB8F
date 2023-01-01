MODULE E458GripperTest
    ! Module for testing gripper, UiS KS Nov. 29, 2018

    ! wobj in the middle of the table, x-axis along short side of table, y-axis along long axis
	TASK PERS wobjdata wobjTableN:=[FALSE,TRUE,"",[[150,-500,8],[0.707106781,0,0,-0.707106781]],[[0,0,0],[1,0,0,0]]];
    
    ! robtargets
	CONST robtarget target_K0:=[[0,0,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
	CONST robtarget target_K1:=[[0,-200,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
	CONST robtarget target_K2:=[[-200,200,0],[0,1,0,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    
	PROC main()
	    MoveL Offs(target_K0, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
	    movePuck;
	ENDPROC    
    
    PROC movePuck()
	    MoveJ Offs(target_K0, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
        WaitTime 1.0;   
        getPuck(target_K1);
        putPuck(target_K2);
	    MoveJ Offs(target_K0, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
        WaitTime 1.0;   
        getPuck(target_K2);
        putPuck(target_K1);
	    MoveJ Offs(target_K0, 0, 0, 200),v500,z10,tGripper\WObj:=wobjTableN;
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