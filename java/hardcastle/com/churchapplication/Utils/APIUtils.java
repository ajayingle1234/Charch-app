package hardcastle.com.churchapplication.Utils;

public class APIUtils {

  public static final String BASE_URL = "http://hardcastle.co.in/PHP_WEB/CHURCH/";

  public static GetDataService getAPIInterface() {

    return RetrofitClientInstance.getRetrofitInstance(BASE_URL).create(GetDataService.class);
  }
}
