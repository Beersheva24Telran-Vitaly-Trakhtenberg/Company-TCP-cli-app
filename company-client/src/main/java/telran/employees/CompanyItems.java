package telran.employees;

import netscape.javascript.JSObject;
import telran.io.Persistable;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class CompanyItems
{
    private static Company company;
    private static Config config = new Config();
    private static PrintWriter out;
    private static BufferedReader in;

    public static Item[] getItems(Company company)
    {
        CompanyItems.company = company;

        Item[] hireSubmenuItems = {
                Item.of("Hire Employee", CompanyItems::hire_employee),
                Item.of("Hire Wage Employee", CompanyItems::hire_wage_employee),
                Item.of("Hire Sales Person", CompanyItems::hire_sales_person),
                Item.of("Hire Manager", CompanyItems::hire_manager),
                Item.of("Return to Main menu...", _ -> {}, true)
        };

        Item[] displaySubmenuItems = {
                Item.of("Display Employee data", CompanyItems::display_employee_by_id),
                Item.of("Department Salary Budget", CompanyItems::department_salary_budget),
                Item.of("List of Departments", CompanyItems::departments_list),
                Item.of("Display Managers with Most Factor", CompanyItems::display_managers_most_factors),
                Item.of("Return to Main menu...", _ -> {}, true)
        };

        Item[] items = {
                createMenuItemWithSubmenu("Hire...", hireSubmenuItems),
                Item.of("Fire Employee", CompanyItems::fire_employee_by_id),
                createMenuItemWithSubmenu("Display...", displaySubmenuItems),
                Item.of("Save & Exit", CompanyItems::save_on_exit),
                Item.of("Exit", CompanyItems::exit, true),
        };

        return items;
    }

    public static void setOutChannel(PrintWriter out) {
        CompanyItems.out = out;
    }

    public static void setInChannel(BufferedReader in)
    {
        CompanyItems.in = in;
    }

    private static void initializeConnection() throws IOException
    {
        Socket socket = new Socket(config.SERVER_ADDRESS, config.PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private static void closeConnection() throws IOException
    {
        if (out != null) out.close();
        if (in != null) in.close();
    }

    private static void exit(InputOutput io)
    {
        try {
            initializeConnection();
            out.print("EXIT");
            closeConnection();
        } catch (IOException e) {
            io.writeLine("Error: " + e.getMessage());
        }
        io.writeLine("Exiting the application...");
        System.exit(0);
    }

    private static Item createMenuItemWithSubmenu(String title, Item[] submenuItems) {
        return Item.of(title, io -> {
            Menu submenu = new Menu(title, submenuItems);
            submenu.perform(io);
        });
    }

    private static void display_employee_by_id(InputOutput io)
    {
        Employee employee = company.getEmployee(io.readNumberRange(
                "Enter Eployee ID:",
                String.format("%s\n%s %d...%d",
                        "Wrong ID",
                        "Correct ID is",
                        config.getMIN_EMPLOYEE_ID(),
                        config.getMAX_EMPLOYEE_ID()),
                config.getMIN_EMPLOYEE_ID(),
                config.getMAX_EMPLOYEE_ID()
        ).longValue());
        io.writeLine(Objects.requireNonNullElse(employee, "No Employee in the company"));
    }

    private static void fire_employee_by_id(InputOutput io)
    {
        Employee employee = company.removeEmployee(io.readNumberRange(
                "Enter Eployee ID to be fired:",
                String.format("%s\n%s %d...%d",
                        "Wrong ID",
                        "Correct ID is",
                        config.getMIN_EMPLOYEE_ID(),
                        config.getMAX_EMPLOYEE_ID()),
                config.getMIN_EMPLOYEE_ID(),
                config.getMAX_EMPLOYEE_ID()
        ).longValue());
        io.writeLine(Objects.requireNonNullElse(employee, "No Employee in the company"));
    }

    private static void department_salary_budget(InputOutput io)
    {
        String department = io.readStringOptions(
                "Enter Department",
                "Wrong Department",
                new HashSet<>(Arrays.asList(config.getDEPARTMENTS()))
        );
        int department_budget = company.getDepartmentBudget(department);
        io.writeLine(String.format("Department '%s' has salary budget is %d", department, department_budget));
    }

    private static void departments_list(InputOutput io)
    {
        String[] departments = company.getDepartments();
        if (departments.length > 0) {
            io.writeLine(String.join(", ", departments));
        } else {
            io.writeLine("No departments available.");
        }
    }

    private static void display_managers_most_factors(InputOutput io)
    {
        Manager[] managers = company.getManagersWithMostFactor();
        if (managers.length > 0) {
            io.writeLine("Managers with the most factors:");
            for (Manager manager : managers) {
                io.writeLine(manager.toString());
            }
        } else {
            io.writeLine("No managers found with the most factors.");
        }
    }

    private static void save_on_exit(InputOutput io)
    {
        if (company instanceof Persistable persistableCompany) {
            try {
                initializeConnection();
                out.print("SAVE_COMPANY");
                String response = in.readLine();
                io.writeLine(response);
                closeConnection();
            } catch (IOException e) {
                io.writeLine("Error: " + e.getMessage());
            }
        } else {
            io.writeLine("Company data could not be saved. The company is not persistable.");
        }
        io.writeLine("Exiting the application...");
        System.exit(0);
    }

    private static void hire_employee(InputOutput io)
    {
        Employee employee = io.readObject("Enter the Employee data in the format: \n" +
                        "[#id]#[Salary]#[Department]",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new Employee(Long.parseLong(tokens[0]), Integer.parseInt(tokens[1]), tokens[2]);
                });
        io.writeLine("You are entered the following Employee data");
        io.writeLine(employee);
        company.addEmployee(employee);
    }

    private static void hire_wage_employee(InputOutput io)
    {
        WageEmployee wage_employee = io.readObject("Enter the Employee data in the format: \n" +
                        "[#id]#[Salary]#[Department]#[wage]#[hours]",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new WageEmployee(Long.parseLong(tokens[0]), Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                });
        io.writeLine("You are entered the following WageEmployee data");
        io.writeLine(wage_employee);
        company.addEmployee(wage_employee);
    }

    private static void hire_sales_person(InputOutput io)
    {
        SalesPerson sales_person = io.readObject("Enter the Employee data in the format: \n" +
                        "[#id]#[Salary]#[Department]#[wage]#[hours]#[percents]#[sales]",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new SalesPerson(
                            Long.parseLong(tokens[0]),
                            Integer.parseInt(tokens[1]),
                            tokens[2],
                            Integer.parseInt(tokens[3]),
                            Integer.parseInt(tokens[4]),
                            Float.parseFloat(tokens[5]),
                            Long.parseLong(tokens[6]));
                });
        io.writeLine("You are entered the following Sales Person data");
        io.writeLine(sales_person);
        company.addEmployee(sales_person);
    }

    private static void hire_manager(InputOutput io)
    {
        Manager manager = io.readObject("Enter the Employee data in the format: \n" +
                        "[#id]#[Salary]#[Department]#[factor]",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new Manager(
                            Long.parseLong(tokens[0]),
                            Integer.parseInt(tokens[1]),
                            tokens[2],
                            Float.parseFloat(tokens[3]));
                });
        io.writeLine("You are entered the following Sales Person data");
        io.writeLine(manager);
        company.addEmployee(manager);
    }
}
