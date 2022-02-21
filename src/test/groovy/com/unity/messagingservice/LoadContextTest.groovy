package com.unity.messagingservice

import com.unity.messagingservice.web.controller.UnityMessageController
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
    private UnityMessageController unityMessageController


    def "when context is loaded then all expected beans are created"() {
        expect: "the UnityMessageController is created"
        unityMessageController
    }
}
