package com.jcope.debug;

import static com.jcope.debug.Debug.DEBUG;

import com.jcope.vnc.shared.StateMachine.CLIENT_EVENT;
import com.jcope.vnc.shared.StateMachine.SERVER_EVENT;

public class LLog
{
	public static void e(Throwable e)
	{
		e(e, true);
	}
	
	public static void e(Throwable e, boolean rethrow)
	{
		e(e, rethrow, false);
	}
	
	public static void e(Throwable e, boolean rethrow, boolean hardStop)
	{
	    if (!DEBUG){if(!hardStop && !rethrow){return;}}
	    (rethrow ? System.err : System.out).println(e.getMessage());
		e.printStackTrace(rethrow ? System.err : System.out);
		if (hardStop)
		{
			System.exit(127);
		}
		else if (rethrow)
		{
			if (e instanceof RuntimeException)
			{
				throw ((RuntimeException)e);
			}
			else
			{
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void i(String info_msg)
	{
	    if (!DEBUG){return;}
		System.out.println(info_msg);
	}
	
	public static void w(String warn_msg)
    {
        if (!DEBUG){return;}
        System.err.println(warn_msg);
    }
    
	public static void w(Exception e)
    {
        if (!DEBUG){return;}
        System.err.println(e.getMessage());
        e.printStackTrace(System.err);
    }
    
    public static void logEvent(String source, SERVER_EVENT event, Object[] args)
	{
	    if (!DEBUG){return;}
	    if (event == SERVER_EVENT.SCREEN_SEGMENT_CHANGED
	            || event == SERVER_EVENT.CURSOR_MOVE
                || event == SERVER_EVENT.CURSOR_GONE
                || (event == SERVER_EVENT.SCREEN_SEGMENT_UPDATE && ((Integer)args[0]) != -1)
                || event == SERVER_EVENT.READ_INPUT_EVENTS)
	    {
	        return;
	    }
		_logEvent(source, event, args);
	}

	public static void logEvent(String source, CLIENT_EVENT event, Object[] args)
	{
	    if (!DEBUG){return;}
	    if ((event == CLIENT_EVENT.GET_SCREEN_SEGMENT && ((Integer)args[0]) != -1)
	            || event == CLIENT_EVENT.OFFER_INPUT)
	    {
	        return;
	    }
		_logEvent(source, event, args);
	}

	private static void _logEvent(String source, Object event, Object[] args)
	{
		String eventName;
		if (event instanceof SERVER_EVENT)
		{
			eventName = ((SERVER_EVENT)event).name();
		}
		else
		{
			eventName = ((CLIENT_EVENT)event).name();
		}
		System.out.print(String.format("%s sent event: %s", source, eventName));
		if (args == null)
		{
			System.out.println(" - null");
		}
		else
		{
			System.out.print(" - [");
			boolean isFirst = true;
			for (Object obj : args)
			{
				if (isFirst)
				{
					isFirst = false;
				}
				else
				{
					System.out.print(", ");
				}
				System.out.print(obj == null ? "null" : obj.toString());
			}
			System.out.println("]");
		}
	}
}
