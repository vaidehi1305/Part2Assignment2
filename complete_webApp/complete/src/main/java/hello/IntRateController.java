package hello;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Yamini on 4/14/2017.
 */
@Controller
public class IntRateController {

    @GetMapping("/regression")
    public String greetingForm(Model model) {
        model.addAttribute("intRate", new IntRate());
        return "regression";
    }

    @PostMapping("/regression")
    public String greetingSubmit(@ModelAttribute IntRate intRate, Model model) throws IOException, ParseException {
        String int_rate = "0";
        String int_rate1 = "0";
        String int_rate2 = "0";

        //purpose
        if(intRate.getPurpose().equalsIgnoreCase("credit_card")){
            intRate.setPurpose("1");
        }else if(intRate.getPurpose().equalsIgnoreCase("car")){
            intRate.setPurpose("2");
        }else if(intRate.getPurpose().equalsIgnoreCase("small_business")){
            intRate.setPurpose("3");
        }else if(intRate.getPurpose().equalsIgnoreCase("other")){
            intRate.setPurpose("4");
        }else if(intRate.getPurpose().equalsIgnoreCase("wedding")){
            intRate.setPurpose("5");
        }else if(intRate.getPurpose().equalsIgnoreCase("debt_consolidation")){
            intRate.setPurpose("6");
        }else if(intRate.getPurpose().equalsIgnoreCase("home_improvement")){
            intRate.setPurpose("7");
        }else if(intRate.getPurpose().equalsIgnoreCase("major_purchase")){
            intRate.setPurpose("8");
        }else if(intRate.getPurpose().equalsIgnoreCase("medical")){
            intRate.setPurpose("9");
        }else if(intRate.getPurpose().equalsIgnoreCase("moving")){
            intRate.setPurpose("10");
        }else if(intRate.getPurpose().equalsIgnoreCase("vacation")){
            intRate.setPurpose("11");
        }else if(intRate.getPurpose().equalsIgnoreCase("house")){
            intRate.setPurpose("12");
        }else if(intRate.getPurpose().equalsIgnoreCase("renewable_energy")){
            intRate.setPurpose("13");
        }else if(intRate.getPurpose().equalsIgnoreCase("educational")){
            intRate.setPurpose("14");
        }

        //Home OwnerShip
        if(intRate.getHome_ownership().equalsIgnoreCase("RENT")){
            intRate.setHome_ownership("1");
        }else if(intRate.getHome_ownership().equalsIgnoreCase("OWN")){
            intRate.setHome_ownership("2");
        }else if(intRate.getHome_ownership().equalsIgnoreCase("MORTGAGE")){
            intRate.setHome_ownership("3");
        }
        else if(intRate.getHome_ownership().equalsIgnoreCase("OTHER")){
            intRate.setHome_ownership("4");
        }else if(intRate.getHome_ownership().equalsIgnoreCase("NONE")){
            intRate.setHome_ownership("5");
        }else if(intRate.getHome_ownership().equalsIgnoreCase("ANY")) {
            intRate.setHome_ownership("6");
        }

        //Loan Status
        if(intRate.getLoan_status().equalsIgnoreCase("Fully Paid")){
            intRate.setLoan_status("1");
        }else{
            intRate.setLoan_status("2");
        }

        //Term


        //Full Data Set
        if(true){
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"term\",\"int_rate\",\"installment\",\"emp_length\",\"home_ownership\",\"annual_inc\",\"addr_state\",\"inq_last_6mths\",\"open_acc\",\"revol_util\",\"ficoMean\",\"home_ownership_num\",\"purpose_num\",\"loan_status_num\",\"credit_history\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append(intRate.getLoan_amnt()).append(",").append(intRate.getTerm()).append(",0,").append(intRate.getInstallment()).append(",")
                    .append(intRate.getEmp_length()).append(",").append("\"dummy\"").append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getAddr_state()).append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getOpen_acc())
                    .append(",").append(intRate.getRevol_util()).append(",").append(intRate.getFicoMean()).append(",").append(intRate.getHome_ownership())
                    .append(",").append(intRate.getPurpose()).append(",").append(intRate.getLoan_status()).append(",").append(intRate.getCredit_history());
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/40ddc72e4d98479198d9feb5c4e4b5a9/services/ff863de3eaf548b4b0282eb61ce46ae3/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer 4gV/U10Lz7JDpeLxY2eOxbDedpIazN90aDaEMv56cjzHHy5K1ibec6ADid/VXcKaabugOJdbkL+RMYYKyyfUNw==");
            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate = (String)((JSONArray)jsonObj3_1.get(0)).get(15);
        }




        //Clustering
        StringBuilder requestCluster = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"state\",\"ficoMean\",\"inq_last_6mths\",\"purpose_num\",\"installment\",\"loan_status_num\",\"term\",\"annual_inc\",\"emp_length\",\"open_acc\",\"credit_history\",\"revol_util\",\"int_rate\"],\"Values\":[[");
        StringBuilder requestCluster2 = new StringBuilder();
        requestCluster2.append(intRate.getLoan_amnt()).append(",").append(intRate.getAddr_state()).append(",").append(intRate.getFicoMean())
                .append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getPurpose()).append(",").append(intRate.getInstallment())
                .append(",\"").append(intRate.getLoan_status()).append("\",").append(intRate.getTerm()).append(",").append(intRate.getAnnual_inc())
                .append(",").append(intRate.getEmp_length()).append(",").append(intRate.getOpen_acc()).append(",").append(intRate.getCredit_history())
                .append(",").append(intRate.getRevol_util()).append(",").append("0").append("");
        StringBuilder requestCluster3 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
        requestCluster.append(requestCluster2).append(requestCluster3);
        System.out.println(requestCluster.toString());

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/a8b3e43820f84c7b9c0bca83f0d2bdf5/services/c4f5a647005c43a0ad32249e15612626/execute?api-version=2.0&details=true");
        post.setHeader("Authorization","Bearer jHBHSM1qQTduVVvZjNuVS1uaUYwaOAjUfQjcOqryuj4rnouOHGUURRp/KRax1ujx9ehTVlghpWnDFHEbod+T0Q==");
        post.setHeader("Content-Type","application/json");
        StringEntity input = new StringEntity(requestCluster.toString());
        input.setContentType("application/json");
        post.setEntity(input);
        HttpResponse response = httpClient.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        StringBuilder output = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            output.append(line);
        }
        System.out.println(output.toString());











