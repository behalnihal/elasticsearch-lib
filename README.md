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
        .select("id", "title")
        .from("articles")
        .where("publish_date > '2023-01-01'")
        .match("content", "elasticsearch")
        .query("machine learning")
        .score("1 / LOG10(1 + age)")
        .count("*")
        .countDistinct("author")
        .groupBy("category")
        .having("COUNT(*) > 5")
        .orderBy("SCORE() DESC")
        .limit(15)
        .build();

        System.out.println(query);
```
This would generate a query like :
```chatinput
SELECT id, title, COUNT(*), COUNT(DISTINCT author), MATCH(content, 'elasticsearch'), QUERY('machine learning'), SCORE(1 / LOG10(1 + age)) 
FROM articles 
WHERE publish_date > '2023-01-01' 
GROUP BY category 
HAVING COUNT(*) > 5 
ORDER BY SCORE() DESC 
LIMIT 15
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
