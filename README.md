Searchbox/Search Framework
=========
Searchbox is a Search Framework developed under java/spring which enables you as a developer or CIO to easily leverage backend search engines such as Apache Solr, ElasticSearch and Google Site Search in an easy way.

**Some of the goals for the project are that it should**

* be easy to leverage Apache Solr, ElasticSearch or Google Site Search
* be easy to extend by adding new components or new adapters for search engines
* be flexible
* be user-friendly on all platforms (desktop, tablet and mobile)
* have good code quality (use SonarSource)
* have good documentation
* be the best search engine framework

Usage
---------------
In order to quickly get started with the framework, you can run it from the command line:

```shell
$ git clone git@github.com:searchbox/framework.git
$ cd framework
$ mvn clean package -DskipTests
$ java -jar target/dependency/jetty-runner.jar --port 8080 target/*.war
```

Overview
---------------
The Searchbox framework provides an easy way to build a search engine to visualise both structured data and non-structured data


Components
---------------

**Collections**

**Search engine**

**Search elements**

**Web**
The Searchbox framework provides an easy way to build a search engine to visualise 


Components
---------------

TODO
=======
* Finish the framework
* Add new components such as RangeFacet and GeoFacet
* Write the technical documentation
* Build a new website for the open-source project


License
=======
Searchbox is distributed under the Apache 2 license. Please keep the existing headers.

Attribution
======
Main developers
- Gamard Stephane - <stephane.gamard@searchbox.com>, @gamars, http://www.searchbox.com
- Rey Jonathan - <jonathan.rey@searchbox.com>, @searchbox, http://www.searchbox.com