SeaChange2 Role Play Simulation
==================================

The Sea Change Online Roleplay Platform: the tool to enable us all to play, create, conduct and share technology enhanced conflict simulations.

=================
3/23/18

This repo is to demonstrate Java Spring coding by Mike Sheliga. It contains Java Spring MVCs (model-view-controllers) with JSPs (Java Server Pages), and Hibernate (a database to object mapper). Currently (3.23.18) the administration options affecting users and organizations have been fully implemented while most of the other sections are under construction.

To log into this simulation and create the database tables.
Use install/index.jsp to create a new administrator account using the key "local".

=============================================================

3/23/18 Entire Eclipse project uploaded using command line git including 40 new or modified files.

Examples of new JSP, controller, formbean, object, dao, and searchFormBuilder code uploaded in the various admin and administration folders including:

JSPs: Located in src/main/webapp/WEB-INF/views. New JSPs: in the admin folder. The jsps in the org sub-folder are all new, and all but createSuccess.jsp have been created or modified in the user sub-folder,
as has adminIndex.jsp.

Controllers: Located in src/main/java/com/seachangesimulations/platform/controllers.
Both AdminController.java and HomeController.java have been significantly expanded.

FormBeans: Located in src/main/java/com/seachangesimulations/platform/mvc/formbeans. Created the admin/AdminCreateOrgFormBean.java class.

Objects: Located in src/main/java/com/seachangesimulations/platform/domain. Modified Person.java and significantly updated Organization.java, including creating searchFor and getDBColumnNames Methods, as well as saving dates when a new organization is created.

DAO: Located in src/main/java/com/seachangesimulations/platform/dao. Modified PersonDaoHibernateImpl and similar Organization file, as well as the BaseDaoHibernateImpl file which now contains routines to search and get database column names. (The interfaces were updated as needed as well.)

SearchFormBuilder: Located in src/main/java/com/seachangesimulations/platform/html. Created SearchFormBuilder.java which supports auto-creation of standard search options for Strings. This includes creating an ignore-case checkbox as well as a select type of operation (such as exactly matches, contains or starts with) select list. This can be expanded to include other searchable SQL types (numeric types, dates, etc.) in the future.

Spring-Hibernate: Modified file such that the database is not recreated for every execution.

Credits: This repo can be compared to the SeaChange repo at skipcole / SeaChangePlatform from which it was dervied. Currently I have implemented the Administration Control section. The other sections were implemented by Skip Cole. Many of the other sections are under construction.
