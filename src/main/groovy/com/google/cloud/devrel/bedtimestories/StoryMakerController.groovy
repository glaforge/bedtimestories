package com.google.cloud.devrel.bedtimestories

import com.google.auth.oauth2.GoogleCredentials
import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.reactor.http.client.ReactorStreamingHttpClient
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@CompileStatic
@Controller("/story")
class StoryMakerController {

    private Logger logger = LoggerFactory.getLogger("StoryMaker");

    @Inject
    private ReactorStreamingHttpClient client;

    private static Closure<String> PROMPT = { String character, String setting, String plot ->
        """
        You are a creative and passionate story teller for kids. 
        Kids love hearing about the stories you invent.
        
        Your stories are split into 5 acts:
        - Act 1 : Sets up the story providing any contextual background the reader needs, but most importantly it contains the inciting moment. This incident sets the story in motion. An incident forces the protagonist to react. It requires resolution, producing narrative tension.
        - Act 2 : On a simplistic level this is the obstacles that are placed in the way of the protagonists as they attempt to resolve the inciting incident.
        - Act 3 : This is the turning point of the story. It is the point of the highest tension. In many modern narratives, this is the big battle or showdown.
        - Act 4 : The falling action is that part of the story in which the main part (the climax) has finished and you're heading to the conclusion. This is the calm after the tension of the climax.
        - Act 5 : This is the resolution of the story where conflicts are resolved and loose ends tied up. This is the moment of emotional release for the reader.
        
        Generate a kid story in 5 acts, where the protagonist is ${character}, where the action takes place ${setting} and ${plot}.
        """.stripIndent()
    }

    @Get("/generate")
    String[] makeStory(@QueryValue String character, @QueryValue String setting, @QueryValue String plot) {
        def credentials = GoogleCredentials.applicationDefault
                .createScoped('https://www.googleapis.com/auth/cloud-platform')
        credentials.refreshIfExpired()
        def token = credentials.accessToken.tokenValue

        def uri = UriBuilder
                .of("/v1/projects/genai-java-demos/locations/us-central1/publishers/google/models/text-bison:predict")
                .scheme("https")
                .host("us-central1-aiplatform.googleapis.com")
                .build()

        def request = HttpRequest
                .POST(uri, """
                    {
                      "instances": [
                        {
                          "content": "${PROMPT(character, setting, plot)}"
                        }
                      ],
                      "parameters": {
                        "temperature": 0.6,
                        "maxOutputTokens": 1000,
                        "topP": 0.8,
                        "topK": 40
                      }
                    }
                """)
                .bearerAuth(token)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .contentType(MediaType.APPLICATION_JSON_TYPE)

        def predictionResponse = client.toBlocking()
                .exchange(request, PredictionResponse)
                .body()

        def content = predictionResponse.predictions.first().content

        logger.info("""\
                character: ${character}
                setting: ${setting}
                plot: ${plot}
            """.stripIndent())
        logger.info("output: ${content}")

        return content.split(/\*\*(?:.*)\*\*/)*.trim().grep() as String[]
    }
}
