package org.gluu.message.consumer.repository;

import org.gluu.message.consumer.domain.OAuth2AuditLoggingEvent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
public class OAuth2AuditLoggingEventRepositoryTest extends RepositoryBase<OAuth2AuditLoggingEventRepository> {

    private SimpleDateFormat timestampDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Test
    public void shouldReturnRepositoryIndex() throws Exception {
        mockMvc.perform(get(getUrlPrefix())).andDo(print()).andExpect(status().isOk()).andExpect(
                jsonPath("$._links.oauth2-audit-logs").exists());
    }

    @Test
    public void shouldRetrieveEntity() throws Exception {
        OAuth2AuditLoggingEvent oAuth2AuditLoggingEvent = addDefaultLog();

        String location = getUrlPrefix() + "/oauth2-audit-logs/" + oAuth2AuditLoggingEvent.getId();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.ip").value(oAuth2AuditLoggingEvent.getIp()))
                .andExpect(jsonPath("$.action").value(oAuth2AuditLoggingEvent.getAction()))
                .andExpect(jsonPath("$.clientId").value(oAuth2AuditLoggingEvent.getClientId()))
                .andExpect(jsonPath("$.username").value(oAuth2AuditLoggingEvent.getUsername()))
                .andExpect(jsonPath("$.scope").value(oAuth2AuditLoggingEvent.getScope()))
                .andExpect(jsonPath("$.success").value(oAuth2AuditLoggingEvent.getSuccess()));
    }

