package org.gluu.message.consumer.repository;

import org.gluu.message.consumer.domain.log4j.OXAuthServerLoggingEvent;
import org.gluu.message.consumer.domain.log4j.OXAuthServerLoggingEventException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by eugeniuparvan on 10/31/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OXAuthServerLoggingEventRepositoryTest extends RepositoryBase<OXAuthServerLoggingEventRepository> {

    @Test
    public void shouldReturnRepositoryIndex() throws Exception {
        mockMvc.perform(get("/api")).andDo(print()).andExpect(status().isOk()).andExpect(
                jsonPath("$._links.oxauth-server-logs").exists());
    }

    @Test
    public void shouldRetrieveEntity() throws Exception {
        OXAuthServerLoggingEvent oxAuthServerLoggingEvent = addDefaultLog();

        String location = "/api/oxauth-server-logs/" + oxAuthServerLoggingEvent.getId();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.formattedMessage").value(oxAuthServerLoggingEvent.getFormattedMessage()))
                .andExpect(jsonPath("$.loggerName").value(oxAuthServerLoggingEvent.getLoggerName()))
                .andExpect(jsonPath("$.level").value(oxAuthServerLoggingEvent.getLevel()))
                .andExpect(jsonPath("$.exceptions").isEmpty());

        oxAuthServerLoggingEvent = addDefaultLogWithException();

        location = "/api/oxauth-server-logs/" + oxAuthServerLoggingEvent.getId();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.formattedMessage").value(oxAuthServerLoggingEvent.getFormattedMessage()))
                .andExpect(jsonPath("$.loggerName").value(oxAuthServerLoggingEvent.getLoggerName()))
                .andExpect(jsonPath("$.level").value(oxAuthServerLoggingEvent.getLevel()))
                .andExpect(jsonPath("$.exceptions").isNotEmpty());
    }

    @Test
    public void shouldQueryEntity() throws Exception {

        OXAuthServerLoggingEvent oxAuthServerLoggingEvent1 = addDefaultLog();
        OXAuthServerLoggingEvent oxAuthServerLoggingEvent2 = addLogWithParams("ERROR TEST", "logger_name", new Date(), "New_one");

        String location = "/api/oxauth-server-logs/search/query?level=" + oxAuthServerLoggingEvent2.getLevel();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].formattedMessage").value(oxAuthServerLoggingEvent2.getFormattedMessage()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].loggerName").value(oxAuthServerLoggingEvent2.getLoggerName()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].level").value(oxAuthServerLoggingEvent2.getLevel()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].exceptions").isEmpty());

        location = "/api/oxauth-server-logs/search/query?formattedMessage=" + "e";
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("2"));

        location = "/api/oxauth-server-logs/search/query?formattedMessage=" + "ew";
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].formattedMessage").value(oxAuthServerLoggingEvent2.getFormattedMessage()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].loggerName").value(oxAuthServerLoggingEvent2.getLoggerName()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].level").value(oxAuthServerLoggingEvent2.getLevel()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].exceptions").isEmpty());

        location = "/api/oxauth-server-logs/search/query?formattedMessage=" + oxAuthServerLoggingEvent2.getFormattedMessage();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].formattedMessage").value(oxAuthServerLoggingEvent2.getFormattedMessage()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].loggerName").value(oxAuthServerLoggingEvent2.getLoggerName()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].level").value(oxAuthServerLoggingEvent2.getLevel()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].exceptions").isEmpty());

        location = "/api/oxauth-server-logs/search/query?loggerName=" + oxAuthServerLoggingEvent2.getLoggerName();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].formattedMessage").value(oxAuthServerLoggingEvent2.getFormattedMessage()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].loggerName").value(oxAuthServerLoggingEvent2.getLoggerName()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].level").value(oxAuthServerLoggingEvent2.getLevel()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].exceptions").isEmpty());

        location = "/api/oxauth-server-logs/search/query?fromDate=" + simpleDateFormat.format(oxAuthServerLoggingEvent2.getTimestamp());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"));

        location = "/api/oxauth-server-logs/search/query?fromDate=" + simpleDateFormat.format(oxAuthServerLoggingEvent1.getTimestamp());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("2"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oxAuthServerLoggingEvent2.getTimestamp());
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        location = "/api/oxauth-server-logs/search/query?fromDate=" + simpleDateFormat.format(calendar.getTime());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("0"));

        calendar.add(Calendar.DAY_OF_MONTH, -10);

        location = "/api/oxauth-server-logs/search/query?fromDate=" + simpleDateFormat.format(calendar.getTime());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("2"));


        location = "/api/oxauth-server-logs/search/query?toDate=" + simpleDateFormat.format(calendar.getTime());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("0"));

        calendar.add(Calendar.DAY_OF_MONTH, 20);
        location = "/api/oxauth-server-logs/search/query?toDate=" + simpleDateFormat.format(calendar.getTime());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("2"));

        location = "/api/oxauth-server-logs/search/query?level=" + oxAuthServerLoggingEvent2.getLevel() +
                "&formattedMessage=" + oxAuthServerLoggingEvent2.getFormattedMessage() +
                "&fromDate=" + simpleDateFormat.format(oxAuthServerLoggingEvent2.getTimestamp()) +
                "&toDate=" + simpleDateFormat.format(oxAuthServerLoggingEvent2.getTimestamp());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].formattedMessage").value(oxAuthServerLoggingEvent2.getFormattedMessage()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].loggerName").value(oxAuthServerLoggingEvent2.getLoggerName()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].level").value(oxAuthServerLoggingEvent2.getLevel()))
                .andExpect(jsonPath("$._embedded.oxauth-server-logs[0].exceptions").isEmpty());
    }


    @Test
    public void shouldDeleteAllByTimestampBefore() throws Exception {
        addDefaultLogWithException();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        repository.deleteAllByTimestampBefore(calendar.getTime());

        Assert.assertTrue(repository.findAll().iterator().hasNext());

        calendar = Calendar.getInstance();
        repository.deleteAllByTimestampBefore(calendar.getTime());

        Assert.assertFalse(repository.findAll().iterator().hasNext());

        addDefaultLogWithException();

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        repository.deleteAllByTimestampBefore(calendar.getTime());

        Assert.assertFalse(repository.findAll().iterator().hasNext());
    }

    private OXAuthServerLoggingEvent addLogWithParams(String level, String loggerName, Date timestamp, String formattedMessage) {
        OXAuthServerLoggingEvent oxAuthServerLoggingEvent = new OXAuthServerLoggingEvent();
        oxAuthServerLoggingEvent.setLevel(level);
        oxAuthServerLoggingEvent.setLoggerName(loggerName);
        oxAuthServerLoggingEvent.setTimestamp(timestamp);
        oxAuthServerLoggingEvent.setFormattedMessage(formattedMessage);
        return repository.save(oxAuthServerLoggingEvent);
    }

    private OXAuthServerLoggingEvent addDefaultLog() {
        return addLogWithParams("INFO_TEST", "org.gluu.message.consumer.repository.OXAuthServerLoggingEventRepositoryTest", new Date(), "Test message");
    }

    private OXAuthServerLoggingEvent addDefaultLogWithException() {
        OXAuthServerLoggingEvent oxAuthServerLoggingEvent = addDefaultLog();
        List<OXAuthServerLoggingEventException> exceptions = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            OXAuthServerLoggingEventException oxAuthServerLoggingEventException = new OXAuthServerLoggingEventException();
            oxAuthServerLoggingEventException.setIndex(i);
            oxAuthServerLoggingEventException.setTraceLine("Test trace line: " + i);
            oxAuthServerLoggingEventException.setLoggingEvent(oxAuthServerLoggingEvent);
            exceptions.add(oxAuthServerLoggingEventException);
        }
        oxAuthServerLoggingEvent.setExceptions(exceptions);
        return repository.save(oxAuthServerLoggingEvent);
    }

}