#!/bin/sh

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# Example MacOS X java runtime environment directory
JRE_DIRECTORY="/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/"

sudo keytool -trustcacerts -keystore $JRE_DIRECTORY/lib/security/cacerts -storepass changeit -alias payara -import -file $SCRIPT_DIR/payara-self-signed-certificate.crt
