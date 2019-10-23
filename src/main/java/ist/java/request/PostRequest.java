package ist.java.request;

import com.google.gson.Gson;
import ist.java.data.Blog;
//PostRequest is a class used to modelize a request to the server.
//It should contain the action you want to accomplish.
public class PostRequest{

       // TODO: not sure how to use this yet!

    public PostRequest(){}

    private static Blog blog = new Blog();
  

    // convert JSON object to readable string?
    public static String formatPost(String jsonString){
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    
}
