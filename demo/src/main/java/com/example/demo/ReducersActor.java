package com.example.demo;
import akka.actor.UntypedActor;
import java.util.HashMap;

public class ReducersActor extends UntypedActor {




    private final HashMap<String, Integer> wordCounts = new HashMap<>();

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Word word) {
            // Increment the word count
            wordCounts.put(word.word(), wordCounts.getOrDefault(word.word(), 0) + 1);
        } else if (message instanceof RequestMessage m) {

            // Print word counts

            String chosenWord = m.word();
            int count = wordCounts.getOrDefault(chosenWord, 0);
            if (count!=0) {
                System.out.println(chosenWord + " count: " +count );}
            getSender().tell(new Word(String.valueOf(count)),getSelf());

            }

    }
}

