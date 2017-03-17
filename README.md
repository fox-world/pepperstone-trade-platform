# pepperstone-trade-platform
Manage your trade orders for multiple accounts at the same time.

## About
This project is used to trade **[Pepperstone](https://pepperstone.com/)** account via **[NJ4X](http://www.jfx-api.com/)** api and it's a project on **[Elance](http://www.elance.com)**.

This project using **[Swing](http://docs.oracle.com/javase/7/docs/technotes/guides/swing/)** as the user interface and using java mutiple threads to manage the account login and trade orders.Once we installed this software we can manage **10 trade accounts** at the same time. 

So far this project only support **[Pepperstone](https://pepperstone.com/)** account to login,but will add support to other broker account later.

## Required
* jdk1.7.40+
* jfx-2.2.1.jar (Download it from here [jfx-2.2.1.7z](http://www.jfx-api.com/downloads)) 
* log4j-1.2.16.jar

## Usage
1. Download this project into your local computer;
2. In the NJ4X folder,open the ***run\_terminal\_server.bat*** file;
2. In the package ***com.elance*** run the ***PepperstoneMain.java*** file;
3. In the [Swing](http://docs.oracle.com/javase/7/docs/technotes/guides/swing/) dialog input your trade account and login;

## License
The [MIT](http://opensource.org/licenses/MIT) License 