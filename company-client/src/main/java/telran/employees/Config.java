package telran.employees;

public class Config 
{
    public final int PORT = 3500;
    public final String SERVER_ADDRESS = "localhost";

    private long MIN_EMPLOYEE_ID;
    private long MAX_EMPLOYEE_ID;
    private int MIN_BASIC_SALARY;
    private int MAX_BASIC_SALARY;
    private String[] DEPARTMENTS;
    private int MIN_EMPLOYEE_AGE;
    private int MAX_EMPLOYEE_AGE;
    private int MIN_WAGE;
    private int MAX_WAGE;
    private int MIN_HOURS;
    private int MAX_HOURS;
    private float MIN_PERCENT;
    private float MAX_PERCENT;
    private long MIN_SALES;
    private long MAX_SALES;
    private double MIN_FACTOR;
    private double MAX_FACTOR;
    private Company company;

    public long getMIN_EMPLOYEE_ID()
    {
        return MIN_EMPLOYEE_ID;
    }
    public long getMAX_EMPLOYEE_ID()
    {
        return MAX_EMPLOYEE_ID;
    }
    public int getMIN_BASIC_SALARY()
    {
        return MIN_BASIC_SALARY;
    }
    public int getMAX_BASIC_SALARY()
    {
        return MAX_BASIC_SALARY;
    }
    public String[] getDEPARTMENTS()
    {
        return DEPARTMENTS;
    }
    public int getMIN_EMPLOYEE_AGE()
    {
        return MIN_EMPLOYEE_AGE;
    }
    public int getMAX_EMPLOYEE_AGE()
    {
        return MAX_EMPLOYEE_AGE;
    }
    public int getMIN_WAGE()
    {
        return MIN_WAGE;
    }
    public int getMAX_WAGE()
    {
        return MAX_WAGE;
    }
    public int getMIN_HOURS()
    {
        return MIN_HOURS;
    }
    public int getMAX_HOURS()
    {
        return MAX_HOURS;
    }
    public float getMIN_PERCENT()
    {
        return MIN_PERCENT;
    }
    public float getMAX_PERCENT()
    {
        return MAX_PERCENT;
    }
    public long getMIN_SALES()
    {
        return MIN_SALES;
    }
    public long getMAX_SALES()
    {
        return MAX_SALES;
    }
    public double getMIN_FACTOR()
    {
        return MIN_FACTOR;
    }
    public double getMAX_FACTOR()
    {
        return MAX_FACTOR;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setMIN_EMPLOYEE_ID(long min_employee_id)
    {
        this.MIN_EMPLOYEE_ID = min_employee_id;
    }

    public void setMAX_EMPLOYEE_ID(long max_employee_id)
    {
        this.MAX_EMPLOYEE_ID = max_employee_id;
    }

    public void setMIN_BASIC_SALARY(int min_basic_salary)
    {
        this.MIN_BASIC_SALARY = min_basic_salary;
    }

    public void setMAX_BASIC_SALARY(int max_basic_salary) {
        this.MAX_BASIC_SALARY = max_basic_salary;
    }

    public void setDEPARTMENTS(String[] departments) {
        this.DEPARTMENTS = departments;
    }

    public void setMIN_EMPLOYEE_AGE(int min_employee_age) {
        this.MIN_EMPLOYEE_AGE = min_employee_age;
    }

    public void setMAX_EMPLOYEE_AGE(int max_employee_age) {
        this.MAX_EMPLOYEE_AGE = max_employee_age;
    }

    public void setMIN_WAGE(int min_wage) {
        this.MIN_WAGE = min_wage;
    }

    public void setMAX_WAGE(int max_wage) {
        this.MAX_WAGE = max_wage;
    }

    public void setMIN_HOURS(int min_hours) {
        this.MIN_HOURS = min_hours;
    }

    public void setMAX_HOURS(int max_hours) {
        this.MAX_HOURS = max_hours;
    }

    public void setMIN_PERCENT(float min_percent) {
        this.MIN_PERCENT = min_percent;
    }

    public void setMAX_PERCENT(float max_percent) {
        this.MAX_PERCENT = max_percent;
    }

    public void setMIN_SALES(long min_sales) {
        this.MIN_SALES = min_sales;
    }

    public void setMAX_SALES(long max_sales) {
        this.MAX_SALES = max_sales;
    }

    public void setMIN_FACTOR(double min_factor) {
        this.MIN_FACTOR = min_factor;
    }

    public void setMAX_FACTOR(double max_factor) {
        this.MAX_FACTOR = max_factor;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