    @Test
    public void shouldQueryEntity() throws Exception {
        OAuth2AuditLoggingEvent oAuth2AuditLoggingEvent1 = addDefaultLog();
        OAuth2AuditLoggingEvent oAuth2AuditLoggingEvent2 = addLogWithParams("clientid1", "username1", "action1", "ip1", "scope1", true, new Date());

        String location = getUrlPrefix() + "/oauth2-audit-logs/search/query?clientId=" + oAuth2AuditLoggingEvent2.getClientId();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].ip").value(oAuth2AuditLoggingEvent2.getIp()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].action").value(oAuth2AuditLoggingEvent2.getAction()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].clientId").value(oAuth2AuditLoggingEvent2.getClientId()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].username").value(oAuth2AuditLoggingEvent2.getUsername()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].scope").value(oAuth2AuditLoggingEvent2.getScope()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].success").value(oAuth2AuditLoggingEvent2.getSuccess()));
        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?username=" + oAuth2AuditLoggingEvent2.getUsername();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].ip").value(oAuth2AuditLoggingEvent2.getIp()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].action").value(oAuth2AuditLoggingEvent2.getAction()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].clientId").value(oAuth2AuditLoggingEvent2.getClientId()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].username").value(oAuth2AuditLoggingEvent2.getUsername()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].scope").value(oAuth2AuditLoggingEvent2.getScope()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].success").value(oAuth2AuditLoggingEvent2.getSuccess()));
        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?ip=" + oAuth2AuditLoggingEvent2.getIp();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].ip").value(oAuth2AuditLoggingEvent2.getIp()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].action").value(oAuth2AuditLoggingEvent2.getAction()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].clientId").value(oAuth2AuditLoggingEvent2.getClientId()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].username").value(oAuth2AuditLoggingEvent2.getUsername()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].scope").value(oAuth2AuditLoggingEvent2.getScope()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].success").value(oAuth2AuditLoggingEvent2.getSuccess()));
        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?action=" + oAuth2AuditLoggingEvent2.getAction();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].ip").value(oAuth2AuditLoggingEvent2.getIp()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].action").value(oAuth2AuditLoggingEvent2.getAction()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].clientId").value(oAuth2AuditLoggingEvent2.getClientId()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].username").value(oAuth2AuditLoggingEvent2.getUsername()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].scope").value(oAuth2AuditLoggingEvent2.getScope()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].success").value(oAuth2AuditLoggingEvent2.getSuccess()));
        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?scope=" + oAuth2AuditLoggingEvent2.getScope();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].ip").value(oAuth2AuditLoggingEvent2.getIp()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].action").value(oAuth2AuditLoggingEvent2.getAction()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].clientId").value(oAuth2AuditLoggingEvent2.getClientId()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].username").value(oAuth2AuditLoggingEvent2.getUsername()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].scope").value(oAuth2AuditLoggingEvent2.getScope()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].success").value(oAuth2AuditLoggingEvent2.getSuccess()));
        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?scope=scope";
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("2"));
        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?success=" + oAuth2AuditLoggingEvent2.getSuccess();
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("2"));
        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?success=false";
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("0"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oAuth2AuditLoggingEvent2.getTimestamp());
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?fromDate=" + simpleDateFormat.format(calendar.getTime());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("0"));

        calendar.add(Calendar.DAY_OF_MONTH, -10);

        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?fromDate=" + simpleDateFormat.format(calendar.getTime());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("2"));


        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?toDate=" + simpleDateFormat.format(calendar.getTime());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("0"));

        calendar.add(Calendar.DAY_OF_MONTH, 20);
        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?toDate=" + simpleDateFormat.format(calendar.getTime());
        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("2"));


        location = getUrlPrefix() + "/oauth2-audit-logs/search/query?clientId=" + oAuth2AuditLoggingEvent2.getClientId() +
                "&username=" + oAuth2AuditLoggingEvent2.getUsername() +
                "&action=" + oAuth2AuditLoggingEvent2.getAction() +
                "&ip=" + oAuth2AuditLoggingEvent2.getIp() +
                "&scope=" + oAuth2AuditLoggingEvent2.getScope() +
                "&success=" + oAuth2AuditLoggingEvent2.getSuccess() +
                "&fromDate=" + simpleDateFormat.format(oAuth2AuditLoggingEvent2.getTimestamp()) +
                "&toDate=" + simpleDateFormat.format(oAuth2AuditLoggingEvent2.getTimestamp());

        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].ip").value(oAuth2AuditLoggingEvent2.getIp()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].action").value(oAuth2AuditLoggingEvent2.getAction()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].clientId").value(oAuth2AuditLoggingEvent2.getClientId()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].username").value(oAuth2AuditLoggingEvent2.getUsername()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].scope").value(oAuth2AuditLoggingEvent2.getScope()))
                .andExpect(jsonPath("$._embedded.oauth2-audit-logs[0].success").value(oAuth2AuditLoggingEvent2.getSuccess()));
    }


    @Test
    public void shouldDeleteAllByTimestampBefore() throws Exception {
        addDefaultLog();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        repository.deleteAllByTimestampBefore(calendar.getTime());

        Assert.assertTrue(repository.findAll().iterator().hasNext());

        calendar = Calendar.getInstance();
        repository.deleteAllByTimestampBefore(calendar.getTime());

        Assert.assertFalse(repository.findAll().iterator().hasNext());

        addDefaultLog();

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        repository.deleteAllByTimestampBefore(calendar.getTime());

        Assert.assertFalse(repository.findAll().iterator().hasNext());
    }

    private OAuth2AuditLoggingEvent addLogWithParams(String clientId, String username, String action, String ip, String scope, boolean success, Date timestamp) {
        OAuth2AuditLoggingEvent oAuth2AuditLoggingEvent = new OAuth2AuditLoggingEvent();
        oAuth2AuditLoggingEvent.setClientId(clientId);
        oAuth2AuditLoggingEvent.setUsername(username);
        oAuth2AuditLoggingEvent.setAction(action);
        oAuth2AuditLoggingEvent.setIp(ip);
        oAuth2AuditLoggingEvent.setScope(scope);
        oAuth2AuditLoggingEvent.setSuccess(success);
        oAuth2AuditLoggingEvent.setTimestamp(timestamp);

        return repository.save(oAuth2AuditLoggingEvent);
    }

    private OAuth2AuditLoggingEvent addDefaultLog() {
        return addLogWithParams("clientId", "username", "action", "ip", "scope", true, new Date());
    }

}
