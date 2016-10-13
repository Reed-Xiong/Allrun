package com.example.reedxiong.allrun.entity;

import org.jivesoftware.smack.packet.Message;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class GroupChatEntity {

    public static ConcurrentHashMap<String, Vector<Message>> map = new ConcurrentHashMap<String, Vector<Message>>();

    public static void addMessage(String room, Message message) {
        Vector<Message> vector = map.get(room);
        if (vector == null) {
            vector = new Vector<Message>();
        }
        vector.add(message);
        map.put(room, vector);
    }
}
