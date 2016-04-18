package wildflyswarm.model;

public class Payroll {

  private Employee employee;

  private long salary;

  public Payroll(Employee employee, long salary) {
    this.employee = employee;
    this.salary = salary;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public long getSalary() {
    return salary;
  }

  public void setSalary(long salary) {
    this.salary = salary;
  }

  public String getEmployeeName() {
    return employee == null ? null : employee.getName();
  }

}
