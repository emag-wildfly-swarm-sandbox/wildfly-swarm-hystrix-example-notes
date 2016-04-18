package wildflyswarm.payroll.api;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import wildflyswarm.model.Employee;
import wildflyswarm.model.Payroll;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/payroll")
public class PayrollController {

  private static final Logger log = Logger.getLogger(PayrollController.class.getName());

  public PayrollController() {
    HystrixCommandProperties.Setter().withCircuitBreakerRequestVolumeThreshold(10);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Payroll> findAll() {
    List<Employee> employees = new FindEmployeesCommand().execute();
    List<Payroll> payroll = new ArrayList<>();
    employees.forEach(employee -> {
      payroll.add(new Payroll(employee, employee.getId() * 1500));
    });

    return payroll;
  }

  class FindEmployeesCommand extends HystrixCommand<List<Employee>> {

    public FindEmployeesCommand() {
      super(HystrixCommandGroupKey.Factory.asKey("EmployeesGroup"));
    }

    @Override
    protected List<Employee> run() throws Exception {
      String url = Utils.getEmployeeEndpoint("/employees");
      Invocation.Builder request = ClientBuilder.newClient().target(url).request();

      try {
        return request.get(new GenericType<List<Employee>>() {
        });
      } catch (Exception e) {
        log.severe("Failed to call Employee service at " + url + ": " + e.getMessage());
        throw e;
      }
    }
  }
}
