package com.kevinjimenez.maven;


import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
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
        try{
            String text = jedis.zrange("ratings", 0, 10).toString();
            Files.write(Paths.get("/home/kevin/fileName.txt"), text.getBytes());
        }catch (Exception e){

        }finally {
            jedis.close();
        }
        Query q = new TermQuery(new Term("id", "1"));
        return q;
    }
}