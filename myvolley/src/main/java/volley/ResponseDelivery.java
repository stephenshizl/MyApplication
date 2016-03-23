package volley;

/**
 * 返回结果分发接口，目前只有基于ExecutorDelivery的在入参 handler 对应线程内进行分发
 * @author admin
 *
 */
public interface ResponseDelivery {
    /**
     * Parses a response from the network or cache and delivers it.
     */
    public void postResponse(Request<?> request, Response<?> response);
    
    /**
     * Parses a response from the network or cache and delivers it. The provided
     * Runnable will be executed after delivery.
     */
    public void postResponse(Request<?> request, Response<?> response, Runnable runnable);
    
    /**
     * Posts an error for the given request.
     */
    public void postError(Request<?> request, VolleyError error);
}
