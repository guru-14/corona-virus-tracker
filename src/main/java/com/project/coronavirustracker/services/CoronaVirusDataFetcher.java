package com.project.coronavirustracker.services;

import com.project.coronavirustracker.models.CoronaVirusStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CoronaVirusDataFetcher {

    private static String url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<CoronaVirusStats> stats = new ArrayList<>();

    public List<CoronaVirusStats> getStats() {
        return stats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 12 * * *")
    public void fetchData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        List<CoronaVirusStats> newStats = new ArrayList<>();
        for (CSVRecord record : records) {
            CoronaVirusStats coronaVirusStat = new CoronaVirusStats();
            coronaVirusStat.setState(record.get("Province/State").length() == 0 ? "----" : record.get("Province/State"));
            coronaVirusStat.setCountry(record.get("Country/Region"));
            int latestCases = (int)Float.parseFloat(record.get(record.size() - 1));
            int prevCases = (int)Float.parseFloat(record.get(record.size() - 2));
            coronaVirusStat.setNumberOfCases(latestCases);
            coronaVirusStat.setNumberOfNewCases(latestCases - prevCases);
            coronaVirusStat.setFormattedNumberOfCases(String.format("%,d", coronaVirusStat.getNumberOfCases()));
            coronaVirusStat.setFormattedNumberOfNewCases(String.format("%,d", coronaVirusStat.getNumberOfNewCases()));
            newStats.add(coronaVirusStat);
        }
        Collections.sort(newStats, new Comparator<CoronaVirusStats>(){
            @Override
            public int compare(CoronaVirusStats s1, CoronaVirusStats s2){
                return (-1 * (Integer.compare(s1.getNumberOfCases(), s2.getNumberOfCases())));
            }
        });
        this.stats = newStats;
    }
}