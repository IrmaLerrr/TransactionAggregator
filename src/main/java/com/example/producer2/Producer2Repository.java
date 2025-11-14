package com.example.producer2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Producer2Repository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Transaction2> findById(String account) {
        String sql = "SELECT id, serverId, account, amount, timestamp FROM transactions WHERE account = ?";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Transaction2 transaction = new Transaction2();
            transaction.setId(resultSet.getString("id"));
            transaction.setServerId(resultSet.getString("serverId"));
            transaction.setAccount(resultSet.getString("account"));
            transaction.setAmount(resultSet.getString("amount"));
            transaction.setTimestamp(resultSet.getString("timestamp"));
            return transaction;
        }, account);
    }

}
