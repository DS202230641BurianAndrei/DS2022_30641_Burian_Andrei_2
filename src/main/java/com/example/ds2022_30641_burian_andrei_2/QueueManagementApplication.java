package com.example.ds2022_30641_burian_andrei_2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class QueueManagementApplication {
    private static final String QUEUE_NAME = "measurement";
    private static final String filename = "sensor.csv";
    private static final String configurationFilePath = "D:\\An4\\Sem1\\SD\\Lab\\ds2022_30641_burian_andrei_2\\ds2022_30641_burian_andrei_2\\src\\main\\resources\\application.properties";

    public static void main(String[] args) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BufferedReader bufferedReaderData = new BufferedReader(new FileReader(filename));
        BufferedReader bufferedReaderConfiguration = new BufferedReader(new FileReader(configurationFilePath));
        String line;
        ConnectionFactory factory = new ConnectionFactory();

        line = bufferedReaderConfiguration.readLine();
        Long deviceId = Long.valueOf(line.substring(11));

        factory.setUri("amqps://creztbhc:aHLquyL6acUaMaL9tQcCKQfgHki4ISmI@goose.rmq2.cloudamqp.com/creztbhc");

        int test_counter = 0;

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            while ((line = bufferedReaderData.readLine()) != null) {
                test_counter++;
                Message message = new Message(dateFormat.format(new Date()), Float.parseFloat(line), deviceId);
                channel.basicPublish("", QUEUE_NAME, null, message.toString().getBytes());
                if(test_counter >= 8){
                    break;
                }
                Thread.sleep(5000);

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