//        StringBuilder requestBody = new StringBuilder("{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"Unnamed: 0\",\"Amount_Requested\",\"Employment_Length\",\"Application_Date\",\"Zip_Code\",\"State\",\"Debt-To-Income-Ratio\",\"Policy_Code\",\"grant_loan\",\"Fico_Score\",\"State_Factorized\"],\"Values\": [[null,");
//        StringBuilder values = new StringBuilder();
//        values.append("\"").append(classification.getAmount_Requested()).append("\",\"")
//                .append(classification.getEmployment_Length()).append("\",null,")
//                .append("\"").append(classification.getZip_code()).append("\",null,\"").append(classification.getDebt_To_Income_Ratio())
//                .append("\",null,null,\"")
//                .append(classification.getFico_Score()).append("\",null");
//        StringBuilder bodyLast = new StringBuilder("]]}},\"GlobalParameters\": {}}");
//        requestBody.append(values.toString()).append(bodyLast);
//        System.out.println(requestBody.toString());
//        HttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost post = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/338268e0005b48f09e88c611a2835a2f/services/1d2759f0779a47a1bc8d54bd5336cb23/execute?api-version=2.0&details=true");
//        post.setHeader("Authorization","Bearer TyOmzIiJI8urNwRn1fkq7wiZuWCTWYciY5FdyrVA4ftuY/UxW4hMHeEeZC0rBxIfqxxyIB+G7ovv98oUjzhLeg==");
//        post.setHeader("Content-Type","application/json");
//        StringEntity input = new StringEntity(requestBody.toString());
//        input.setContentType("application/json");
//        post.setEntity(input);
//        HttpResponse response = httpClient.execute(post);
//        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//        String line = "";
//        StringBuilder output = new StringBuilder();
//        while ((line = rd.readLine()) != null) {
//            output.append(line);
//        }
//        System.out.println(output.toString());
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject)parser.parse(output.toString());
        JSONObject jsonObj1 = (JSONObject)((JSONObject)((JSONObject)jsonObj.get("Results")).get("output1")).get("value");
        JSONArray jsonObj3 = (JSONArray)jsonObj1.get("Values");
        String label = (String)((JSONArray)jsonObj3.get(0)).get(14);
        if(label.equalsIgnoreCase("0")){
            //bin 0
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"Column 0\",\"loan_amnt\",\"state\",\"ficoMean\",\"inq_last_6mths\",\"purpose_num\",\"installment\",\"dti\",\"loan_status_num\",\"term\",\"annual_inc\",\"emp_length\",\"open_acc\",\"credit_history\",\"revol_util\",\"int_rate\",\"Assignments\",\"DistancesToClusterCenter no.0\",\"DistancesToClusterCenter no.1\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append("0").append(",").append(intRate.getLoan_amnt()).append(",").append(intRate.getAddr_state()).append(",").append(intRate.getFicoMean())
                    .append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getPurpose()).append(",").append(intRate.getInstallment()).append(",")
                    .append("0")
                    .append(",\"").append(intRate.getLoan_status()).append("\",").append(intRate.getTerm()).append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getEmp_length()).append(",").append(intRate.getOpen_acc()).append(",").append(intRate.getCredit_history())
                    .append(",").append(intRate.getRevol_util()).append(",").append("0").append(",").append("0").append(",").append("0").append(",").append("0");
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/a8b3e43820f84c7b9c0bca83f0d2bdf5/services/1a2f8a990a234d24adfa33bc60ffb0e0/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer 0JjuPOEN2hCe5TBtpSVurB/loJgVh4Wlkk4o5ZVmSabRxNXbzquygtmZ33OPsiw11qmQIKkN/dGXQBmcvvnm4w==");
            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate1 = (String)((JSONArray)jsonObj3_1.get(0)).get(14);
        }else{
            //bin 1
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"Column 0\",\"loan_amnt\",\"state\",\"ficoMean\",\"inq_last_6mths\",\"purpose_num\",\"installment\",\"dti\",\"loan_status_num\",\"term\",\"annual_inc\",\"emp_length\",\"open_acc\",\"credit_history\",\"revol_util\",\"int_rate\",\"Assignments\",\"DistancesToClusterCenter no.0\",\"DistancesToClusterCenter no.1\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append("0").append(",").append(intRate.getLoan_amnt()).append(",").append(intRate.getAddr_state()).append(",").append(intRate.getFicoMean())
                    .append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getPurpose()).append(",").append(intRate.getInstallment()).append(",")
                    .append("0")
                    .append(",\"").append(intRate.getLoan_status()).append("\",").append(intRate.getTerm()).append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getEmp_length()).append(",").append(intRate.getOpen_acc()).append(",").append(intRate.getCredit_history())
                    .append(",").append(intRate.getRevol_util()).append(",").append("0").append(",").append("0").append(",").append("0").append(",").append("0");
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/a8b3e43820f84c7b9c0bca83f0d2bdf5/services/2e7cea4d9a6340ada7605ac1295e0ae6/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer uSuM7PCRcqc5z9yrMQ94++762hvBYyIvs20gVnJqlrEIgh5EL6/If4SD425Q2wueevWr06d8n9lSlLrVeOk/fw==");
            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate1 = (String)((JSONArray)jsonObj3_1.get(0)).get(14);
        }


        //Bins Interest Rate
        //Rent 1 Own 2 Mortgage 3 Other 4
        if(intRate.getTerm().equalsIgnoreCase("60") && intRate.getHome_ownership().equalsIgnoreCase("1")){
            //regression bin 2
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"term\",\"int_rate\",\"installment\",\"emp_length\",\"annual_inc\",\"addr_state\",\"inq_last_6mths\",\"open_acc\",\"revol_util\",\"ficoMean\",\"purpose_num\",\"application_type_num\",\"loan_status_num\",\"credit_history\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append(intRate.getLoan_amnt()).append(",").append(intRate.getTerm()).append(",0,").append(intRate.getInstallment()).append(",")
                    .append(intRate.getEmp_length()).append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getAddr_state()).append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getOpen_acc())
                    .append(",").append(intRate.getRevol_util()).append(",").append(intRate.getFicoMean())
                    .append(",").append(intRate.getPurpose()).append(",1,").append(intRate.getLoan_status()).append(",").append(intRate.getCredit_history());
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/a8b3e43820f84c7b9c0bca83f0d2bdf5/services/028caee7a53343b793d8dc4e56de6b9b/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer hhsJ1CqF1eh2o7+5uHtIWVvz8rbmwnHRx9wgKo0AGgG/+wrwkPCLkvDYwjHUdtp+hnV6bumxYMC62v+5+ZBgiQ==");

            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate = (String)((JSONArray)jsonObj3_1.get(0)).get(15);
        }else if(intRate.getTerm().equalsIgnoreCase("60") && intRate.getHome_ownership().equalsIgnoreCase("2")){
            //bin 3
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"term\",\"int_rate\",\"installment\",\"emp_length\",\"annual_inc\",\"addr_state\",\"inq_last_6mths\",\"open_acc\",\"revol_util\",\"ficoMean\",\"purpose_num\",\"loan_status_num\",\"credit_history\",\"home_ownership_num\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append(intRate.getLoan_amnt()).append(",").append(intRate.getTerm()).append(",0,").append(intRate.getInstallment()).append(",")
                    .append(intRate.getEmp_length()).append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getAddr_state()).append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getOpen_acc())
                    .append(",").append(intRate.getRevol_util()).append(",").append(intRate.getFicoMean()).append(",").append(intRate.getPurpose()).append(",").append(intRate.getLoan_status()).append(",").append(intRate.getCredit_history()).append(",").append(intRate.getHome_ownership());
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/40ddc72e4d98479198d9feb5c4e4b5a9/services/f2a7a61a4d64416fb0f15857f05debf8/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer GvYnH/NfrqCIBkLSyMu4V8dfKMSHWUFON/eJ1BYzbSp3iEhCAcNida5ft6MnqJf4Dc9Yg9Wrnu3qg+U1DeuRbA==");
            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate = (String)((JSONArray)jsonObj3_1.get(0)).get(15);
        }else if(intRate.getTerm().equalsIgnoreCase("60") && intRate.getHome_ownership().equalsIgnoreCase("3")){
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"term\",\"int_rate\",\"installment\",\"emp_length\",\"annual_inc\",\"addr_state\",\"inq_last_6mths\",\"open_acc\",\"revol_util\",\"ficoMean\",\"home_ownership_num\",\"purpose_num\",\"loan_status_num\",\"credit_history\"],\"Values\":[[");

            StringBuilder requestCluster2_1 = new StringBuilder();

            requestCluster2_1.append(intRate.getLoan_amnt()).append(",").append(intRate.getTerm()).append(",0,").append(intRate.getInstallment()).append(",")

                    .append(intRate.getEmp_length()).append(",").append(",").append(intRate.getAnnual_inc())

                    .append(",").append(intRate.getAddr_state()).append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getOpen_acc())

                    .append(",").append(intRate.getRevol_util()).append(",").append(intRate.getFicoMean()).append(",").append(intRate.getHome_ownership())

                    .append(",").append(intRate.getPurpose()).append(",").append(intRate.getLoan_status()).append(",").append(intRate.getCredit_history());

            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");

            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);

            System.out.println(requestCluster_1.toString());

            HttpClient httpClient_1 = HttpClientBuilder.create().build();

            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/a8b3e43820f84c7b9c0bca83f0d2bdf5/services/06e8e6a5bcb648ebad37a821cf60c199/execute?api-version=2.0&details=true");

            post_1.setHeader("Authorization","Bearer Byii70NwJQymWFr5Mq014SrwBoOPii5ONBipgGFYajSRGJEJoN03iYbyNgoGzFiFn0JQWw2euOeZWZmaf/6EFg==");

            post_1.setHeader("Content-Type","application/json");

            StringEntity input_1 = new StringEntity(requestCluster_1.toString());

            input_1.setContentType("application/json");

            post_1.setEntity(input_1);

            HttpResponse response_1 = httpClient_1.execute(post_1);

            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));

            String line_1 = "";

            StringBuilder output_1 = new StringBuilder();

            while ((line_1 = rd_1.readLine()) != null) {

                output_1.append(line_1);

            }

            System.out.println(output_1.toString());

            JSONParser parser_1 = new JSONParser();

            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());

            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");

            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");

            int_rate = (String)((JSONArray)jsonObj3_1.get(0)).get(15);

        }else if(intRate.getTerm().equalsIgnoreCase("60") && intRate.getHome_ownership().equalsIgnoreCase("4")){

            //bin 1
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"term\",\"int_rate\",\"installment\",\"emp_length\",\"annual_inc\",\"addr_state\",\"inq_last_6mths\",\"open_acc\",\"revol_util\",\"ficoMean\",\"purpose_num\",\"application_type_num\",\"loan_status_num\",\"credit_history\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append(intRate.getLoan_amnt()).append(",").append(intRate.getTerm()).append(",0,").append(intRate.getInstallment()).append(",")
                    .append(intRate.getEmp_length()).append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getAddr_state()).append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getOpen_acc())
                    .append(",").append(intRate.getRevol_util()).append(",").append(intRate.getFicoMean())
                    .append(",").append(intRate.getPurpose()).append(",1,").append(intRate.getLoan_status()).append(",").append(intRate.getCredit_history());
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/a8b3e43820f84c7b9c0bca83f0d2bdf5/services/83ff495b1caa4d4b9c39c743c05ac46a/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer n3XQn336BqHm01T5vLULEE4jfHfblYgl1WZxBaTczXLb7sC+xbfTNhpPjuYmxtOF8kJRUbxXxYcEgSYe2g2pvA==");
            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate = (String)((JSONArray)jsonObj3_1.get(0)).get(15);


        }else if(intRate.getTerm().equalsIgnoreCase("36") && intRate.getHome_ownership().equalsIgnoreCase("1")){
            //bin 7
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"term\",\"int_rate\",\"installment\",\"emp_length\",\"annual_inc\",\"addr_state\",\"inq_last_6mths\",\"open_acc\",\"revol_util\",\"ficoMean\",\"home_ownership_num\",\"purpose_num\",\"loan_status_num\",\"credit_history\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append(intRate.getLoan_amnt()).append(",").append(intRate.getTerm()).append(",0,").append(intRate.getInstallment()).append(",")
                    .append(intRate.getEmp_length()).append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getAddr_state()).append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getOpen_acc())
                    .append(",").append(intRate.getRevol_util()).append(",").append(intRate.getFicoMean()).append(",").append(intRate.getHome_ownership()).append(",").append(intRate.getPurpose()).append(",").append(intRate.getLoan_status()).append(",").append(intRate.getCredit_history());
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/40ddc72e4d98479198d9feb5c4e4b5a9/services/03b8f0bbc45041eda336f67af87cbae8/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer 4pqWpkIwA4F0AjyRFCpL+9t62Ob7V7+1SEM29r+uH27d3ZZF37zcpNp2cpTBOtj+VR4or/80WOBZZezuZT5Lig==");
            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate = (String)((JSONArray)jsonObj3_1.get(0)).get(15);
        }else if(intRate.getTerm().equalsIgnoreCase("36") && intRate.getHome_ownership().equalsIgnoreCase("2")){
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"term\",\"int_rate\",\"installment\",\"emp_length\",\"annual_inc\",\"addr_state\",\"inq_last_6mths\",\"open_acc\",\"revol_util\",\"ficoMean\",\"home_ownership_num\",\"purpose_num\",\"loan_status_num\",\"credit_history\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append(intRate.getLoan_amnt()).append(",").append(intRate.getTerm()).append(",0,").append(intRate.getInstallment()).append(",")
                    .append(intRate.getEmp_length()).append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getAddr_state()).append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getOpen_acc())
                    .append(",").append(intRate.getRevol_util()).append(",").append(intRate.getFicoMean()).append(",").append(intRate.getHome_ownership()).append(",").append(intRate.getPurpose()).append(",").append(intRate.getLoan_status()).append(",").append(intRate.getCredit_history());
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/40ddc72e4d98479198d9feb5c4e4b5a9/services/1e58463d2cb54e428d75bd60e3756d4c/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer TlTvfJ4VouzAsl5DTITgwb7WIfdRPY5R88stmEpiQ+zsPDPs5ydn1dGnaFbxdZGZpALtmHFYaVuClj40+LO5+w==");
            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate = (String)((JSONArray)jsonObj3_1.get(0)).get(15);
        }else if(intRate.getTerm().equalsIgnoreCase("36") && intRate.getHome_ownership().equalsIgnoreCase("3")){
            //bin 4
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"term\",\"int_rate\",\"installment\",\"emp_length\",\"annual_inc\",\"addr_state\",\"inq_last_6mths\",\"open_acc\",\"revol_util\",\"ficoMean\",\"home_ownership_num\",\"purpose_num\",\"loan_status_num\",\"credit_history\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append(intRate.getLoan_amnt()).append(",").append(intRate.getTerm()).append(",0,").append(intRate.getInstallment()).append(",")
                    .append(intRate.getEmp_length()).append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getAddr_state()).append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getOpen_acc())
                    .append(",").append(intRate.getRevol_util()).append(",").append(intRate.getFicoMean()).append(",").append(intRate.getHome_ownership()).append(",").append(intRate.getPurpose()).append(",").append(intRate.getLoan_status()).append(",").append(intRate.getCredit_history());
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/40ddc72e4d98479198d9feb5c4e4b5a9/services/bfedf5470fd2471e95b81d38a4df5278/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer Qa6oLOnE019JGaNuxgzxwH5A2EUfvl8xuWzoJoWcc+tSmY2tkixOmbDxGh+zobu6grGt8YB8bUyFhoKQCIRpog==");
            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate = (String)((JSONArray)jsonObj3_1.get(0)).get(15);
        }else if(intRate.getTerm().equalsIgnoreCase("36") && intRate.getHome_ownership().equalsIgnoreCase("4")){
            StringBuilder requestCluster_1 = new StringBuilder("{\"Inputs\":{\"input1\":{\"ColumnNames\":[\"loan_amnt\",\"term\",\"int_rate\",\"installment\",\"emp_length\",\"annual_inc\",\"addr_state\",\"inq_last_6mths\",\"open_acc\",\"revol_util\",\"ficoMean\",\"home_ownership_num\",\"purpose_num\",\"loan_status_num\",\"credit_history\"],\"Values\":[[");
            StringBuilder requestCluster2_1 = new StringBuilder();
            requestCluster2_1.append(intRate.getLoan_amnt()).append(",").append(intRate.getTerm()).append(",0,").append(intRate.getInstallment()).append(",")
                    .append(intRate.getEmp_length()).append(",").append(intRate.getAnnual_inc())
                    .append(",").append(intRate.getAddr_state()).append(",").append(intRate.getInq_last_6mths()).append(",").append(intRate.getOpen_acc())
                    .append(",").append(intRate.getRevol_util()).append(",").append(intRate.getFicoMean()).append(",").append(intRate.getHome_ownership()).append(",").append(intRate.getPurpose()).append(",").append(intRate.getLoan_status()).append(",").append(intRate.getCredit_history());
            StringBuilder requestCluster3_1 = new StringBuilder("]]}},\"GlobalParameters\":{}}");
            requestCluster_1.append(requestCluster2_1).append(requestCluster3_1);
            System.out.println(requestCluster_1.toString());
            HttpClient httpClient_1 = HttpClientBuilder.create().build();
            HttpPost post_1 = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/40ddc72e4d98479198d9feb5c4e4b5a9/services/df7c664d88b44101a1175fe872808358/execute?api-version=2.0&details=true");
            post_1.setHeader("Authorization","Bearer USmhOmsRGxzmph9+tPT1DsJGpD6CUxlD7tLWBLILrFPAuZnwa4/nGykD/WoNCbDvtpxMcbsfULVe0nSTrQoWuw==");
            post_1.setHeader("Content-Type","application/json");
            StringEntity input_1 = new StringEntity(requestCluster_1.toString());
            input_1.setContentType("application/json");
            post_1.setEntity(input_1);
            HttpResponse response_1 = httpClient_1.execute(post_1);
            BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
            String line_1 = "";
            StringBuilder output_1 = new StringBuilder();
            while ((line_1 = rd_1.readLine()) != null) {
                output_1.append(line_1);
            }
            System.out.println(output_1.toString());
            JSONParser parser_1 = new JSONParser();
            JSONObject jsonObj_1 = (JSONObject)parser_1.parse(output_1.toString());
            JSONObject jsonObj1_1 = (JSONObject)((JSONObject)((JSONObject)jsonObj_1.get("Results")).get("output1")).get("value");
            JSONArray jsonObj3_1 = (JSONArray)jsonObj1_1.get("Values");
            int_rate = (String)((JSONArray)jsonObj3_1.get(0)).get(15);
        }


//        System.out.println("=== Label ==="+label);
//        if(label.equals("0")){
//            System.out.println("Loan Rejected");
//        }else{
//            System.out.println("Loan Accepted");
//
//        }

        String intrest_rate_final = "0.0";
        if(Double.valueOf(int_rate)>=Double.valueOf(int_rate1)){
            intrest_rate_final = int_rate;
            if(Double.valueOf(intrest_rate_final) <= Double.valueOf(int_rate2)){
                intrest_rate_final = int_rate2;
            }
        }else{
            intrest_rate_final = int_rate1;
            if(Double.valueOf(intrest_rate_final) <= Double.valueOf(int_rate2)) {
                intrest_rate_final = int_rate2;
            }
        }



        model.addAttribute("intrest_rate_final", intrest_rate_final);
        return "result";
    }
}
