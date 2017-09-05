# Alternative CookieManager for CAS

This implementantion ignores IP stored in CookieManager for enviroments where switching between
WIFI and cable networks is often and brokes *remember me* functionality.

Works with CAS 5.1+ server.

Add following snippet to Maven overlay.

    <dependency>
        <groupId>org.apereo.cas</groupId>
        <artifactId>cas-server-support-cookie-roaming</artifactId>
        <version>1.0</version>
    </dependency>

