# Alternative CookieManager for CAS

This implementantion ignores IP stored in CookieManager for enviroments where switching between
WIFI and cable networks is often and brokes *remember me* functionality.

Works with CAS 5.1+ server.

You have to install this component to your maven repository and add following snippet to Maven overlay.

    <dependency>
        <groupId>org.apereo.cas</groupId>
        <artifactId>cas-server-support-cookie-roaming</artifactId>
        <version>5.3.0</version>
    </dependency>

## Note

CAS 6.x provides optional property to switch off the pinning to IP/UserAgent of browser. This makes this component redundant.

    cas.tgc.pinToSession=false

## Changelog

### 5.3.0

Version compatible with CAS 5.3.x

### 5.2.0

Version compatible with CAS 5.2.x

### 1.0

Version compatible with CAS 5.1.x
