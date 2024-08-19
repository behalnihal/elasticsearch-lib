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
        if (fields != null) {
            selectFields.addAll(Arrays.asList(fields));
        }
        return this;
    }

    public SQLQueryBuilder from(String table) {
        if (table != null && !table.isEmpty()) {
            this.fromTable = table;
        } else {
            throw new IllegalStateException("FROM clause is required");
        }
        return this;
    }

    public SQLQueryBuilder where(String condition) {
        if (condition != null && !condition.isEmpty()) {
            whereClauses.add(condition);
        }
        return this;
    }

    public SQLQueryBuilder groupBy(String... fields) {
        if (fields != null) {
            groupByClauses.addAll(Arrays.asList(fields));
        }
        return this;
    }

    public SQLQueryBuilder having(String condition) {
        if (condition != null && !condition.isEmpty()) {
            havingClauses.add(condition);
        }
        return this;
    }

    public SQLQueryBuilder orderBy(String... fields) {
        if (fields != null) {
            orderByClauses.addAll(Arrays.asList(fields));
        }
        return this;
    }

    public SQLQueryBuilder limit(int limit) {
        if (limit >= 0) {
            this.limit = limit;
        } else {
            throw new IllegalStateException("Limit must be a non-negative integer");
        }
        return this;
    }

    public SQLQueryBuilder match(String field, String query) {
        if (field != null && !field.isEmpty() && query != null && !query.isEmpty()) {
            whereClauses.add("MATCH(" + field + ", '" + query + "')");
        }
        return this;
    }

    public SQLQueryBuilder query(String query) {
        if (query != null && !query.isEmpty()) {
            fullTextFunctions.add("QUERY('" + query + "')");
        }
        return this;
    }

    public SQLQueryBuilder score(String scoreExpression) {
        if (scoreExpression != null && !scoreExpression.isEmpty()) {
            this.scoreFunction = "SCORE(" + scoreExpression + ")";
        }
        return this;
    }

    public SQLQueryBuilder count(String field) {
        if (field != null && !field.isEmpty()) {
            aggregateFunctions.add("COUNT(" + field + ")");
        }
        return this;
    }

    public SQLQueryBuilder countDistinct(String field) {
        if (field != null && !field.isEmpty()) {
            aggregateFunctions.add("COUNT(DISTINCT " + field + ")");
        }
        return this;
    }

    public String build() {
        if (fromTable == null || fromTable.isEmpty()) {
            throw new IllegalStateException("FROM clause is required");
        }

        StringBuilder query = new StringBuilder("SELECT ");

        if (!selectFields.isEmpty()) {
            query.append(String.join(", ", selectFields));
        } else {
            query.append("*");
        }

        query.append(" FROM ").append(fromTable);

        if (!whereClauses.isEmpty() || !fullTextFunctions.isEmpty()) {
            query.append(" WHERE ");
            if (!whereClauses.isEmpty()) {
                query.append(String.join(" AND ", whereClauses));
            }
            if (!fullTextFunctions.isEmpty()) {
                if (!whereClauses.isEmpty()) {
                    query.append(" AND ");
                }
                query.append(String.join(" AND ", fullTextFunctions));
            }
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