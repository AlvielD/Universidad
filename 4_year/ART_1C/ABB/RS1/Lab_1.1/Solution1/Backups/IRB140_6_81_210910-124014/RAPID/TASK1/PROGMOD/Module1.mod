MODULE Module1
        PERS tooldata UISpenholder:=[TRUE,[[0,0,110],[1,0,0,0]],[1,[0,0,1],[1,0,0,0],0,0,0]];
    TASK PERS wobjdata WobjTable:=[FALSE,TRUE,"",[[475,625,2],[0.707106781,0,0,-0.707106781]],[[425,-150,308],[1,0,0,0]]];
    CONST robtarget Target_10:=[[0,300,0],[0,1,0,0],[0,0,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_20:=[[400,300,0],[0,-0.707106781,0.707106781,0],[-1,0,-1,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_30:=[[400,0,0],[0,0,1,0],[-1,0,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_40:=[[0,0,0],[0,0.707106781,0.707106781,0],[0,0,-2,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_50:=[[100,275,15],[0,1,0,0],[0,0,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_60:=[[300,275,15],[0,-0.707106781,0.707106781,0],[-1,0,-1,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_70:=[[300,25,15],[0,0,1,0],[-1,0,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_80:=[[100,25,15],[0,0.707106781,0.707106781,0],[0,0,-2,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_90:=[[125,250,40],[0,1,0,0],[0,0,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_90_2:=[[125,250,28],[0,1,0,0],[0,0,-1,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_100:=[[275,250,40],[0,-0.707106781,0.707106781,0],[-1,0,-1,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_100_2:=[[275,250,28],[0,-0.707106781,0.707106781,0],[-1,0,-1,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_110:=[[275,50,40],[0,0,1,0],[-1,0,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_110_2:=[[275,50,28],[0,0,1,0],[-1,0,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_120:=[[125,50,40],[0,0.707106781,0.707106781,0],[0,0,-2,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
    CONST robtarget Target_120_2:=[[125,50,28],[0,0.707106781,0.707106781,0],[0,0,-2,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];
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
        !MoveL Target_Center,v1000,z5,UISpenholder\WObj:=WobjTable;
        Path_10;
        Path_20;
        Path_30;
        !MoveL Target_Center,v1000,z5,UISpenholder\WObj:=WobjTable;
    ENDPROC
    PROC Path_10()
        MoveL Target_10,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_20,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_30,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_40,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_10,v1000,z5,UISpenholder\WObj:=WobjTable;
    ENDPROC
    PROC Path_20()
        MoveL Target_50,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_60,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_70,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_80,v1000,z5,UISpenholder\WObj:=WobjTable;
        MoveL Target_50,v1000,z5,UISpenholder\WObj:=WobjTable;
    ENDPROC
    PROC Path_30()
        MoveL Target_90,v1000,z100,UISpenholder\WObj:=WobjTable;
        MoveL Target_90_2,v1000,z100,UISpenholder\WObj:=WobjTable;
        MoveL Target_100,v1000,z100,UISpenholder\WObj:=WobjTable;
        MoveL Target_100_2,v1000,z100,UISpenholder\WObj:=WobjTable;
        MoveL Target_110,v1000,z100,UISpenholder\WObj:=WobjTable;
        MoveL Target_110_2,v1000,z100,UISpenholder\WObj:=WobjTable;
        MoveL Target_120,v1000,z100,UISpenholder\WObj:=WobjTable;
        MoveL Target_120_2,v1000,z100,UISpenholder\WObj:=WobjTable;
    ENDPROC
ENDMODULE