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
List<ObjectNode> documents = getDocumentsByDateRange(
    client,
    "my_index",
    "dateField",
    "startDate",
    "endDate",
    "dateFormat"
);
```

The default date format used by Elasticsearch is yyyy-MM-dd. 
You can use multiple date formats such as dd/MM/yyyy, dd-MM-yyyy, etc.

### Sql Query Builder

```java
SQLQueryBuilder builder = new SQLQueryBuilder();
String query = builder
        .select("column1", "column2", ... )
        .from("table")
        .limit(10)
        .build();
```

### Sql Method

```java
String response = Sql.sql(client, query);
System.out.println(response);
```


## Error Handling

All methods throw `IOException`. Make sure to handle these exceptions in your code.

## Note

This library assumes you're using the Elasticsearch Java API. Ensure you have the correct dependencies in your project.
