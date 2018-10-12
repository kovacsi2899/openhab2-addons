package org.openhab.binding.gmailparadoxparser.gmail.adapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.google.api.services.gmail.model.Message;

public interface GmailAdapter {
    static final String QUERY_UNREAD = "label:unread ";
    static final String QUERY_READ = "label:read ";

    public List<Message> retrieveMessages(String query) throws IOException, UnsupportedEncodingException;

    public List<String> retrieveAllMessagesContents(List<Message> messages) throws IOException;

    default public List<String> retrieveAllMessagesContents(String query) throws IOException {
        List<Message> retrievedMessages = retrieveMessages(query);
        return retrieveAllMessagesContents(retrievedMessages);
    }

    public void markMessagesRead(List<Message> msgIds) throws IOException;
}
