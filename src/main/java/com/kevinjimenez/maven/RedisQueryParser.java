package com.kevinjimenez.maven;


import org.apache.lucene.search.Query;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SyntaxError;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RedisQueryParser extends QParser {

    public RedisQueryParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        super(qstr, localParams, params, req);
    }

    @Override
    public Query parse() throws SyntaxError {
        try{
            String text = getParam("command");//jedis.zrange("ratings", 0, 10).toString();
            Files.write(Paths.get("/home/kevin/fileName.txt"), text.getBytes());
        }catch (Exception e){

        }
        return null;
    }
}