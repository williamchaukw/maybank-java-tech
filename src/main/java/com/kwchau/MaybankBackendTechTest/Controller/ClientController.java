package com.kwchau.MaybankBackendTechTest.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kwchau.MaybankBackendTechTest.Dao.ClientDao;
import com.kwchau.MaybankBackendTechTest.Model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Gson gson;
    @GetMapping("/getClientByUsername")
    public ResponseEntity getClientByUsername(@RequestParam(required = true) String username) {
        logger.info("Request param: username={}", username);

        Client resultClient = clientDao.getClientByUsername(username);

        logger.info("Response={}", resultClient);
        return resultClient == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found, username=" + username)
                : ResponseEntity.ok(resultClient);
    }

    // API with pagination
    @GetMapping("/getAllClients")
    public ResponseEntity getAllClients(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        logger.info("Request param: pageNumber={}, pageSize={}", pageNumber, pageSize);

        List<Client> clientList = mapToClient(clientDao.getAllClients(pageNumber, pageSize));

        return clientList.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No client found")
                : ResponseEntity.ok(clientList);
    }

    @PostMapping("/createClient")
    @Transactional
    public ResponseEntity createClient(@RequestParam(required = true) String username,
                                       @RequestParam(required = true) String name,
                                       @RequestParam(required = true) String area){
        logger.info("Request param: username={}, name={}, area={} ", username, name, area);

        Client client = new Client();
        client.setUsername(username);
        client.setName(name);
        client.setArea(area);

        int success = clientDao.createNewClient(client);

        return success > 0
                ? ResponseEntity.ok("Username " + username + " created success")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + username + " create fail");
    }

    @PutMapping("/updateClient/{username}")
    @Transactional
    public ResponseEntity updateClient(@PathVariable String username,
                                       @RequestParam(required = true) String name,
                                       @RequestParam(required = true) String area) {
        logger.info("Request param: username={}, name={}, area={} ", username, name, area);

        int success = clientDao.updateClient(username, name, area);

        return success > 0
                ? ResponseEntity.ok("Username " + username + " updated success")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + username + "update fail");
    }

    // API calling 3rd party api
    @GetMapping("/getQuotes")
    public ResponseEntity getQuotes() throws Exception{
        String url = "https://api.quotable.io/quotes/random";
        String quote = "";
        String quoteStr = restTemplate.getForObject(url, String.class);

        JsonArray quoteArr = gson.fromJson(quoteStr, JsonArray.class);

        if(!quoteArr.isEmpty()) {
            JsonObject quoteObject = (JsonObject) quoteArr.get(0);
            quote = quoteObject.get("content").toString();
        }

        return ResponseEntity.ok(quote);
    }

    private List<Client> mapToClient(List<Map<String, Object>> mapList) {
        List<Client> clientList = new ArrayList<>();
        if(mapList.size() > 0) {
            for(Map<String, Object> clientMap: mapList) {
                Client client = new Client();
                client.setName(clientMap.get("name").toString());
                client.setArea(clientMap.get("area").toString());
                clientList.add(client);
            }
        }
        return clientList;
    }
}
