# Gateway (Agent System)

## Repository
https://github.com/NLCProject/Gateway

## Installation
### Database
Install any SQL database on your local machine. This project was tested with MYSQL
(https://dev.mysql.com/downloads/mysql/). The following description is based on MYSQL.

+ During installation, create an user with name 'user' and password 'password'. If you want to use different 
credentials, change it in file 'application.properties' in fields 'spring.datasource.username' and 
'spring.datasource.password' too.
+ After installation, open the 'MySQL 8.0 Command Line Client' terminal and login as root.
+ Create the database with command 'CREATE DATABASE gateway'. If you want to use different
  database name, change it in file 'application.properties' in field 'spring.datasource.url'.
+ Tables are created by the Spring framework automatically when the application is started.

## Frontend
The Gateway application has a frontend application to display and control the connected batteries

+ The first step is to install all dependencies. Go to the location '.\Gateway\internal-frontend'
+ Enter command 'npm install --save --legacy-peer-deps'.
+ In order to start the frontend, enter the command 'ng serve'
+ If the frontend application is running, enter the URL 'localhost:4200' in the browser. Since it is an Angular 
application, it is highly recommended to use Chrome to avoid any display errors.
+ Watch out to use different ports when starting the Virtual Power Plant simulator too. In order to start the 
application on a different port, use the command 'ng serve --port 4201'.

## Database Configuration
By default, the database is cleared and created new at every startup. To avoid this, a change configuration has to be
done in the file 'application.properties'. Change the following files to persist the data in the database after a
restart:
+ spring.jpa.hibernate.ddl-auto=none
+ spring.datasource.initialization-mode=never

## Application
The application is divided in different modules. The 'Core' module contains the entry point of the application.
You can start the application via the file 'Application.kt'. But make sure you installed the database first.

### Server Port
This application starts on port 8082. As user this is not that important, since the frontend connects with it 
automatically. In case the port is already used, it can be changed in file 'application.properties' and field 
'server.port'. Additionally, the server port needs to be changed in the frontend too. The port is defined in the file 
'rest-header.service.ts'

### REST interface
For passive communication with the frontend and the virtual power plant, this application provides a REST interface. 
The controllers are available via the configured server port and defined in the '*Controller.kt' classes.

### Websocket
For a reactive communication with the frontend and the virtual power plant, a websocket communication is provided. It 
connects automatically at startup and sends all defined data via the internal (frontend) or public (VPP) connection. 
The configuration can be found in file 'application.conf' in the 'Core' module. Be aware that the websocket starts on a 
different port than the server application (by default 8081).

### Multi-Start
The system is designed to start it multiple times and to provide a proper communication with the virtual power plant. 
But it is highly recommended to start this application on different host machines, connected via the same local network.
But anyhow, if it should be started on the same machine, for every instance, different ports and databases have to be 
defined in the configuration (see description above). Actually there is no limit how many instances can be used.

### Virtual Power Plant connection
The gateway doesn't search the entire the network for the virtual power plant. Instead, the required information to #
connect with it, is defined in the file 'application.properties'.
+ adapter.vpp.port - Port
+ adapter.vpp.url - URL to connect with the REST interface. Required for initial registration
+ adapter.vpp.host - IP address
+ adapter.vpp.enabled - Defines if this gateway should connect to the VPP at all

## Communication with the virtual power plant
Actually it is required, that the virtual power plant has to be started first. When this application starts, it tries 
to connect with the virtual power plant after 15 seconds when it is ready. This wanted delay is required, to make sure 
any part of the application is ready before it starts communication with the virtual power plant. In past, the 
websocket connection was slower at startup than the application itself.

## Battery Management Clients
### Hardware
Hardware clients are connected via a serial connection. On a host machine this is done via a USB port. The application 
checks a pre-defined COM port which provides the serial connection to this port. This port can change from device to 
device and any user has to find out the correct port by itself. The port can be changed in the file 
'application.properties' in the field 'gateway.bms.port'.

<b>Hint</b>: The available COM ports can be found in the Windows Device Manager. If a serial device is connected, the 
status of a COM port must change.

The configuration for the hardware BMS client setup is defined in the file 'application.properties'.
+ gateway.bms.port=COM8 - COM port which provides the serial connection
+ gateway.bms.port.override=false - On linux systems, the port defined in field 'gateway.bms.port' needs to be set in 
the system properties too. This is only done if enabled.
+ gateway.bms.baudRate=115200 - Baudrate. Make sure the battery management system uses the same value
+ gateway.bms.simulator.enabled=true - Defines if the virtual simulators should be started

### Virtual
All virtual clients are managed by the 'Simulator' module. In this module, the configuration file 'VirtualBatteries.kt' 
is defined. This file contains all virtual batteries, which are started automatically with and by the app. Every 
virtual client injects automatically its measurement data in the application. The application cannot distinguish 
between data from a hardware (real) or virtual battery client. Actually there is no limit how many virtual clients can 
be used.
