package hello;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.io.UnsupportedEncodingException;

@Controller
public class ClassificationController {

    @GetMapping("/home")
    public String greetingForm(Model model) {
        model.addAttribute("classification", new Classification());
        return "classification";
    }

    @PostMapping("/classification")
    public String greetingSubmit(@ModelAttribute Classification classification) throws IOException, ParseException {
        StringBuilder requestBody = new StringBuilder("{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"Unnamed: 0\",\"Amount_Requested\",\"Employment_Length\",\"Application_Date\",\"Zip_Code\",\"State\",\"Debt-To-Income-Ratio\",\"Policy_Code\",\"grant_loan\",\"Fico_Score\",\"State_Factorized\"],\"Values\": [[null,");
        StringBuilder values = new StringBuilder();
        values.append("\"").append(classification.getAmount_Requested()).append("\",\"")
                .append(classification.getEmployment_Length()).append("\",null,")
        .append("\"").append(classification.getZip_code()).append("\",null,\"").append(classification.getDebt_To_Income_Ratio())
                .append("\",null,null,\"")
                .append(classification.getFico_Score()).append("\",null");
        StringBuilder bodyLast = new StringBuilder("]]}},\"GlobalParameters\": {}}");
        requestBody.append(values.toString()).append(bodyLast);
        System.out.println(requestBody.toString());
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://ussouthcentral.services.azureml.net/workspaces/338268e0005b48f09e88c611a2835a2f/services/1d2759f0779a47a1bc8d54bd5336cb23/execute?api-version=2.0&details=true");
        post.setHeader("Authorization","Bearer TyOmzIiJI8urNwRn1fkq7wiZuWCTWYciY5FdyrVA4ftuY/UxW4hMHeEeZC0rBxIfqxxyIB+G7ovv98oUjzhLeg==");
        post.setHeader("Content-Type","application/json");
        StringEntity input = new StringEntity(requestBody.toString());
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
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject)parser.parse(output.toString());
        JSONObject jsonObj1 = (JSONObject)((JSONObject)((JSONObject)jsonObj.get("Results")).get("output1")).get("value");
        JSONArray jsonObj3 = (JSONArray)jsonObj1.get("Values");
        String label = (String)((JSONArray)jsonObj3.get(0)).get(2);
        System.out.println("=== Label ==="+label);
        if(label.equals("0")){
            System.out.println("Loan Rejected");
            return "rejected";
        }else{
            System.out.println("Loan Accepted");
            return "accepted";
        }


//        return "result";
    }

}
