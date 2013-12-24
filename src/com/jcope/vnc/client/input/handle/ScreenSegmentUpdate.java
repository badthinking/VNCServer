package com.jcope.vnc.client.input.handle;

import static com.jcope.debug.Debug.assert_;

import com.jcope.vnc.client.StateMachine;
import com.jcope.vnc.shared.input.Handle;

public class ScreenSegmentUpdate extends Handle<StateMachine>
{
    public ScreenSegmentUpdate()
    {
        super(StateMachine.class);
    }
    
    @Override
    public void handle(StateMachine stateMachine, Object[] args)
    {
        assert_(args != null);
        assert_(args.length == 2);
        assert_(args[0] instanceof Integer);
        assert_(args[1] instanceof int[]);
        int segmentID = (Integer) args[0];
        int[] pixels = (int[]) args[1];
        assert_(segmentID >= -1);
        assert_(pixels != null);
        if (segmentID == -1)
        {
            loadEntireScreen(pixels);
        }
        else
        {
            loadScreenSegment(segmentID, pixels);
        }
    }
    
    private void loadEntireScreen(int[] pixels)
    {
        // TODO: 
    }
    
    private void loadScreenSegment(int segmentID, int[] pixels)
    {
        // TODO: 
    }
}
