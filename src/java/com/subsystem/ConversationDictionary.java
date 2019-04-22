package com.subsystem;

import java.util.HashMap;
import java.util.UUID;

public class ConversationDictionary {
private static HashMap<String, Conversation> _activeConversation = new HashMap();
    public static Conversation getConversation(String conversationId)
    {
        Conversation conv = null;
        conv = _activeConversation.get(conversationId);
        return conv;
    }
    public static void delConversation(String conversationId)
    {
        _activeConversation.remove(conversationId);
    }
    public static void addConversation(Conversation conv)
    {
        if (conv == null) return;
        Conversation existingConversation = getConversation(conv.ConvId);
        if (existingConversation == null)
            _activeConversation.put(conv.ConvId, conv);
    }
    public static void clearAllConversation(UUID conversationId)
    {
        _activeConversation.clear();
    }
}

