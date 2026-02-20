# JDBC_pro - Java Database Connectivity Practice Project

A structured Java project built on Java SE 21 that demonstrates practical JDBC programming using PostgreSQL. The project covers all major JDBC statement types and their CRUD operations, organized into four focused packages. Each class is a standalone, runnable program targeting a real PostgreSQL database.

---

## Table of Contents

1. Project Overview
2. Project Structure
3. Referenced Library - PostgreSQL JDBC Driver JAR
4. How to Add the JAR to Your Project
5. How to Switch to a Different Database
6. Database and Table Details
7. JDBC Connection Configuration
8. Package and Class Breakdown
9. Operations Covered Per Class
10. Key JDBC Concepts Demonstrated
11. Prerequisites
12. How to Run
13. Contributing

---

## 1. Project Overview

JDBC_pro is a hands-on reference project for understanding JDBC using real SQL operations on a PostgreSQL database. The project is organized by JDBC statement type so that each package focuses on a single approach: plain Statement, PreparedStatement, Batch Execution using both statement types, and CallableStatement for invoking stored procedures. All programs operate on the public.sports_list table and follow proper resource management with finally-block cleanup.

---

## 2. Project Structure

```
JDBC_pro/
|
|-- src/
|   |
|   |-- BatchExecution/
|   |   |-- BatchExe_PreparedStatement.java    (Batch INSERT, UPDATE, DELETE via PreparedStatement)
|   |   |-- BatchExe_Statement.java            (Batch UPDATE, INSERT, DELETE via Statement)
|   |
|   |-- CallableStatement/
|   |   |-- CallableStatement_procedure.java   (Calling stored procedure insert_sport)
|   |
|   |-- PreparedStatement/
|   |   |-- PreparedStatement_With_CRUD.java   (Full CRUD using PreparedStatement)
|   |
|   |-- Statement/
|       |-- Statement_With_CRUD.java           (Full CRUD using Statement + execute() demo)
|
|-- Referenced Libraries/
|   |-- postgresql-42.7.8.jar                  (PostgreSQL JDBC Driver - already added)
|
|-- README.md
```

---

## 3. Referenced Library - PostgreSQL JDBC Driver JAR

This project uses the PostgreSQL JDBC driver JAR to establish a connection between Java and the PostgreSQL database. Without this JAR on the classpath, all programs will throw a ClassNotFoundException at the Class.forName() line and fail immediately.

The JAR already referenced in this project is:

    postgresql-42.7.8.jar

It is located at:

    C:\Users\Hema\Downloads\postgresql-42.7.8.jar

This JAR is visible in the Eclipse IDE under Referenced Libraries in the project tree. Anyone cloning this repository must download and add the same JAR to their own project manually, as JAR files are not committed to Git repositories.

---

## 4. How to Add the JAR to Your Project

### Step 1 - Download the PostgreSQL JDBC Driver JAR

Visit the official download page and download the latest stable JAR:

    https://jdbc.postgresql.org/download/

The file will be named something like:

    postgresql-42.7.8.jar

### Step 2 - Create a lib folder and place the JAR inside it

    JDBC_pro/
    |-- lib/
    |   |-- postgresql-42.7.8.jar

### Step 3 - Add the JAR to the Eclipse build path

1. Right-click the project in Package Explorer
2. Select Build Path and then Configure Build Path
3. Click the Libraries tab
4. Click Add External JARs
5. Navigate to your lib folder and select the JAR
6. Click Apply and Close

The JAR will now appear under Referenced Libraries in the project tree, matching what is shown in this project.

### Step 4 - Add the JAR to the IntelliJ IDEA build path

1. Open File and then Project Structure using Ctrl + Alt + Shift + S
2. Go to Modules and then the Dependencies tab
3. Click the plus icon and choose JARs or Directories
4. Select the JAR file and click OK
5. Click Apply

### Step 5 - Verify the setup

Run Statement_With_CRUD.java. If the console prints "Driver Loaded Successfully" followed by "Database Connected", the JAR is correctly configured.

---

## 5. How to Switch to a Different Database

This project connects to PostgreSQL by default, but any JDBC-compatible relational database can be used by changing two things in each Java file: the driver class name and the connection URL. The JAR file must also be swapped.

### Step 1 - Download the JAR for your chosen database

| Database    | JAR File Name                  | Official Download                                              |
|-------------|--------------------------------|----------------------------------------------------------------|
| PostgreSQL  | postgresql-42.x.x.jar          | https://jdbc.postgresql.org/download/                         |
| MySQL       | mysql-connector-j-x.x.x.jar   | https://dev.mysql.com/downloads/connector/j/                  |
| Oracle      | ojdbc8.jar                     | https://www.oracle.com/database/technologies/appdev/jdbc.html |
| H2          | h2-x.x.x.jar                   | https://h2database.com/html/download.html                     |
| SQL Server  | mssql-jdbc-x.x.x.jre8.jar     | https://learn.microsoft.com/en-us/sql/connect/jdbc/download   |

