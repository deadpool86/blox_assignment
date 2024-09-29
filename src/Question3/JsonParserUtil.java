package Question3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/*
 * Question Statement :
   Write a function to parse any valid json string into a corresponding Object, List, or Map
   object. You can use C,C++, Java, Scala, Kotlin, Python, Node. Note that the integer and
   floating point should be arbitrary precision.
 */

public class JsonParserUtil {

    public static Object parseJson(String jsonString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        
        return convertJsonNodeToObject(jsonNode);
    }

    private static Object convertJsonNodeToObject(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            return convertObjectNodeToMap((ObjectNode) jsonNode);
        } else if (jsonNode.isArray()) {
            return convertArrayNodeToList(jsonNode);
        } else if (jsonNode.isTextual()) {
            return jsonNode.asText();
        } else if (jsonNode.isInt()) {
            return jsonNode.bigIntegerValue();  
        } else if (jsonNode.isLong()) {
            return jsonNode.bigIntegerValue(); 
        } else if (jsonNode.isDouble()) {
            return jsonNode.decimalValue();     
        } else if (jsonNode.isBoolean()) {
            return jsonNode.asBoolean();
        } else if (jsonNode.isNull()) {
            return null;
        }
        return jsonNode; 
    }

    private static Map<String, Object> convertObjectNodeToMap(ObjectNode objectNode) {
        Map<String, Object> result = new LinkedHashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            result.put(entry.getKey(), convertJsonNodeToObject(entry.getValue()));
        }

        return result;
    }

    private static List<Object> convertArrayNodeToList(JsonNode arrayNode) {
        List<Object> list = new ArrayList<>();
        for (JsonNode jsonNode : arrayNode) {
            list.add(convertJsonNodeToObject(jsonNode));
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            String jsonString = "{\n" +
                    "  \"name\": \"John\",\n" +
                    "  \"age\": 12345678901234567890,\n" +
                    "  \"salary\": 12345.67890,\n" +
                    "  \"isEmployed\": true,\n" +
                    "  \"projects\": [\"Project A\", \"Project B\"],\n" +
                    "  \"address\": {\n" +
                    "    \"city\": \"New York\",\n" +
                    "    \"zip\": 10001\n" +
                    "  }\n" +
                    "}";

            Object parsedObject = parseJson(jsonString);
            System.out.println("Parsed Object: " + parsedObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

