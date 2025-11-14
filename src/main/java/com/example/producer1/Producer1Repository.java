package com.example.producer1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Producer1Repository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Transaction1> findById(String account) {
        String sql = "SELECT id, serverId, account, amount, timestamp FROM transactions WHERE account = ?";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Transaction1 transaction = new Transaction1();
            transaction.setId(resultSet.getString("id"));
            transaction.setServerId(resultSet.getString("serverId"));
            transaction.setAccount(resultSet.getString("account"));
            transaction.setAmount(resultSet.getString("amount"));
            transaction.setTimestamp(resultSet.getString("timestamp"));
            return transaction;
        }, account);
    }

}
