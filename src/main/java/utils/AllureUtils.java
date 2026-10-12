package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public final class AllureUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private AllureUtils() 
    {
    }
    
    public static void attachJson(String name, Object object) {

        try {

            String json;

            if (object instanceof String string) {
                // If already JSON, parse it instead of serializing the String again
                var root = OBJECT_MAPPER.readTree(string);

                if (root instanceof ObjectNode objectNode) {

                    if (objectNode.has("password")) {
                        objectNode.put("password", "********");
                    }

                    if (objectNode.has("token")) {
                        objectNode.put("token", "********");
                    }
                }

                json = OBJECT_MAPPER
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(root);

            } else {

                var root = OBJECT_MAPPER.valueToTree(object);

                if (root instanceof ObjectNode objectNode) {

                    if (objectNode.has("password")) {
                        objectNode.put("password", "********");
                    }

                    if (objectNode.has("token")) {
                        objectNode.put("token", "********");
                    }
                }

                json = OBJECT_MAPPER
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(root);
            }

            Allure.addAttachment(
                    name,
                    "application/json",
                    new ByteArrayInputStream(
                            json.getBytes(StandardCharsets.UTF_8)
                    ),
                    ".json"
            );
 
        } catch (Exception e) {

            Allure.addAttachment(
                    name,
                    object == null ? "" : object.toString()
            );
        }
    }

    public static void attachJsons(String name, Object object) {

        try {

            String json =  OBJECT_MAPPER
                            .writerWithDefaultPrettyPrinter()
                            .writeValueAsString(object);

            /*
             * Mask sensitive values.
             */
            if (object != null) {

                var root = OBJECT_MAPPER.readTree(json);

                if (root instanceof ObjectNode objectNode) {

                    if (objectNode.has("password")) 
                    {

                        objectNode.put("password","********");
                    }

                    if (objectNode.has("token")) 
                    {

                        objectNode.put("token", "********");
                    }

                    json =   OBJECT_MAPPER
                                    .writerWithDefaultPrettyPrinter()
                                    .writeValueAsString(objectNode); 
                }
            }

   Allure.addAttachment(name, "application/json",  new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)),".json");

        } 
        catch (Exception e) 
        {

            Allure.addAttachment(name, String.valueOf(object));
        }
    }

    public static void attach(String name, String content) 
    {

        Allure.addAttachment(name, "text/plain", content == null ? "" : content, ".txt");
    }
    
    
}

