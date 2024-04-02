package com.kwchau.MaybankBackendTechTest.Dao;

import com.kwchau.MaybankBackendTechTest.Model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ClientDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ClientDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Client getClientByUsername(String username) {
        String sql = "SELECT * FROM clients WHERE username = ?;";
        Client client = null;

        try {
            client = jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
                Client sqlClient =new Client();
                sqlClient.setName(rs.getString("name"));
                sqlClient.setArea(rs.getString("area"));
                return sqlClient;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    public List<Map<String, Object>> getAllClients(int pageNumber, int pageSize) {
        String sql = "SELECT * FROM clients ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = (pageNumber - 1) * pageSize;
        List<Map<String, Object>> resultMapList = new ArrayList<>();
        try{
            resultMapList = jdbcTemplate.queryForList(sql, offset, pageSize);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return resultMapList;
    }

    public int createNewClient(Client client){
        String sql = "INSERT INTO clients (username, name, area) VALUES(?,?,?);";
        try {
            return jdbcTemplate.update(sql, client.getUsername(), client.getName(), client.getArea());
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public int updateClient(String username, String name, String area) {
        String sql = "UPDATE clients SET name = ?, area = ? WHERE username = ?";
        try{
            return jdbcTemplate.update(sql, name, area, username);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }
}
