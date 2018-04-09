package algorithms;

import java.lang.management.*;

/**
 * Class provides methods for timing single-threaded tasks
 * Taken from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking
 * Use like:
 * long startSystemTimeNano = getSystemTime( );
 * long startUserTimeNano   = getUserTime( );
 * ... do task ...
 * long taskUserTimeNano    = getUserTime( ) - startUserTimeNano;
 * long taskSystemTimeNano  = getSystemTime( ) - startSystemTimeNano;
 */
public class Timing {


    /** Get CPU time in nanoseconds. */
    public static long getCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadCpuTime( ) : 0L;
    }

    /** Get user time in nanoseconds. */
    public static long getUserTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadUserTime( ) : 0L;
    }

    /** Get system time in nanoseconds. */
    public static long getSystemTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                (bean.getCurrentThreadCpuTime( ) - bean.getCurrentThreadUserTime( )) : 0L;

    }


}
