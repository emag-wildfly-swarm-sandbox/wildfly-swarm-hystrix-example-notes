package wildflyswarm.payroll.api;

public class Utils {

  private static final String EMPLOYEE_ENDPOINT_KEY = "swarm.employee.endpoint";

  public static String getEmployeeEndpoint(String resourcePath) {
    String baseUrl = "";

    // check kubernetes service
    if (!isEmpty(System.getenv("EMPLOYEE_APP_SERVICE_HOST"))
        && !isEmpty(System.getenv("EMPLOYEE_APP_SERVICE_PORT"))) {
      baseUrl = "http://" + System.getenv("EMPLOYEE_APP_SERVICE_HOST") + ":" + System.getenv("EMPLOYEE_APP_SERVICE_PORT");
    }

    if (isEmpty(baseUrl)) {
      baseUrl = System.getProperty(EMPLOYEE_ENDPOINT_KEY);
    }

    if (isEmpty(baseUrl)) {
      baseUrl = System.getenv(EMPLOYEE_ENDPOINT_KEY);
    }

    if (isEmpty(baseUrl)) {
      baseUrl = "http://localhost:8080";
    }

    return baseUrl + resourcePath;
  }

  public static boolean isEmpty(String str) {
    return str == null || str.trim().length() == 0;
  }

}
