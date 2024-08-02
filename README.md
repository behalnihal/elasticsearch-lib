# Elasticsearch Library

This library provides a simple interface for Elasticsearch operations using the Elasticsearch Java API.

## Setup

1. Download this project.
2. Open project in desired IDE.
3. run command
 ``` mvn clean install ```
4. Now add the dependency in your pom.xml file
5. Ensure you have the Elasticsearch Java API dependencies in your project.

## Usage

First, create an Elasticsearch client:

```java
ElasticsearchClient client = ElasticsearchConfig.getElasticsearchClient("username", "password");
```

### Search Index
```java
boolean foo = ElasticsearchOperations.searchIndex(client, indexName);
```
this method returns a boolean if the index indexName exists or not.

### Get Document by ID

```java
ObjectNode document = ElasticsearchOperations.getDocumentById(client, "index_name", "document_id");
```

### Search Documents

```java
List<ObjectNode> results = ElasticsearchOperations.searchDocuments(client, "index_name", "search_term", "field_name");
```

### Get Documents by Date Range

```java
long startTimestamp = 1641203425;
long endTimestamp = 1641280856;
List<ObjectNode> documents = ElasticsearchOperations.getDocumentsByDateRange(client, "my_index", "timestamp", startTimestamp, endTimestamp);
```
The timestamps in the example are Unix timestamps, which represent the number of seconds that have elapsed since January 1, 1970 (UTC). 
## Error Handling

All methods throw `IOException`. Make sure to handle these exceptions in your code.

## Note

This library assumes you're using the Elasticsearch Java API. Ensure you have the correct dependencies in your project.