Place the JAR in the lib folder and add it to the build path as described in Section 4.

### Step 2 - Change the Class.forName() driver class in each Java file

PostgreSQL (currently used in all files):

    Class.forName("org.postgresql.Driver");

MySQL:

    Class.forName("com.mysql.cj.jdbc.Driver");

Oracle:

    Class.forName("oracle.jdbc.OracleDriver");

H2:

    Class.forName("org.h2.Driver");

SQL Server:

    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

### Step 3 - Change the connection URL in each Java file

PostgreSQL (currently used):

    String url = "jdbc:postgresql://localhost:5432/postgres";

MySQL:

    String url = "jdbc:mysql://localhost:3306/your_database_name";

Oracle:

    String url = "jdbc:oracle:thin:@localhost:1521:xe";

H2 In-Memory:

    String url = "jdbc:h2:mem:testdb";

SQL Server:

    String url = "jdbc:sqlserver://localhost:1433;databaseName=your_database_name";

### Complete Driver Reference Table

| Database    | Driver Class                                       | JDBC URL Format                                      |
|-------------|-----------------------------------------------------|------------------------------------------------------|
| PostgreSQL  | org.postgresql.Driver                              | jdbc:postgresql://localhost:5432/dbname              |
| MySQL       | com.mysql.cj.jdbc.Driver                           | jdbc:mysql://localhost:3306/dbname                   |
| Oracle      | oracle.jdbc.OracleDriver                           | jdbc:oracle:thin:@localhost:1521:xe                  |
| H2          | org.h2.Driver                                      | jdbc:h2:mem:testdb                                   |
| SQL Server  | com.microsoft.sqlserver.jdbc.SQLServerDriver       | jdbc:sqlserver://localhost:1433;databaseName=dbname  |

No changes to application logic are needed when switching databases. Only the JAR file, Class.forName() value, and connection URL need to be updated.

---

## 6. Database and Table Details

All five programs in this project operate on a single PostgreSQL table named public.sports_list.

### public.sports_list

| Column | Data Type | Notes                            |
|--------|-----------|----------------------------------|
| id     | SERIAL    | Primary key, auto-incremented    |
| name   | VARCHAR   | Name of the person               |
| sport  | VARCHAR   | Sport played by the person       |

Create this table in your PostgreSQL database before running any program:

    CREATE TABLE public.sports_list (
        id    SERIAL PRIMARY KEY,
        name  VARCHAR(100),
        sport VARCHAR(100)
    );

### Stored Procedure - insert_sport

Used by CallableStatement_procedure.java. Create this procedure in PostgreSQL before running that program:

    CREATE OR REPLACE PROCEDURE insert_sport(p_name VARCHAR, p_sport VARCHAR)
    LANGUAGE plpgsql
    AS $$
    BEGIN
        INSERT INTO public.sports_list (name, sport) VALUES (p_name, p_sport);
    END;
    $$;

---

## 7. JDBC Connection Configuration

All programs in this project share the same connection parameters. Update the user and password values in each file to match your local PostgreSQL setup before running.

| Property     | Value                                     |
|--------------|-------------------------------------------|
| Driver Class | org.postgresql.Driver                     |
| URL          | jdbc:postgresql://localhost:5432/postgres |
| Username     | postgres                                  |
| Database     | postgres                                  |
| JAR Used     | postgresql-42.7.8.jar                     |
| Java Version | Java SE 21                                |

---

## 8. Package and Class Breakdown

### Statement package

Contains one class that demonstrates full CRUD operations using the basic Statement interface. Statement executes static SQL strings with no parameters. This class also demonstrates the difference between executeQuery(), executeUpdate(), and execute() methods.

Class: Statement_With_CRUD.java

What it does: Loads the driver, establishes a connection, creates a Statement, and runs INSERT, SELECT, UPDATE, DELETE, and execute() operations on public.sports_list. All three resources (Connection, Statement, ResultSet) are closed in the finally block with individual null checks.

### PreparedStatement package

Contains one class that demonstrates the same full CRUD operations using PreparedStatement. Unlike Statement, PreparedStatement uses parameterized queries with question mark placeholders and binds values using setString() and setInt() methods.

Class: PreparedStatement_With_CRUD.java

What it does: Executes INSERT, SELECT, UPDATE, and DELETE operations using separate PreparedStatement instances for each query. The ResultSet is read using getInt() and getString() with column names. Each PreparedStatement is explicitly closed after use before the next one is created.

### BatchExecution package

Contains two classes demonstrating batch processing, where multiple SQL statements are grouped and sent to the database in a single network call instead of one at a time.

Class: BatchExe_Statement.java

What it does: Creates a Statement and adds three different SQL operations (UPDATE, INSERT, DELETE) to a batch using addBatch(). The batch is sent with executeBatch() which returns an integer array. Each element in the array represents the row count affected by the corresponding statement.

