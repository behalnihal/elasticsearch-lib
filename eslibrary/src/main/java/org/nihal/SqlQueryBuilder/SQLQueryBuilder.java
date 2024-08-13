package org.nihal.SqlQueryBuilder;

import java.util.Collections;

public class SQLQueryBuilder {
    private StringBuilder query;
    private boolean isInsert = false;
    private boolean isUpdate = false;

    public SQLQueryBuilder() {
        query = new StringBuilder();
    }

    public SQLQueryBuilder select(String... columns) {
        query.append("SELECT ").append(String.join(", ", columns));
        return this;
    }

    public SQLQueryBuilder from(String table) {
        query.append(" FROM ").append(table);
        return this;
    }

    public SQLQueryBuilder where(String condition) {
        query.append(" WHERE ").append(condition);
        return this;
    }

    public SQLQueryBuilder and(String condition) {
        query.append(" AND ").append(condition);
        return this;
    }

    public SQLQueryBuilder or(String condition) {
        query.append(" OR ").append(condition);
        return this;
    }

    public SQLQueryBuilder join(String type, String table, String condition) {
        query.append(" ").append(type).append(" JOIN ").append(table).append(" ON ").append(condition);
        return this;
    }

    public SQLQueryBuilder groupBy(String... columns) {
        query.append(" GROUP BY ").append(String.join(", ", columns));
        return this;
    }

    public SQLQueryBuilder having(String condition) {
        query.append(" HAVING ").append(condition);
        return this;
    }

    public SQLQueryBuilder orderBy(String... columns) {
        query.append(" ORDER BY ").append(String.join(", ", columns));
        return this;
    }

    public SQLQueryBuilder limit(int limit) {
        query.append(" LIMIT ").append(limit);
        return this;
    }

    public SQLQueryBuilder insert(String table, String... columns) {
        isInsert = true;
        query.append("INSERT INTO ").append(table).append(" (")
                .append(String.join(", ", columns)).append(") VALUES (");
        return this;
    }

    public SQLQueryBuilder values(Object... values) {
        query.append(String.join(", ", Collections.nCopies(values.length, "?")))
                .append(")");
        return this;
    }

    public SQLQueryBuilder update(String table) {
        isUpdate = true;
        query.append("UPDATE ").append(table).append(" SET ");
        return this;
    }

    public SQLQueryBuilder set(String column, String value) {
        if (isUpdate && query.toString().endsWith(" SET ")) {
            query.append(column).append(" = ?");
        } else {
            query.append(", ").append(column).append(" = ?");
        }
        return this;
    }

    public String build() {
        return query.toString();
    }
}
