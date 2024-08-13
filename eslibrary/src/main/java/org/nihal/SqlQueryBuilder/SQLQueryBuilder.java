package org.nihal.SqlQueryBuilder;
import java.util.*;

public class SQLQueryBuilder {
    private List<String> selectFields = new ArrayList<>();
    private String fromTable;
    private List<String> whereClauses = new ArrayList<>();
    private List<String> groupByClauses = new ArrayList<>();
    private List<String> havingClauses = new ArrayList<>();
    private List<String> orderByClauses = new ArrayList<>();
    private int limit = -1;
    private List<String> aggregateFunctions = new ArrayList<>();
    private List<String> fullTextFunctions = new ArrayList<>();
    private String scoreFunction;

    public SQLQueryBuilder select(String... fields) {
        selectFields.addAll(Arrays.asList(fields));
        return this;
    }

    public SQLQueryBuilder from(String table) {
        this.fromTable = table;
        return this;
    }

    public SQLQueryBuilder where(String condition) {
        whereClauses.add(condition);
        return this;
    }

    public SQLQueryBuilder groupBy(String... fields) {
        groupByClauses.addAll(Arrays.asList(fields));
        return this;
    }

    public SQLQueryBuilder having(String condition) {
        havingClauses.add(condition);
        return this;
    }

    public SQLQueryBuilder orderBy(String... fields) {
        orderByClauses.addAll(Arrays.asList(fields));
        return this;
    }

    public SQLQueryBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public SQLQueryBuilder match(String field, String query) {
        fullTextFunctions.add("MATCH(" + field + ", '" + query + "')");
        return this;
    }

    public SQLQueryBuilder query(String query) {
        fullTextFunctions.add("QUERY('" + query + "')");
        return this;
    }

    public SQLQueryBuilder score(String scoreExpression) {
        this.scoreFunction = "SCORE(" + scoreExpression + ")";
        return this;
    }

    public SQLQueryBuilder count(String field) {
        aggregateFunctions.add("COUNT(" + field + ")");
        return this;
    }

    public SQLQueryBuilder countDistinct(String field) {
        aggregateFunctions.add("COUNT(DISTINCT " + field + ")");
        return this;
    }

    public String build() {
        if (fromTable == null || fromTable.isEmpty()) {
            throw new IllegalStateException("FROM clause is required");
        }

        StringBuilder query = new StringBuilder("SELECT ");

        if (!selectFields.isEmpty()) {
            query.append(String.join(", ", selectFields));
        }

        if (!aggregateFunctions.isEmpty()) {
            if (!selectFields.isEmpty()) query.append(", ");
            query.append(String.join(", ", aggregateFunctions));
        }

        if (!fullTextFunctions.isEmpty()) {
            if (!selectFields.isEmpty() || !aggregateFunctions.isEmpty()) query.append(", ");
            query.append(String.join(", ", fullTextFunctions));
        }

        if (scoreFunction != null) {
            if (!selectFields.isEmpty() || !aggregateFunctions.isEmpty() || !fullTextFunctions.isEmpty()) query.append(", ");
            query.append(scoreFunction);
        }

        if (selectFields.isEmpty() && aggregateFunctions.isEmpty() && fullTextFunctions.isEmpty() && scoreFunction == null) {
            query.append("*");
        }

        query.append(" FROM ").append(fromTable);

        if (!whereClauses.isEmpty()) {
            query.append(" WHERE ").append(String.join(" AND ", whereClauses));
        }

        if (!groupByClauses.isEmpty()) {
            query.append(" GROUP BY ").append(String.join(", ", groupByClauses));
        }

        if (!havingClauses.isEmpty()) {
            query.append(" HAVING ").append(String.join(" AND ", havingClauses));
        }

        if (!orderByClauses.isEmpty()) {
            query.append(" ORDER BY ").append(String.join(", ", orderByClauses));
        }

        if (limit > 0) {
            query.append(" LIMIT ").append(limit);
        }

        return query.toString();
    }
}