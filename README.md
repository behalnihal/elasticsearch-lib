# elasticsearch-lib
A Spring Boot library for simplifying Elasticsearch queries in Spring applications. It provides a simple API for executing Elasticsearch queries without the need to directly interact with the Elasticsearch client.



Components Interaction :- 

![image](https://github.com/user-attachments/assets/2f538f01-29e9-4dc4-ae46-ece26b57cfbb)


1) User : external app using dependency
2) REST API : receives user's request
3) Core lib : receives req from REST API -> build query -> send query to elastic client
4) Config : configuration settings used by core lib
5) ES client wrapper: communicates directly with Elasticsearch
6) Elasticsearch : cluster that stores data

Features : - 
1) Supports multiple queries
2) error handling and input validation
3) can configure connection settings
