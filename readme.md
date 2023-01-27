# Stock Market System
## About the Project

The project is a client-server based application providing multiple functionalities as any conventional stock market/investment banking application. It supports two user personas viz. Admin & User. To name a few features:
1. Buy/Sell of stocks at market price or by setting limits.
2. Adding & Withdrawal of funds.
3. Display of current user portfolio along with list of transactions performed.
4. Registration of new users.

The admin console helps you to manage stock market by:
1. Adding new stocks.
2. Updating active time duration of the market.

Stock market can be simulated via an engine which takes care of order execution as well as cancellation of expiry

The project as three executables
1. Angular client which server frontend of the application
2. Jetty Serrver Application serving REST endpoints to interact with database
3. Executable to randomize stock prices and execute orders.

**Technology Stack**
1. Server - JAVA 8 (JDK 1.8)
2. Java Build - Maven v3.8
2. Client - Angular10
3. Database - PostreSQL v13

**Configuration Steps**

1. Database Setup
- Execute `sql-scripts/create.sql` script to schema and table objects in database.
- - The commented section in the file contains some test entries in the system
- - How to run the script?
- - Via commandline: [refer this](https://kb.objectrocket.com/postgresql/how-to-run-an-sql-file-in-postgres-846)
- - Via DBeaver: ![screenshot] (postgres-execute-sql.png) 
- Update the database connection string in `config.json`

2. Build the JAVA code to generate deployables.
- Run `mvn clean install package` in the root folder.
- Execute Stock engine by `java -jar engine\target\engine-0.1.jar` command.
- Start Jetty server to serve endpoints by `java -jar container\target\container-0.1.jar`

3. Build Angular code to start the client server
- navigate to `client` folder
- Run `npm install` to install node dependencies
- execute `npm start` to start the node server.

## Just execute it.
Java packages are already readly built for you. Navigate to `binaries` folder to run the engine and server directly.
 - Update database connection in `config.json`.
 - Open command prompt in `binaries`  folder
 - Execute `java -jar container\container-0.1.jar` to run API Server
 - Execute `java -jar engine\engine-0.1.jar` to run Stocks Engine
 
 ---
 *About Author*
 > **check license**
