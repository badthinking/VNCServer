package com.jcope.vnc.client.input.handle;

import static com.jcope.debug.Debug.assert_;

import com.jcope.vnc.client.StateMachine;
import com.jcope.vnc.shared.input.Handle;

public class ConnectionClosed extends Handle<StateMachine>
{
    public ConnectionClosed()
    {
        super(StateMachine.class);
    }
    
    @Override
    public void handle(StateMachine stateMachine, Object[] args)
    {
        assert_(true); // TODO: remove me and finish
    }
}
