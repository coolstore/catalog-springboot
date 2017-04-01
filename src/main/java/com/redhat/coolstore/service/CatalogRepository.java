/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.coolstore.service;

import java.util.List;

import com.redhat.coolstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CatalogRepository {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<Product> rowMapper = (rs, rowNum) -> new Product(
            rs.getString("itemId"),
            rs.getString("name"),
            rs.getString("desc"),
            rs.getDouble("price"));

    @Autowired
    public CatalogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM catalog WHERE itemId = " + id, rowMapper);
    }

    public List<Product> list() {
        return jdbcTemplate.query("SELECT * FROM catalog", rowMapper);
    }

    public void insert(Product product) {
        jdbcTemplate.update("INSERT INTO catalog (itemId, name, desc, price) VALUES (?, ?, ?, ?)", product.getItemId(),
                product.getName(), product.getDesc(), product.getPrice());
    }

    public boolean update(Product product) {
        int update = jdbcTemplate.update("UPDATE catalog SET desc = ? WHERE itemId = ? ", product.getDesc(), product.getItemId());
        return (update > 0);
    }

    public boolean delete(long id) {
        int update = jdbcTemplate.update("DELETE FROM catalog WHERE itemId = " + id);
        return (update > 0);
    }
}