//package com.example.app_server;
//
//package com.example.app_server;
//
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.calendar.Calendar;
//import com.google.api.services.calendar.CalendarScopes;
//import com.google.api.services.calendar.model.Event;
//import com.google.api.services.calendar.model.EventDateTime;
//import com.google.api.services.calendar.model.Events;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//public class GoogleCalendarService {
//
//    private static final String APPLICATION_NAME = "Your Application Name";
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//
//    @Value("${google.credentials.path}")
//    private String credentialsFilePath;
//
//    private Calendar getCalendarService() throws IOException, GeneralSecurityException {
//        // Load client secrets.
//        FileInputStream in = new FileInputStream(credentialsFilePath);
//        GoogleCredential credential = GoogleCredential.fromStream(in)
//                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));
//        return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }
//
//    public String createEvent(String summary, LocalDateTime startDateTime, LocalDateTime endDateTime, String attendeeEmail) throws IOException, GeneralSecurityException {
//        Calendar service = getCalendarService();
//
//        Event event = new Event()
//                .setSummary(summary)
//                .setStart(new EventDateTime().setDateTime(new DateTime(startDateTime.toString())).setTimeZone("America/Los_Angeles"))
//                .setEnd(new EventDateTime().setDateTime(new DateTime(endDateTime.toString())).setTimeZone("America/Los_Angeles"))
//                .setConferenceData(new ConferenceData().setCreateRequest(new CreateConferenceRequest().setRequestId("random-id").setConferenceSolutionKey(new ConferenceSolutionKey().setType("hangoutsMeet"))))
//                .setAttendees(Collections.singletonList(new EventAttendee().setEmail(attendeeEmail)));
//
//        Event createdEvent = service.events().insert("primary", event).setConferenceDataVersion(1).execute();
//        return createdEvent.getHangoutLink();
//    }
//}
//
