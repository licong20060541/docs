```
/**
 * Ensure a single status bar service implementation is running at all times.
 *
 * <p>The implementation either comes from a service component running in a remote process (defined
 * using a secure setting), else falls back to using the in-process implementation according
 * to the product config.
 */


    // manages the implementation coming from the remote process
    private ServiceMonitor mServiceMonitor;

    // in-process fallback implementation, per the product config
    private BaseStatusBar mStatusBar;

 // internal handler + messages used to serialize access to internal state
```