Class: BatchExe_PreparedStatement.java

What it does: Demonstrates batch execution using three separate PreparedStatement instances for INSERT, UPDATE, and DELETE. Each PreparedStatement has its own batch. Parameters are set using setString() and setInt() and each row is staged with addBatch() before calling executeBatch(). All three PreparedStatement objects are closed independently in the finally block.

### CallableStatement package

Contains one class that calls a stored procedure defined in the PostgreSQL database using CallableStatement.

Class: CallableStatement_procedure.java

What it does: Loads the driver, establishes a connection, and calls the stored procedure insert_sport using prepareCall() with JDBC escape syntax. Two IN parameters (name and sport) are bound using setString(). The procedure is invoked with execute(). The program prints step-by-step confirmation messages for each stage of the JDBC workflow.

---

## 9. Operations Covered Per Class

| Class                          | INSERT | SELECT | UPDATE | DELETE | BATCH | PROCEDURE |
|--------------------------------|--------|--------|--------|--------|-------|-----------|
| Statement_With_CRUD            | Yes    | Yes    | Yes    | Yes    | No    | No        |
| PreparedStatement_With_CRUD    | Yes    | Yes    | Yes    | Yes    | No    | No        |
| BatchExe_Statement             | Yes    | No     | Yes    | Yes    | Yes   | No        |
| BatchExe_PreparedStatement     | Yes    | No     | Yes    | Yes    | Yes   | No        |
| CallableStatement_procedure    | No     | No     | No     | No     | No    | Yes       |

---

## 10. Key JDBC Concepts Demonstrated

Statement vs PreparedStatement: Statement executes a static SQL string every time. PreparedStatement compiles the query once and allows parameter values to be swapped using setXxx() methods. PreparedStatement is preferred for security because it prevents SQL injection, and for performance because the query plan is reused across executions.

executeUpdate vs executeQuery vs execute: executeUpdate() is used for INSERT, UPDATE, and DELETE and returns the number of rows affected as an integer. executeQuery() is used for SELECT and returns a ResultSet. execute() is a general-purpose method that returns a boolean indicating whether the result is a ResultSet. If it returns false, getUpdateCount() is called to retrieve the row count.

Batch Execution: Grouping SQL statements into a batch using addBatch() and sending them all at once with executeBatch() reduces the number of network round trips to the database. This is significantly more efficient than sending each statement individually when working with multiple rows. executeBatch() returns an int array where each index corresponds to the rows affected by that statement.

CallableStatement and Stored Procedures: When logic is defined inside the database as a stored procedure, Java invokes it using prepareCall() with the procedure name wrapped in JDBC escape syntax. Parameters are bound using the same setXxx() methods as PreparedStatement.

Resource Cleanup in the Finally Block: Connection, Statement, and ResultSet are all costly objects that hold active database connections and cursors. Closing them in a finally block with individual null checks ensures they are always released, even if an exception occurs during query execution. Failing to close these resources in long-running applications causes connection pool exhaustion.

---

## 11. Prerequisites

- Java SE 21 or compatible JDK installed
- Eclipse IDE or IntelliJ IDEA
- PostgreSQL installed and running locally on port 5432
- PostgreSQL JDBC Driver JAR downloaded from https://jdbc.postgresql.org/download/ and added to the project build path (see Section 4)
- The public.sports_list table created in your PostgreSQL database (see Section 6)
- The insert_sport stored procedure created if running CallableStatement_procedure.java (see Section 6)

---

## 12. How to Run

1. Clone or download this repository

       git clone https://github.com/your-username/JDBC_pro.git

2. Open the project in Eclipse or IntelliJ IDEA

3. Download postgresql-42.7.8.jar from https://jdbc.postgresql.org/download/ and add it to the build path as described in Section 4

4. Create the public.sports_list table and the insert_sport stored procedure in your PostgreSQL database using the SQL provided in Section 6

5. Open the Java file you want to run and update the user and password variables to match your PostgreSQL credentials

6. Right-click the file and select Run As Java Application in Eclipse, or click the Run button in IntelliJ IDEA

7. View output in the console

Recommended run order for learning: start with Statement_With_CRUD.java to verify your setup, then run PreparedStatement_With_CRUD.java, then both BatchExecution classes, and finally CallableStatement_procedure.java after creating the stored procedure.

---

## 13. Contributing

Contributions are welcome. To add new JDBC examples or improve existing programs:

1. Fork the repository on GitHub
2. Create a new branch for your changes
3. Follow the existing package naming convention when adding new programs
4. Ensure each class focuses on one JDBC concept or statement type
5. Remove all hardcoded credentials before committing
6. Open a Pull Request with a clear description of what was added or changed

---

## License

This project is open source and available under the MIT License.

---

## Author

Your Name
GitHub: [https://github.com/your-username](https://github.com/Hemapadmavathi-sanka)
