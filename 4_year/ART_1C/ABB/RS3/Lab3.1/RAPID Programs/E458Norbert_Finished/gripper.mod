MODULE gripper
    ! Module for gripper, UiS KS Nov. 29, 2018
    
    PERS tooldata tGripper:=[TRUE,[[0,0,114.25],[0,0,0,1]],[1,[-0.095984607,0.082520613,38.69176324],[1,0,0,0],0,0,0]];

    ! for virtual controller Norbert (simulation)
    !PROC closeGripper(bool state)
    !    WaitTime 0.2;
    !    IF state=TRUE THEN
    !      SetDO doClose, 1;
    !      SetDO doOpen, 0;
    !    ELSEIF state=FALSE THEN
    !      SetDO doClose, 0;
    !      SetDO doOpen, 1;
    !    ENDIF
    !    WaitTime 1.2;
    !ENDPROC

   ! for actual or physical robot Norbert
   ! Note that different IO signals are used here
    PROC closeGripper(bool state)
        WaitTime 0.1;
        IF state=TRUE THEN
          setDO AirValve1, 1; 
          setDO AirValve2, 0;   
        ELSEIF state=FALSE THEN
          setDO AirValve1, 0; 
          setDO AirValve2, 1; 
        ENDIF
        WaitTime 0.2;
    ENDPROC
        
ENDMODULE