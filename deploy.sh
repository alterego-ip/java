#!/bin/bash

APP_NAME=lab1
TOMCAT_DIR=/opt/tomcat

echo "Building project..."
mvn clean package || { echo "Build failed"; exit 1; }

echo "Removing old version..."
sudo rm -rf $TOMCAT_DIR/webapps/$APP_NAME

echo "Copying new version..."
sudo cp target/$APP_NAME.war $TOMCAT_DIR/webapps/

echo "Restarting Tomcat..."
sudo $TOMCAT_DIR/bin/shutdown.sh
sleep 2
sudo $TOMCAT_DIR/bin/startup.sh

echo "Deployment complete! Open in browser: http://localhost:8080/$APP_NAME/products"
