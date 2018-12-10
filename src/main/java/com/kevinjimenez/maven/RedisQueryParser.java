package com.kevinjimenez.maven;


import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SyntaxError;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RedisQueryParser extends QParser {
    private Jedis jedis;
    public RedisQueryParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req, Jedis jedis) {
        super(qstr, localParams, params, req);
        this.jedis = jedis;
    }

    @Override
    public Query parse() throws SyntaxError {
        int start = Integer.parseInt(localParams.get("range_start"));
        int end = Integer.parseInt(localParams.get("range_end"));
        String key = localParams.get("key");
        Object text[] = jedis.zrevrange(key, start, end).toArray();
        jedis.close();

        BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
        for (Object o: text) {
            String id = o.toString();
            booleanQueryBuilder.add(new TermQuery(new Term("id", id)), BooleanClause.Occur.SHOULD);
        }
        Query q = booleanQueryBuilder.build();
        return q;
    }
}