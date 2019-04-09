<b>Prerequisites:</b><br/>
    Any IDE(e.g IntelliJ, Eclipse), Gradle , GIT, Java 8, MySQL 5.6+

Adeva Library applications require MySQL database to store its data. Make sure to update application.properties with
<b>spring.datasource.URL</b>, <b>spring.datasource.username</b>, and  <b>spring.datasource.password</b>.

The Adeva Library application uses liquibase for Database changes. In case you need to update the Database, please create a new changeset file in
resources/db.changelog folder and include the newly created file in db.changelog-master.xml

For more details on liquibase follow https://www.liquibase.org/documentation/changeset.html

<b>Adeva Library Application:</b><br/>
    To start the application run AdevaLibraryApplication.java main method from your IDE.