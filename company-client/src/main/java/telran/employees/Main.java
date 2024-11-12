package telran.employees;

import org.json.JSONObject;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardInputOutput;

import java.io.*;
import java.net.Socket;

public class Main
{
    private static Config config = new Config();

    public static void main(String[] args) throws IOException
    {
        System.out.println("Connecting to server: " + config.SERVER_ADDRESS + ":" + config.PORT);
        try (Socket socket = new Socket(config.SERVER_ADDRESS, config.PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            InputOutput io = new StandardInputOutput();

            if (socket.isConnected()) {
                System.out.println("Connected to server: " + socket.getRemoteSocketAddress() + "; Client details :" + socket.getLocalAddress() + ":" + socket.getLocalPort());
            } else {
                System.out.println("Connection failed");
                return;
            }

            Config config = initializeClient(out, in);
            Company company = config.getCompany();

            Item[] items = CompanyItems.getItems(company);
            CompanyItems.setInChannel(in);
            CompanyItems.setOutChannel(out);

            Menu menu = new Menu("Company's Employees Management application", items);
            menu.perform(new StandardInputOutput());

            while (true) {
                String command = io.readString("Enter command (Exit, Save & Exit):");
                if (command.equalsIgnoreCase("Exit")) {
                    break;
                } else if (command.equalsIgnoreCase("Save & Exit")) {
                    out.println("SAVE_COMPANY");
                    String response = in.readLine();
                    io.writeLine(response);
                    break;
                } else {
                    io.writeLine("Unknown command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Config initializeClient(PrintWriter out, BufferedReader in)  throws IOException
    {
        out.println("INIT_PARAMS");
        String response = in.readLine();
        JSONObject jsonResponse = new JSONObject(response);
        String responseData = jsonResponse.getString("response");

        JSONObject jsonData = new JSONObject(responseData);
        Config config = new Config();

        config.setDEPARTMENTS(jsonData.getJSONArray("departments").toList().toArray(new String[0]));
        config.setMAX_EMPLOYEE_ID(jsonData.getLong("maxEmployeeId"));
        config.setMIN_EMPLOYEE_ID(jsonData.getLong("minEmployeeId"));
        config.setMIN_BASIC_SALARY(jsonData.getInt("minBasicSalary"));
        config.setMAX_BASIC_SALARY(jsonData.getInt("maxBasicSalary"));
        config.setMIN_EMPLOYEE_AGE(jsonData.getInt("minEmployeeAge"));
        config.setMAX_EMPLOYEE_AGE(jsonData.getInt("maxEmployeeAge"));
        config.setMIN_WAGE(jsonData.getInt("minWage"));
        config.setMAX_WAGE(jsonData.getInt("maxWage"));
        config.setMIN_HOURS(jsonData.getInt("minHours"));
        config.setMAX_HOURS(jsonData.getInt("maxHours"));
        config.setMIN_PERCENT(jsonData.getFloat("minPercent"));
        config.setMAX_PERCENT(jsonData.getFloat("maxPercent"));
        config.setMIN_SALES(jsonData.getLong("minSales"));
        config.setMAX_SALES(jsonData.getLong("maxSales"));
        config.setMIN_FACTOR(jsonData.getDouble("minFactor"));
        config.setMAX_FACTOR(jsonData.getDouble("maxFactor"));

        config.setCompany(convertJsonToCompany(jsonData.getJSONObject("company")));

        return config;
    }

    private static Company convertJsonToCompany(JSONObject json_company)
    {
        Company company = new CompanyImpl();
        //company.setXXX(json_company.getString("XXX"));

        return company;
    }
}
