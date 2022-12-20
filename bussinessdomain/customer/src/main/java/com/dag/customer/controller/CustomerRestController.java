package com.dag.customer.controller;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.dag.customer.entity.Customer;
import com.dag.customer.entity.CustomerProduct;
import com.dag.customer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.JsonNode;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    @Autowired
    private CustomerRepository repository;

    @Value("${user.role}")
    private String role;

    private final WebClient.Builder webClientBuilder;

    public CustomerRestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    // define timeout
    TcpClient client = TcpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    // HttpClient client = HttpClient.create()
    // Connection Timeout: is a period within which a connection between a client
    // and a server must be established
    // .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
    // .option(ChannelOption.SO_KEEPALIVE, true)
    // .option(EpollChannelOption.TCP_KEEPIDLE, 300)
    // .option(EpollChannelOption.TCP_KEEPINTVL, 60)
    // Response Timeout: The maximun time we wait to receive a response after
    // sending a request
    // .responseTimeout(Duration.ofSeconds(1))
    // Read and Write Timeout: A read timeout occurs when no data was read within a
    // certain
    // period of time, while the write timeout when a write operation cannot finish
    // at a specific time
    // .doOnConnected(connection -> {
    // connection.addHandlerLast(new ReadTimeoutHandler(5000,
    // TimeUnit.MILLISECONDS));
    // connection.addHandlerLast(new WriteTimeoutHandler(5000,
    // TimeUnit.MILLISECONDS));
    // });

    @GetMapping
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Customer get(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<Customer> create(
            @RequestBody Customer input) {

        input.getProducts().forEach(p -> p.setCustomer(input));

        Customer save = repository.save(input);
        return ResponseEntity.ok(save);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(
            @PathVariable Long id, @RequestBody Customer input) {

        Customer found = repository.findById(id).orElse(null);

        if (found != null) {
            found.setCode(input.getCode());
            found.setAccountNumber(input.getAccountNumber());
            found.setName(input.getName());
            found.setSurname(input.getSurname());
            found.setPhone(input.getPhone());
            found.setAddress(input.getAddress());
        }

        Customer save = repository.save(found);

        return ResponseEntity.ok(save);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable long id) {

        Customer found = repository.findById(id).orElse(null);

        if (found != null) {
            repository.deleteById(found.getId());
        }

        return ResponseEntity.ok().build();

    }

    @GetMapping("/full")
    public Customer getByCode(@RequestParam String code) {

        Customer customer = repository.findByCode(code).orElse(null);

        List<CustomerProduct> products = customer.getProducts();

        // for each product find it name
        products.forEach(x -> {
            String productName = getProductName(x.getProductId());
            x.setProductName(productName);
        });

        // find all transactions that belong this account number
        List<?> transactions = getTransactions(customer.getAccountNumber());
        customer.setTransactions(transactions);

        return customer;

    }

    /**
     * Call Product Microservice, find a product by Id and return its name
     * 
     * @param id of product to find
     * @return name of product if it was find
     */
    private String getProductName(long id) {

        WebClient build = webClientBuilder.clientConnector(
                // new ReactorClientHttpConnector(client))
                new ReactorClientHttpConnector(HttpClient.from(client)))
                .baseUrl("http://bussinessdomain-product/products")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap(
                        "url", "http://bussinessdomain-product/products"))
                .build();

        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();

        String name = block.get("name").asText();

        return name;

    }

    private List<?> getTransactions(String accountNumber) {

        WebClient build = webClientBuilder.clientConnector(
                // new ReactorClientHttpConnector(client))
                new ReactorClientHttpConnector(HttpClient.from(client)))
                .baseUrl("http://bussinessdomain-transaction/transactions")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap(
                        "url", "http://bussinessdomain-transaction/transactions"))
                .build();

        return build.method(HttpMethod.GET).uri(
                uriBuilder -> uriBuilder
                        .path("/customer/transactions")
                        .queryParam("accountNumber", accountNumber)
                        .build())
                .retrieve().bodyToFlux(Object.class).collectList().block();

    }

    @GetMapping("/role")
    public String showRole() {
        return "Hello your role is: " + role;
    }

}
