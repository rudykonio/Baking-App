package rodionkonioshko.com.bakingapp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import rodionkonioshko.com.bakingapp.data.Recipe;

/**
 * interface for getting the json data via Retrofit
 */
public interface BakingClient
{

    //protocol to get our data
    String PROTOCOL = "https://";
    //authority of the data
    String AUTHORITY = "d17h27t6h515a5.cloudfront.net";
    //data path
    String PATH = "/topher/2017/May/59121517_baking/";
    //url that we will take our recipes from
    String BASE_URL = PROTOCOL + AUTHORITY + PATH;

    @Headers("Content-Type: application/json")
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

}


























