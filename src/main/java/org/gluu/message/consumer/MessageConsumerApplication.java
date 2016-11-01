package org.gluu.message.consumer;

import org.gluu.message.consumer.config.MessageConsumerProperties;
import org.gluu.message.consumer.config.util.Constants;
import org.gluu.message.consumer.config.util.DefaultProfileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
@EnableConfigurationProperties({MessageConsumerProperties.class})
public class MessageConsumerApplication {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumerApplication.class);

    @Inject
    private Environment env;


    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(MessageConsumerApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp{}://127.0.0.1:{}\n\t" +
                        "External: \thttp{}://{}:{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.ssl.key-store") == null ? "" : "s",
                env.getProperty("server.port") + env.getProperty("spring.data.rest.base-path"),
                env.getProperty("server.ssl.key-store") == null ? "" : "s",
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port") + env.getProperty("spring.data.rest.base-path"));
    }

    /**
     * Initializes message-consumer.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     */
    @PostConstruct
    public void initApplication() {
        log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
        }
    }
}
