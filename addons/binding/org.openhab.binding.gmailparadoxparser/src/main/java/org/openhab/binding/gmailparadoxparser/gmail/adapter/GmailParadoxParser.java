package org.openhab.binding.gmailparadoxparser.gmail.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.slf4j.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.BatchModifyMessagesRequest;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;

public class GmailParadoxParser {
    private static final String APPLICATION_NAME = "Gmail Paradox mail parser";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String QUERY_UNREAD = "label:unread ";
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    private static Gmail googleService;
    private Logger logger;

    public GmailParadoxParser(Logger logger) throws GeneralSecurityException, IOException {
        this(QUERY_UNREAD, CREDENTIALS_FILE_PATH, logger);
    }

    public GmailParadoxParser(String filterLabel, String credentialsFile, Logger logger)
            throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        googleService = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME).build();
        this.logger = logger;
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        GmailParadoxParser parser = new GmailParadoxParser(new MyLogger());

        List<String> mailContents = parser.retrieveAllMailsContents();
        for (String mail : mailContents) {
            String[] split = mail.split(System.getProperty("line.separator"));
            MailParser.getInstance().parseToMap(split);
        }

    }

    public List<String> retrieveAllMailsContents() throws IOException, UnsupportedEncodingException {
        // Print the messages in the user's account.
        ArrayList<String> result = new ArrayList<String>();
        String user = "me";
        ListMessagesResponse listResponse = googleService.users().messages().list(user).setQ(QUERY_UNREAD).execute();
        List<Message> messages = listResponse.getMessages();
        if (messages == null || messages.isEmpty()) {
            logger.debug("No mails found.");
        } else {
            List<String> msgIds = new ArrayList<>();
            System.out.println("Messages:");
            for (Message message : messages) {
                String msgId = message.getId();
                Message mail = googleService.users().messages().get(user, msgId).setFormat("full").execute();
                MessagePart payload = mail.getPayload();
                if (payload == null) {
                    logger.debug("Payload is null");
                    continue;
                }

                MessagePartBody body = payload.getBody();
                String encodedContent = body.getData();
                String content = new String(Base64.decodeBase64(encodedContent.getBytes()), "UTF-8");
                logger.debug("Payload content: " + content);

                msgIds.add(msgId);
                result.add(content);
            }
            BatchModifyMessagesRequest modifyRequest = new BatchModifyMessagesRequest().setIds(msgIds)
                    // .setAddLabelIds();
                    .setRemoveLabelIds(Collections.singletonList("UNREAD"));
            googleService.users().messages().batchModify(user, modifyRequest).execute();
        }
        return result;
    }

    private void initializeGoogleService() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        googleService = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailParadoxParser.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                        .setAccessType("offline").build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public void updateStatus(ThingStatus unknown) {
        // TODO Auto-generated method stub

    }

}