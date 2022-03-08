package com.wh.messagingservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@Title("MessageController Specification")
@Narrative("The Specification of the behaviour of the MessageController.")
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
class CreateMessageEntityIntegrationTest extends Specification {

    @Autowired
    private MockMvc mvc

    def "should succeed with status 204 with no content'"() {
        expect: "Status is 204 for valid payload'"

        mvc.perform(MockMvcRequestBuilders
                .post("/messages")
                .content(asJsonString(buildValidMessagePayload()))
                .header("tenant", "test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
    }


    @Unroll
    def "should fail with 400 when violating validation rules#i"(def i, def usecase, def testedAttributes) {
        given:
        def validPayload = buildValidMessagePayload()
        def invalidPayload = validPayload << testedAttributes

        expect: "Status is 400 for invalid payload'"
        mvc.perform(MockMvcRequestBuilders
                .post("/messages")
                .content(asJsonString(invalidPayload))
                .header("tenant", "test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())

        where:
        i | usecase                    | testedAttributes
        /*----------------------------------------------------------------------*/
        1 | "ts invalid"               | ["ts": ""]
        2 | "ts invalid"               | ["ts": "xyz"]
        3 | "ts not present"           | ["ts": null]
        4 | "sender not present"       | ["sender": null]
        5 | "message not present"      | ["message": null]
        6 | "message not valid"        | ["message": "not a json"]
        7 | "message size less than 1" | ["message": [[]]]
        8 | "sent-from-ip not valid"   | ["sent-from-ip": "not a valid Ip"]
        9 | "extra properties"         | ["extra-property": "extra value"]
    }

    @Unroll
    def "should succeed with status 204 with no content'"(def i, def usecase, def testedAttributes) {

        given:
        def Payload = buildValidMessagePayload()
        def validPayload = Payload << testedAttributes
        expect: "Status is 204 for valid payload'"

        mvc.perform(MockMvcRequestBuilders
                .post("/messages")
                .content(asJsonString(validPayload))
                .header("tenant", "test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
        where:
        i | usecase                             | testedAttributes
        /*----------------------------------------------------------------------*/
        1 | "sent-from-ip not present"          | ["sent-from-ip": null]
        2 | "priority not present"              | ["priority": null]
        3 | "message of 1 field set"            | ["message": ["item1": "value1"]]
        4 | "message of 1 field set null value" | ["message": ["item1": null]]
    }


    def static buildValidMessagePayload() {
        [
                "ts"          : "1645372554",
                "sender"      : "testy-test-service",
                "message"     : [
                        "foo": "bar",
                        "baz": "bang"
                ],
                "sent-from-ip": "192.0.33.146",
                "priority"    : 2

        ]
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}