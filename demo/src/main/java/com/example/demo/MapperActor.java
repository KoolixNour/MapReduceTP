package com.example.demo;

import akka.actor.UntypedActor;

public class MapperActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            String line = (String) message;
            // Process the received line
            String[] words = line.split("\\s+"); // Split the line into words
            for (String word : words) {
                // Send each word to reducers
                String reducerName = partition(word); // Determine the reducer for this word
                getContext().actorSelection("/user/" + reducerName).tell(new Word(word), getSelf());
            }

    }

}
    private String partition(String word) {
        int reducerCount = 2;
        int hashCode = word.hashCode();
        int reducerIndex = Math.abs(hashCode) % reducerCount; //
        return "reducer" + (reducerIndex + 1); // Adjust for 1-based indexing of reducers
    }}