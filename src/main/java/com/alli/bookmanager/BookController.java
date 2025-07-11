package com.alli.bookmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;


import java.sql.SQLException;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Better to reuse one instance

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public void addBook(@RequestBody Book book) throws SQLException {
        bookService.add(book);

        // Serialize Book (Java object) into a JSON

        String json;
        try {
            json = objectMapper.writeValueAsString(book);
            System.out.println(json); // for testing — later you'll send this to Kinesis and S3
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize book", e);
        }

        // Send it to Kinesis

        KinesisClient kinesisClient = KinesisClient.create();

        PutRecordRequest recordRequest = PutRecordRequest.builder()
                .streamName("book-stream")
                .partitionKey(String.valueOf(book.getId()))  // required by Kinesis
                .data(SdkBytes.fromUtf8String(json))
                .build();

        kinesisClient.putRecord(recordRequest);

        // Upload to S3

        S3Client s3Client = S3Client.create();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("book-bucket")
                .key(book.getId() + ".json")
                .build();


        s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(json.getBytes(StandardCharsets.UTF_8)));



    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) throws SQLException {
        return bookService.findById(id);
    }
}
