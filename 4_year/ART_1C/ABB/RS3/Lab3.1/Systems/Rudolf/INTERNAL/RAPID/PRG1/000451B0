MODULE E458PenTest
    ! Module for testing pen, UiS KS Nov. 29, 2018

    ! wobj in the middle of the table
    TASK PERS wobjdata wobjTableR:=[FALSE,TRUE,"",[[150,500,8],[1,0,0,0]],[[0,0,0],[1,0,0,0]]];
    PERS tooldata tSimplePen:=[TRUE,[[0,0,110],[1,0,0,0]],[1,[0,0,1],[1,0,0,0],0,0,0]];

    CONST robtarget Target_10:=[[0,0,100],[0,0,1,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    CONST robtarget Target_20:=[[100,0,100],[0,0,1,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    CONST robtarget Target_30:=[[100,200,100],[0,0,1,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    CONST robtarget Target_40:=[[0,200,100],[0,0,1,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];
    CONST robtarget Target_50:=[[50,100,300],[0,0,1,0],[0,0,0,0],[9E9,9E9,9E9,9E9,9E9,9E9]];

    PROC main()
        Path_10;
    ENDPROC    
    
    PROC Path_10()
        MoveL Target_50,v500,z10,tSimplePen\WObj:=wobjTableR;
        MoveL Target_10,v500,z10,tSimplePen\WObj:=wobjTableR;
        MoveL Target_20,v500,z10,tSimplePen\WObj:=wobjTableR;
        MoveL Target_30,v500,z10,tSimplePen\WObj:=wobjTableR;
        MoveL Target_40,v500,z10,tSimplePen\WObj:=wobjTableR;
        MoveL Target_10,v500,z10,tSimplePen\WObj:=wobjTableR;
        MoveL Target_50,v500,z10,tSimplePen\WObj:=wobjTableR;
    ENDPROC
    
ENDMODULE