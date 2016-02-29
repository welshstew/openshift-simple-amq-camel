package bp.eu.symphony;

import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Inject;

/**
 * Created by swinchester on 18/02/2016.
 */
public class SimpleTimerRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("{{env:mytimerthing}}")
                .transform().method("org.apache.commons.lang3.RandomStringUtils", "randomNumeric(3)")
                .choice()
                    .when(simple("${body} > 500"))
                        .log("High priority message : ${body}").endChoice()
                    .otherwise()
                        .log("Low priority message  : ${body}").endChoice();

        from("activemq:queue:things").to("activemq:topic:stuff");
    }
}
