package telran.employees;

import java.io.File;
import java.io.IOException;
import org.json.JSONObject;

import telran.io.Persistable;
import telran.view.*;
import telran.net.*;

public class Main
{
    private static final int PORT = 3500;
    private static final String FILE_PATH = "../CompanyData/employees.data";

    final static long MIN_EMPLOYEE_ID = 100000;
    final static long MAX_EMPLOYEE_ID = 999999;
    final static int MIN_BASIC_SALARY = 5000;
    final static int MAX_BASIC_SALARY = 30000;
    final static String[] DEPARTMENTS = { "QA", "Audit", "Development", "Management" };
    final static int MIN_EMPLOYEE_AGE = 18;
    final static int MAX_EMPLOYEE_AGE = 70;
    final static int MIN_WAGE = 5;
    final static int MAX_WAGE = 50;
    final static int MIN_HOURS = 1;
    final static int MAX_HOURS = 40;
    final static float MIN_PERCENT = 10;
    final static float MAX_PERCENT = 100;
    final static long MIN_SALES = 5;
    final static long MAX_SALES = 99999;
    final static double MIN_FACTOR = 0.1;
    final static double MAX_FACTOR = 1.0;


    public static void main(String[] args)
    {
        Company company = new CompanyImpl();
        if (company instanceof Persistable persistable_company) {
            try {
                CompanyServer server = new CompanyServer();

                try {
                    server.seanceStarts(PORT);
                    System.out.println("Server started...");

                    while (true) {
                        String client_message = server.receiveMessage();
                        if (client_message == null) {
                            break;
                        }
                        String response = handleClientMessage(client_message, company);
                        server.sendMessage(response);
                    }
                } catch (IOException e) {
                    InputOutput io = new StandardInputOutput();
                    io.writeLine(String.format("IOException error performed \n%s\n%s", e.getMessage(), e.toString()));
                } catch (RuntimeException e) {
                    InputOutput io = new StandardInputOutput();
                    io.writeLine(String.format("RuntimeError performed \n%s\n%s", e.getMessage(), e.toString()));
                } finally {
                    try {
                        server.seanceStops();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (RuntimeException e) {
                InputOutput io = new StandardInputOutput();
                io.writeLine(String.format("RuntimeError performed \n%s\n%s", e.getMessage(), e.toString()));
            }
        }
    }

    private static String handleClientMessage(String client_message, Company company)
    {
        String res;
        JSONObject json = new JSONObject();

        if (client_message.equals("INIT_PARAMS")) {
            res = initializeParameters(company);
        } else if (client_message.equals("SAVE_COMPANY")) {
            res = saveCompany(company);
        } else {
            res = "Unknown command";
        }

        json.put("response", res);
        return json.toString();
    }

    private static String initializeParameters(Company company)
    {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            ((CompanyImpl) company).restoreFromFile(FILE_PATH);
        }

        JSONObject json = new JSONObject();
        json.put("departments", DEPARTMENTS);
        json.put("maxEmployeeId", MAX_EMPLOYEE_ID);
        json.put("minEmployeeId",MIN_EMPLOYEE_ID);
        json.put("maxEmployeeId",MAX_EMPLOYEE_ID);
        json.put("minBasicSalary",MIN_BASIC_SALARY);
        json.put("maxBasicSalary",MAX_BASIC_SALARY);
        json.put("departments",DEPARTMENTS);
        json.put("minEmployeeAge",MIN_EMPLOYEE_AGE);
        json.put("maxEmployeeAge",MAX_EMPLOYEE_AGE);
        json.put("minWage",MIN_WAGE);
        json.put("maxWage",MAX_WAGE);
        json.put("minHours",MIN_HOURS);
        json.put("maxHours",MAX_HOURS);
        json.put("minPercent",MIN_PERCENT);
        json.put("maxPercent",MAX_PERCENT);
        json.put("minSales",MIN_SALES);
        json.put("maxSales",MAX_SALES);
        json.put("minFactor",MIN_FACTOR);
        json.put("maxFactor",MAX_FACTOR);
        json.put("company",convertCompanyToJson(company));

        return json.toString();
    }

    public static String saveCompany(Company company)
    {
        String msg;
        int code;
        JSONObject json = new JSONObject();
        msg = "Company data could not be saved.";
        code = 400;
        json.put("code", code);
        json.put("message", msg);
        return json.toString();

/*
        if (company instanceof Persistable persistableCompany) {
            try {
                ((CompanyImpl) company).saveToFile(FILE_PATH);
                msg = "Company data saved successfully.";
                code = 200;
            } catch (RuntimeException e) {
                msg = String.format("Error saving company data: %s", e.getMessage());
                code = 500;
            }
        } else {
            msg = "Company data could not be saved. The company is not persistable.";
            code = 400;
        }

        json.put("code", code);
        json.put("message", msg);
        return json.toString();
*/
    }

    private static JSONObject convertCompanyToJson(Company company)
    {
        JSONObject companyJson = new JSONObject();
        //companyJson.put("XXX", company.getXXX());

        return companyJson;
    }
}
