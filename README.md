# Proxy-Server
Simple proxy server with host blocking mechanism to implement host filtering.


## Steps to set up the project:

1. Add the hosts that need to be blocked to the Hashset declared in https://github.com/saifsabir97/Proxy-Server/blob/master/src/main/java/Main.java#L13.

Example:
```
HashSet<String> blockedHosts = new HashSet<String>(){{
    add("netflix.com");
    add("github.com");
}};
```
2. Run the project.
3. Set up the proxy address to the device's IP address and port 8081.
