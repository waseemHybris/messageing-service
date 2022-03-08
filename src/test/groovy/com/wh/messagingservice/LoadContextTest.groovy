package com.wh.messagingservice


import com.wh.messagingservice.web.controller.MessageController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title("Application Specification")
@Narrative("Specification which beans are expected")
@SpringBootTest
class LoadContextTest extends Specification {

    @Autowired(required = false)
    private MessageController messageController


    def "when context is loaded then all expected beans are created"() {
        expect: "the MessageController is created"
        messageController
    }
}
