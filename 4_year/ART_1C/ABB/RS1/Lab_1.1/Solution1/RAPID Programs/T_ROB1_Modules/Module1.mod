MODULE Module1
        PERS tooldata UISpenholder:=[TRUE,[[0,0,110],[1,0,0,0]],[1,[0,0,1],[1,0,0,0],0,0,0]];
    TASK PERS wobjdata WobjTable:=[FALSE,TRUE,"",[[475,625,0],[0.707106781,0,0,-0.707106781]],[[425,-150,308],[1,0,0,0]]];
    CONST robtarget Target_10:=[[0,300,0],[0,1,0,0],[0,0,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_20:=[[400,300,0],[0,-0.707106781,0.707106781,0],[-1,0,-1,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_30:=[[400,0,0],[0,0,1,0],[-1,0,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_40:=[[0,0,0],[0,0.707106781,0.707106781,0],[0,0,-2,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
!***********************************************************
    !
    ! Module:  Module1
    !
    ! Description:
    !   <Insert description here>
    !
    ! Author: aeste
    !
    ! Version: 1.0
    !
    !***********************************************************
    
    
    !***********************************************************
    !
    ! Procedure main
    !
    !   This is the entry point of your program
    !
    !***********************************************************
    PROC main()
        !Add your code here
    ENDPROC
    PROC Path_10()
        MoveL Target_10,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_20,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_30,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_40,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_10,v1000,z5,UISpenholder\WObj:=WobjTable;
    ENDPROC
ENDMODULE