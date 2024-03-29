package com.example.demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scala.concurrent.duration.FiniteDuration;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class mapServiceImpl implements mapService {
    ActorRef mapper1, mapper2, mapper3, reducer1, reducer2;
    ActorSystem system = ActorSystem.create("MySystem");

    @Override
    public void init() {

        mapper1 = system.actorOf(Props.create(MapperActor.class), "mapper1");
        mapper2 = system.actorOf(Props.create(MapperActor.class), "mapper2");
        mapper3 = system.actorOf(Props.create(MapperActor.class), "mapper3");
        reducer1 = system.actorOf(Props.create(ReducersActor.class), "reducer1");
        reducer2 = system.actorOf(Props.create(ReducersActor.class), "reducer2");

    }

    @Override
    public void submit(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader br = new BufferedReader(inputStreamReader)) {
            String line;
            int mapperIndex = 0;
            while ((line = br.readLine()) != null) {
                // Send each line to a Mapper
                if (mapperIndex == 0) {
                    mapper1.tell(line, ActorRef.noSender());
                } else if (mapperIndex == 1) {
                    mapper2.tell(line, ActorRef.noSender());
                } else {
                    mapper3.tell(line, ActorRef.noSender());
                }
                mapperIndex = (mapperIndex + 1) % 3; // Alternate distribution of lines
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String nbOcc(String word) {
        reducer1.tell(new RequestMessage(word), ActorRef.noSender());
       // reducer2.tell(new RequestMessage(word), ActorRef.noSender());
        Inbox inbox = Inbox.create(system);
        inbox.send(reducer1, new RequestMessage(word));
        Object reply = null;
        try {
            reply = inbox.receive(FiniteDuration.create(5, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        if (reply instanceof Word rm) {
            System.out.println("Réponse reçu : " + rm.word());
            return rm.word();
        }
      return "non";

    }
}